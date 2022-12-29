<?php


namespace App\Http\Controllers\admin;


use App\Http\Controllers\Controller;
use App\Models\MonAn;
use App\Models\DanhMuc;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Session;
use Facade\Ignition\Http\Requests;
use Illuminate\Support\Facades\Redirect;
session_start();

class MonAnController extends Controller
{
    public function DanhSachMonAn()
    {
        return view('admin.MonAn.DanhSachMonAn', ['MonAn' => MonAn::paginate(12)]);

    }

    public function ThemMonAn()
    {
        return view('admin.MonAn.ThemMonAn', ['DanhMuc' => DanhMuc::all()]);
    }

    public function PostThemMonAn(Request $req)
    {
        $file_name = '';
        if ($req->hasFile('upload_file')) {
            $file = $req->file('upload_file');
            $file_name = date('Y-m-d-h-i-s') . $file->getClientOriginalName();
            $file->move(public_path('uploads/'), $file_name);
        }
        $req->merge(['HinhAnh' => $file_name]);
        MonAn::create($req->all());
        return redirect()->route('danhsachmonan');
    }

    public function SuaMonAn($id)
    {
        $monan = MonAn::find($id);
        return view('admin.MonAn.SuaMonAn',['MonAn'=>$monan,'DanhMuc'=>DanhMuc::all()]);
    }

    public function PostSuaMonAn($id,Request $req)
    {
        $file_name = '';
        if ($req->hasFile('upload_file')) {
            $file = $req->file('upload_file');
            $file_name = date('Y-m-d-h-i-s') . $file->getClientOriginalName();
            $file->move(public_path('uploads/'), $file_name);
        }
        $req->merge(['HinhAnh' => $file_name]);
        MonAn::find($id)->update($req->all());
        return redirect()->route('danhsachmonan');
    }

    public function XoaMonAn($id)
    {
        MonAn::destroy($id);
        return redirect()->route('danhsachmonan');
    }
}
