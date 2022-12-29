<?php


namespace App\Http\Controllers\admin;


use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class DangNhapController extends Controller
{
    public function DangNhap(){
        if(Auth::check())
            return redirect('admin');
        else
            return view('admin.dangnhap');
    }

    public function PostDangNhap(Request $req){
        Auth::attempt(['name' => $req->username, 'password' => $req->password]);
        if(Auth::check()){
            return redirect('admin');
        }else{
            return view('admin.dangnhap',['error'=>'Sai tài khoản hoặc mật khẩu']);
        }
    }

    public function DangXuat(Request $req){
        Auth::logout();
        return redirect('dangnhap');
    }
}

