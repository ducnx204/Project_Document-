@extends('admin.master')
@section('content')
<div class="container-fluid">

    <!-- DataTales Example -->
    <div class="card shadow mb-4">
      <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Danh sách đặt bàn</h6>
      </div>
      <div class="card-body">
        <div class="table-responsive">
          <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
            <thead>
              <tr>
                <th>STT</th>
                <th>Họ và tên</th>
                <th>Email</th>
                <th>SDT</th>
                <th>Ngày</th>
                <th>Số người</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
                @foreach ($DatBan as $item)
                <tr>
                    <td>{{$loop->iteration}}</td>
                    <td>{{$item->HoTen}}</td>
                    <td>{{$item->Email}}</td>
                    <td>{{$item->SDT}}</td>
                    <td>{{date_format(new DateTime($item->Ngay),'d/m/Y')}} {{date_format(new DateTime($item->Gio),'H:i')}}</td>
                    <td>{{$item->SoNguoi}}</td>
                    <td><a href="{{route('xoadatban',[ 'id' => $item->id])}}" class="btn btn-xs btn-danger"  onclick="return confirm('Bạn đồng ý xóa')"> Xóa</a></td>
                </tr>
                @endforeach
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
@endsection


@section('script')
<script src="{{url('/')}}/js/demo/datatables-demo.js"></script>
@endsection
