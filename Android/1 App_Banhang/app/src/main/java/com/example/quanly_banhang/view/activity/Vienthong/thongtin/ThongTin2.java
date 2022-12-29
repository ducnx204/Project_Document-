package com.example.quanly_banhang.view.activity.Vienthong.thongtin;

import android.os.Bundle;
import android.view.View;

import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.databinding.ActivityThongTin2Binding;


public class ThongTin2 extends Base_Activity {
    private ActivityThongTin2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongTin2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionToolBar();
        initview();
    }

    void initview() {
        binding.ButtonLottieNext.setOnClickListener(view -> {
            navigate(ThongTin3.class);
            finish();
        });
    }

    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

}