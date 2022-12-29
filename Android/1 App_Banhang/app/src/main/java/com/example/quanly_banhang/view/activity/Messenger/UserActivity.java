package com.example.quanly_banhang.view.activity.Messenger;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_banhang.R;
import com.example.quanly_banhang.controller.adapter.User_adapter;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.model.User;
import com.example.quanly_banhang.databinding.ActivityUserBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

//import com.google.firebase.firestore.DocumentChange;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
public class UserActivity extends Base_Activity {
    private ActivityUserBinding binding;
    User_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // tao public 
        ActionToolBar();
        initview();
        getUserFromFire();
    }

    void getUserFromFire() {
        // file base mess

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                List<User>  userList = new ArrayList<>();
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    User user = new User();
                                    user.setId(documentSnapshot.getLong("id").intValue());
                                    user.setUsername(documentSnapshot.getString("username"));
                                    userList.add(user);
                                }
                                if (userList.size()>-0){
                                    adapter = new User_adapter(getApplicationContext(),userList);
                                    binding.RecyclerView.setAdapter(adapter);
                                }
                            }
                });

// file base mess
    }

    void initview() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.RecyclerView.setLayoutManager(layoutManager);
        binding.RecyclerView.setHasFixedSize(true);

    }

    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }
}