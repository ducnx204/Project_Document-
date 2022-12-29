@extends('admin.master')
@section('content')

<div class="container-fluid">
    <!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Thống kê số bàn đặt</h6>
        </div>

        <div class="card-body">
            <div id="myfirstchart" style="height: 250px;"></div>

        </div>
        
        <?php 
            $list_ = $list; 
            foreach($list as $key => $value){
                $count = $value->num;
                $date = $value->Ngay;
            }
        ?>
    </div>
</div>

@endsection


@section('script')
<script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.2/raphael-min.js"></script>
<script src="{{url('/')}}/morris/morris.min.js"></script>
<script>
    var list = <?php echo json_encode($list_); ?>;

    console.log(list);

    new Morris.Line({
        element: 'myfirstchart',
        data: list,
        xkey: 'Ngay',
        ykeys: ['num'],
        labels: ['Số bàn đặt:']
    });
</script>
@endsection
