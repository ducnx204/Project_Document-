package com.example.quanly_banhang.view.activity.Vienthong.thongtin;

import android.os.Bundle;
import android.view.View;

import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.databinding.ActivityThongtinBinding;

public class Thongtin_Activity extends Base_Activity {
    private ActivityThongtinBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongtinBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionToolBar();
        initview();

    }

    void initview() {
        binding.txt1.setText(" + ShopsPE là một thương hiệu giao dịch mua bản các loại sản phẩm theo hình thức online hoặc tại cửa hàng nếu như các quý khách hàng muốn mua những mặt hàng online mà không biết làm sao để thanh toán qua mạng ngại đường xa vậy thì đừng ngần ngại đến với ShopsPE chúng tôi");
        binding.txt2.setText(" + Quý khánh hàng không biết làm sao để thanh toán online vậy thì mời quý khách xem tiếp");
        binding.ButtonLottieNext.setOnClickListener(view -> {
            navigate(Thongtin1.class);
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