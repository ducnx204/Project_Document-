<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class DanhMuc extends Model
{
    protected $table = 'danhmuc';

    protected $fillable = ['TenDanhMuc'];

    public $timestamps = false;

    public function monan()
    {
        return $this->hasMany('App\Models\MonAn', 'idDanhMuc', 'id');
    }
}