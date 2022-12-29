package com.example.quanly_banhang.controller.utils;

import com.example.quanly_banhang.controller.model.GioHang;
import com.example.quanly_banhang.controller.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String id = "172.16.28.96";
    public static final String BASE_URL= "http://"+id+"/banhang/";
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
    public static List<GioHang> manggiohang;


    // chat sever firebase
    public static  String ID_RECEIVED;
    // Tao key chat
    public static final String SENDID = "idsend";
    public static final String RECEIVEDID = "idreceived";
    public static final String MESS = "message";
    public static final String DATETIME = "datetime";
    public static final String PATH_CHAT = "chat";
}
