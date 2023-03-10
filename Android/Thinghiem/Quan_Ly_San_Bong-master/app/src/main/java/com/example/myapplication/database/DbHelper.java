package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper{
    static final String dbName = "QLSB";
    static final int dbVision = 6;
    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, dbVision);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_CumSan = "CREATE TABLE CumSan"+
                "(maCumSan INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "chuSan TEXT NOT NULL, " +
                "diaChi TEXT NOT NULL, " +
                "tenCumSan TEXT )";

        db.execSQL(create_CumSan);

        String create_User = "CREATE TABLE User" +
                "(taiKhoan TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL," +
                "matKhau TEXT NOT NULL," +
                "phanQuyen TEXT NOT NULL, " +
                "hinh BLOB )";
        db.execSQL(create_User);


        String create_San = "CREATE TABLE San" +
                "(maSan INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "tenSan TEXT NOT NULL, " +
                "giaSan INTEGER NOT NULL, " +
                "loaiSan INTEGER NOT NULL, " +
                "maCumSan INTEGER NOT NULL ," +
                "anhSan BLOB )";
        db.execSQL(create_San);

        String create_PhieuThue = "CREATE TABLE PhieuThue" +
                "(maPT INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maSan INTEGER NOT NULL, " +
                "nguoiThue TEXT NOT NULL, " +
                "caThue TEXT NOT NULL, " +
                "ngayThue DATE NOT NULL, " +
                "soKM INTEGER NOT NULL, " +
                "tienSan INTEGER NOT NULL, " +
                "danhGia INTEGER, " +//:0-ch??a ????nh gi??; 1-???? ????nh gi??
                "sao INTEGER, " +
                "phanHoi TEXT)";// 1-5 sao
        db.execSQL(create_PhieuThue);

        String create_KhuyenMai = "CREATE TABLE KHUYENMAI" +
                "(maID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "soKM INTEGER NOT NULL, " +
                "maSan INTEGER NOT NULL, " +
                "caThue TEXT NOT NULL, "+
                "ngayThue DATE NOT NULL)";
        db.execSQL(create_KhuyenMai);

        String INSERT_User = "Insert into User(taiKhoan,hoTen,matKhau,phanQuyen,hinh) values " +
                "('0999999999','Admin','123456','AD',null)," +
                "('0777777777','Nguy???n Ph??c Ng??n','123456','NT',null)," +
                "('0777777771','Nguy???n V??n Vinh','123456','NT',null)," +
                "('0777777772','Nguy???n Qu???c Tu???n','123456','NT',null)," +
                "('0777777773','Nguy???n Ho??i L????ng','123456','NT',null)," +
                "('0777777774','Nguy???n Qu???c T??n','123456','NT',null)," +
                    "('0777777775','L?? Ho??i Nh??','123456','NT',null)," +
                "('0777777776','Nguy???n Qu???c Tu???n','123456','NT',null)," +
                "('0888888888','Nguy???n Ho??i L????ng','123456','CS',null)," +
                "('0333333333','Nguy???n Ho??i Tr??m','123456','CS',null)," +
                "('0333333331','Nguy???n ?????c L????ng','123456','CS',null)," +
                "('0333333332','V??n Vinh Nguy???n','123456','CS',null)," +
                "('0333333334','Nguy???n Ng??n Ph??c','123456','CS',null)," +
                "('0333333335','Nguy???n Tu???n Qu???c','123456','CS',null)," +
                "('0333333336','Nguy???n V??n Nam','123456','CS',null)," +
                "('0555555555','Nguy???n Ng???c Anh','123456','CS',null)";
        db.execSQL(INSERT_User);

        String INSERT_San1 = "Insert into San(tenSan,giaSan,loaiSan, maCumSan, anhSan) values " +
                "('S??n 5A','150000',5, 1,null)," +
                "('S??n 7A','200000',7, 1,null)," +
                "('S??n 5B','150000',5, 1,null)," +
                "('S??n 7B','200000',7, 1,null)," +
                "('S??n 5A','150000',5, 2,null)," +
                "('S??n 7A','200000',7, 2,null)," +
                "('S??n 5A','150000',5, 3,null)," +
                "('S??n 7A','200000',7, 3,null)," +
                "('S??n 5A','250000',5, 4,null)," +
                "('S??n 7A','200000',7, 4,null)," +
                "('S??n 5B','150000',5, 4,null)," +
                "('S??n 7A','200000',7, 5,null)," +
                "('S??n 7B','250000',7, 5,null)," +
                "('S??n 7C','250000',7, 5,null)," +
                "('S??n 5A','150000',5, 6,null)," +
                "('S??n 5B','220000',5, 6,null)," +
                "('S??n 5C','220000',5, 6,null)," +
                "('S??n 7A','220000',7, 7,null)," +
                "('S??n 5A','150000',5, 7,null)";
        db.execSQL(INSERT_San1);

        String INSERT_CumSan = "Insert into CumSan(tenCumSan,diaChi,chuSan) values " +
                "('S??n B??ng ???? Chuy??n Vi???t','151 ??u C??, Ho?? Kh??nh B???c, Li??n Chi???u, ???? N???ng','0888888888')," +
                "('S??n B??ng Manchester United','59 ??. Ng?? Th?? Nh???m, Ho?? Kh??nh Nam, Li??n Chi???u, ???? N???ng','0888888888')," +
                "('S??n B??ng ???? 360','911 Nguy???n L????ng B???ng, Ho?? Hi???p Nam, Li??n Chi???u, ???? N???ng','0888888822')," +
                "('S??n B??ng Li??n Chi???u','522 Nguy???n L????ng B???ng, Ho?? Hi???p Nam, Li??n Chi???u, ???? N???ng','0333333331')," +
                "('S??n b??ng nh??n t???o Nam Cao','347X+C74, Ho?? Kh??nh Nam, Li??n Chi???u, ???? N???ng','0888888883')," +
                "('S??n B??ng Ng???c Th???ch','K207 ??. Ph???m Nh?? X????ng, Ho?? Kh??nh Nam, Li??n Chi???u, ???? N???ng','0333333331')," +
                "('S??n b??ng An Trung','An Trung 3, An H???i T??y, S??n Tr??, ???? N???ng','0888888883')," +
                "('S??n b??ng ???? T20','M??? Kh?? 4, Ph?????c M???, S??n Tr??, ???? N???ng','0888888884')," +
                "('S??n b??ng Harmony','Ph???m V??n ?????ng, An H???i B???c, S??n Tr??, ???? N???ng','0888888884')," +
                "('S??n b??ng ???? Chuy??n Vi???t','98 Ti???u La, H??a Thu???n ????ng, H???i Ch??u, ???? N???ng','0888888884')," +
                "('S??n b??ng ???? Trang Ho??ng','86 Duy T??n, H??a Thu???n Nam, H???i Ch??u, ???? N???ng','0333333336')," +
                "('S??n b??ng ???? Duy T??n','H??a Thu???n ????ng, H???i Ch??u, ???? N???ng','0888888885')," +
                "('S??n B??ng ???? An Ph??c 2','409 Tr??ng N??? V????ng, H??a Thu???n Nam, H???i Ch??u, ???? N???ng','0333333336')," +
                "('S??n b??ng ???? M??? Nh???t Quang','498 Nguy???n H???u Th???, Khu?? Trung, C???m L???, ???? N???ng','0888888885')," +
                "('S??n B??ng ???? Mini Vi???t H??n','Ho?? H???i, Ng?? H??nh S??n, ???? N???ng','0888888588')," +
                "('S??n B??ng ???? Th??p Vi???t','??. Nghi??m Xu??n Y??m, Khu?? M???, Ng?? H??nh S??n, ???? N???ng','0888888884')," +
                "('S??n b??ng ???? Minh H??','B???c M??? Ph??, Ng?? H??nh S??n, ???? N???ng','0888888588')," +
                "('S??n b??ng ???? mini B??? V??n ????n','B??? V??n ????n, Ch??nh Gi??n, Thanh Kh??, ???? N???ng','0333333336')," +
                "('S??n b??ng ???? An H?? 1','243 Tr?????ng Chinh, An Kh??, Thanh Kh??, ???? N???ng','0888888881')," +
                "('S??n B??ng ???? An Ph??c 1','303 Tr??ng N??? V????ng, H??a Thu???n Nam, H???i Ch??u, ???? N???ng','0333333336')," +
                "('S??n B??ng ???? H??a H??n','??. T??n ?????n, Ho?? Th??? T??y, C???m L???, ???? N???ng','0888888882')," +
                "('S??n B??ng ???? Mini Tu???n Nh??n','Nguy???n V??n T???o, Ho?? An, C???m L???, ???? N???ng','0888888883')," +
                "('S??n B??ng ???? Nh??n','Li??n Chi???u - ???? N???ng','0888888881')";
        db.execSQL(INSERT_CumSan);


        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngay = simpleDateFormat.format(now);
        String INSERT_PT = "Insert into PhieuThue(maSan,nguoiThue,caThue,ngayThue,soKM,tienSan, danhGia, sao, phanHoi) values " +
                "(1,'0777777777','1','"+ngay+"',0,150000, 1, 5, 'Gi?? h???p l??, s??n ok')," +
                "(1,'0777777773','2','"+ngay+"',0,200000, 1, 2, 'M???t s??n x???u')," +
                "(1,'0777777777','3','"+ngay+"',0,150000, 1, 5, 'Ok')," +
                "(1,'0777777777','5','"+ngay+"',0,150000, 1, 5, 'Gi?? r???, s??n ?????p')," +
                "(2,'0777777777','6','"+ngay+"',0,150000, 1, 5, 'Ok')," +
                "(2,'0777777771','9','"+ngay+"',0,200000, 1, 4, 'Ok')," +
                "(2,'0777777771','10','"+ngay+"',0,150000, 1, 5, 'Ok')," +
                "(2,'0777777771','3','"+ngay+"',0,150000, 1, 2, 'S??n ?????p')," +
                "(3,'0777777774','1','"+ngay+"',0,150000, 1, 5, 'Ok')," +
                "(3,'0777777774','3','"+ngay+"',0,200000, 1, 4, 'Ok')," +
                "(3,'0777777775','6','"+ngay+"',0,150000, 1, 5, 'Ok')," +
                "(3,'0777777775','7','"+ngay+"',0,200000, 1, 4, 'Ok')," +
                "(4,'0777777775','1','"+ngay+"',0,200000, 1, 4, 'Ok')," +
                "(4,'0777777775','3','"+ngay+"',0,200000, 1, 4, 'Ok')," +
                "(4,'0777777775','4','"+ngay+"',0,200000, 1, 4, 'Ok')," +
                "(11,'0777777777','1','24-11-2021',0,150000, 1, 3, 'ok')," +
                "(9,'0777777777','3','29-11-2021',0,200000, 1, 2, 'Ok')," +
                "(9,'0777777777','6','05-12-2021',0,150000, 1, 1, 'Ok')," +
                "(4,'0777777775','6','"+ngay+"',0,200000, 1, 4, 'Ok')";
        db.execSQL(INSERT_PT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists San");
        db.execSQL("drop table if exists PhieuThue");
        db.execSQL("drop table if exists CumSan");
        db.execSQL("drop table if exists KhuyenMai");
        onCreate(db);
    }
}
