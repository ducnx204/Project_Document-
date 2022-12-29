package com.example.quanly_banhang.view.activity.Sanpham;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanly_banhang.R;
import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.adapter.Sanphamlienquan_Adapter;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.model.GioHang;
import com.example.quanly_banhang.controller.model.SanPhamMoi;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityChiTietBinding;
import com.example.quanly_banhang.view.activity.MainActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Chi_Tiet_Activity extends Base_Activity {
    private ActivityChiTietBinding binding;
    SanPhamMoi sanPhamMoi;
    // get Utils get modo API
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api_BanHang api_banHang;
    // get san lien quan dien thoai
    List<SanPhamMoi> mangSpMoi; // tạo 1 list vể mảng sản phẩm mới hay còn gọi là arr
    Sanphamlienquan_Adapter sanphamlienquan_adapter; // Khởi tạo adapter
    // get Utils get modo API
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChiTietBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // khởi tạo client hay còn gọi là ánh xạ client
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);
        mangSpMoi = new ArrayList<>(); // khởi tạo arrayList arr như 1 vòng lặp for
        getsanpham_dienthoai();
        //khai bao public
        ActionToolBar();
        itemview();
        initData();
        initControl();

    }

    void getsanpham_dienthoai() {
                compositeDisposable.add(api_banHang.getsanphamlienquandienthoai() // get san pham lien quan theo id san pham
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                sanPhamMoiModel -> {
                                    if (sanPhamMoiModel.isSuccess()){
                                        mangSpMoi = sanPhamMoiModel.getResult();
                                        sanphamlienquan_adapter= new Sanphamlienquan_Adapter(getApplicationContext(),mangSpMoi);
                                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
                                        binding.RecyclerviewSanphamlienquan.setLayoutManager(layoutManager);
                                        binding.RecyclerviewSanphamlienquan.setAdapter(sanphamlienquan_adapter);
                                    }
                                },
                                throwable -> {}
                        ));

    }
    void itemview() {

        /// chuyen layout khi clicl
        binding.FrameLayoutGiohang.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
            startActivity(intent);
            finish(); //  ket thuc khi click vaof framelayoutgiohang
        });


        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i = 0 ; i<Utils.manggiohang.size();i++){
                totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
            }

            binding.menusl.setText(String.valueOf(totalItem));
        }
    }

    void initControl() {
        binding.btnthemvaogiohang.setOnClickListener(view -> {
            themGioHang();
        });
    }

    void themGioHang() {
        if (Utils.manggiohang.size()>0){
            boolean flag  = false;
            int soluong = Integer.parseInt(binding.spinner.getSelectedItem().toString());
            for (int i = 0 ; i< Utils.manggiohang.size(); i++){
                if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    // tong so lương
                    long gia = Long.parseLong(sanPhamMoi.getGiasanpham()) * Utils.manggiohang.get(i).getSoluong();
                    // post đơn giá
                    Integer dongia = Integer.parseInt(sanPhamMoi.getGiasanpham());

                    Utils.manggiohang.get(i).setGiasp(gia);
                    Utils.manggiohang.get(i).setDongia(dongia);
                    flag = true;
                }
            }
            if (flag == false){
                // tong so lương
                long gia = Long.parseLong(sanPhamMoi.getGiasanpham()) * soluong;
                // post đơn giá
                Integer dongia = Integer.parseInt(sanPhamMoi.getGiasanpham());

                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getTensanpham());
                gioHang.setHinhsp(sanPhamMoi.getHinhanhsanpham());
                gioHang.setDongia(dongia);
                Utils.manggiohang.add(gioHang);

            }

        }else {
            int soluong = Integer.parseInt(binding.spinner.getSelectedItem().toString());
                long gia = Long.parseLong(sanPhamMoi.getGiasanpham()) * soluong;
            Integer dongia = Integer.parseInt(sanPhamMoi.getGiasanpham());
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setDongia(dongia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getTensanpham());
            gioHang.setHinhsp(sanPhamMoi.getHinhanhsanpham());

            Utils.manggiohang.add(gioHang);
        }
        int totalItem = 0;
        for (int i = 0 ; i<Utils.manggiohang.size();i++){
            totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
        }
        binding.menusl.setText(String.valueOf(totalItem));
    }

    void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        binding.txttensp.setText(sanPhamMoi.getTensanpham());
        binding.txtmota.setText(sanPhamMoi.getMotasanpham());
//        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanhsanpham()).into(binding.imgChitiet);
        if (sanPhamMoi.getHinhanhsanpham().contains("http")){
            Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanhsanpham()).into(binding.imgChitiet);
        }else {
            String hinh = Utils.BASE_URL+"images/"+sanPhamMoi.getHinhanhsanpham();
            Glide.with(getApplicationContext()).load(hinh).into(binding.imgChitiet);

        }
        // arr số lượng sản phâm  mảng từ 1 -> 10 và ngược lại
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
         binding.txtgiasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasanpham()))+"Đ");
         Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspnier = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,so);
        binding.spinner.setAdapter(adapterspnier);


    }

    // nhấn img actiontoobal quay lại trang main và kết thúc trang chi tiết sản activity
    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            navigate(MainActivity.class);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i = 0 ; i<Utils.manggiohang.size();i++){
                totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            binding.menusl.setText(String.valueOf(totalItem));
        }

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}