<?php

namespace App\Http\Controllers\client;

use App\Http\Controllers\Controller;
use App\Models\MonAn;
use App\Models\DanhMuc;

class MenuController extends Controller {
	public function Get() {
        return view('client.menu',[
            "data"=>json_decode(file_get_contents(base_path("app/home.json"))),
            "MonAn"=>MonAn::All(),
            "DanhMuc"=>DanhMuc::all(),
            "khachhang"=>json_decode(file_get_contents(base_path("app/khachhang.json"))),
            "daubep"=>json_decode(file_get_contents(base_path("app/daubep.json"))),
        ]);
	}
}
