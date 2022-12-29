package com.example.quanly_banhang.view.activity.Sanpham;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_banhang.controller.adapter.GioHangAdapter;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.model.Evenrbus.TinhTongEvent;
import com.example.quanly_banhang.controller.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class    GiohangActivity extends Base_Activity {
    //khai bao bien
    private com.example.quanly_banhang.databinding.ActivityGiohangBinding binding;
    GioHangAdapter adapter;
    long tongtiensp;
    // khai bao
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.quanly_banhang.databinding.ActivityGiohangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // new public
        initControl();
        ActionToolBar();
        tinhtongtien();
        // new public

    }

    void tinhtongtien() {
        tongtiensp = 0;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        for (int i = 0; i<Utils.mangmuahang.size();i++){
            tongtiensp = tongtiensp + (Utils.mangmuahang.get(i).getGiasp()*Utils.mangmuahang.get(i).getSoluong());
        }
        binding.txttongtien.setText(decimalFormat.format(tongtiensp));
    }


    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            Utils.mangmuahang.clear();
            finish();
        });
    }

    void initControl() {

        binding.btnmuahang.setOnClickListener(view -> {
            int i = 0;
            if (tongtiensp == i){
                    showMsg("không có đơn hàng cần thanh toán");
            }else {
                Intent intent = new Intent(getApplicationContext(),ThanhToanActivity.class);
                intent.putExtra("tongtien",tongtiensp);
                Utils.manggiohang.clear();
                startActivity(intent);
            }
        });

        binding.RecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.RecyclerView.setLayoutManager(layoutManager);

        if (Utils.manggiohang.size() == 0){
                binding.txtgiohangtrong.setVisibility(View.VISIBLE);
        }else {
            adapter = new GioHangAdapter(getApplicationContext(),Utils.manggiohang);
            binding.RecyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTinhtien(TinhTongEvent event){
        if (event != null){
            tinhtongtien();
        }
    }
}