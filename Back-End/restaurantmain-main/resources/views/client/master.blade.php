<!DOCTYPE html>
<html lang="en">
  <head>
    <title>@yield('title')</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700|Raleway" rel="stylesheet">
    <link rel="stylesheet" href="{{url('../resources')}}/css/bootstrap.min.css">
    <link rel="stylesheet" href="{{url('../resources')}}/css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="{{url('../resources')}}/css/animate.css">

    <link rel="stylesheet" href="{{url('../resources')}}/css/owl.carousel.min.css">
    <link rel="stylesheet" href="{{url('../resources')}}/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="{{url('../resources')}}/css/magnific-popup.css">

    <link rel="stylesheet" href="{{url('../resources')}}/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="{{url('../resources')}}/css/jquery.timepicker.css">

    <link rel="stylesheet" href="{{url('../resources')}}/css/icomoon.css">
    <link rel="stylesheet" href="{{url('../resources')}}/css/style.css">
  </head>
  <body>
  @include('client.layout.header')

  @yield('content')

  @include('client.layout.footer')


  <script src="{{url('../resources')}}/js/jquery.min.js"></script>
  <script src="{{url('../resources')}}/js/popper.min.js"></script>
  <script src="{{url('../resources')}}/js/bootstrap.min.js"></script>
  <script src="{{url('../resources')}}/js/jquery.easing.1.3.js"></script>
  <script src="{{url('../resources')}}/js/jquery.waypoints.min.js"></script>
  <script src="{{url('../resources')}}/js/owl.carousel.min.js"></script>
  <script src="{{url('../resources')}}/js/jquery.magnific-popup.min.js"></script>
  <script src="{{url('../resources')}}/js/bootstrap-datepicker.js"></script>
  <script src="{{url('../resources')}}/js/jquery.timepicker.min.js"></script>
  <script src="{{url('../resources')}}/js/jquery.animateNumber.min.js"></script>
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
  <script src="{{url('../resources')}}/js/google-map.js"></script>
  <script src="{{url('../resources')}}/js/main.js"></script>
  </body>
</html>
