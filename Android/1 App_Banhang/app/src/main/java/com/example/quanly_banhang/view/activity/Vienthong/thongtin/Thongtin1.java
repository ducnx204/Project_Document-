package com.example.quanly_banhang.view.activity.Vienthong.thongtin;

import android.os.Bundle;
import android.view.View;

import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.databinding.ActivityThongtin1Binding;

public class Thongtin1 extends Base_Activity {
    private ActivityThongtin1Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongtin1Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionToolBar();
        initview();
    }

    void initview() {
        binding.ButtonLottieNext.setOnClickListener(view -> {
            navigate(ThongTin2.class);
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