package com.example.quanly_banhang.view.activity.Login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.databinding.ActivityManHinhChaoBinding;
import com.example.quanly_banhang.view.activity.MainActivity;

import io.paperdb.Paper;


public class ManHinhChao_Activity extends Base_Activity {
    private ActivityManHinhChaoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManHinhChaoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Paper.init(this);
        Thread thread= new Thread(){
            public void run(){
                try {
                    sleep(4000);
                }catch (Exception ex){
                    Log.d("TAGLogin","Lỗi");
                }finally {
//                     nếu đăng xuất user thì khi khởi chạy app sẻ chạy màn hình đăng nhập
                    if (Paper.book().read("user")==null){
                        navigate(DangNhap_Activity.class);
                    }else {
                        navigate(MainActivity.class);
                    }
                    finish();
                }
            }
        };
        thread.start();
    }
}