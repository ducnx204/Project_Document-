package com.example.quanly_banhang.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class DienThoaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context context;
        List<SanPhamMoi>array;

    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    public DienThoaiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }



    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienthoai,parent,false);
            return new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            SanPhamMoi sanPham = array.get(position);
            myViewHolder.tensp.setText(sanPham.getTensanpham().trim());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPham.getGiasanpham()))+"VND");
            myViewHolder.mota.setText(sanPham.getMotasanpham());
//            Glide.with(context).load(sanPham.getHinhanhsanpham()).into(myViewHolder.imghinhanh);
            if (sanPham.getHinhanhsanpham().contains("http")){
                Glide.with(context).load(sanPham.getHinhanhsanpham()).into(((MyViewHolder) holder).imghinhanh);
            }else {
                String hinh = Utils.BASE_URL+"images/"+sanPham.getHinhanhsanpham();
                Glide.with(context).load(hinh).into(((MyViewHolder) holder).imghinhanh);
            }
            myViewHolder.setItemClickListener((view, pos, isLongclick) -> {
                if (!isLongclick){
                    // click
                    Intent intent = new Intent(context, Chi_Tiet_Activity.class);
                    intent.putExtra("chitiet",sanPham);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }else {
            LoadingViewHolder loadingViewHolder  = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

        @Override
        public int getItemViewType(int position) {
            return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
        }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class  LoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tensp,giasp,mota;
        ImageView imghinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhanh = itemView.findViewById(R.id.item_dienthoai_imageview);
            tensp = itemView.findViewById(R.id.item_dienthoai_txtten);
            giasp = itemView.findViewById(R.id.item_dienthoai_txtgia);
            mota = itemView.findViewById(R.id.item_dienthoai_txtmota);
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
