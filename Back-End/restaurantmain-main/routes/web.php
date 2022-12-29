<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

/*Route::get('/', function () {
    return view('welcome');
});*/
Route::namespace ('client')->group(function () {
    Route::get('/', 'TrangChuController@get')->name('trangchu');
    Route::get('/gioi-thieu', 'TrangChuController@gioithieu')->name('gioithieu');
    Route::get('/menu', 'MenuController@get')->name('menu');
    Route::get('/dat-ban', 'TrangChuController@datban')->name('datban');
    Route::post('/dat-ban', 'TrangChuController@Post');
});

Route::namespace ('admin')->group(function () {
    Route::get('/dangnhap', 'DangNhapController@DangNhap')->name('dangnhap');
    Route::post('/dangnhap', 'DangNhapController@PostDangNhap');
    Route::get('/dangxuat', 'DangNhapController@DangXuat')->name('dangxuat');
});

//admin
//Route::namespace ('admin')->prefix('admin')->middleware('required.login')->group(function () {
Route::namespace ('admin')->prefix('admin')->group(function () {
    Route::get('/', function () {
        return view('admin.home');
    })->name('homeadmin');

    //route món ăn
    Route::prefix('monan')->group(function () {
        Route::get('/', 'MonAnController@DanhSachMonAn')->name('danhsachmonan');
        //thêm món ăn
        Route::get('/add', 'MonAnController@ThemMonAn')->name('themmonan');
        Route::post('/add', 'MonAnController@PostThemMonAn');
        //sửa món ăn
        Route::get('/edit/{id}', 'MonAnController@SuaMonAn')->name('suamonan');
        Route::post('/edit/{id}', 'MonAnController@PostSuaMonAn');
        //xóa món ăn
        Route::get('/delete/{id}', 'MonAnController@XoaMonAn')->name('xoamonan');
    });

    //route danh mục
    Route::prefix('danhmuc')->group(function () {
        Route::get('/', 'DanhMucController@DanhSachDanhMuc')->name('danhsachdanhmuc');
        //Thêm danh mục
        Route::get('/add', 'DanhMucController@ThemDanhMuc')->name('themdanhmuc');
        Route::post('/add', 'DanhMucController@PostThemDanhMuc');
        //Sửa danh mục
        Route::get('/edit/{id}', 'DanhMucController@SuaDanhMuc')->name('suadanhmuc');
        Route::post('/edit/{id}', 'DanhMucController@PostSuaDanhMuc');
        //Xóa danh mục
        Route::get('/delete/{id}', 'DanhMucController@XoaDanhMuc')->name('xoadanhmuc');
    });

    Route::prefix('taikhoan')->group(function () {
        Route::get('/', 'TaiKhoanController@DanhSachTaiKhoan')->name('danhsachtaikhoan');
        //Thêm tài khoản
        Route::get('/add', 'TaiKhoanController@ThemTaiKhoan')->name('themtaikhoan');
        Route::post('/add', 'TaiKhoanController@PostThemTaiKhoan');
        //Xóa tài khoản
        Route::get('/delete/{id}', 'TaiKhoanController@XoaTaiKhoan')->name('xoataikhoan');
        //sửa tài khoản
        Route::get('/edit/{id}','TaiKhoanController@SuaTaiKhoan')->name('suataikhoan');
        Route::post('/edit/{id}','TaiKhoanController@PostSuaTaiKhoan');
    });

    Route::prefix('datban')->group(function () {
        Route::get('/', 'DatBanController@DanhSachDatBan')->name('danhsachdatban');
        Route::get('/delete/{id}', 'DatBanController@XoaDatBan')->name('xoadatban');
        Route::get('/thongke', 'DatBanController@thongke')->name('thongke');
    });
});

