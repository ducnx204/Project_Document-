package com.example.quanly_banhang.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanly_banhang.R;
import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.adapter.LoaiSpAdapter;
import com.example.quanly_banhang.controller.adapter.SanphammoiAdapter;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.model.LoaiSp;
import com.example.quanly_banhang.controller.model.SanPhamMoi;
import com.example.quanly_banhang.controller.model.User;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityMainBinding;
import com.example.quanly_banhang.view.activity.Login.DangNhap_Activity;
import com.example.quanly_banhang.view.activity.Login.DoimatkhauActivity;
import com.example.quanly_banhang.view.activity.Messenger.UserActivity;
import com.example.quanly_banhang.view.activity.Post.QuanLy_Activity;
import com.example.quanly_banhang.view.activity.Sanpham.Dienthoai_Activity;
import com.example.quanly_banhang.view.activity.Sanpham.GiohangActivity;
import com.example.quanly_banhang.view.activity.Sanpham.Laptop_Activity;
import com.example.quanly_banhang.view.activity.Sanpham.TimkiemActivity;
import com.example.quanly_banhang.view.activity.Sanpham.xemdonhang_Activity;
import com.example.quanly_banhang.view.activity.Sanpham.xemdonhang_user;
import com.example.quanly_banhang.view.activity.Vienthong.Lienhe_Activity;
import com.example.quanly_banhang.view.activity.Vienthong.thongtin.Thongtin_Activity;
import com.example.quanly_banhang.view.activity.thongke.Thongke_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends Base_Activity {

    private ActivityMainBinding binding;
    private String url = "http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png";private String url1 = "http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png";private String url2 = "http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg";
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangloaisp;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api_BanHang api_banHang;
    // get san pham moi
    List<SanPhamMoi> mangSpMoi;
    SanphammoiAdapter sanphammoiAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Paper.init(this);
        if (Paper.book().read("user")!= null){
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }


        app();
        ActionBar();

        // get username va email tai khoan
        binding.txtTaikhoan.setText(Utils.user_current.getUsername());
        binding.txtEmail.setText(Utils.user_current.getEmail());
        // get username va email tai khoan
    try {
        // check xem co mang hay khong
        if (isConnected(this)){
            ActionViewFlipper();
            getEnvenClick();
            getLoaiSanPham();
            getSpMoi();
            getToken();
        }else{
            showMsg("Vui lòng kết nối INTERNET");
        }
    }catch (Exception e){
        Log.d("TAg",e.toString());
        e.printStackTrace();
    }

    }


    // xu ly cap nhat token ma hoa pass word
    void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(s -> {
                    if (!TextUtils.isEmpty(s)){
                        compositeDisposable.add(api_banHang.updatetoken(Utils.user_current.getId(),s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel -> {

                                },throwable -> {
                                    Log.d("Log",throwable.getMessage());
                                }
                        ));
                    }
                });

        /// get id nguoi nhan
        compositeDisposable.add(api_banHang.gettoken(1)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                userModel -> {
                    if (userModel.isSuccess()){
                        Utils.ID_RECEIVED = String.valueOf(userModel.getResult().get(0).getId());
                    }
                },throwable -> {

                }
        ));
    }
    void getSpMoi() {

            compositeDisposable.add(api_banHang.getSanpham_Moi()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            sanPhamMoiModel -> {
                                if (sanPhamMoiModel.isSuccess()){
                                    mangSpMoi = sanPhamMoiModel.getResult();
                                    sanphammoiAdapter= new SanphammoiAdapter(getApplicationContext(),mangSpMoi);
                                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2); // set layout 2
                                    binding.RecyclerView.setLayoutManager(layoutManager);
                                    binding.RecyclerView.setAdapter(sanphammoiAdapter);
                                }},
                            throwable -> { Log.d("TAG","Khong the ket noi sever"); }
                    ));

    }

    void app() {
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);

