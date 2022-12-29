package com.example.quanly_banhang.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
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
import com.example.quanly_banhang.controller.model.Evenrbus.SuaXoaEvent;
import com.example.quanly_banhang.controller.model.SanPhamMoi;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.view.activity.Sanpham.Chi_Tiet_Activity;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class QuanLy_Adapter extends RecyclerView.Adapter<QuanLy_Adapter.MyViewHolder> {
    Context context;
    List<SanPhamMoi> arr;

    public QuanLy_Adapter(Context context, List<SanPhamMoi> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanly, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull QuanLy_Adapter.MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = arr.get(position);
        holder.item_quanly_tendonhang.setText(sanPhamMoi.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_quanly_giadonhang
                .setText("Giá: " + decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasanpham())) + "Đ");
        if (sanPhamMoi.getHinhanhsanpham().contains("http")) {
            Glide.with(context).load(sanPhamMoi.getHinhanhsanpham()).into(holder.item_img_quanly);
        } else {
            String hinh = Utils.BASE_URL + "images/" + sanPhamMoi.getHinhanhsanpham();
            Glide.with(context).load(hinh).into(holder.item_img_quanly);
        }
        holder.setItemClickListener((view, pos, isLongclick) -> {
            if (!isLongclick) {
                // click
                Intent intent = new Intent(context, Chi_Tiet_Activity.class);
                intent.putExtra("chitiet", sanPhamMoi);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                EventBus.getDefault().postSticky(new SuaXoaEvent(sanPhamMoi));
            }
        });

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnCreateContextMenuListener, View.OnLongClickListener {
        TextView item_quanly_tendonhang, item_quanly_giadonhang;
        ImageView item_img_quanly;

        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_quanly_tendonhang = itemView.findViewById(R.id.item_quanly_tendonhang);
            item_quanly_giadonhang = itemView.findViewById(R.id.item_quanly_giadonhang);
            item_img_quanly = itemView.findViewById(R.id.item_img_quanly);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view,
                ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, 0, getAdapterPosition(), "Cập nhật");
            contextMenu.add(0, 1, getAdapterPosition(), "Xóa");

        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return false;
        }
    }
}
