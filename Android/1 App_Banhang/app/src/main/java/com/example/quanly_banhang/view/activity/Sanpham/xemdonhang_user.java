package com.example.quanly_banhang.view.activity.Sanpham;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.adapter.DonHangAdapter;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityXemdonhangUserBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class xemdonhang_user extends Base_Activity {
    private ActivityXemdonhangUserBinding binding;
    Api_BanHang api_banHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityXemdonhangUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initview();
        ActionToolBar();
        getOder();
    }

    void getOder() {
        compositeDisposable.add(api_banHang.xemdonhang(Utils.user_current.getId()) //  người dùng có thể xem lịch sử đơn hàng đã mua
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donhangModel -> {
                            DonHangAdapter adapter = new DonHangAdapter(getApplicationContext(),donhangModel.getResult());
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
        LinearLayoutManager layoutManager    = new LinearLayoutManager(this);
        binding.RecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}