//        khoi tao list
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();

        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();

        }else {
            int totalItem = 0;
            for (int i = 0 ; i<Utils.manggiohang.size();i++){
                totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
            }
            binding.menusl.setText(String.valueOf(totalItem));
        }
//        khoi tao adapter

        binding.FrameLayoutGiohangMain.setOnClickListener(view -> {
                navigate(GiohangActivity.class);
        });
        binding.imgTimkiem.setOnClickListener(view -> {
             navigate(TimkiemActivity.class);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // set tổng giá trị đơn hàng của giỏ h
        int totalItem = 0;
        for (int i = 0 ; i<Utils.manggiohang.size();i++){
            totalItem =  totalItem+Utils.manggiohang.get(i).getSoluong();
        }
        binding.menusl.setText(String.valueOf(totalItem));
    }

    void getEnvenClick() {

        binding.ListView.setOnItemClickListener((adapterView, view, i, l) -> {


            // nếu Admin là bằng 1 sẻ hiện thi tất cả năng và ngược lại
            if (Utils.user_current.getStatus()==1){
                switch (i){
                    case 0: navigate(MainActivity.class);showMsg("Trang Chủ"); break;
                    case 1:
                        Intent dienthoai = new Intent(getApplicationContext(), Dienthoai_Activity.class);
                        dienthoai.putExtra("idsanpham",1);
                        startActivity(dienthoai);
                        break;
                    case 2:
                        Intent laptop = new Intent(getApplicationContext(), Laptop_Activity.class);
                        laptop.putExtra("idsanpham",2);
                        startActivity(laptop);
                        break;
                    // phân quyền user đơn hàng theo Admin
                    case 3:
//                        if (Utils.user_current.getId()){
                            navigate(xemdonhang_Activity.class);
//                        }
                        break;

                    case 4:
                        Utils.user_current.getId();
                        navigate(DoimatkhauActivity.class);
                        break;

                    case 5:
                        if (Utils.user_current.getStatus()==1){
                            navigate(QuanLy_Activity.class);
                        }else if (Utils.user_current.getStatus() == 0){
                            showMsg("Bạn chưa mở quyền Kinh doanh sản phẩm");
                        }
                        break;
                    case 6: navigate(UserActivity.class);showMsg("Nhắn tin");break;
                    case 7: navigate(Thongtin_Activity.class);showMsg("Thông Tin");break;
                    case 8: navigate(Lienhe_Activity.class);showMsg("Liên Hệ");break;
                    case 9 :
                        if (Utils.user_current.getStatus()==1){
                            navigate(Thongke_Activity.class);showMsg("Thống kê");break;
                        }else if (Utils.user_current.getStatus()==0){
                            showMsg("Bạn chưa mở quyền kinh doanh sản phẩm");
                        }
                        break;
                    case 10:
                        // xóa key user
                        Paper.book().delete("email");
                        FirebaseAuth.getInstance().signOut();
                        Utils.manggiohang.clear();
                        // dang xuat firebaseauth
                        navigate(DangNhap_Activity.class);
                        finish();
                        break;

                }

            }else if (Utils.user_current.getStatus()==0){
                switch (i){
                    case 0: navigate(MainActivity.class);showMsg("Trang Chủ"); break;
                    case 1:
                        Intent dienthoai = new Intent(getApplicationContext(), Dienthoai_Activity.class);
                        dienthoai.putExtra("idsanpham",1);
                        startActivity(dienthoai);
                        break;
                    case 2:
                        Intent laptop = new Intent(getApplicationContext(), Laptop_Activity.class);
                        laptop.putExtra("idsanpham",2);
                        startActivity(laptop);
                        break;
                    // phân quyền user đơn hàng theo user xem đơn hàng
                    case 3: navigate(xemdonhang_user.class);break;
                    case 4: Utils.user_current.getId();navigate(DoimatkhauActivity.class);break;
                    case 5: navigate(UserActivity.class);showMsg("Nhắn tin");break;
                    case 6: navigate(Thongtin_Activity.class);showMsg("Thông Tin");break;
                    case 7: navigate(Lienhe_Activity.class);showMsg("Liên Hệ");break;
                    case 8:
                        // xóa key user
                        Paper.book().delete("user");
                        FirebaseAuth.getInstance().signOut();
                        Utils.manggiohang.clear();
                        // dang xuat firebaseauth
                        navigate(DangNhap_Activity.class);
                        finish();
                        break;

                }

            }

        });
    }

    void getLoaiSanPham(){
        compositeDisposable.add(api_banHang.getLoaiSp().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
           loaiSpModel -> {
               if (loaiSpModel.isSuccess()){
                   mangloaisp =  loaiSpModel.getResult();
                    // phan quyen theo status
                   if (Utils.user_current.getStatus()==1){
                       mangloaisp.add(new LoaiSp("Đơn hàng","https://theme.hstatic.net/1000341850/1000438988/14/infor3_icon_2_1.png?v=798"));
                       mangloaisp.add(new LoaiSp("Đổi mật khẩu","https://cdn-icons-png.flaticon.com/512/6195/6195700.png"));
                       mangloaisp.add(new LoaiSp("Quản lý sản phẩm","https://cdn-icons-png.flaticon.com/512/1802/1802979.png"));
                       mangloaisp.add(new LoaiSp("Nhắn tin","https://img.icons8.com/color/2x/facebook-messenger.png"));
                       mangloaisp.add(new LoaiSp("Thông tin","https://ngochieu.name.vn/img/info.png"));
                       mangloaisp.add(new LoaiSp("Liên hệ","https://ngochieu.name.vn/img/contact.png"));
                       mangloaisp.add(new LoaiSp("Thông kê","https://cdn.pixabay.com/photo/2018/03/28/11/17/analytics-3268935_1280.png"));
                       mangloaisp.add(new LoaiSp("Đăng Xuất","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTc22dwCspKLts7nNbsI_j4a2-1hHPTa4wPw&usqp=CAU"));

                   }else if (Utils.user_current.getStatus()==0){
                       mangloaisp.add(new LoaiSp("Đơn hàng","https://theme.hstatic.net/1000341850/1000438988/14/infor3_icon_2_1.png?v=798"));
                       mangloaisp.add(new LoaiSp("Đổi mật khẩu","https://cdn-icons-png.flaticon.com/512/6195/6195700.png"));
                       mangloaisp.add(new LoaiSp("Nhắn tin","https://img.icons8.com/color/2x/facebook-messenger.png"));
                       mangloaisp.add(new LoaiSp("Thông tin","https://ngochieu.name.vn/img/info.png"));
                       mangloaisp.add(new LoaiSp("Liên hệ","https://ngochieu.name.vn/img/contact.png"));
                       mangloaisp.add(new LoaiSp("Đăng Xuất","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTc22dwCspKLts7nNbsI_j4a2-1hHPTa4wPw&usqp=CAU"));
                   }

                   loaiSpAdapter = new LoaiSpAdapter(mangloaisp,getApplicationContext());
                   binding.ListView.setAdapter(loaiSpAdapter);
               }
           }
        ));

    }



    void ActionBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            binding.DrawerLayout.openDrawer(GravityCompat.START);
        });
    }

    void ActionViewFlipper() {
        Animation slide_in,slide_out;
        List<String>manquangcao = new ArrayList<>();
        manquangcao.add(url);manquangcao.add(url1);manquangcao.add(url2);

        for (int i = 0; i<manquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(manquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.ViewFlipper.addView(imageView);
        }
        binding.ViewFlipper.setFlipInterval(3000);
        binding.ViewFlipper.setAutoStart(true);
        slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        binding.ViewFlipper.setInAnimation(slide_in);
        binding.ViewFlipper.setOutAnimation(slide_out);
    }


    boolean isConnected (Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi  = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // them quyen manifest
        NetworkInfo mobile  = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi != null && wifi.isConnected() || (mobile != null && mobile.isConnected())){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}