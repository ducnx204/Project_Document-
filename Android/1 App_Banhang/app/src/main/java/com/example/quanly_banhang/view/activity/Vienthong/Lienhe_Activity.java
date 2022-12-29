package com.example.quanly_banhang.view.activity.Vienthong;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanly_banhang.databinding.ActivityLienheBinding;

public class Lienhe_Activity extends AppCompatActivity {
    private ActivityLienheBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLienheBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        intview();
        ActionToolBar();
    }

    private void intview() {
        binding.txt1.setText("Bạn đang gặp khó khăn cần được hỗ trợ hay cần đóng góp ý kiến cho bộ phận chăm sóc khách hàng? Hãy liên hệ với ShopsPE qua bộ phận chăm sóc khách hàng. Chúng tôi sẽ giải quyết vấn đề của bạn nhanh nhất có thể!");
        binding.txt2.setText("Tầng 5, 320 Đường 2/9, P. Hòa Cường Bắc, Q. Hải Châu\n" +
                "Tel: 02363582582");
    }
    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }
}