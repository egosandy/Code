
<div id="layoutSidenav">

            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Dashboard</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">Dashboard</li>
                        </ol>
                        <div class="row">
<div class="col-xl-3 col-md-6">
    <div class="card bg-primary text-white mb-4">
        <div class="card-footer d-flex align-items-center justify-content-between">
            <div class="d-flex flex-column">
                <a class="text-secondary-font-weight-bold text-white stretched-link">Pelanggan<br>
                    <div class="h5 mb-0 font-weight-bold text-white text-gray-800"><?= count($user); ?> Costumer</div>
                </a>
            </div>
            <span class="iconify" data-icon="fa:group" style="color: secondary;" data-width="50" data-height="50"></span>
        </div> 
        <div class="card-footer d-flex align-items-center justify-content-between">
            <a class="small text-white stretched-link" href="<?= base_url('users'); ?>">Lihat Detail</a>
            <div class="small text-white">
                <span class="iconify" data-icon="bi:chevron-right" data-inline="false"></span>
            </div>
        </div>
    </div>
</div>

<div class="col-xl-3 col-md-6">
    <div class="card bg-warning text-white mb-4">
        <div class="card-footer d-flex align-items-center justify-content-between">
            <div class="d-flex flex-column">
                <a class="text-secondary-font-weight-bold text-white stretched-link">Pengemudi<br>
                    <div class="h5 mb-0 font-weight-bold text-white text-gray-800"><?= count($hitungdriver); ?> Driver</div>
                </a>
            </div>
            <span class="iconify" data-icon="ant-design:car-filled" style="color: secondary;" data-width="50" data-height="50"></span>
        </div> 
        <div class="card-footer d-flex align-items-center justify-content-between">
            <a class="small text-white stretched-link" href="<?= base_url('driver'); ?>">Lihat Detail</a>
            <div class="small text-white">
                <span class="iconify" data-icon="bi:chevron-right" data-inline="false"></span>
            </div>
        </div>
    </div>
</div>

<div class="col-xl-3 col-md-6">
    <div class="card bg-danger text-white mb-4">
        <div class="card-footer d-flex align-items-center justify-content-between">
            <div class="d-flex flex-column">
                <a class="text-secondary-font-weight-bold text-white stretched-link">Mitra<br>
                    <div class="h5 mb-0 font-weight-bold text-white text-gray-800"><?= count($mitra); ?> Merchant</div>
                </a>
            </div>
            <span class="iconify" data-icon="fa-solid:store-alt" style="color: secondary;" data-width="50" data-height="50"></span>
        </div> 
        <div class="card-footer d-flex align-items-center justify-content-between">
            <a class="small text-white stretched-link" href="<?= base_url('mitra'); ?>">Lihat Detail</a>
            <div class="small text-white">
                <span class="iconify" data-icon="bi:chevron-right" data-inline="false"></span>
            </div>
        </div>
    </div>
</div>

<div class="col-xl-3 col-md-6">
    <div class="card bg-success text-white mb-4">
        <div class="card-footer d-flex align-items-center justify-content-between">
            <div class="d-flex flex-column">
                <a class="text-secondary-font-weight-bold text-white stretched-link">Aktivitas<br>
                    <div class="h5 mb-0 font-weight-bold text-white text-gray-800"><?= count($transaksi); ?> Transaksi</div>
                </a>
            </div>
            <span class="iconify" data-icon="ion:bar-chart-sharp" style="color: secondary;" data-width="50" data-height="50"></span>
        </div> 
        <div class="card-footer d-flex align-items-center justify-content-between">
            <a class="small text-white stretched-link">Wallet</a>
            <div class="small text-white">
                <span class="small text-white false="false><?= $currency['app_currency'] ?>
                                                                        <?= rupiah($saldo['total']) ?></span>
            </div>
        </div>
    </div>
