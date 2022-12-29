package com.example.quanly_banhang.view.activity.Messenger;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_banhang.controller.adapter.Chat_adapter;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.model.ChatMessage;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityTinnhanBinding;
// file base mess
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
// file base mess


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Tinnhan_Activity extends Base_Activity {
    private ActivityTinnhanBinding binding;
    // user
    int iduser;
    String iduser_str;
    // user
    FirebaseFirestore db;
    Chat_adapter adapter;
    List<ChatMessage> list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTinnhanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // user
        iduser = getIntent().getIntExtra("id",0); // id nguoi nhan
        iduser_str = String.valueOf(iduser);
        // tao public class
        intView();
        initControl();
        listenMess();
        ActionToolBar();
        // post du lieu chat cua user
        insertUser();

    }


    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }
    void initControl() {
        binding.imgsend.setOnClickListener(view -> {
            sendMessToFire();
        });
    }

    void insertUser() {
        HashMap<String,Object> user =  new HashMap<>();
        user.put("id",Utils.user_current.getId());
        user.put("username",Utils.user_current.getUsername());
        db.collection("users").document(String.valueOf(Utils.user_current.getId())).set(user);
    }

    void sendMessToFire() {
        String  str_mess = binding.edtinputtex.getText().toString().trim();
        if (TextUtils.isEmpty(str_mess)){
                
        }else {
            HashMap<String,Object> message = new HashMap<>();
            // id nguoi nhan
            message.put(Utils.SENDID,String.valueOf(Utils.user_current.getId()));
//            message.put(Utils.RECEIVEDID,Utils.ID_RECEIVED);
            message.put(Utils.RECEIVEDID,iduser_str); // nguoi nhan cung phai la kieu chuoi string
            message.put(Utils.MESS,str_mess);
            message.put(Utils.DATETIME,new Date());
            db.collection(Utils.PATH_CHAT).add(message);
            binding.edtinputtex.setText("");
        }
    }
    private void listenMess(){
        // so sanh truy van
        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.SENDID,String.valueOf(Utils.user_current.getId())) // nguoi gui
//                .whereEqualTo(Utils.RECEIVEDID,Utils.ID_RECEIVED)
                .whereEqualTo(Utils.RECEIVEDID,iduser_str)
                .addSnapshotListener(eventListener);

        db.collection(Utils.PATH_CHAT)
//                .whereEqualTo(Utils.SENDID,Utils.ID_RECEIVED) // nguoi nhan
                .whereEqualTo(Utils.SENDID,iduser_str) // nguoi nhan
                .whereEqualTo(Utils.RECEIVEDID,String.valueOf(Utils.user_current.getId()))
                .addSnapshotListener(eventListener);


    }


    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null){
            return;
        }
        if (value != null){
            // kiem tra list co gia tri hay khong
            int count =  list.size();

            for (DocumentChange documentChange: value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.sendid = documentChange.getDocument().getString(Utils.SENDID);
                    chatMessage.receivedid = documentChange.getDocument().getString(Utils.RECEIVEDID);
                    chatMessage.mess = documentChange.getDocument().getString(Utils.MESS);
                    chatMessage.dateObj = documentChange.getDocument().getDate(Utils.DATETIME);
                    chatMessage.datetime = format_date(documentChange.getDocument().getDate(Utils.DATETIME));
                    list.add(chatMessage);
                }
            }
            Collections.sort(list,(obj1,obj2)-> obj1.dateObj.compareTo(obj2.dateObj));
            if (count == 0){
                adapter.notifyDataSetChanged();
            }else {
                adapter.notifyItemRangeInserted(list.size(),list.size());
                binding.RecyclerView.smoothScrollToPosition(list.size()-1);
            }
        }
    };

    // tao bien date time
    private String format_date(Date date){
        // tao bien gio
        return new SimpleDateFormat("MMMM dd, yyy - hh:mm a", Locale.getDefault()).format(date);
    }
    void intView() {
        list = new ArrayList<>();
        db= FirebaseFirestore.getInstance();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.RecyclerView.setLayoutManager(layoutManager);
        binding.RecyclerView.setHasFixedSize(true);
        adapter = new Chat_adapter(getApplicationContext(),list,String.valueOf(Utils.user_current.getId()));
        binding.RecyclerView.setAdapter(adapter);
    }
}