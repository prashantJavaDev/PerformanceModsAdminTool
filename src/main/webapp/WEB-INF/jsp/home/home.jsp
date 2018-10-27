<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- META SECTION -->
        <%@include file="../common/meta.jsp"%>
        <!-- END META SECTION -->

        <!-- CSS INCLUDE -->
        <%@include file="../common/css.jsp"%>
        <link rel="stylesheet" type="text/css" id="theme" href="../css/theme-default.css"/>
        <!-- EOF CSS INCLUDE -->
    </head>

    <body>
        <div id="page_wrapper">
            <!-- START PAGE CONTAINER -->
            <div class="page-container page-navigation-toggled page-container-wide">
                <%@include file="../common/sidebar.jsp" %>

                <!-- PAGE CONTENT -->
                <div class="page-content">
                    <%@include file="../common/topbar.jsp" %>

                    <ul class="breadcrumb">
                        <li><a href="#">Home</a></li>
                    </ul>
                    <!-- END BREADCRUMB --> 

                    <!-- END PAGE TITLE -->

                    <!-- PAGE CONTENT WRAPPER -->
                    <sec:authentication property="principal" var="user"/>
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->
                    <div class="page-content-wrap">
                        <div class="row">
                            <div class="col-md-12">
                                <form class="form-horizontal">
                                    <div class="panel panel-default tabs">                            
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li><a href="#tab-first" role="tab" data-toggle="tab">Marketplace</a></li>
                                            <li class="active"><a href="#tab-second" role="tab" data-toggle="tab">Tickets</a></li>
                                            <li><a href="#tab-third" role="tab" data-toggle="tab">Website</a></li>
                                        </ul>
                                        <div class="panel-body tab-content">
                                            <div class="tab-pane " id="tab-first">
                                                <div class="row">
                                                    <div class="col-md-7">

                                                        <!-- START USERS ACTIVITY BLOCK -->
                                                        <div class="panel panel-default">
                                                            <div class="panel-heading">
                                                                <div class="panel-title-box">
                                                                    <h3>Order Un-shipped</h3>
                                                                    <span>(Un-shipped order count of last 7 days)</span>
                                                                </div>                                    
                                                                <ul class="panel-controls" style="margin-top: 2px;">
                                                                    <li><a href="#" class="panel-fullscreen"><span class="fa fa-expand"></span></a></li>
                                                                    <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                                                    <li class="dropdown">
                                                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-cog"></span></a>                                        
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="#" class="panel-collapse"><span class="fa fa-angle-down"></span> Collapse</a></li>
                                                                            <li><a href="#" class="panel-remove"><span class="fa fa-times"></span> Remove</a></li>
                                                                        </ul>                                        
                                                                    </li>                                        
                                                                </ul>                                    
                                                            </div>                                
                                                            <div class="panel-body padding-0">
                                                                <div id="chart3" style="height: 300px;"></div>
                                                            </div>                                    
                                                        </div>
                                                        <!-- END USERS ACTIVITY BLOCK -->

                                                    </div>
                                                </div>
                                                <!--end of the row-->

                                                <!-- panel for order--->
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h3>Order Information</h3>
                                                    </div>
                                                    <div class="panel-body">
                                                        <div class="form-group">
                                                            <!--row for order shipped unshipped tile-->
                                                            <div class="row">
                                                                <div class="col-md-3">

                                                                    <!-- START WIDGET TOTAL ORDER -->
                                                                    <div class="widget widget-default widget-item-icon">
                                                                        <div>                                    
                                                                            <div class="widget-title">Total Orders</div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${totalOrders}</div>
                                                                        </div>
                                                                    </div>                            
                                                                </div>         
                                                                <!-- END WIDGET TOTAL ORDER -->


                                                                <div class="col-md-3">

                                                                    <!-- START WIDGET ORDER SHIPPED -->
                                                                    <div class="widget widget-default widget-item-icon">
                                                                        <div>                                    
                                                                            <div class="widget-title">Orders Shipped</div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${totalShippedOrders}</div>
                                                                        </div>
                                                                    </div>                          
                                                                    <!-- END WIDGET ORDER SHIPPED -->

                                                                </div>
                                                                <div class="col-md-3">

                                                                    <!-- START WIDGET ORDER UNSHIPPED -->
                                                                    <div class="widget widget-default widget-item-icon">
                                                                        <div>                                    
                                                                            <div class="widget-title">Orders Unshipped</div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${totalUnshippedOrders}</div>
                                                                        </div>
                                                                    </div>                            
                                                                    <!-- END WIDGET ORDER UNSHIPPED -->

                                                                </div>
                                                                <div class="col-md-3">

                                                                    <!-- START WIDGET ORDER SHIPPED AFTER DEADLINE -->
                                                                    <div class="widget widget-default widget-item-icon">
                                                                        <div>                                    
                                                                            <div class="widget-title">Orders Shipped After deadline </div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${lateShipped}</div>
                                                                        </div>
                                                                    </div>                            
                                                                    <!-- END WIDGET ORDER SHIPPED AFTER DEADLINE -->
                                                                </div>
                                                            </div>
                                                            <!--end of order shipped unshipped-->
                                                        </div>
                                                    </div>
                                                </div>

                                                <!---end of panel--->

                                                <!---Start of Panel for product info---->
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h3>Product Information</h3>
                                                    </div>
                                                    <div class="panel-body">
                                                        <div class="form-group">
                                                            <!--row for Product listing-->
                                                            <div class="row">
                                                                <div class="col-md-3">

                                                                    <!-- START WIDGET PRODUCT OERPRICED -->
                                                                    <div class="widget widget-default widget-item-icon">
                                                                        <div>                                    
                                                                            <div class="widget-title">Over Priced Product</div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${overPriced}</div>
                                                                        </div>
                                                                    </div>                            
                                                                </div>         
                                                                <!-- END WIDGET PRODUCT OVER PRICED -->


                                                                <div class="col-md-3">

                                                                    <!-- START WIDGET PRODUCT UNDER PRICED -->
                                                                    <div class="widget widget-default widget-item-icon">
                                                                        <div>                                    
                                                                            <div class="widget-title">Under Priced Product</div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${underPriced}</div>
                                                                        </div>
                                                                    </div>                          
                                                                    <!-- END WIDGET PRODUCT UNDER PRICED -->

                                                                </div>
                                                                <div class="col-md-3">
                                                                    <!-- START WIDGET PRODUCT LOW COUNT -->
                                                                    <div class="widget widget-default widget-item-icon">
                                                                        <div>                                    
                                                                            <div class="widget-title">Low Count Product</div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${lowCount}</div>
                                                                        </div>
                                                                    </div>                            
                                                                    <!-- END WIDGET PRODUCT LOW COUNR -->
                                                                </div>
                                                                <div class="col-md-3">
                                                                    <!-- START WIDGET Product Added Manually -->
                                                                    <div class="widget widget-default widget-item-icon">
                                                                        <div>                                    
                                                                            <div class="widget-title">Product Added Manually</div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${manuallyCreated}</div>
                                                                        </div>
                                                                    </div>                            
                                                                    <!-- END WIDGET Product Added Manually -->
                                                                </div>
                                                            </div>
                                                            <!--end of row Product listing-->
                                                        </div>
                                                    </div>
                                                </div>
                                                <!---End of Panel for product info---->


                                            </div>
                                            <div class="tab-pane active" id="tab-second">
                                                <!-- START WIDGETS -->    
                                                <div class="panel-body">
                                                    <div class="panel panel-default">
                                                        <div class="panel-heading">
                                                            <h3>Tickets Status</h3>
                                                        </div>
                                                        <div class="panel-body">
                                                            <div class="form-group">
                                                                <div class="row">
                                                                    <div class="col-md-2">

                                                                        <!-- START WIDGET SLIDER -->
                                                                        <div class="widget widget-default widget-item-icon">
                                                                            <div>                                    
                                                                                <div class="widget-title">Tickets</div>
                                                                                <div class="widget-subtitle">Open</div>
                                                                                <div class="widget-int num-count">${openTicketCount.openTickets}</div>
                                                                            </div>
                                                                        </div>                            
                                                                    </div>         
                                                                    <!-- END WIDGET SLIDER -->


                                                                    <div class="col-md-2">

                                                                        <!-- START WIDGET MESSAGES -->
                                                                        <div class="widget widget-default widget-item-icon">
                                                                            <div>                                    
                                                                                <div class="widget-title">Returns</div>
                                                                                <div class="widget-subtitle">Open</div>
                                                                                <div class="widget-int num-count">${openTicketCount.orderReturn}</div>
                                                                            </div>
                                                                        </div>                          
                                                                        <!-- END WIDGET MESSAGES -->

                                                                    </div>
                                                                    <div class="col-md-2">

                                                                        <!-- START WIDGET REGISTRED -->
                                                                        <div class="widget widget-default widget-item-icon">
                                                                            <div>                                    
                                                                                <div class="widget-title"> Cancellation</div>
                                                                                <div class="widget-subtitle">Open</div>
                                                                                <div class="widget-int num-count">${openTicketCount.cancellation}</div>
                                                                            </div>
                                                                        </div>                            
                                                                        <!-- END WIDGET REGISTRED -->

                                                                    </div>
                                                                    <div class="col-md-2">

                                                                        <!-- START WIDGET REGISTRED -->
                                                                        <div class="widget widget-default widget-item-icon">
                                                                            <div>                                    
                                                                                <div class="widget-title">Refund</div>
                                                                                <div class="widget-subtitle">Open</div>
                                                                                <div class="widget-int num-count">${openTicketCount.refund}</div>
                                                                            </div>
                                                                        </div>                            
                                                                        <!-- END WIDGET REGISTRED -->
                                                                    </div>

                                                                    <div class="col-md-2">
                                                                        <!-- START WIDGET REGISTRED -->
                                                                        <div class="widget widget-default widget-item-icon">
                                                                            <div>                                    
                                                                                <div class="widget-title">Claims</div>
                                                                                <div class="widget-subtitle">Open</div>
                                                                                <div class="widget-int num-count">${openTicketCount.claims}</div>
                                                                            </div>
                                                                        </div>                            
                                                                        <!-- END WIDGET REGISTRED -->
                                                                    </div>
                                                                    <div class="col-md-2">

                                                                        <!-- START WIDGET CLOCK -->
                                                                        <div class="widget widget-default widget-item-icon">
                                                                            <div>                                    
                                                                                <div class="widget-title">Others</div>
                                                                                <div class="widget-subtitle">Open</div>
                                                                                <div class="widget-int num-count">${openTicketCount.other}</div>
                                                                            </div>
                                                                        </div>                        
                                                                        <!-- END WIDGET CLOCK -->

                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6">

                                                            <!-- START STACKED AREA CHART -->
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading">
                                                                    <ul class="panel-controls">
                                                                        <li><a href="#" class="panel-fullscreen"><span class="fa fa-expand"></span></a></li>
                                                                        <li><rect></rect></li>
                                                                    </ul>
                                                                </div>
                                                                <div class="panel-body">
                                                                    <div id="chart1" style="height: 300px;"></div>
                                                                </div>
                                                            </div>
                                                            <!-- END STACKED AREA CHART -->
                                                        </div>    

                                                        <div class="col-md-6">

                                                            <!-- START STACKED AREA CHART -->
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading">
                                                                    <ul class="panel-controls">
                                                                        <li><a href="#" class="panel-fullscreen"><span class="fa fa-expand"></span></a></li>
                                                                        <li><rect></rect></li>
                                                                    </ul>
                                                                </div>
                                                                <div class="panel-body">
                                                                    <div id="chart2" style="height: 300px;"></div>
                                                                </div>
                                                            </div>
                                                            <!-- END STACKED AREA CHART -->
                                                        </div>    
                                                    </div>
                                                </div>
                                                <!-- END WIDGETS -->
                                            </div>
                                            <!---Start of third tab website--->
                                            <div class="tab-pane " id="tab-third">

                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h3>Product Status on Website</h3>
                                                    </div>
                                                    <div class="panel-body">
                                                        <div class="form-group">
                                                            <!--row for product details like no of product, missing data etc-->
                                                            <div class="row">
                                                                <div class="col-md-3">

                                                                    <!-- START WIDGET Total Products -->
                                                                    <div id="totalProduct" class="widget widget-default widget-item-icon" onclick="totalProductAvailable();">
                                                                        <div>                                    
                                                                            <div class="widget-title">Total Products Available</div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${totalProducts}</div>
                                                                        </div>
                                                                    </div>                            
                                                                </div>         
                                                                <!-- END WIDGET Total Products  -->
                                                                <div class="col-md-3">

                                                                    <!-- START WIDGET Total Main-Categories-->
                                                                    <div class="widget widget-default widget-item-icon">
                                                                        <div>                                    
                                                                            <div class="widget-title">Total Main-Categories listed</div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${mainCategories}</div>
                                                                        </div>
                                                                    </div>                            
                                                                    <!-- END WIDGET Total Main-Categories -->

                                                                </div>
                                                                <div class="col-md-3">

                                                                    <!-- START WIDGET Total Sub-Categories -->
                                                                    <div class="widget widget-default widget-item-icon">
                                                                        <div>                                    
                                                                            <div class="widget-title">Total Sub-Categories listed</div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${subCategories}</div>
                                                                        </div>
                                                                    </div>                            
                                                                    <!-- END WIDGET Total Sub-Categories -->
                                                                </div>
                                                                <div class="col-md-3">

                                                                    <!-- START WIDGET Total Sub-Categories -->
                                                                    <div id="productMissing" class="widget widget-default widget-item-icon"  onclick="missingProduct();">
                                                                        <div>                                    
                                                                            <div class="widget-title">Total Product with Missing data</div>
                                                                            <!--<div class="widget-subtitle">Open</div>-->
                                                                            <div class="widget-int num-count">${productWithMissigData}</div>
                                                                        </div>
                                                                    </div>                            
                                                                    <!-- END WIDGET Total Sub-Categories -->
                                                                </div>
                                                            </div>
                                                            <!--end of product details -->
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-6">

                                                        <!--START USERS ACTIVITY BLOCK--> 
                                                        <div class="panel panel-default">
                                                            <div class="panel-heading">
                                                                <div class="panel-title-box">
                                                                    <h3>Product Listed On website</h3>
                                                                    <span></span>
                                                                </div>                                    
                                                                <ul class="panel-controls" style="margin-top: 2px;">
                                                                    <li><a href="#" class="panel-fullscreen"><span class="fa fa-expand"></span></a></li>
                                                                    <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                                                    <li class="dropdown">
                                                                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-cog"></span></a>                                        
                                                                        <ul class="dropdown-menu">
                                                                            <li><a href="#" class="panel-collapse"><span class="fa fa-angle-down"></span> Collapse</a></li>
                                                                            <li><a href="#" class="panel-remove"><span class="fa fa-times"></span> Remove</a></li>
                                                                        </ul>                                        
                                                                    </li>                                        
                                                                </ul>                                    
                                                            </div>                                
                                                            <div class="panel-body padding-0">
                                                                <div id="chart4" style="height: 300px;"></div>
                                                            </div>                                    
                                                        </div>
                                                        <!--END USERS ACTIVITY BLOCK--> 

                                                    </div>
                                                    <div class="col-md-6">

                                                            <!-- START STACKED AREA CHART -->
                                                            <div class="panel panel-default">
                                                                <div class="panel-heading">
                                                                    <ul class="panel-controls">
                                                                        <li><a href="#" class="panel-fullscreen"><span class="fa fa-expand"></span></a></li>
                                                                        <li><rect></rect></li>
                                                                    </ul>
                                                                </div>
                                                                <div class="panel-body">
                                                                    <div id="chart5" style="height: 300px;"></div>
                                                                </div>
                                                            </div>
                                                            <!-- END STACKED AREA CHART -->
                                                        </div>
                                                     
                                                </div>
                                                <!--end of the row-->
                                            </div>
                                        </div>
                                    </div>                                
                                </form>
                            </div>
                            <form name="form2" id="form2" action="" method="Get">
                                <input type="hidden" id="OrderId" name="marketplaceOrderId"/>   
                            </form>
                        </div>                    
                    </div>
                    <!-- END PAGE CONTENT WRAPPER -->
                </div>
                <!-- END PAGE CONTENT -->
            </div>
            <!-- END PAGE CONTAINER -->

            <%@include file="../common/messagebox.jsp" %>

            <%@include file="../common/script.jsp" %>

            <!-- START THIS PAGE PLUGINS-->  
            <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>        
            <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
            <script type="text/javascript" src="../js/plugins/owl/owl.carousel.min.js"></script>                 
            <!-- END THIS PAGE PLUGINS-->        

            <!-- START TEMPLATE -->
            <script type="text/javascript" src="../js/plugins.js"></script>        
            <script type="text/javascript" src="../js/actions.js"></script>
            <script type="text/javascript" src="../js/highcharts.js"></script>
            <script type="text/javascript" src="../js/exporting.js"></script>
            <script type="text/javascript" src="../js/Chart.js"></script>

            <!-- END TEMPLATE -->
            <style>

                #productMissing :hover {
                    
                    transform: scale(1.1);
                    }
                
                #totalProduct :hover{
                    transform: scale(1.1);
                }
             </style>
                <script>


                                                                        var chart1, chart2, chart3, chart4,chart5; // globally available
                                                                        $(document).ready(function() {
                                                                            chart1 = new Highcharts.Chart({
                                                                                chart: {
                                                                                    renderTo: 'chart1',
                                                                                    type: 'column' // change with your choice
                                                                                },
                                                                                title: {
                                                                                    text: 'Ticket Summary'
                                                                                },
                                                                                xAxis: {
                                                                                    categories: ['Last Month', 'This Month', ]
                                                                                            // Should be replace with JSON
                                                                                },
                                                                                yAxis: {
                                                                                    allowDecimals: false,
                                                                                    min: 0,
                                                                                    title: {
                                                                                        text: 'Total Tickets'
                                                                                    }, stackLabels: {
                                                                                        enabled: true,
                                                                                        formatter: function() {
                                                                                            return  this.stack;
                                                                                        }
                                                                                    }
                                                                                },
                                                                                tooltip: {
                                                                                    formatter: function() {
                                                                                        return '<b>' + this.x + '</b><br/>' +
                                                                                                this.series.name + ': ' + this.y + '<br/>' +
                                                                                                'Total: ' + this.point.stackTotal;
                                                                                    }
                                                                                },
                                                                                plotOptions: {
                                                                                    series: {
                                                                                        stacking: 'normal'
                                                                                    }
                                                                                },
                                                                                series: [//Should be replace with JSON
                                                                                    {
                                                                                        name: 'Open',
                                                                                        data: ${open1}
                                                                                    },
                                                                                    {
                                                                                        name: 'Closed',
                                                                                        data: ${closed1}
                                                                                    },
                                                                                    {
                                                                                        name: 'Canceled',
                                                                                        data: ${canceled1}
                                                                                    }
                                                                                ]
                                                                            });

                                                                            //Chart 2
                                                                            chart2 = new Highcharts.Chart({
                                                                                chart: {
                                                                                    renderTo: 'chart2',
                                                                                    type: 'column' // change with your choice
                                                                                },
                                                                                title: {
                                                                                    text: 'Ticket Summary'
                                                                                },
                                                                                xAxis: {
                                                                                    categories: ['Yesterday', 'Today', ]
                                                                                            // Should be replace with JSON
                                                                                },
                                                                                yAxis: {
                                                                                    allowDecimals: false,
                                                                                    min: 0,
                                                                                    title: {
                                                                                        text: 'Total Tickets'
                                                                                    }, stackLabels: {
                                                                                        enabled: true,
                                                                                        formatter: function() {
                                                                                            return  this.stack;
                                                                                        }
                                                                                    }
                                                                                },
                                                                                tooltip: {
                                                                                    formatter: function() {
                                                                                        return '<b>' + this.x + '</b><br/>' +
                                                                                                this.series.name + ': ' + this.y + '<br/>' +
                                                                                                'Total: ' + this.point.stackTotal;
                                                                                    }
                                                                                },
                                                                                plotOptions: {
                                                                                    series: {
                                                                                        stacking: 'normal'
                                                                                    }
                                                                                },
                                                                                series: [//Should be replace with JSON
                                                                                    {
                                                                                        name: 'Open',
                                                                                        data: ${open2}
                                                                                    },
                                                                                    {
                                                                                        name: 'Closed',
                                                                                        data: ${closed2}
                                                                                    },
                                                                                    {
                                                                                        name: 'Canceled',
                                                                                        data: ${canceled2}
                                                                                    }
                                                                                ]
                                                                            });

                                                                            //Chart 3
                                                                            chart3 = new Highcharts.Chart({
                                                                                chart: {
                                                                                    renderTo: 'chart3',
                                                                                    type: 'column' // change with your choice
                                                                                },
                                                                                title: {
                                                                                    text: 'Order Un-shipped'
                                                                                },
                                                                                xAxis: {
                                                                                    categories: ${days}
                                                                                    // Should be replace with JSON
                                                                                },
                                                                                yAxis: {
                                                                                    allowDecimals: false,
                                                                                    min: 0,
                                                                                    title: {
                                                                                        text: 'Total Un-shipped Orders'
                                                                                    }, stackLabels: {
                                                                                        enabled: true,
                                                                                        formatter: function() {
                                                                                            return  this.stack;
                                                                                        }
                                                                                    }
                                                                                },
                                                                                tooltip: {
                                                                                    formatter: function() {
                                                                                        return '<b>' + this.x + '</b><br/>' +
                                                                                                this.series.name + ': ' + this.y + '<br/>' +
                                                                                                'Total: ' + this.point.stackTotal;
                                                                                    }
                                                                                },
                                                                                plotOptions: {
                                                                                    series: {
                                                                                        stacking: 'normal'
                                                                                    }
                                                                                },
                                                                                series: [//Should be replace with JSON
                                                                                    {
                                                                                        name: 'Amazon',
                                                                                        data: ${totalCount7}
                                                                                    },
                                                                                    {
                                                                                        name: 'Walmart',
                                                                                        data: ${closed2}
                                                                                    },
                                                                                    {
                                                                                        name: 'NewEgg',
                                                                                        data: ${canceled2}
                                                                                    }
                                                                                ]
                                                                            });

                                                                            //Chart 4
                                                                            chart4 = new Highcharts.Chart({
                                                                                chart: {
                                                                                    renderTo: 'chart4',
                                                                                    type: 'column' // change with your choice
                                                                                },
                                                                                title: {
                                                                                    text: 'Products On Website'
                                                                                },
                                                                                xAxis: {
                                                                                    categories: ${days}
                                                                                    // Should be replace with JSON
                                                                                },
                                                                                yAxis: {
                                                                                    allowDecimals: false,
                                                                                    min: 0,
                                                                                    title: {
                                                                                        text: 'Total Number Of products'
                                                                                    }, stackLabels: {
                                                                                        enabled: true,
                                                                                        formatter: function() {
                                                                                            return  this.stack;
                                                                                        }
                                                                                    }
                                                                                },
                                                                                tooltip: {
                                                                                    formatter: function() {
                                                                                        return '<b>' + this.x + '</b><br/>' +
                                                                                                this.series.name + ': ' + this.y + '<br/>' +
                                                                                                'Total: ' + this.point.stackTotal;
                                                                                    }
                                                                                },
                                                                                plotOptions: {
                                                                                    series: {
                                                                                        stacking: 'normal'
                                                                                    }
                                                                                },
                                                                                series: [//Should be replace with JSON
                                                                                    {
                                                                                        name: 'Admin Tool',
                                                                                    },
                                                                                    {
                                                                                        name: 'The Brands depot',
                                                                                    },
                                                                                    {
                                                                                        name: 'HeadSet Gallery',
                                                                                    }
                                                                                ]
                                                                            });

                                                                        });
                                                                        
                                                                        //chart5
                                                                        chart5 = new Highcharts.Chart({
                                                                                chart: {
                                                                                    renderTo: 'chart5',
                                                                                    type: 'column' // change with your choice
                                                                                },
                                                                                title: {
                                                                                    text: 'Products On Website'
                                                                                },
                                                                                xAxis: {
                                                                                    categories: ['Yesterday', 'Today', ]
                                                                                            // Should be replace with JSON
                                                                                },
                                                                                yAxis: {
                                                                                    allowDecimals: false,
                                                                                    min: 0,
                                                                                    title: {
                                                                                        text: 'Total Number Of products'
                                                                                    }, stackLabels: {
                                                                                        enabled: true,
                                                                                        formatter: function() {
                                                                                            return  this.stack;
                                                                                        }
                                                                                    }
                                                                                },
                                                                                tooltip: {
                                                                                    formatter: function() {
                                                                                        return '<b>' + this.x + '</b><br/>' +
                                                                                                this.series.name + ': ' + this.y + '<br/>' +
                                                                                                'Total: ' + this.point.stackTotal;
                                                                                    }
                                                                                },
                                                                                plotOptions: {
                                                                                    series: {
                                                                                        stacking: 'normal'
                                                                                    }
                                                                                },
                                                                                series: [//Should be replace with JSON
                                                                                    {
                                                                                        name: 'Admin Tool',
                                                                                    },
                                                                                    {
                                                                                        name: 'The Brands depot',
                                                                                    },
                                                                                    {
                                                                                        name: 'HeadSet Gallery',
                                                                                    }
                                                                                ]
                                                                            });

                                                                        function  fullUnshippedList() {
                                                                            window.location.href = '/performanceModsAdminTool/order/showUnshippedOrderList.htm';
                                                                        }

                                                                        function  lowCountProductList() {
                                                                            window.location.href = '/performanceModsAdminTool/product/lowCountProduct.htm';
                                                                        }


                                                                        function  missingProduct() {
                                                                            window.location.href = '/performanceModsAdminTool/product/missingProductDataList.htm';
                                                                        }
                                                                        
                                                                        function  totalProductAvailable() {
                                                                            window.location.href = '/performanceModsAdminTool/product/listProduct.htm';
                                                                        }

                                                                        $('#marketPlaceId td').click(function() {
                                                                            $('#OrderId').val($(this).parent().data("marketplace-order-id"));
                                                                            $('#form2').prop('action', '../order/orderDetails.htm');
                                                                            $('#form2').submit();
                                                                        });





                                                                        var ctx = document.getElementById("myChart").getContext('2d');
                                                                        var myChart = new Chart(ctx, {
                                                                            type: 'bar',
                                                                            data: {
                                                                                //                                                                            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
                                                                                labels: ${days},
                                                                                datasets: [{
                                                                                        label: 'Unshipped order',
                                                                                        //                                                                                     data: [12, 19, 3, 5, 2, 3],
                                                                                        data: ${totalCount7},
                                                                                        backgroundColor: [
                                                                                            'rgba(255, 99, 132, 0.2)',
                                                                                            'rgba(255, 99, 132, 0.2)',
                                                                                            'rgba(255, 99, 132, 0.2)',
                                                                                            'rgba(255, 99, 132, 0.2)',
                                                                                            'rgba(255, 99, 132, 0.2)',
                                                                                            'rgba(255, 99, 132, 0.2)',
                                                                                            'rgba(255, 99, 132, 0.2)'
                                                                                                    //                                                                                        'rgba(54, 162, 235, 0.2)',
                                                                                                    //                                                                                        'rgba(255, 206, 86, 0.2)',
                                                                                                    //                                                                                        'rgba(75, 192, 192, 0.2)',
                                                                                                    //                                                                                        'rgba(153, 102, 255, 0.2)',
                                                                                                    //                                                                                        'rgba(255, 159, 64, 0.2)'
                                                                                        ],
                                                                                        borderColor: [
                                                                                            'rgba(255,99,132,1)',
                                                                                            'rgba(255,99,132,1)',
                                                                                            'rgba(255,99,132,1)',
                                                                                            'rgba(255,99,132,1)',
                                                                                            'rgba(255,99,132,1)',
                                                                                            'rgba(255,99,132,1)',
                                                                                            'rgba(255,99,132,1)'
                                                                                                    //                                                                                        'rgba(54, 162, 235, 1)',
                                                                                                    //                                                                                        'rgba(255, 206, 86, 1)',
                                                                                                    //                                                                                        'rgba(75, 192, 192, 1)',
                                                                                                    //                                                                                        'rgba(153, 102, 255, 1)',
                                                                                                    //                                                                                        'rgba(255, 159, 64, 1)'
                                                                                        ],
                                                                                        borderWidth: 1
                                                                                    }]
                                                                            },
                                                                            options: {
                                                                                scales: {
                                                                                    xAxes: [{
                                                                                            barPercentage: 0.6
                                                                                        }],
                                                                                    yAxes: [{
                                                                                            gridLines: {
                                                                                                color: "rgba(0, 0, 0, 0)",
                                                                                                lineWidth: 0
                                                                                            },
                                                                                            ticks: {
                                                                                                beginAtZero: true
                                                                                            }
                                                                                        }]
                                                                                }
                                                                            }
                                                                        });

                </script>
        </body>
    </html>
