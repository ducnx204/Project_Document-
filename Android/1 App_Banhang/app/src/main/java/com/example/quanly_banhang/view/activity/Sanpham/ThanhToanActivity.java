package com.example.quanly_banhang.view.activity.Sanpham;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.quanly_banhang.controller.Interface.ApiPushNofication;
import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.model.NotiSendData;
import com.example.quanly_banhang.controller.model.Zalopay.CreateOrder;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.retrofit.RetrofitClientNoti;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityThanhToanBinding;
import com.example.quanly_banhang.view.activity.MainActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.momo.momo_partner.AppMoMoLib;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends Base_Activity {


    private ActivityThanhToanBinding binding;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    Api_BanHang api_banHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    long tongtien;
    int totalItem;
    int iddonhang;
//    Gson gson = new Gson();
    // momo
    private String amount = "10000"; /// set cứng đon vị tiền tệ
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Thanh toán đơn hàng";
    private String merchantCode = "MOMOTV6H20220802";
    private String merchantNameLabel = "NGUYEN XUAN DUC";
    private String description = "ShopPE";
    // mommo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThanhToanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // mo mo
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        // zalo pay
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // ZaloPay SDK Init
        ZaloPaySDK.init(2554, Environment.SANDBOX);
        // bind components with ids
//        BindView();
        // zalo pay
        // new public
        initControl();
        ActionToolBar();
        initview();
        countItem();
        // new public
    }

    void countItem() {
         totalItem = 0;
        for (int i = 0 ; i<Utils.mangmuahang.size();i++){
            totalItem =  totalItem+Utils.mangmuahang.get(i).getSoluong();
        }
    }

    // momo
    //Get token through MoMo app
    private void requestPayment(String iddonhang) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
//        if (edAmount.getText().toString() != null && edAmount.getText().toString().trim().length() != 0)
//            amount = edAmount.getText().toString().trim();

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("ShopPE", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("MOMOTV6H20220802", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", iddonhang); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", iddonhang); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());
        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);

    }

    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d( "thanhcong",data.getStringExtra("message"));
//                    tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response

                    // xu li luong chay momo khi thanh cong
                    compositeDisposable.add(api_banHang.updatenganhangmomo(iddonhang,token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()){
                                    Log.d("TAG TOKEN=>","idtoken");
//                                    pushNotitoUser();
                                    showMsg("Thanh toan thanh cong");
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },throwable -> {
                                Log.d("Err",throwable.getMessage());
                            }
                    ));

                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d( "TAG =>","Không thành công");
//                        tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
//                    tvMessage.setText("message: " + message);
                    Log.d( "TAG =>","Không thành công");

                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
