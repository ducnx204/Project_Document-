<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class DatBan extends Model{
    protected $table = 'datban';

    protected $fillable = ['HoTen','Email','SDT','Ngay','Gio','SoNguoi'];

    public $timestamps = false;
}
