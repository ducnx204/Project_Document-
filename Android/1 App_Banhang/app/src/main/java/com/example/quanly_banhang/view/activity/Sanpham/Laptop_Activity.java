package com.example.quanly_banhang.view.activity.Sanpham;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.adapter.DienThoaiAdapter;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.model.SanPhamMoi;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityLaptopBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Laptop_Activity extends Base_Activity {
    private ActivityLaptopBinding binding;
    Api_BanHang api_banHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    // khai bao model  API
    int page = 1;
    int idsanpham;
    // khai bao Adapter
    DienThoaiAdapter adapter;
    List<SanPhamMoi> sanPhamMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;
    // khai bao Adapter

    //khai bao model API
    // khai bao bien
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            binding = ActivityLaptopBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);
        idsanpham = getIntent().getIntExtra("idsanpham",2);
        // khai bao 1 puclic
        ActionToolBar();
        RC_Dienthoai();
        getData(page);
        addEventLoad();
        // khai bao 1  public
    }
    void addEventLoad() {
        binding.RecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition()== sanPhamMoiList.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    void loadMore() {
        handler.post(() -> {
            // add null
            sanPhamMoiList.add(null);
            adapter.notifyItemInserted(sanPhamMoiList.size()-1);
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // remover null
                sanPhamMoiList.remove(sanPhamMoiList.size()-1);
                adapter.notifyItemRemoved(sanPhamMoiList.size());
                page = page+1;
                getData(page);
                adapter.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);
    }

    void getData(int page) {
        compositeDisposable.add(api_banHang.getSanpham_dienthoai(page,idsanpham)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {

                            if (sanPhamMoiModel.isSuccess()){
                                if (adapter == null){
                                    sanPhamMoiList = sanPhamMoiModel.getResult();
                                    adapter = new DienThoaiAdapter(getApplicationContext(),sanPhamMoiList);
                                    binding.RecyclerView.setAdapter(adapter);
                                }else {
                                    int vitri = sanPhamMoiList.size()-1;
                                    int soluongadd = sanPhamMoiModel.getResult().size();
                                    for (int i = 0 ; i<soluongadd; i++){
                                        sanPhamMoiList.add(sanPhamMoiModel.getResult().get(i));
                                    }
                                    adapter.notifyItemRangeInserted(vitri,soluongadd);
                                }
                            }else {
                                showMsg("Hết dử liệu");
                            }
                        }, throwable -> {
                            showMsg("Không kết nối được sever");

                        }
                ));
    }

    void RC_Dienthoai() {
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.RecyclerView.setLayoutManager(linearLayoutManager);
        binding.RecyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }

    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}