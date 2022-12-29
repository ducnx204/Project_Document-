package com.example.quanly_banhang.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_banhang.R;
import com.example.quanly_banhang.controller.Interface.ItemClickListener;
import com.example.quanly_banhang.controller.model.User;
import com.example.quanly_banhang.view.activity.Messenger.Tinnhan_Activity;

import java.util.List;

public class User_adapter extends RecyclerView.Adapter<User_adapter.MyviewHolder> {
    Context context;
    List<User>userList;

    public User_adapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User_adapter.MyviewHolder holder, int position) {
        User user = userList.get(position);
        holder.txt_iduser.setText(user.getId()+"");

        holder.txt_username.setText(user.getUsername());
        // bắt sự kiện click
        holder.setItmItemClickListener((view, pos, isLongclick) -> {
            if (!isLongclick){
                Intent intent = new Intent(context, Tinnhan_Activity.class);
                intent.putExtra("id",user.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_iduser,txt_username;
        ItemClickListener itmItemClickListener;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            txt_iduser=itemView.findViewById(R.id.txt_iduser);
            txt_username=itemView.findViewById(R.id.txt_username);
            itemView.setOnClickListener(this);
        }

        public void setItmItemClickListener(ItemClickListener itmItemClickListener) {
            this.itmItemClickListener = itmItemClickListener;
        }

        @Override
        public void onClick(View view) {
            itmItemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }
}