</div>
                        </div>
                        <div class="row">
                            <div class="col-xl-6">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-chart-area me-1"></i>
                                        Area Chart
                                    </div>
                    <div class="hk-legend-wrap mb-10">
                        <div class="hk-legend">
                            <span class="d-10 bg-warning d-inline-block"></span><span>Transportasi</span>
                        </div>
                        <div class="hk-legend">
                            <span class="d-10 bg-danger  d-inline-block"></span><span>Pengiriman</span>
                        </div>
                        <div class="hk-legend">
                            <span class="d-10 bg-green-light-2  d-inline-block"></span><span>Rental</span>
                        </div>
                        <div class="hk-legend">
                            <span class="d-10 bg-primary  d-inline-block"></span><span>Jasa</span>
                        </div>
                    </div>
                                    <div id="mycart" class="echart" style="height: 240px;"></div>
                                </div>
                            </div>
                            <div class="col-xl-6">
                                <div class="card mb-4">
                                    <div class="card-header">
                                        <i class="fas fa-chart-bar me-1"></i>
                                        Bar Chart
                                    </div>
                                    <div class="hk-legend-wrap mb-10">
                        <div class="hk-legend">
                            <span class="d-10 bg-warning rounded-circle d-inline-block"></span><span>Transportasi</span>
                        </div>
                        <div class="hk-legend">
                            <span class="d-10 bg-danger rounded-circle d-inline-block"></span><span>Pengiriman</span>
                        </div>
                        <div class="hk-legend">
                            <span class="d-10 bg-green-light-2 rounded-circle d-inline-block"></span><span>Rental</span>
                        </div>
                        <div class="hk-legend">
                            <span class="d-10 bg-primary rounded-circle d-inline-block"></span><span>Jasa</span>
                        </div>
                    </div>
                    <div id="mycart3" class="echart" style="height: 240px;"></div>
                                </div>
                            </div>
                        </div>

                    </div>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Your Website 2023</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>

 <!-- /Main Content -->
        <?php $this->load->view('includes/footer'); ?>
        <script>
        "use strict"; 
        $.toast({
            heading: 'Selamat Datang!',
            text: '<p>Selamat Datang Di <?=$this->config->item('APPNAME');?>.</p>',
            position: 'bottom-right',
            loaderBg:'#22AF47',
            class: 'jq-toast-success',
            hideAfter: 3500, 
            stack: 6,
            showHideTransition: 'fade'
        });
        </script>

        
    
        <script>
            "use strict"; 
            if( $('#mycart').length > 0 ){
            var eChart_5 = echarts.init(document.getElementById('mycart'));
            var option4 = {
                color: ['#FFF500', '#FF0000', '#69c982','#00A3FF'],     
                tooltip: {
                    show: true,
                    trigger: 'axis',
                    backgroundColor: '#fff',
                    borderRadius:6,
                    padding:6,
                    axisPointer:{
                        lineStyle:{
                            width:0,
                        }
                    },
                    textStyle: {
                        color: '#324148',
                        fontFamily: '-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji"',
                        fontSize: 12
                    }   
                },
                toolbox: {
                    show: false,
                },
                grid: {
                    left: '3%',
                    right: '3%',
                    bottom: '3%',
                    top:'3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        data : ['Jan','Feb','Mar','Apr','Mei','Jun','Jul','Ags','Sep','Okt','Nov','Des'],
                        axisLine: {
                            show:false
                        },
                        axisLabel: {
                            textStyle: {
                                color: '#878787'
                            }
                        },
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLine: {
                            show:false
                        },
                        axisLabel: {
                            textStyle: {
                                color: '#878787'
                            }
                        },
                        splitLine: {
                            show: false,
                        }
                    }
                ],
                series : [
                    {
                        name:'Transportasi',
                        type:'bar',
                         data: [
                                    <?= $jan1[0]['jumlah'] ?>,
                                    <?= $feb1[0]['jumlah'] ?>,
                                    <?= $mar1[0]['jumlah'] ?>,
                                    <?= $apr1[0]['jumlah'] ?>,
                                    <?= $mei1[0]['jumlah'] ?>,
                                    <?= $jun1[0]['jumlah'] ?>,
                                    <?= $jul1[0]['jumlah'] ?>,
                                    <?= $aug1[0]['jumlah'] ?>,
                                    <?= $sep1[0]['jumlah'] ?>,
                                    <?= $okt1[0]['jumlah'] ?>,
                                    <?= $nov1[0]['jumlah'] ?>,
                                    <?= $des1[0]['jumlah'] ?>
                                ]
                    },
                    {
                        name:'Pengiriman',
                        type:'bar',
                         data: [
                                    <?= $jan2[0]['jumlah'] ?>,
                                    <?= $feb2[0]['jumlah'] ?>,
                                    <?= $mar2[0]['jumlah'] ?>,
                                    <?= $apr2[0]['jumlah'] ?>,
                                    <?= $mei2[0]['jumlah'] ?>,
                                    <?= $jun2[0]['jumlah'] ?>,
                                    <?= $jul2[0]['jumlah'] ?>,
                                    <?= $aug2[0]['jumlah'] ?>,
                                    <?= $sep2[0]['jumlah'] ?>,
                                    <?= $okt2[0]['jumlah'] ?>,
                                    <?= $nov2[0]['jumlah'] ?>,
                                    <?= $des2[0]['jumlah'] ?>
                                ]
                    },
                    {
                        name:'Rental',
                        type:'bar',
                         data: [
                                    <?= $jan3[0]['jumlah'] ?>,
                                    <?= $feb3[0]['jumlah'] ?>,
                                    <?= $mar3[0]['jumlah'] ?>,
                                    <?= $apr3[0]['jumlah'] ?>,
                                    <?= $mei3[0]['jumlah'] ?>,
                                    <?= $jun3[0]['jumlah'] ?>,
                                    <?= $jul3[0]['jumlah'] ?>,
                                    <?= $aug3[0]['jumlah'] ?>,
                                    <?= $sep3[0]['jumlah'] ?>,
                                    <?= $okt3[0]['jumlah'] ?>,
                                    <?= $nov3[0]['jumlah'] ?>,
                                    <?= $des3[0]['jumlah'] ?>
                                ]
                    },
                    {
                        name:'Jasa',
                        type:'bar',
                         data: [
                                    <?= $jan4[0]['jumlah'] ?>,
                                    <?= $feb4[0]['jumlah'] ?>,
                                    <?= $mar4[0]['jumlah'] ?>,
                                    <?= $apr4[0]['jumlah'] ?>,
                                    <?= $mei4[0]['jumlah'] ?>,
                                    <?= $jun4[0]['jumlah'] ?>,
                                    <?= $jul4[0]['jumlah'] ?>,
                                    <?= $aug4[0]['jumlah'] ?>,
                                    <?= $sep4[0]['jumlah'] ?>,
                                    <?= $okt4[0]['jumlah'] ?>,
                                    <?= $nov4[0]['jumlah'] ?>,
                                    <?= $des4[0]['jumlah'] ?>
                                ]
                    }
                ]
            };
    
            eChart_5.setOption(option4);
            eChart_5.resize();
        }
        </script>

