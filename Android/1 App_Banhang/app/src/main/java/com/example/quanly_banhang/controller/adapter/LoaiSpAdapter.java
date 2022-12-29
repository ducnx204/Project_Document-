package com.example.quanly_banhang.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanly_banhang.R;
import com.example.quanly_banhang.controller.model.LoaiSp;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoaiSpAdapter extends BaseAdapter {

    List<LoaiSp> array;
    Context context;

    public LoaiSpAdapter(List<LoaiSp> array, Context context) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();

    }

    @Override
    public Object getItem(int i) {
        return array.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txt_item_tenloaisanpham;
        ImageView txt_item_image_loaisanpham;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_loaisanpham,null);
            viewHolder.txt_item_tenloaisanpham = view.findViewById(R.id.item_tenloaisanpham);
            viewHolder.txt_item_image_loaisanpham = view.findViewById(R.id.item_image_loaisanpham);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.txt_item_tenloaisanpham.setText(array.get(i).getTenloaisanpham());
        Glide.with(context).load(array.get(i).getHinhanhloaisanpham()).into(viewHolder.txt_item_image_loaisanpham);

//        LoaiSp loaiSp = (LoaiSp) getItem(i);
//        viewHolder.txt_item_tenloaisanpham.setText(loaiSp.getTenloaisanpham());
//        Picasso.get()
//                .load(loaiSp.getHinhanhloaisanpham())
//                .placeholder(R.drawable.ic_launcher_foreground)
//                .error(R.drawable.ic_baseline_perm_media_24)
//                .fit()
//                .into(viewHolder.txt_item_image_loaisanpham);
        return view;
    }
}
