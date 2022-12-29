package com.example.quanly_banhang.view.activity.Sanpham;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quanly_banhang.R;
import com.example.quanly_banhang.controller.Interface.ApiPushNofication;
import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.adapter.DonHangAdapter;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.model.DonHang;
import com.example.quanly_banhang.controller.model.Evenrbus.DonhangEvenrbus;
import com.example.quanly_banhang.controller.model.NotiSendData;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.retrofit.RetrofitClientNoti;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityXemdonhangBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class xemdonhang_Activity extends Base_Activity {
    private ActivityXemdonhangBinding binding;
    Api_BanHang api_banHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    DonHang donHang;
    AlertDialog dialog;
    int tinhtrang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityXemdonhangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initview();
        ActionToolBar();
        getOder();

    }

    void getOder() {
        compositeDisposable.add(api_banHang.xemdonhang(0) // người dùng có thể xem lịch sử đơn hàng đã mua
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donhangModel -> {
                            DonHangAdapter adapter = new DonHangAdapter(getApplicationContext(),
                                    donhangModel.getResult());
                            binding.RecyclerView.setAdapter(adapter);
                        },
                        throwable -> {
                        }));
    }

    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    void initview() {
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.RecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    // admin
    void showCustumDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_donhang, null);
        Spinner spinner = view.findViewById(R.id.spinner_dialog);
        AppCompatButton btn_dongy = view.findViewById(R.id.dongy_dialog);
        List<String> list = new ArrayList<>();
        list.add("Đơn hàng đang được xử lí");
        list.add("Đơn hàng đã chấp nhận");
        list.add("Đơn hàng đã giao đến đơn vị vấn chuyển");
        list.add("Thành công");
        list.add("Đơn hàng đã hủy");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
        // vị trí cần chọn
        spinner.setSelection(donHang.getTrangthai());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tinhtrang = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_dongy.setOnClickListener(view1 -> {
            capNhatdonhang();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

    }

    // admin
    void capNhatdonhang() {
        compositeDisposable.add(api_banHang.capnhattrangthai(donHang.getId(), tinhtrang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            getOder();
                            // pushNotitoUser();
                            // đống dialog

                            dialog.dismiss();
                        },
                        throwable -> {

                        }));
    }

    private void pushNotitoUser() {
        // gettoken
        compositeDisposable.add(api_banHang.gettokenadmin(0, donHang.getIduser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()) {
                                for (int i = 0; i < userModel.getResult().size(); i++) {
                                    Map<String, String> data = new HashMap<>();
                                    data.put("title", "Thông báo");
                                    data.put("body", trangthaiDon(tinhtrang));
                                    NotiSendData notiSendData = new NotiSendData(
                                            userModel.getResult().get(i).getToken(), data);
                                    ApiPushNofication apiPushNofication = RetrofitClientNoti.getInstance()
                                            .create(ApiPushNofication.class);
                                    compositeDisposable.add(apiPushNofication.sendNofitication(notiSendData)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    notiResponse -> {

                                                    }, throwable -> {
                                                        Log.d("Log tokenadmin ==>", throwable.getMessage());
                                                    }));

                                }
                            }
                        },
                        throwable -> {
                            Log.d("LogThanh toan 2", throwable.getMessage());
                        }));

    }

    // admin
    private String trangthaiDon(int status) {
        String result = "";
        switch (status) {
            case 0:
                result = "Đơn hàng đang được xử lí";
                break;
            case 1:
                result = "Đơn hàng đã chấp nhận";
                break;
            case 2:
                result = "Đơn hàng đã giao đến đơn vị vấn chuyển";
                break;
            case 3:
                result = "Thành công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
        }

        return result;
    }

    // admin
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void evenDonhang(DonhangEvenrbus event) {
        if (event != null) {
            donHang = event.getDonHang();
            showCustumDialog();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}