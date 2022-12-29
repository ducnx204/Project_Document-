@extends('client.master')
@section('title')
  Nhà hàng {{$data->TenNhaHang}}
@endsection
@section('content')
<section class="site-cover" style="background-image: url({{url('../resources')}}/img/trangchu.jpg);" id="section-home">
    <div class="container">
        <div class="row align-items-center justify-content-center text-center site-vh-100">
        <div class="col-md-12">
            <h1 class="site-heading site-animate mb-3">Nhà hàng {{$data->TenNhaHang}}</h1>
            <h2 class="h5 site-subheading mb-5 site-animate">Hãy đến và ăn ngon với những món ăn ngon và lành mạnh của chúng tôi.</h2>
            <p><a href="" target="_blank" class="btn btn-outline-white btn-lg site-animate" data-toggle="modal" data-target="#reservationModal">Đặt bàn</a></p>
        </div>
        </div>
    </div>
</section>

<section class="site-section" id="section-about">
    <div class="container">
      <div class="row">
        <div class="col-md-5 site-animate mb-5">
          <h4 class="site-sub-title">Our Story</h4>
          <h2 class="site-primary-title display-4">Nhà hàng {{$data->TenNhaHang}}</h2>
          <p>{{$data->GioiThieu1}}</p>

          <p class="mb-4">Xuân Long tự hào là nơi cung cấp những buổi tiệc cuối năm với đầy đủ tiêu chí : riêng tư, gần
              gũi, menu món ăn chất lượng cùng những ly rượu vang ấm áp, trong một không gian có chút sôi nổi nhưng vẫn
              có những khoảng lặng vừa đủ để bạn không bỏ qua bất kì cảm xúc nào của chính mình!</p>
        </div>
        <div class="col-md-1"></div>
        <div class="col-md-6 site-animate img" data-animate-effect="fadeInRight">
          <img src="{{url('../resources')}}/img/about_img_1.jpg" alt="Free Template by colorlib.com" class="img-fluid">
        </div>
      </div>
    </div>
</section>

<div class="modal fade" id="reservationModal" tabindex="-1" role="dialog" aria-labelledby="reservationModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-body">
          <div class="row">
            <div class="col-lg-12">
              <div class="bg-image" style="background-image: url({{url('../resources')}}/img/reservation_1.jpg);"></div>
            </div>
            <div class="col-lg-12 p-5">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <small>CLOSE </small><span aria-hidden="true">&times;</span>
              </button>
              <h1 class="mb-4">Đặt bàn</h1>
                <form method="post" action="{{url('/')}}/dat-ban">
                    <input type="hidden" name="_token" value="{{csrf_token()}}">
                    <div class="row">
                        <div class="col-md-6">
                        <div class="form-group">
                            <label for="">Họ và tên</label>
                            <input type="text" class="form-control" placeholder="Tên" name="HoTen" required>
                        </div>
                        </div>
                        <div class="col-md-6">
                        <div class="form-group">
                            <label for="">Email</label>
                            <input type="email" class="form-control" placeholder="Email" name="Email" required>
                        </div>
                        </div>
                        <div class="col-md-6">
                        <div class="form-group">
                            <label for="">Số điện thoại</label>
                            <input type="number" class="form-control" placeholder="SĐT" name="SDT" required>
                        </div>
                        </div>
                        <div class="col-md-6">
                        <div class="form-group">
                            <label for="">Ngày</label>
                            <input type="text" class="form-control" id="m_date" placeholder="Ngày" name="Ngay" required>
                        </div>
                        </div>
                        <div class="col-md-6">
                        <div class="form-group">
                            <label for="">Thời gian</label>
                            <input type="text" class="form-control" id="m_time" placeholder="Thời gian" name="Gio" required>
                        </div>
                        </div>
                        <div class="col-md-6">
                        <div class="form-group">
                            <label for="">Số người</label>
                            <div class="select-wrap one-third">
                            <div class="icon"><span class="ion-ios-arrow-down"></span></div>
                            <select name="SoNguoi" id="" class="form-control">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4+">4+</option>
                            </select>
                            </div>
                        </div>
                        </div>
                        <div class="col-md-12 mt-3">
                        <div class="form-group text-center">
                            <input type="submit" value="Đặt bàn" class="btn btn-primary btn-lg btn-block"">
                        </div>
                        </div>
                    </div>
                </form>
            </div>
          </div>

        </div>
      </div>
    </div>
</div>

<!-- loader -->
<div id="site-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>
@endsection