<script>
    "use strict"; 
    if ($('#mycart3').length > 0) {
        var eChart_5 = echarts.init(document.getElementById('mycart3'));
        var option4 = {
            color: ['#FFF500', '#FF0000', '#69c982', '#00A3FF'],
            tooltip: {
                show: true,
                trigger: 'item',
                backgroundColor: '#fff',
                borderRadius: 6,
                padding: 6,
                textStyle: {
                    color: '#324148',
                    fontFamily: '-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji"',
                    fontSize: 12
                },
                formatter: '{b}: {d}%'
            },
            toolbox: {
                show: false,
            },

            series: [
                {
                    name: 'Transportasi',
                    type: 'pie',
                    radius: '50%',
                    center: ['50%', '50%'],
                    label: {
                        show: true,
                        position: 'inside',
                        formatter: '{d}%' // Hanya menampilkan persentase di dalam lingkaran
                    },
                    data: [
                        { value: <?= $jan1[0]['jumlah'] ?>, name: 'Transportasi (Jan)' },
                        { value: <?= $feb1[0]['jumlah'] ?>, name: 'Transportasi (Feb)' },
                        { value: <?= $mar1[0]['jumlah'] ?>, name: 'Transportasi (Mar)' },
                        { value: <?= $apr1[0]['jumlah'] ?>, name: 'Transportasi (Apr)' },
                        { value: <?= $mei1[0]['jumlah'] ?>, name: 'Transportasi (Mei)' },
                        { value: <?= $jun1[0]['jumlah'] ?>, name: 'Transportasi (Jun)' },
                        { value: <?= $jul1[0]['jumlah'] ?>, name: 'Transportasi (Jul)' },
                        { value: <?= $aug1[0]['jumlah'] ?>, name: 'Transportasi (Ags)' },
                        { value: <?= $sep1[0]['jumlah'] ?>, name: 'Transportasi (Sep)' },
                        { value: <?= $okt1[0]['jumlah'] ?>, name: 'Transportasi (Okt)' },
                        { value: <?= $nov1[0]['jumlah'] ?>, name: 'Transportasi (Nov)' },
                        { value: <?= $des1[0]['jumlah'] ?>, name: 'Transportasi (Des)' }
                    ]


                },

                // Penyesuaian serupa untuk series yang lain
            ]
        };

        eChart_5.setOption(option4);
        eChart_5.resize();
    }
</script>

        
<script src="https://code.iconify.design/2/2.2.1/iconify.min.js"></script>