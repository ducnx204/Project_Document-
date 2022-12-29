package com.example.quanly_banhang.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanly_banhang.R;
import com.example.quanly_banhang.controller.Interface.ItemClickListener;
import com.example.quanly_banhang.controller.model.SanPhamMoi;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.view.activity.Sanpham.Chi_Tiet_Activity;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

public class Sanphamlienquan_Adapter extends RecyclerView.Adapter<Sanphamlienquan_Adapter.MyviewHolder> {

    Context context;
    List<SanPhamMoi> arr;

    public Sanphamlienquan_Adapter(Context context, List<SanPhamMoi> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @NotNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanphamlienquan,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyviewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = arr.get(position);
//        holder.txttensanpham_lienquan.setText(sanPhamMoi.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.txtgia.setText(sanPhamMoi.getGiasanpham()+"Vnd");
        holder.txt_giasanphamlienquan.setText("Giá: " +decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasanpham()))+"VND");

        if (sanPhamMoi.getHinhanhsanpham().contains("http")){
            Glide.with(context).load(sanPhamMoi.getHinhanhsanpham()).into(holder.image_sanphamlienquan);
        }else {
            String hinh = Utils.BASE_URL+"images/"+sanPhamMoi.getHinhanhsanpham();
            Glide.with(context).load(hinh).into(holder.image_sanphamlienquan);
        }
        holder.setItemClickListener((view, pos, isLongclick) -> {
            if (!isLongclick){
                // click
                Intent intent = new Intent(context, Chi_Tiet_Activity.class);
                intent.putExtra("chitiet",sanPhamMoi);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image_sanphamlienquan;
        TextView txt_giasanphamlienquan;
        private ItemClickListener itemClickListener;

        public MyviewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image_sanphamlienquan = itemView.findViewById(R.id.image_sanphamlienquan);
            txt_giasanphamlienquan = itemView.findViewById(R.id.txt_giasanphamlienquan);
            itemView.setOnClickListener(this);
        }
    // xu ly item click
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getPosition(),false);
        }
    }
}
