package com.example.quanly_banhang.view.activity.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityDoimatkhauBinding;
import com.example.quanly_banhang.view.activity.MainActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DoimatkhauActivity extends Base_Activity {
    private ActivityDoimatkhauBinding binding;
    // khai báo biến
    Api_BanHang api_banHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoimatkhauBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionToolBar();
        // tao public
        inttView();
        initControll();
    }

    void initControll() {
        binding.btnDoimatkhau.setOnClickListener(view -> {
            suasanpham();
        });
    }
    void suasanpham() {
        String str_matkhaumoi = binding.edtMatkhaumoi.getText().toString().trim();
        String str_re_password = binding.edtNhaplaimatkhaumoi.getText().toString().trim();

        if (TextUtils.isEmpty(str_matkhaumoi)||TextUtils.isEmpty(str_re_password)){
            showMsg("Vui lòng nhập đầy đủ thông tin");
        }else {
            if (str_matkhaumoi.equals(str_re_password)) {
                compositeDisposable.add(api_banHang.doimatkhau(str_matkhaumoi,Utils.user_current.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel ->{
                                    if (userModel.isSuccess()){
                                        navigate(MainActivity.class);
                                        finish();
                                        showMsg("Cập nhật thành công");

                                    }else {
                                        showMsg("Cập nhật thất bại ");
                                    }
                                },throwable -> {
                                    showMsg("Lỗi");
                                }
                        ));
            }else {
                showMsg("Mật khẩu chưa khớp");
            }

        }
    }





    void inttView() {
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);
    }
    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}