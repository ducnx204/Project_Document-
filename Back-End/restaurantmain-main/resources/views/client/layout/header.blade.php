<nav class="navbar navbar-expand-lg navbar-dark site_navbar bg-dark site-navbar-light" id="site-navbar">
  <div class="container">
    <a class="navbar-brand" href="{{route('trangchu')}}">{{$data->TenNhaHang}}</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#site-nav" aria-controls="site-nav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="oi oi-menu"></span> Menu
    </button>

    <div class="collapse navbar-collapse" id="site-nav">
      <ul class="navbar-nav ml-auto">
        <li class="nav-item {{\Route::current()->getName() == 'trangchu'?'active':''}}"><a href="{{route('trangchu')}}" class="nav-link">Trang chủ</a></li>
		<li class="nav-item {{\Route::current()->getName() == 'gioithieu'?'active':''}}"><a href="{{route('gioithieu')}}" class="nav-link">Giới thiệu</a></li>
		<li class="nav-item {{\Route::current()->getName() == 'menu'?'active':''}}"><a href="{{route('menu')}}" class="nav-link">Menu</a></li>
		<li class="nav-item {{\Route::current()->getName() == 'datban'?'active':''}}"><a href="{{route('datban')}}" class="nav-link">Đặt bàn</a></li>
          <li class="nav-item {{\Route::current()->getName() == 'dangnhap'?'active':''}}"><a href="{{route('dangnhap')}}" class="nav-link">Đăng nhập</a></li>
      </ul>
    </div>
  </div>
</nav>
