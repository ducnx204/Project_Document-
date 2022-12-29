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

import java.text.DecimalFormat;
import java.util.List;

public class SanphammoiAdapter extends RecyclerView.Adapter<SanphammoiAdapter.MyViewHolder> {
        Context context;
        List<SanPhamMoi> arr;

    public SanphammoiAdapter(Context context, List<SanPhamMoi> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham_moi,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull SanphammoiAdapter.MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = arr.get(position);
        holder.txtten.setText(sanPhamMoi.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.txtgia.setText(sanPhamMoi.getGiasanpham()+"Vnd");
          holder.txtgia.setText("Giá: " +decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasanpham()))+"VND");

          if (sanPhamMoi.getHinhanhsanpham().contains("http")){
              Glide.with(context).load(sanPhamMoi.getHinhanhsanpham()).into(holder.imageviewhinhanh);
          }else {
              String hinh = Utils.BASE_URL+"images/"+sanPhamMoi.getHinhanhsanpham();
              Glide.with(context).load(hinh).into(holder.imageviewhinhanh);
          }
//          Glide.with(context).load(sanPhamMoi.getHinhanhsanpham()).into(holder.imageviewhinhanh);
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


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtgia,txtten;
        ImageView imageviewhinhanh;

        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgia = itemView.findViewById(R.id.itemsp_giamoi);
            txtten = itemView.findViewById(R.id.itemsp_tenmoi);
            imageviewhinhanh = itemView.findViewById(R.id.itemsp_imgmoi);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }
}
