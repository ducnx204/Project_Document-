@extends('admin.master')
@section('content')
<div class="container-fluid">
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Thêm tài khoản</h6>
          </div>
    <div class="card-body">
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" method="POST" action="" enctype="multipart/form-data" name="form1">
                        <input type="hidden" name="_token" value="{{csrf_token()}}">
                        <div class="form-group">
                            <label>Tên tài khoản:</label>
                            <input class="form-control" placeholder="Tên tài khoản" name="name">
                        </div>
                        <div class="form-group">
                            <label>Email:</label>
                            <input class="form-control" placeholder="Email" name="email">
                        </div>
                        <div class="form-group">
                            <label>Mật khẩu:</label>
                            <input class="form-control" placeholder="Mật khẩu" name="password">
                        </div>
                        <button type="submit" class="btn btn-info" >Thêm</button>
                        <button type="reset" class="btn btn-danger">Xóa </button>
                    </form>
                    <p id="thongbao"></p>
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
