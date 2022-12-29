package com.example.quanly_banhang.controller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_banhang.R;
import com.example.quanly_banhang.controller.Interface.ItemClickListener;
import com.example.quanly_banhang.controller.model.DonHang;
import com.example.quanly_banhang.controller.model.Evenrbus.DonhangEvenrbus;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyviewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang>listdonhang;

    public DonHangAdapter(Context context, List<DonHang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.MyviewHolder holder, int position) {
        DonHang donHang = listdonhang.get(position);
        holder.txtdonhang.setText("Mã Đơn hàng: "+donHang.getId());
        holder.txttinhtrang.setText(trangthaiDon(donHang.getTrangthai()));
//   thay doi mau trang thai don
        final DonHang item = listdonhang.get(position);
        if (item.getTrangthai() == 0){
            holder.txttinhtrang.setTextColor(Color.BLACK);
        }else if (item.getTrangthai() == 1){
            holder.txttinhtrang.setTextColor(Color.BLACK);
        }else if (item.getTrangthai() == 2) {
            holder.txttinhtrang.setTextColor(Color.BLACK);
        }else if (item.getTrangthai() == 3) {
            holder.txttinhtrang.setTextColor(Color.BLUE);
        }else if (item.getTrangthai() == 4) {
            holder.txttinhtrang.setTextColor(Color.RED);
        }
//   thay doi mau trang thai don

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerview_chitiet.getContext(),
                LinearLayoutManager.VERTICAL,false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());

        // adapter chi tiét
        ChitietAdapter chitietAdapter = new ChitietAdapter(context,donHang.getItem());
        holder.recyclerview_chitiet.setLayoutManager(layoutManager);
        holder.recyclerview_chitiet.setAdapter(chitietAdapter);
        holder.recyclerview_chitiet.setRecycledViewPool(viewPool);
        // băt sự kiện click
        // post su kien qua xem don hang
        holder.setListener((view, pos, isLongclick) -> {
            if (isLongclick){
                EventBus.getDefault().postSticky(new DonhangEvenrbus(donHang));
            }
        });
    }

    private String trangthaiDon(int status){
        String result= "";
        switch (status){
            case 0:
                result = "Đơn hàng đang được xử lí";
                break;
            case 1:
                result = "Đơn hàng đã chấp nhận";
                break;
            case 2:
                result = "Đơn hàng đã giao đến đơn vị vấn chuyển";
                break;
            case 3:
                result = "Thành công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtdonhang,txttinhtrang;
        RecyclerView recyclerview_chitiet;
        ItemClickListener listener;
        public MyviewHolder(@NonNull  View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            txttinhtrang = itemView.findViewById(R.id.txttinhtrang);
            recyclerview_chitiet = itemView.findViewById(R.id.recyclerview_chitiet);
            itemView.setOnLongClickListener(this);
        }

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onClick(view,getAdapterPosition(),true);
            return false;
        }
    }

}
