package com.example.quanly_banhang.view.activity.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityQuenmatkhauBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuenmatkhauActivity extends Base_Activity {
    private ActivityQuenmatkhauBinding binding;
    Api_BanHang api_banHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.quanly_banhang.databinding.ActivityQuenmatkhauBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // tao public
        inttView();
        initControll();

    }

    void initControll() {

        binding.btnQuenmatkhau.setOnClickListener(view -> {

            String str_email = binding.edtQuenmatkhauEmail.getText().toString().trim();
            if (TextUtils.isEmpty(str_email)) {
                showMsg("Bạn chưa nhập email");
            } else {
                binding.progressbar.setVisibility(View.VISIBLE);
                compositeDisposable.add(api_banHang.quenmatkhau(str_email)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()) {
                                        showMsg(userModel.getMessage());
                                        navigate(DangNhap_Activity.class);
                                        finish();
                                    } else {
                                        showMsg(userModel.getMessage());
                                    }
                                    binding.progressbar.setVisibility(View.INVISIBLE);
                                },
                                throwable -> {
                                    showMsg(throwable.getMessage());
                                    binding.progressbar.setVisibility(View.INVISIBLE);

                                }));
            }

        });
    }

    void inttView() {
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}