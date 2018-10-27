<%-- 
Document   : orderProcess
Created on : 11 Jul, 2017, 6:49:32 PM
Author     : Ritesh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- META SECTION -->
        <%@include file="../common/meta.jsp"%>
        <!-- END META SECTION -->

        <!-- CSS INCLUDE -->
        <%@include file="../common/css.jsp"%>
        <!-- EOF CSS INCLUDE -->
    </head>
    <body>
        <!-- START PAGE CONTAINER -->
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>

            <!-- PAGE CONTENT -->
            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Order</a></li>
                    <li><a href="#">Process Order</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->
                    <div class="row">
                        <div class="col-md-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><spring:message code="title.orderDetails"/> For PO Number :<b> ${poNumber}</b><br>Marketplace Order Number :<b> ${order.marketplaceOrderId}</b></h3>
                                    <ul class="panel-controls">
                                        <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                    </ul>
                                </div>
                                <form:form  cssClass="form-horizontal" commandName="order" method="POST" name="form1" id="form1">
                                    <div class="panel-body">
                                        <div class="panel panel-warning">
                                            <div class="panel-body">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <table class="table table-striped table-bordered">
                                                            <tr><td><label><spring:message code="marketplaceName"/></label></td><td> ${order.marketplace.marketplaceName}</td></tr>
                                                            <tr><td><label><spring:message code="orderDate"/></label></td><td>${order.orderDate}</td></tr>
                                                            <tr><td><label><spring:message code="customerName"/></label></td><td>${order.customerName}</td></tr>
                                                            <tr><td><label><spring:message code="shipTo"/></label></td><td>${order.shipToName}<br>${order.shippingAddressLine1}${order.shippingAddressLine2}${order.shippingAddressLine3}<br>${order.city}<br>${order.state}<br>${order.postalCode}</td></tr>
                                                            <tr><td><label><spring:message code="shipByDate"/></label></td><td>${order.shipByDate}</td></tr>
                                                            <tr><td><label><spring:message code="deliveryByDate"/></label></td><td>${order.deliveryByDate}</td></tr>
                                                        </table>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <table class="table table-striped table-bordered">
                                                            <tr><td><label><spring:message code="marketplaceSku"/></label></td><td>${order.marketplaceSku}</td></tr>
                                                            <tr><td><label><spring:message code="marketplaceListingId"/></label></td><td>${order.marketplaceListingId}</td></tr>
                                                            <c:set var="p" value="${order.price}"/> 
                                                            <c:set var="t" value="${order.tax}"/> 
                                                            <tr><td><label><spring:message code="price"/></label></td><td>${p-t}</td></tr>
                                                            <tr><td><label><spring:message code="tax"/></label></td><td>${order.tax}</td></tr>
                                                            <tr><td><label><spring:message code="totalPrice"/></label></td><td>${order.price}</td></tr>
                                                            <tr><td><label><spring:message code="quantityUnshipped"/></label></td><td>${order.quantityUnshipped}</td></tr>
                                                            <tr><td><label><spring:message code="quantityShipped"/></label></td><td>${order.quantityShipped}</td></tr>
                                                            <tr><td><label><spring:message code="productImage"/></label></td><td><img src="${productList[0].imageUrl}" style="height: 100px; width: 100px"/></tr>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h3 class="panel-title">Shipping Details</h3>
                                                    </div>
                                                    <div class="panel-body">

                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <table class="table table-striped table-bordered">
                                                                    <tr><td><label>Order Processed By</label></td><td>${order.processedBy.username}</td></tr>
                                                                    <tr><td><label>Order Processed Date</label></td><td> ${order.processedDate}</td></tr>
                                                                    <tr><td><label>Order Shipped From</label></td><td> ${order.warehouse.warehouseName}</td></tr>
                                                                    <tr><td><label>Click on + to add Tracking</label></td><td>  
                                                                            <span class="add input-group-addon col-md-2" id ="addtracking" title="Add Tracking" onclick="openModel();"><span class="fa fa-plus"></span></span>  
                                                                        </td></tr>
                                                                </table>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <table class="table table-striped table-bordered">
                                                                    <tr><td><label>Shipping Carrier Name</label></td><td> ${order.trackingCarrier}</td></tr>
                                                                    <tr><td><label><spring:message code="trackingId"/></label></td><td> ${order.trackingId}</td></tr>
                                                                    <tr><td><label>Tracking Done By</label></td><td> ${order.trackingBy.username}</td></tr>
                                                                    <tr><td><label>Tracking Date</label></td><td> ${order.trackingDate}</td></tr>
                                                                </table>
                                                            </div>
                                                        </div><br>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h3 class="panel-title">Order History</h3>
                                                    </div>
                                                    <div class="panel-body">

                                                        <div class="row">
                                                            <div class="col-md-12 scrollable">
                                                                <table class="table datatable table-bordered" >
                                                                    <thead>
                                                                        <tr class="">
                                                                            <th width="200px">Updated Date</th>
                                                                            <th width="100px"><spring:message code="quantityUnshipped"/></th>
                                                                            <th width="100px"><spring:message code="quantityShipped"/></th>
                                                                            <th width="100px"><spring:message code="fulfillmentChannel"/></th>
                                                                            <th width="200px"><spring:message code="orderStatus"/></th>
                                                                            <th width="200px"><spring:message code="performanceModsorderStatus"/></th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach items="${orderTrans}" var="orderTrans">
                                                                            <tr>
                                                                                <td><c:out value="${orderTrans.lastUpdatedDate}"/></td>
                                                                                <td><c:out value="${orderTrans.quantityUnshipped}"/></td>
                                                                                <td><c:out value="${orderTrans.quantityShipped}"/></td>
                                                                                <td><c:out value="${orderTrans.fulfillmentChannel}"/></td>
                                                                                <td><c:out value="${orderTrans.orderStatus}"/></td>
                                                                                <td><c:out value="${orderTrans.orderStatus}"/></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div><br>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h3 class="panel-title">Warehouse List of Product</h3>
                                                    </div>
                                                    <div class="panel-body">
                                                        <c:if test="${fn:length(productList) eq 0}">
                                                            <div class="row">
                                                                <label style="color: red;">This product either Shipped or not present in database</label>
                                                            </div>   
                                                        </c:if>
                                                        <c:if test="${fn:length(productList)>0}">
                                                            <div class="row">
                                                                <div class="col-md-12 scrollable">
                                                                    <table class="table  table-bordered" id="processTable" >
                                                                        <thead>
                                                                            <tr class="">
                                                                                <th width="200px"><spring:message code="warehouseName"/></th>
                                                                                <th width="200px"><spring:message code="warehouseMpn"/></th>
                                                                                <th width="200px">Current Feed Date</th>
                                                                                <th width="200px">Current Quantity</th>
                                                                                <th width="200px">Current Price</th>
                                                                                <th width="200px">Current Shipping</th>
                                                                                <th width="200px">Total Cost</th>
                                                                                <th width="150px">Process Order</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <c:forEach items="${productList}" var="productItem"> 
                                                                                <c:set var="titleValue" value=''/> 
                                                                                <c:choose>
                                                                                    <c:when test="${order.warehouse.warehouseId != 0}">
                                                                                        <c:set var="rowColor" value='background-color:  yellow'/>
                                                                                        <c:set var="titleValue" value='Order is already processed'/>
                                                                                    </c:when> 
                                                                                </c:choose>
                                                                                <tr style="${rowColor}" data-warehouse-id="${productItem.warehouseId}" title="${titleValue}">
                                                                                    <td><c:out value="${productItem.warehouseName}"/></td> 
                                                                                    <td><c:out value="${productItem.warehouseMpn}"/></td>
                                                                                    <td><c:out value="${productItem.currentFeedDate}"/></td>
                                                                                    <td ${quantityStyle}><c:out value="${productItem.currentQuantity}"/></td>
                                                                                    <td ${priceStyle}><c:out value="${productItem.currentPrice}"/></td>
                                                                                    <td ${priceStyle}><c:out value="${productItem.shipping}"/></td>
                                                                                    <td ${priceStyle}><c:out value="${productItem.shipping + productItem.currentPrice}"/></td>
                                                                                    <td><button type="button" onclick="onclickFunction(${productItem.warehouseId});" class="btn btn-info getWarehouseId"  ${tktCount>=1 ? 'disabled' : productItem.currentQuantity  == 0  ? 'disabled' :  (order.warehouse.warehouseId != 0)? 'disabled' : ''} >Process</button></td>
                                                                                </tr>
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div><br>
                                                        </c:if>  
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h3 class="panel-title">Ticket History of Product</h3>
                                                    </div>
                                                    <div class="panel-body">
                                                        <c:if test="${fn:length(ticketList)>0}">
                                                            <div class="row">
                                                                <div class="col-md-12 scrollable">
                                                                    <table class="table datatable table-bordered" >
                                                                        <thead>
                                                                            <tr class="">
                                                                                <th width="95px"><spring:message code="ticketNo"/></th> 
                                                                                <th><spring:message code="createdBy"/></th>
                                                                                <th><spring:message code="marketplaceName"/></th>
                                                                                <th><spring:message code="ticketType"/></th>
                                                                                <th><spring:message code="priority"/></th>
                                                                                <th><spring:message code="orderId"/></th>
                                                                                <th><spring:message code="trackingId"/></th>
                                                                                <th><spring:message code="desc"/></th>
                                                                                <th><spring:message code="companyName"/></th>
                                                                                <th><spring:message code="customerName"/></th>
                                                                                <th><spring:message code="custPhoneNumber"/></th>
                                                                                <th><spring:message code="assignedTo"/></th>
                                                                                <th><spring:message code="assignedOn"/></th>
                                                                                <th><spring:message code="ticketStatus"/></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <c:forEach items="${ticketList}" var="tt">
                                                                                <c:choose>
                                                                                    <c:when test="${tt.read==false}"><c:set var="rowStyle" value="bold"/></c:when>
                                                                                    <c:otherwise><c:set var="rowStyle" value=""/></c:otherwise>
                                                                                </c:choose>
                                                                                <tr class="getOrderId clickableRow ${rowStyle}" id="editRow" data-ticket-id="${tt.ticketId}" data-order-id="${tt.orderId}">                    
                                                                                    <td style="font-family: monospace;">${tt.ticketNo}</td>
                                                                                    <td>${tt.createdBy.username}</td>
                                                                                    <td>${tt.marketplace.marketplaceName}</td>
                                                                                    <td>${tt.ticketType.ticketTypeDesc}</td>
                                                                                    <td>${tt.ticketPriority.priorityName}</td>
                                                                                    <td>${tt.orderId}</td>
                                                                                    <td>${tt.trackingId}</td>
                                                                                    <td>${tt.description}</td>
                                                                                    <td>${tt.company.companyName}</td>
                                                                                    <td>${tt.customerName}</td>
                                                                                    <td>${tt.custPhoneNumber}</td>
                                                                                    <td>${tt.assignedTo.username}</td>
                                                                                    <td><fmt:formatDate value="${tt.assignedOn}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                                                                                    <td>${tt.ticketStatus.statusDesc}</td>
                                                                                    <td style="display:none;">${tt.read}</td>
                                                                                </tr>
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div><br>
                                                        </c:if>  
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-footer">
                                            <div class="pull-right">
                                                <button type="button" id="backButton" onclick="backButtonPress();" name="_cancel" class="btn btn-primary" formnovalidate="formnovalidate">Back</button>
                                            </div>
                                        </div>
                                    </div>

                                </form:form>
                            </div>
                        </div>
                    </div>
                    <form name="form2" id="form2" action="" method="Get">
                        <input type="hidden" name="startDate" value="${startDate}"/>   
                        <input type="hidden" name="stopDate" value="${stopDate}"/>   
                    </form>

                    <form name="form3" id="form3" action="" method="Get">
                        <input type="hidden" id="ticketId" name="ticketId"/>
                        <input type="hidden" id="uniqueCode" name="uniqueCode" value="1"/>
                    </form>

                </div>
                <!-- END PAGE CONTENT WRAPPER -->
            </div>
            <!-- END PAGE CONTENT -->
        </div>
        <!-- END PAGE CONTAINER -->

        <!-- dialog box form start -->
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="exampleModalLabel">Add Order Tracking</h4>
                    </div>
                    <div class="modal-body">
                        <form name="form3" id="form3">
                            <div class="form-group">
                                <label for="message-text" class="control-label req"><spring:message code="warehouseName"/></label>
                                <div>
                                    <select id ="warehouseId" name="warehouseId" class="form-control select" onchange="$('#warehouseName').val($('#warehouseId').find('option:selected').text());" required> 
                                        <option value="">-</option>
                                        <c:forEach items="${warehouseList}" var="warehouse">
                                            <option value="${warehouse.warehouseId}">${warehouse.warehouseName}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" id="warehouseName" name="warehouseName"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="message-text" class="control-label req"><spring:message code="trackingId"/>: </label>
                                <input name="trackingId" id="trackingId" Class="req form-control" required/>
                            </div>
                            <div class="form-group">
                                <label for="message-text" class="control-label req"><spring:message code="trackingCarrierName"/>: </label>
                                <input name="trackingCarrier" id="trackingCarrier" Class="req form-control" required/>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-success" onclick="addTracking();">Submit</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- dialog box form end -->


        <%@include file="../common/messagebox.jsp" %>
        <%@include file="../common/script.jsp" %>
        <!-- START THIS PAGE PLUGINS-->
        <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>    
        <script type="text/javascript" src="../js/plugins/pagination/jquery.bootpag.min.js"></script>
        <!-- END THIS PAGE PLUGINS-->
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript" defer="defer">
                            $('#editRow td').click(function() {
                                $('#ticketId').val($(this).parent().data("ticket-id"));
                                $('#form3').prop('action', '../tickets/editTicket.htm');
                                $('#form3').submit();
                            });


                            $('.table').dataTable({
                                "dom": 'f<"toolbar">rt',
//                "order": [3, "desc"],
//                 "ordering": false,
                                "bPaginate": false
                            });

                            $("div.toolbar").html('<div class="pull-left"><c:out value="${rowCount}"/> <spring:message code="rowsFound"/></div>');

                            $('.demo2').bootpag({
                                total: 100,
                                page: 1,
                                maxVisible: 10
                            }).on('page', function(event, num) {
                                $('#pageNo').val(num);
                                //                $('#form1').submit();
                            });


                            $('.select').on('change', function() {
                                if ($(this).val() != "-") {
                                    $(this).valid();
                                    $(this).next('div').addClass('valid');
                                } else {
                                    $(this).next('div').removeClass('valid');
                                }
                            });

                            //            $('.getOrderId').click(function(event) {
                            //                $('#orderId').val($(this).data("order-id"));  
                            //        
                            //            });
                            //                                                    $(".getWarehouseId").click(function(event) {
                            //                                                        var orderId = '${order.marketplaceOrderId}';
                            //                                                        $.ajax({
                            //                                                            url: "../ajax/ajaxProcessWarehouseOrder.htm",
                            //                                                            data: {
                            //                                                                warehouseId: $(this).parent().parent().data("warehouse-id"),
                            //                                                                orderId: orderId
                            //
                            //                                                            },
                            //                                                            dataType: "json",
                            //                                                            success: function(json) {
                            //                                                                if (json.id != 0) {
                            //                                                                    location.reload();
                            //                                                                }
                            //                                                            },
                            //                                                            errors: function(e) {
                            //                                                                alert('error occured');
                            //                                                            }
                            //                                                        });
                            //                                                    });


                            function onclickFunction(x) {
                                var orderId = '${order.marketplaceOrderId}';
                                var poNumber = '${poNumber}';
                                $.ajax({
                                    url: "../ajax/ajaxProcessWarehouseOrder.htm",
                                    data: {
                                        warehouseId: x,
                                        orderId: orderId,
                                        poNumber: poNumber
                                    },
                                    dataType: "json",
                                    success: function(json) {
                                        if (json.id != 0) {
                                            location.reload();
                                        }
                                    },
                                    errors: function(e) {
                                        alert('error occured');
                                    }

                                });
                            }

                            function backButtonPress() {
                                $('#form2').prop('action', '../order/marketplaceOrderList.htm');
                                $('#form2').submit();
                            }





                            var jvalidate = $("#form3").validate({
                                ignore: [],
                                rules: {
                                    warehouseId: {
                                        required: true
                                    },
                                    trackingId: {
                                        required: true
                                    },
                                    trackingCarrierName: {
                                        required: true
                                    }

                                },
                                errorPlacement: function(error, element) {
                                    error.insertAfter(element);
                                }
                            });

                            function  openModel() {
                                $('#exampleModal').modal('show');
                            }


                            //add Sub category
                            function addTracking() {
                                var orderId = '${order.marketplaceOrderId}';
                                $.ajax({
                                    url: "../ajax/addTrackingByOrderId.htm",
                                    data: {
                                        warehouseId: $("#warehouseId").val(),
                                        trackingId: $("#trackingId").val(),
                                        trackingCarrier: $("#trackingCarrier").val(),
                                        marketplaceOrderId: orderId
                                    },
                                    dataType: "json",
//                                        async: false,
                                    success: function(json) {
                                        if (json != null) {
                                            if (json == 'Success') {
                                                alert("Tracking Saved");
                                                location.reload();
                                            } else {
                                                var id = '${order.warehouse.warehouseId}';
                                                if(id == 0){
                                                   alert("Order is not processed yet.") ;
                                                }else{
                                                var w_name = '${order.warehouse.warehouseName}';
                                                alert("Order is processed From Warehouse " + w_name);}
                                            }
                                        }
                                        $('#exampleModal').modal('hide');
                                    },
                                    errors: function(e) {
                                        alert('error occured');
                                    }
                                });
                            }

                            $('#exampleModal').on('hidden.bs.modal', function() {
                                $(this).find('form').trigger('reset');
                            });


        </script>
    </body>
</html>
