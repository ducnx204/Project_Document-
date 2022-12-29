@extends('admin.master')
@section('content')
<div class="container-fluid">
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Sửa món ăn</h6>
          </div>
    <div class="card-body">
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" method="POST" action="" enctype="multipart/form-data">
                        <input type="hidden" name="_token" value="{{csrf_token()}}">
                        <div class="form-group">
                            <label>Tên món ăn:</label>
                            <input class="form-control" placeholder="Tên món ăn" name="TenMonAn" value="{{$MonAn->TenMonAn}}">
                        </div>
                        <div class="form-group">
                            <label>Ảnh món ăn</label>
                            <div style='width: 20%;'>
                                <img id='HinhAnh' src='{{url('../resources/uploads/'.$MonAn->HinhAnh)}}' width="100%">
                            </div>
                            <input type="file" name="upload_file" onchange="readURL(this)">
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <label>Danh mục:</label>
                                <select name="idDanhMuc" id="input" class="form-control"  >
                                    @foreach($DanhMuc as $item)
                                    <option value="{{ $item->id}}" @if($item->id==$MonAn->idDanhMuc) selected @endif> {{$item->TenDanhMuc}}</option>
                                    @endforeach
                                </select>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Giá:</label>
                                    <input class="form-control" placeholder="Giá" name="Gia" value='{{$MonAn->Gia}}'>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Mô tả</label>
                            <textarea class="form-control" rows="3" name="MoTa">{{$MonAn->MoTa}} </textarea>
                        </div>
                        <button type="submit" class="btn btn-info">Sửa</button>
                        <button type="reset" class="btn btn-danger">Xóa </button>
                    </form>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
    </div>
</div>
</div>
@endsection

@section('script')
<script>
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function(e) {
                $('#HinhAnh').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }
</script>
@endsection
