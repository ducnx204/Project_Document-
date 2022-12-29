package com.example.quanly_banhang.view.activity.Vienthong.thongtin;

import android.os.Bundle;
import android.view.View;

import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.databinding.ActivityThongTin3Binding;
import com.example.quanly_banhang.view.activity.MainActivity;

public class ThongTin3 extends Base_Activity {
    private ActivityThongTin3Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongTin3Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionToolBar();    }
    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            navigate(MainActivity.class);
            finish();
        });
    }
}