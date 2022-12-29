package com.example.quanly_banhang.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_banhang.R;
import com.example.quanly_banhang.controller.model.ChatMessage;

import java.util.List;

public class Chat_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ChatMessage> chatMessageList;
    private String sendid;
    private static final int TYPE_SEND = 1;
    private static final int TYPE_RECEIVE = 2;

    public Chat_adapter(Context context, List<ChatMessage> chatMessageList, String sendid) {
        this.context = context;
        this.chatMessageList = chatMessageList;
        this.sendid = sendid;
    }

    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_SEND){
            // nếu trạng thái người dùng là 0 và ngược lại là trang thái chủ sell là 1
            view = LayoutInflater.from(context).inflate(R.layout.item_send_tinnhan,parent,false);
            return new SendMessViewHolder(view);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_received,parent,false);
            return new ReceivedViewHolder(view);
        }
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position)==TYPE_SEND){// Truyền tại vị trí người dùng gửi
            ((SendMessViewHolder) holder).txtmessend.setText(chatMessageList.get(position).mess);
            ((SendMessViewHolder) holder).txttimesend.setText(chatMessageList.get(position).datetime);
        }else {
            ((ReceivedViewHolder) holder).txtmessend_received.setText(chatMessageList.get(position).mess);
            ((ReceivedViewHolder) holder).txttime_received.setText(chatMessageList.get(position).datetime);
        }

    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessageList.get(position).sendid.equals(sendid)){
            return TYPE_SEND;
        }else {
            return TYPE_RECEIVE;
        }
//        return super.getItemViewType(position);
    }

    class SendMessViewHolder extends RecyclerView.ViewHolder{

        TextView txtmessend,txttimesend;
        public SendMessViewHolder(@NonNull View itemView) {
            super(itemView);

            txtmessend = itemView.findViewById(R.id.txtmessend);
            txttimesend = itemView.findViewById(R.id.txttimesend);

        }
    }

    class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView txtmessend_received,txttime_received;
        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);

            txtmessend_received = itemView.findViewById(R.id.txtmessend_received);
            txttime_received = itemView.findViewById(R.id.txttime_received);

        }
    }
}