//                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                    Log.d( "TAG =>","Không thành công");

                } else {
                    //TOKEN FAIL
//                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                    Log.d( "TAG =>","Không thành công");

                }
            } else {
//                tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                Log.d( "TAG =>","Không thành công");

            }
        } else {
//            tvMessage.setText("message: " + this.getString(R.string.not_receive_info_err));
            Log.d( "TAG =>","Không thành công");

        }
    }
    // momo

    void initview() {
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);
    }

    void initControl() {
        tongtien = getIntent().getLongExtra("tongtien",0);
        binding.txttongtienthanhtoan.setText(decimalFormat.format(tongtien));
        binding.txtEmail.setText(Utils.user_current.getEmail());
        binding.txtSdt.setText(Utils.user_current.getMobile());
        binding.btndathang.setOnClickListener(view -> {
            String str_diachi = binding.edtDiachigiaohang.getText().toString().trim();
            if (TextUtils.isEmpty(str_diachi)){
                showMsg("Không được để trống");
            }else {
                // post dử liệu
                String str_email = Utils.user_current.getEmail();
                String str_sdt = Utils.user_current.getMobile();
                int id  =Utils.user_current.getId();

                Log.d("Test",new Gson().toJson(Utils.mangmuahang)); // kiem tra thu hang

                compositeDisposable.add(api_banHang.createOder(str_email,str_sdt,String.valueOf(tongtien),id,str_diachi,totalItem,new Gson().toJson(Utils.mangmuahang))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel -> {
                                    //post thong bao
//                                        pushNotitoUser();
                                        showMsg("Thành Công");
                                        Utils.mangmuahang.clear();
                                        navigate(MainActivity.class);
                                        finish();
                                },
                                throwable -> {
                                    showMsg(throwable.getMessage());
                                }));
                     }
        });

        binding.btnMomo.setOnClickListener(view -> {
            String str_diachi = binding.edtDiachigiaohang.getText().toString().trim();
            if (TextUtils.isEmpty(str_diachi)){
                showMsg("Không được để trống");
            }else {
                // post dử liệu
                String str_email = Utils.user_current.getEmail();
                String str_sdt = Utils.user_current.getMobile();
                int id  =Utils.user_current.getId();
                Log.d("Test",new Gson().toJson(Utils.mangmuahang)); // kiem tra thu hang
                compositeDisposable.add(api_banHang.createOder(str_email,str_sdt,String.valueOf(tongtien),id,str_diachi,totalItem,new Gson().toJson(Utils.mangmuahang))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel -> {
//                                        pushNotitoUser();
                                        Utils.mangmuahang.clear();
                                        iddonhang = Integer.parseInt(messageModel.getIddonhang());
                                        requestPayment(messageModel.getIddonhang());
                                },
                                throwable -> {
                                    showMsg(throwable.getMessage());
                                }));
            }
        });


        // zalopay

        binding.btnZalopay.setOnClickListener(view -> {
            String str_diachi = binding.edtDiachigiaohang.getText().toString().trim();
            if (TextUtils.isEmpty(str_diachi)){
                showMsg("Không được để trống");
            }else {
                // post dử liệu
                String str_email = Utils.user_current.getEmail();
                String str_sdt = Utils.user_current.getMobile();
                int id  =Utils.user_current.getId();
                Log.d("Test",new Gson().toJson(Utils.mangmuahang)); // kiem tra thu hang
                compositeDisposable.add(api_banHang.createOder(str_email,str_sdt,String.valueOf(tongtien),id,str_diachi,totalItem,new Gson().toJson(Utils.mangmuahang))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel -> {
//                                        pushNotitoUser();
                                        Utils.mangmuahang.clear();
                                        iddonhang = Integer.parseInt(messageModel.getIddonhang());
                                        requestZalo();
                                },
                                throwable -> {
                                    showMsg(throwable.getMessage());
                                }));
            }
        });
    }

    private void requestZalo() {

        CreateOrder orderApi = new CreateOrder();

        try {
//            JSONObject data = orderApi.createOrder(txtAmount.getText().toString());
//            Log.d("Amount", txtAmount.getText().toString());
//            lblZpTransToken.setVisibility(View.VISIBLE);

//            Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();
            JSONObject data = orderApi.createOrder("10000"); //set cung gia tien zalo pay
            String code = data.getString("return_code");
            Log.d("Test",code);
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {

                        // xu li luong chay  khi zalo bay thanh toan  thanh cong ep cap nhat token momo zalopay
                compositeDisposable.add(api_banHang.updatenganhangmomo(iddonhang,token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messageModel -> {
                                    if (messageModel.isSuccess()){
                                        Log.d("TAG TOKEN=>","idtoken");
//                                        pushNotitoUser();
                                        showMsg("Thanh toan thanh cong");
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },throwable -> {
                                    Log.d("Err",throwable.getMessage());
                                }
                        ));
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {

                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                    }
                });
//                data.getString("zp_trans_token");
//                String token = txtToken.getText().toString();
//                lblZpTransToken.setText("zptranstoken");
//                txtToken.setText(data.getString("zp_trans_token"));
//                IsDone();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pushNotitoUser() {
        //gettoken
        compositeDisposable.add(api_banHang.gettoken(1)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                userModel -> {
                    if (userModel.isSuccess()){
                        for (int i = 0; i<userModel.getResult().size();i++){
                            Map<String,String > data = new HashMap<>();
                            data.put("title","Thông báo");
                            data.put("body","Bạn có đơn hàng mới");
                            NotiSendData notiSendData = new NotiSendData(userModel.getResult().get(i).getToken(),data);
                            ApiPushNofication apiPushNofication = RetrofitClientNoti.getInstance().create(ApiPushNofication.class);
                            compositeDisposable.add(apiPushNofication.sendNofitication(notiSendData)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            notiResponse -> {

                                            },throwable -> {
                                                Log.d("LogThanh toan",throwable.getMessage());
                                            }
                                    ));

                        }
                    }
                },
                throwable -> {
                    Log.d("LogThanh toan 2",throwable.getMessage());
                }
        ));

    }

    void ActionToolBar() {
            setSupportActionBar(binding.Toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            binding.Toolbar.setNavigationOnClickListener(view -> {
                finish();
            });
        }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    // zalopay
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}