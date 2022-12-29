<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class MonAn extends Model
{
    protected $table = 'monan';

    protected $fillable = ['TenMonAn','MoTa','HinhAnh','Gia','idDanhMuc'];

    public $timestamps = false;

    public function DanhMuc()
    {
        return $this->belongsTo('App\Models\DanhMuc','idDanhMuc','id');
    }
}
