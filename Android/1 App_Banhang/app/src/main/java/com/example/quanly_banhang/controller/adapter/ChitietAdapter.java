package com.example.quanly_banhang.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanly_banhang.R;
import com.example.quanly_banhang.controller.model.Item;
import com.example.quanly_banhang.controller.utils.Utils;

import java.util.List;

public class ChitietAdapter extends RecyclerView.Adapter<ChitietAdapter.MyViewHolder> {
    Context context;
    List<Item>itemList;


    public ChitietAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ChitietAdapter.MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtten.setText("Tên sản phẩm: "+item.getTensanpham()+"");
        holder.txtsoluong.setText("Số lượng:"+item.getSoluong()+"");
        if (item.getHinhanhsanpham().contains("http")){
            Glide.with(context).load(item.getHinhanhsanpham()).into(holder.imagechitiet);
        }else {
            String hinh = Utils.BASE_URL+"images/"+item.getHinhanhsanpham();
            Glide.with(context).load(hinh).into(holder.imagechitiet);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imagechitiet;
        TextView txtten,txtsoluong;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagechitiet = itemView.findViewById(R.id.item_chitiet_imageview);
            txtten = itemView.findViewById(R.id.item_chitiet_tensanpham);
            txtsoluong = itemView.findViewById(R.id.item_chitiet_soluong);

        }
    }
}
