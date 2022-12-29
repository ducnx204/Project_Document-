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

import java.util.List;

public class Spinner_LoaiSpAdapter extends BaseAdapter {

    List<LoaiSp> array;
    Context context;

    public Spinner_LoaiSpAdapter(List<LoaiSp> array, Context context) {
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
        TextView spinner_tenloaisanpham;
        ImageView spinner_imageview_item;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_loaisanpham,null);
            viewHolder.spinner_tenloaisanpham = view.findViewById(R.id.spinner_tenloaisanpham);

            viewHolder.spinner_imageview_item = view.findViewById(R.id.spinner_imageview_item);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.spinner_tenloaisanpham.setText(array.get(i).getTenloaisanpham());
        Glide.with(context).load(array.get(i).getHinhanhloaisanpham()).into(viewHolder.spinner_imageview_item);
        return view;
    }
}
