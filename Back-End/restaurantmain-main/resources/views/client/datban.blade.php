@extends('client.master')
@section('title')
Đặt bàn
@endsection
@section('content')
<section class="site-cover" style="background-image: url({{url('../resources')}}/img/trangchu.jpg);" id="section-home">
    <div class="container">
        <div class="row align-items-center justify-content-center text-center site-vh-100">
        <div class="col-md-12">
            <h1 class="site-heading site-animate mb-3">Đặt bàn</h1>
            <h2 class="h5 site-subheading mb-5 site-animate"><span class="mr-2"><a href="{{route('trangchu')}}">Trang chủ <i class="ion-ios-arrow-forward"></i></a></span> > <span>Đặt bàn <i class="ion-ios-arrow-forward"></i></span></h2>
        </div>
    </div>
</section>
 @if(isset($DatBan))
  <section class="ftco-section img" style="background-image: url({{url('../resources')}}/img/trangchu.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
      <div class="row d-flex">
        <div class="ftco-animate makereservation">
          <div class="heading-section ftco-animate text-center">
            <span>Cảm ơn bạn {{$DatBan->HoTen}} đã đặt bàn</span>
          </div>
        </div>
      </div>
    </div>
  </section>
  @elseif(isset($error))
  <section class="ftco-section img" style="background-image: url({{url('../resources')}}/client/images/bg_3.jpg)" data-stellar-background-ratio="0.5">
    <div class="container">
      <div class="row d-flex">
        <div class="ftco-animate makereservation">
          <div class="heading-section ftco-animate text-center">
            <span>{{$error}}</span>
          </div>
        </div>
      </div>
    </div>
  </section>
  @else
  <section class="ftco-section ftco-no-pt ftco-no-pb">
    <div class="container-fluid px-0">
      <div class="row d-flex no-gutters">
        <div class="col-md-6 order-md-last ftco-animate makereservation p-4 p-md-5 pt-5">
          <div class="py-md-5">
            <div class="heading-section ftco-animate mb-5 text-center">
	          	<span class="subheading">Đặt bàn</span>
	          </div>
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
                    <input type="submit" value="Đặt bàn" class="btn btn-primary py-3 px-5">
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
        <div class="col-md-6 d-flex align-items-stretch pb-5 pb-md-0">
          <div id="map"></div>
        </div>
      </div>
    </div>
  </section>
  @endif
@endsection
