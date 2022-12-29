package com.example.quanly_banhang.view.activity.thongke;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityThongkeBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Thongke_Activity extends Base_Activity {
    private ActivityThongkeBinding binding;
    Api_BanHang api_banHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongkeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ActionToolBar();
        initview();
        // get bieu đồ hình tròn
        getdataChart();
    }

    void getdataChart() {
        List<PieEntry> listdata =  new ArrayList<>();
        compositeDisposable.add(api_banHang.getthongkeChar()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                thongkeModel -> {
                    if (thongkeModel.isSuccess()){
                        for (int i = 0; i<thongkeModel.getResult().size();i++){
                            String tensp = thongkeModel.getResult().get(i).getTensanpham();
                            int tong = thongkeModel.getResult().get(i).getTong();
                            listdata.add(new PieEntry(tong,tensp));
                        }
                        PieDataSet pieDataSet = new PieDataSet(listdata,"Thống kê ");
                        PieData data = new PieData();
                        data.setDataSet(pieDataSet);
                        data.setValueTextSize(12f);
                        data.setValueFormatter(new PercentFormatter(binding.PieChart));
                        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                        binding.PieChart.setData(data);
                        binding.PieChart.animateXY(2000,2000);
                        binding.PieChart.setUsePercentValues(true);
                        binding.PieChart.getDescription().setEnabled(false);
                        binding.PieChart.invalidate();

                    }
                },
                throwable -> {
                    Log.d("Log",throwable.getMessage());
                }
        ));
    }

    private void initview() {
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);

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