package com.example.quanly_banhang.view.activity.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityDangKiBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DangKi_Activity extends Base_Activity {
    private ActivityDangKiBinding binding;
    FirebaseAuth firebaseAuth;Api_BanHang api_banHang;CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangKiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // tao public
        inttView();
        initControll();
    }
     void initControll() {
        binding.btndangki.setOnClickListener(view -> {
            dangki();
        });
    }

    void dangki() {
        String str_hoten = binding.edtHoten.getText().toString().trim();
        String str_email = binding.edtEmail.getText().toString().trim();
        String str_password = binding.edtPassword.getText().toString().trim();
        String str_re_password = binding.edtRePassword.getText().toString().trim();
        String str_mobile = binding.edtMobile.getText().toString().trim();


        if (TextUtils.isEmpty(str_hoten)) { showMsg("Không được để trống họ và tên");
        } else if (TextUtils.isEmpty(str_email)) { showMsg("Không được để trống email");
        } else if (TextUtils.isEmpty(str_password)) { showMsg("Không được để trống mật khẩu"); }
        else if (TextUtils.isEmpty(str_re_password)) { showMsg("Không được để trống nhập lại mật khẩu");
        } else if (TextUtils.isEmpty(str_mobile)) { showMsg("Không được để trống số điện thoại");
        } else{// so sanh chuoi
            if (str_password.equals(str_re_password)) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(str_email, str_password)
                        .addOnCompleteListener(DangKi_Activity.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if (user != null) {
                                    postData(str_hoten, str_email, str_password, str_mobile, user.getUid());
                                }
                            } else {
                                if (str_password.length()>6){ showMsg("mật khẩu phải lớn hơn 6 kí tự"); }
                                Log.d("TAG","loi ki tu");
                            }
                        });
            } else {
                showMsg("Mật khẩu chưa khớp");
            } } }
    private void postData(String str_hoten,String str_email,String str_password,String str_mobile,String uid){
        compositeDisposable.add(api_banHang.dangki(str_hoten,str_email,str_password,str_mobile,uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()){
                                // con tro
                                Utils.user_current.setEmail(str_email);
                                Utils.user_current.setPassword(str_password);
                                // con tro
                                navigate(DangNhap_Activity.class);
                                showMsg("Đăng ký thành công");finish();
                            } else { showMsg("Đăng ký thất bại"); } }, throwable -> { showMsg("Lỗi"); })); }
    void inttView() {
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }


}