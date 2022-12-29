package com.example.quanly_banhang.view.activity.Post;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.example.quanly_banhang.controller.Interface.Api_BanHang;
import com.example.quanly_banhang.controller.base.Base_Activity;
import com.example.quanly_banhang.controller.model.MessageModel;
import com.example.quanly_banhang.controller.model.SanPhamMoi;
import com.example.quanly_banhang.controller.retrofit.RetrofitClient;
import com.example.quanly_banhang.controller.utils.Utils;
import com.example.quanly_banhang.databinding.ActivityThemSpBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.net.Uri.*;

public class ThemSP_Activity extends Base_Activity {
    private ActivityThemSpBinding binding;
    Api_BanHang api_banHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    // khai báo biến
    int loai = 0;
    // hinh anh
    String mediaPath;
    SanPhamMoi sanPhamsua;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemSpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initData();
        initView();
        ActionToolBar();
        Intent intent = getIntent();
        sanPhamsua = (SanPhamMoi) intent.getSerializableExtra("Cập nhật");
        if (sanPhamsua == null) {
            // them moi
            flag = false;
        } else {
            // cap nhat
            flag = true;
            binding.Toolbar.setTitle("Cập nhật sản phẩm");
            binding.btnThemsanpham.setText("Cập nhật");
            // show data
            binding.edtTensanpham.setText(sanPhamsua.getTensanpham());
            binding.edtGiasanpham.setText(sanPhamsua.getGiasanpham() + "");
            binding.edtHinhanhsanpham.setText(sanPhamsua.getHinhanhsanpham());
            binding.edtMotasanpham.setText(sanPhamsua.getMotasanpham());
            // show spiner
            binding.spinner.setSelection(sanPhamsua.getIdsanpham());
        }

    }

    void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("Vui lòng Chọn Loại");
        stringList.add("Điện Thoại");
        stringList.add("LapTop");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                stringList);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loai = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.btnThemsanpham.setOnClickListener(view -> {
            if (flag == false) {
                themsanpham();
            } else {
                suasanpham();

            }
        });

        binding.imgCamera.setOnClickListener(view -> {
            ImagePicker.with(ThemSP_Activity.this)
                    .crop() // Crop image(Optional), Check Customization for more option
                    .compress(1024) // Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080) // Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = data.getDataString();
        uploadMultipleFiles();
        Log.d("TAG2", "onActivityResult" + mediaPath);
    }

    void suasanpham() {
        String str_ten = binding.edtTensanpham.getText().toString().trim();
        String str_gia = binding.edtGiasanpham.getText().toString().trim();
        String str_hinhanh = binding.edtHinhanhsanpham.getText().toString().trim();
        String str_mota = binding.edtMotasanpham.getText().toString().trim();

        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_hinhanh)
                || TextUtils.isEmpty(str_mota) || loai == 0) {
            showMsg("Vui lòng nhập đầy đủ thông tin");
        } else {
            compositeDisposable
                    .add(api_banHang.capnhatsanpham(str_ten, str_gia, str_hinhanh, str_mota, loai, sanPhamsua.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        if (messageModel.isSuccess()) {
                                            navigate(QuanLy_Activity.class);
                                            finish();
                                            showMsg("Cập nhật thành công");

                                        } else {
                                            showMsg("Cập nhật thất bại ");
                                        }
                                    }, throwable -> {
                                        showMsg("Lỗi");
                                    }));
        }
    }

    void themsanpham() {
        String str_ten = binding.edtTensanpham.getText().toString().trim();
        String str_gia = binding.edtGiasanpham.getText().toString().trim();
        String str_hinhanh = binding.edtHinhanhsanpham.getText().toString().trim();
        String str_mota = binding.edtMotasanpham.getText().toString().trim();

        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_hinhanh)
                || TextUtils.isEmpty(str_mota) || loai == 0) {
            showMsg("Vui lòng nhập đầy đủ thông tin");
        } else {
            compositeDisposable.add(api_banHang.themsanpham(str_ten, str_gia, str_hinhanh, str_mota, (loai))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()) {
                                    navigate(QuanLy_Activity.class);
                                    finish();
                                    showMsg("Thêm thành công");

                                } else {
                                    showMsg("Thêm thất bại ");
                                }
                            }, throwable -> {
                                showMsg("Lỗi");
                            }));

        }
    }

    private String getPath(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }

        return result;
    }

    // Uploading Image/Video
    void uploadMultipleFiles() {
        try {
            File file;
            Uri uri;
            uri = parse(mediaPath);
            file = new File(getPath(uri));
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
            Call<MessageModel> call = api_banHang.uploadFile(fileToUpload1);
            call.enqueue(new Callback<MessageModel>() {
                @Override
                public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                    MessageModel serverResponse = response.body();
                    if (serverResponse != null) {
                        if (serverResponse.isSuccess()) {
                            showMsg("Thành Công");
                            binding.edtHinhanhsanpham.setText(serverResponse.getName());
                        } else {
                            showMsg("Thất bại Lỗi err");
                        }
                    } else {
                        Log.v("Response", serverResponse.toString());
                    }
                }

                @Override
                public void onFailure(Call<MessageModel> call, Throwable t) {
                    Log.d("TAG", t.getMessage());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "lỗi camera");
        }
    }

    void ActionToolBar() {
        setSupportActionBar(binding.Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    void initView() {
        api_banHang = RetrofitClient.getInstance(Utils.BASE_URL).create(Api_BanHang.class);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}