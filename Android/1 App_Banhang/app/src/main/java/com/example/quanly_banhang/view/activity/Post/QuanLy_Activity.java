package com.example.quanly_banhang.view.activity.Post;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.adapter.QuanLy_Adapter;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.model.Evenrbus.SuaXoaEvent;
import com.example.quanly_banhang.controller.model.SanPhamMoi;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityQuanLyBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLy_Activity extends Base_Activity {
    private ActivityQuanLyBinding binding;
    Api_BanHang api_banHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    LinearLayoutManager linearLayoutManager;
    // get san pham moi
    List<SanPhamMoi> quanlysanpham;
    QuanLy_Adapter quanLydonhang_adapter;
    SanPhamMoi sanPhamMoi_suaxoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuanLyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionToolBar();
        initView();
        getSpMoi();
        binding.linerlayoutThem.setOnClickListener(view1 -> {
            navigate(ThemSP_Activity.class);
            finish();
        });
    }

    void initView() {
        // get api
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.RecyclerView.setHasFixedSize(true);
        binding.RecyclerView.setLayoutManager(layoutManager);
    }

    void getSpMoi() {
        quanlysanpham = new ArrayList<>();

        compositeDisposable.add(api_banHang.getSanpham_Moi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()){
                                quanlysanpham = sanPhamMoiModel.getResult();
                                quanLydonhang_adapter= new QuanLy_Adapter(getApplicationContext(),quanlysanpham);
                                linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                                binding.RecyclerView.setLayoutManager(linearLayoutManager);
                                binding.RecyclerView.setAdapter(quanLydonhang_adapter);

                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được sever"+throwable.getMessage(),Toast.LENGTH_LONG).show();
                        }
                ));
    }





    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Cập nhật")){
            capnhatsanpham();
        }else if (item.getTitle().equals("Xóa")){
            xoasanpham();
        }
        return super.onContextItemSelected(item);
    }

    void capnhatsanpham() {
        Intent intent = new Intent(getApplicationContext(),ThemSP_Activity.class);
        intent.putExtra("Cập nhật",sanPhamMoi_suaxoa);
        startActivity(intent);
        finish();
    }

    void xoasanpham() {
        compositeDisposable.add(api_banHang.xoaSanPham(sanPhamMoi_suaxoa.getId())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                messageModel -> {
                        if (messageModel.isSuccess()){
//                            showMsg(messageModel.getMessage());
                            showMsg("Xóa thành công");
                            getSpMoi();
                        }else {
//                            showMsg(messageModel.getMessage());
                            showMsg("Xóa thất bại");
                        }
                },
                throwable -> {
//                    Log.d("log",throwable.getMessage());
                    showMsg("Lỗi");
                }
        ));
    }


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void evenSuaXoa(SuaXoaEvent event){
        if (event != null){
            sanPhamMoi_suaxoa = event.getSanPhamMoi();
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