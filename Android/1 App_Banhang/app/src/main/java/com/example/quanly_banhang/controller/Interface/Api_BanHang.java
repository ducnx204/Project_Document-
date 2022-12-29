package com.example.quanly_banhang.controller.Interface;

import com.example.quanly_banhang.controller.model.DonhangModel;
import com.example.quanly_banhang.controller.model.LoaiSpModel;
import com.example.quanly_banhang.controller.model.MessageModel;
import com.example.quanly_banhang.controller.model.SanPhamMoiModel;
import com.example.quanly_banhang.controller.model.ThongkeModel;
import com.example.quanly_banhang.controller.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api_BanHang {

        // navigation heard

        // tao san pham
        String id_loai_sanpham = "getloaisp1.php";
        String id_sanpham_moi = "getspmoinhat.php";
        String id_sanpham_dienthoai = "chitiet.php";
        String id_donhang = "donhang.php";
        String id_xemdonhang = "xemdonhang.php";
        String id_timkiem = "timkiem.php";
        String id_gettoken = "gettoken.php";
        String id_sanphamlienquan_dienthoai = "sanphamlienquan_dienthoai.php";

        // login
        String id_dangki = "dangki.php";
        String id_dangnhap = "dangnhap.php";
        String id_doimatkhau = "doimatkhau.php";
        String id_quenmatkhau = "quenmatkhau.php";
        String id_updatetoken = "updatetoke.php";

        // quản lý sản phẩm
        String id_themsanpham = "insertsp.php";
        String id_capnhatsanpham = "capnhatsanpham.php";
        String id_xoasanpham = "xoasanpham.php";
        String id_hinhanh = "upload.php";
        String id_capnhattrangthai = "capnhatrangthai.php";

        // thong ke san pham
        String id_thongke = "thongke.php";
        // zalo pay and momo
        String id_momo = "updatemomo.php";

        @GET(id_loai_sanpham)
        Observable<LoaiSpModel> getLoaiSp();

        @POST(id_sanphamlienquan_dienthoai)
        // @FormUrlEncoded
        Observable<SanPhamMoiModel> getsanphamlienquandienthoai();

        // @Field("idsanpham") int idsanpham

        @GET(id_thongke)
        Observable<ThongkeModel> getthongkeChar();

        @GET(id_sanpham_moi)
        Observable<SanPhamMoiModel> getSanpham_Moi();

        // POST data
        @POST(id_sanpham_dienthoai)
        @FormUrlEncoded
        Observable<SanPhamMoiModel> getSanpham_dienthoai(
                        @Field("page") int page,
                        @Field("idsanpham") int idsanpham);

        // dang ki
        @POST(id_dangki)
        @FormUrlEncoded
        Observable<UserModel> dangki(
                        @Field("username") String username,
                        @Field("email") String email,
                        @Field("password") String password,
                        @Field("mobile") String mobile,
                        @Field("uid") String uid);

        // dang nhap
        @POST(id_dangnhap)
        @FormUrlEncoded
        Observable<UserModel> dangnhap(
                        @Field("email") String email,
                        @Field("password") String password);

        // doi mat khau
        @POST(id_doimatkhau)
        @FormUrlEncoded
        Observable<UserModel> doimatkhau(
                        @Field("password") String password,
                        @Field("id") int id

        );

        // quen mat khau
        @POST(id_quenmatkhau)
        @FormUrlEncoded
        Observable<UserModel> quenmatkhau(
                        @Field("email") String email);

        // thanh toán đơn hàng
        @POST(id_donhang)
        @FormUrlEncoded
        Observable<MessageModel> createOder(
                        @Field("email") String email,
                        @Field("sdt") String sdt,
                        @Field("tongtien") String tongtien,
                        @Field("iduser") int id,
                        @Field("diachi") String diachi,
                        @Field("soluong") int soluong,
                        @Field("chitiet") String chitiet);

        @POST(id_xemdonhang)
        @FormUrlEncoded
        Observable<DonhangModel> xemdonhang(
                        @Field("iduser") int id);

        @POST(id_timkiem)
        @FormUrlEncoded
        Observable<SanPhamMoiModel> timkiem(
                        @Field("search") String search);

        // quản lý sản phẩm
        // them san pham
        @POST(id_themsanpham)
        @FormUrlEncoded
        Observable<MessageModel> themsanpham(
                        @Field("tensanpham") String tensanpham,
                        @Field("giasanpham") String giasanpham,
                        @Field("hinhanhsanpham") String hinhanhsanpham,
                        @Field("motasanpham") String motasanpham,
                        @Field("idsanpham") int idsanpham

        );

        // sửa san pham
        @POST(id_capnhatsanpham)
        @FormUrlEncoded
        Observable<MessageModel> capnhatsanpham(
                        @Field("tensanpham") String tensanpham,
                        @Field("giasanpham") String giasanpham,
                        @Field("hinhanhsanpham") String hinhanhsanpham,
                        @Field("motasanpham") String motasanpham,
                        @Field("idsanpham") int idsanpham,
                        @Field("id") int id

        );

        @POST(id_updatetoken)
        @FormUrlEncoded
        Observable<MessageModel> updatetoken(
                        @Field("id") int id,
                        @Field("token") String token);

        @POST(id_momo)
        @FormUrlEncoded
        Observable<MessageModel> updatenganhangmomo(
                        @Field("id") int id,
                        @Field("token") String token);

        @POST(id_gettoken)
        @FormUrlEncoded
        Observable<UserModel> gettoken(
                        @Field("status") int status

        );

        @POST(id_gettoken)
        @FormUrlEncoded
        Observable<UserModel> gettokenadmin(
                        @Field("status") int status,
                        @Field("iduser") int iduser);

        @POST(id_capnhattrangthai)
        @FormUrlEncoded
        Observable<MessageModel> capnhattrangthai(
                        @Field("id") int id,
                        @Field("trangthai") int trangthai);

        @Multipart
        @POST(id_hinhanh)
        Call<MessageModel> uploadFile(@Part MultipartBody.Part file);
        // xoa san pham

        @POST(id_xoasanpham)
        @FormUrlEncoded
        Observable<MessageModel> xoaSanPham(
                        @Field("id") int id);

}
