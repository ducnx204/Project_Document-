package com.example.quanly_banhang.view.activity.Login;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityDangNhapBinding;
import com.example.quanly_banhang.view.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DangNhap_Activity extends Base_Activity {
    // khai báo xml & biến
    private ActivityDangNhapBinding binding;
    Api_BanHang api_banHang;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    boolean isLogin = false;
    // khai báo xml & biến

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangNhapBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // tao public
        inttView();
        initControll();
    }

    //   Xử lý sự kiện click
    void initControll() {
       binding.txtdangki.setOnClickListener(view -> {
           navigate(DangKi_Activity.class);
            return;
       });
       binding.btndangnhap.setOnClickListener(view -> {
        dangnhap();
       });

       binding.txtQuenmatkhau.setOnClickListener(view -> {
           navigate(QuenmatkhauActivity.class);
           return;
       });
    }
    //   Xử lý sự kiện click

    void dangnhap() {
        String str_email = binding.edtLoginEmail.getText().toString().trim();
        String str_password = binding.edtLoginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(str_email)){
            showMsg("Bạn chưa nhập email");
        }else if (TextUtils.isEmpty(str_password)){
            showMsg("Bạn chưa nhập mật khẩu");
        }else {
            // lưu mật khẩu
            Paper.book().write("email",str_email);
            Paper.book().write("password",str_password);
            // lưu mật khẩus

            if (user != null){
                // user da  co dang nhap firebase
                dangnhap1(str_email,str_password);
            }else {
                // user da dang xuat firebase
                firebaseAuth.signInWithEmailAndPassword(str_email,str_password)
                        .addOnCompleteListener(DangNhap_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    dangnhap1(str_email,str_password);
                                }
                            }
                        });

            }
            dangnhap1(str_email,str_password);
        }

    }
    void inttView() {
        firebaseAuth = FirebaseAuth.getInstance();
        user  = firebaseAuth.getCurrentUser();
        //pager
        Paper.init(this);
        // đọc dử liệu
        if (Paper.book().read("email") != null && Paper.book().read("password") != null){
            binding.edtLoginEmail.setText(Paper.book().read("email") );
            binding.edtLoginPassword.setText(Paper.book().read("password") );

            // dang nhap  1 lan
            if (Paper.book().read("isLogin")!= null){
                boolean flag = Paper.book().read("isLogin");
                if (flag){
                    new Handler().postDelayed(() -> {
//                            dangnhap1(Paper.book().read("email"),Paper.book().read("password"));
                    },5);
                }
            }
        }
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);
    }

    void dangnhap1(String email,String password) {

        compositeDisposable.add(api_banHang.dangnhap(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {

                            if (userModel.isSuccess()){
                                isLogin= true;
                                Paper.book().write("isLogin",isLogin);
                                Utils.user_current = userModel.getResult().get(0);
                                // lưu lại thông tin
                                Paper.book().write("user",userModel.getResult().get(0));
                                navigate(MainActivity.class);
                                finish();
                                showMsg(userModel.getMessage());

                            }
                            else {
                                showMsg(userModel.getMessage());
                            }
                        },
                        throwable -> {
                            showMsg(throwable.getMessage());
                        }
                ));
    }



    // xử lý code và thực hiện
    // hiện ẩn password bằng checkbox

    public  void checkbox(View view){
        binding.checkbox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                binding.edtLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                Log.i("TAG","trạng thái ẩn");
                binding.checkbox.setChecked(true);

            }else {
                binding.edtLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.checkbox.setChecked(false);
                Log.i("TAG","trạng thái hiện");


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getEmail() != null && Utils.user_current.getPassword() != null ){
            binding.edtLoginEmail.setText(Utils.user_current.getEmail());
            binding.edtLoginPassword.setText(Utils.user_current.getPassword());
        }
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}