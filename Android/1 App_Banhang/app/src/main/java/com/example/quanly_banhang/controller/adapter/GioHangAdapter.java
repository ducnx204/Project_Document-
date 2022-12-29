package com.example.quanly_banhang.controller.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanly_banhang.R;
import com.example.quanly_banhang.controller.Interface.ItemImageClickListenner;
import com.example.quanly_banhang.controller.model.Evenrbus.TinhTongEvent;
import com.example.quanly_banhang.controller.model.GioHang;
import com.example.quanly_banhang.controller.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {

    Context context;
    List<GioHang> gioHangList;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    long gia;
    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  GioHangAdapter.MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTensp());

        holder.item_giohang_checkbox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                    Utils.mangmuahang.add(gioHang);
                    EventBus.getDefault().postSticky(new TinhTongEvent());
            }else {
                for (int i = 0; i<Utils.mangmuahang.size();i++){
                    if (Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()){
                        holder.item_giohang_checkbox.setChecked(b);
                        Utils.mangmuahang.remove(i);
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }
                }
            }
        });

        // get đơn giá
//        holder.item_giohang_gia.setText("Đơn giá: "+decimalFormat.format((gioHang.getGiasp()))+"Đ");  // giá sản phẩm 1

        holder.item_giohang_gia.setText("Đơn Giá: "+decimalFormat.format(gioHang.getDongia()));
        // get đơn giá
        holder.item_giohang_soluong.setText(gioHang.getSoluong()+ "");

        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_imageview);

        if (gioHang.getHinhsp().contains("http")){
            Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_imageview);
        }else {
            String hinh = Utils.BASE_URL+"images/"+gioHang.getHinhsp();
            Glide.with(context).load(hinh).into(holder.item_giohang_imageview);
        }
//
        gia = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));  //giá sản phẩm 2
        // tang giam gia tri so luong san pham
        holder.setListenner((view, pos, giatri) -> {
            Log.d("TAG","onImageClick:"+pos +"..." +giatri);

            if (giatri == 1){
                    if (gioHangList.get(pos).getSoluong()>1){
                        int soluongmoi = gioHangList.get(pos).getSoluong() -1;
                        gioHangList.get(pos).setSoluong(soluongmoi);

                        holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + " ");
                        gia = gioHangList.get(pos).getSoluong()*gioHangList.get(pos).getGiasp();

                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia)); // giá sản phẩm 2
                        EventBus.getDefault().postSticky(new TinhTongEvent());


                    }else if (gioHangList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn Có muốn xóa sản phẩm khỏi giỏ hàng");
                        builder.setPositiveButton("Đồng ý", (dialogInterface, i) -> {
                            // set lại giá nếu đã xóa 1 sản phẩm
                            Utils.mangmuahang.clear();
                            // xóa 1 sản phẩm trong giỏ hàng
                            Utils.manggiohang.remove(pos);
                            notifyDataSetChanged();
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        });
                        builder.setNegativeButton("Hủy bỏ", (dialogInterface, i) -> dialogInterface.dismiss());
                        builder.show();

                    }
            }else if (giatri == 2){
                if (gioHangList.get(pos).getSoluong()<10){
                    int soluongmoi = gioHangList.get(pos).getSoluong() +1;
                    gioHangList.get(pos).setSoluong(soluongmoi);

                    holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + " ");
                    gia = gioHangList.get(pos).getSoluong()*gioHangList.get(pos).getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia)); // giá sản phẩm 2
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView item_giohang_imageview,item_giohang_imageview_tru,item_giohang_imageview_cong;
            TextView item_giohang_tensp,item_giohang_gia,item_giohang_giasp2,item_giohang_soluong;
            CheckBox item_giohang_checkbox;
            ItemImageClickListenner listenner;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_imageview    = itemView.findViewById(R.id.item_giohang_imageview);
            item_giohang_tensp    = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia    = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_giasp2    = itemView.findViewById(R.id.item_giohang_giasp2);
            item_giohang_soluong    = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_imageview_tru    = itemView.findViewById(R.id.item_giohang_imageview_tru);
            item_giohang_imageview_cong    = itemView.findViewById(R.id.item_giohang_imageview_cong);
            item_giohang_checkbox    = itemView.findViewById(R.id.item_giohang_checkbox);

            //event click
            item_giohang_imageview_tru.setOnClickListener(this);
            item_giohang_imageview_cong.setOnClickListener(this);

        }

        public void setListenner(ItemImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == item_giohang_imageview_tru){
                listenner.onImageClick(view,getAdapterPosition(),1);
                // gia tri 1 tru

            }else  if (view == item_giohang_imageview_cong){
                // gia tri 2 cong
                listenner.onImageClick(view,getAdapterPosition(),2);
            }
        }
    }
}
