<%-- 
Document   : orderList
Created on : 1 Jun, 2017, 3:31:27 PM
Author     : Ritesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
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
                    <li><a href="#">Order List</a></li>
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
                                    <h3 class="panel-title"><spring:message code="title.orderList"/></h3>
                                    <ul class="panel-controls">
                                        <sec:authorize ifAnyGranted="ROLE_BF_EXPORT_ORDER_LIST">
                                            <c:if test="${fn:length(marketplaceOrderList)>0}"><li><a href="#" onclick="$('#excelForm').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li></c:if>
                                        </sec:authorize>
                                        <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                    </ul>

                                </div>
                                <form name="form1" id="form1" method="post">
                                    <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
                                    <div class="panel-body">

                                        <div class="panel panel-warning">
                                            <div class="panel-body">
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="marketplaceName"/></label>
                                                        <select id ="marketplaceId" name="marketplaceId" class="form-control select"> 
                                                            <option value="0">-</option>
                                                            <c:forEach items="${marketplaceList}" var="marketplace">
                                                                <option value="${marketplace.marketplaceId}"<c:if test="${marketplaceId==marketplace.marketplaceId}">selected</c:if>>${marketplace.marketplaceName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="PONumber"/></label>
                                                        <input type="text" name="poNumber" id="poNumber" value="${poNumber}" Class="form-control"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="marketplaceOrderId"/></label>
                                                        <input type="text" name="marketplaceOrderId" id="marketplaceOrderId" value="${marketplaceOrderId}" Class="form-control"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="customerName"/></label>
                                                        <input type="text" name="customerName" id="customerName" value="${customerName}" Class="form-control"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="marketplaceSku"/></label>
                                                        <input type="text" name="marketplaceSku" id="marketplaceSku" value="${marketplaceSku}" Class="form-control"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="marketplaceListingId"/></label>
                                                        <input type="text" name="marketplaceListingId" id="marketplaceListingId" value="${marketplaceListingId}" Class="form-control"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2" style="margin-top: 20px">
                                                    <div class="form-group">
                                                        <label><spring:message code="orderStatus"/></label>
                                                        <select id ="orderStatus" name="orderStatus" class="form-control select">
                                                            <option value="">-</option>
                                                            <option <c:if test="${orderStatus=='Shipped'}">selected</c:if> value="Shipped">Shipped</option>
                                                            <option <c:if test="${orderStatus=='Unshipped'}">selected</c:if> value="Unshipped">Unshipped</option>
                                                            <option <c:if test="${orderStatus=='Canceled'}">selected</c:if> value="Canceled">Canceled</option>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-2" style="margin-top: 20px">
                                                        <div class="form-group">
                                                            <label><spring:message code="fulfillmentChannel"/></label>
                                                        <select id ="fulfillmentChannel" name="fulfillmentChannel" class="form-control select"> 
                                                            <option value="">-</option>
                                                            <option <c:if test="${fulfillmentChannel=='Seller'}">selected</c:if> value="Seller">Seller</option>
                                                            <option <c:if test="${fulfillmentChannel=='Amazon'}">selected</c:if> value="Amazon">Amazon</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-2" style="margin-top: 20px">
                                                    <div class="form-group">
                                                        <label>Order Date from</label>
                                                        <input type="text" id="startDate" name="startDate" value="${startDate}" Class="form-control datepicker"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2" style="margin-top: 20px">
                                                    <div class="form-group">
                                                        <label>Order Date to</label>
                                                        <input type="text" id="stopDate" name="stopDate" value="${stopDate}" Class="form-control datepicker"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-1 btn-filter" style="margin-top: 40px">
                                                    <input type="submit"  class="btn-info btn-sm" name="btnSubmit" value="<spring:message code="button.Go"/>"/>
                                                </div>
                                                <div class="col-md-1 btn-filter" style="margin-top: 40px; margin-left: -50px">
                                                    <input type="button"  class="btn-info btn-sm" name="btnClear" value="Clear" onclick="clearTextFields();"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12 scrollable">
                                                <table class="table datatable table-bordered" >
                                                    <thead>
                                                        <tr>
                                                            <th width="50px"><spring:message code="poNumber"/></th>
                                                            <%--<th width="50px"><spring:message code="marketplaceName"/></th> --%>
                                                            <th width="100px"><spring:message code="marketplaceOrderId"/></th>
                                                            <th width="100px"><spring:message code="marketplaceSku"/></th>
                                                            <th width="100px"><spring:message code="marketplaceListingId"/></th>
                                                            <th width="100px"><spring:message code="orderDate"/></th>
                                                            <th width="50px"><spring:message code="customerName"/></th>
                                                            <th width="50px"><spring:message code="customerPhoneNo"/></th>
                                                            <th width="40px"><spring:message code="quantityUnshipped"/></th>
                                                            <th width="40px"><spring:message code="quantityShipped"/></th>
                                                            <th width="30px"><spring:message code="price"/></th>
                                                            <th width="30px"><spring:message code="fulfillmentChannel"/></th>
                                                            <th width="30px"><spring:message code="orderStatus"/></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${marketplaceOrderList}" var="item">

                                                            <c:set var="titleValue" value=''/>

                                                            <fmt:formatDate var="shipdate" value="${item.shipByDate}" pattern="yyyy-MM-dd"/>
                                                            <c:set var="today_date" value="<%=new java.util.Date()%>"/>

                                                            <fmt:formatDate var="todaydt" value="${today_date}" pattern="yyyy-MM-dd"/>
                                                            <c:if test="${item.shipByDate != null && shipdate == todaydt} && item.orderStatus == 'Unshipped'">
                                                                <c:set var="rowColor" value='background-color: yellow'/>
                                                                <c:set var="titleValue" value='order ship day'/>
                                                            </c:if>
                                                            <c:if test="${item.shipByDate != null && (shipdate < todaydt) && item.orderStatus == 'Unshipped'}">
                                                                <c:set var="rowColor" value='background-color: red'/>
                                                                <c:set var="titleValue" value=' order ship day cross'/>
                                                            </c:if> 

                                                            <tr id="marketplaceSku" style="${rowColor}" data-marketplacesku="${item.marketplaceSku}" 
                                                                data-marketplaceorderid="${item.marketplaceOrderId}" class="clickableRow" title="${titleValue}" >

                                                                <td><c:out value="${item.poNumber}"/></td>
                                                                <%--<td><c:out value="${item.marketplace.marketplaceName}"/></td> --%>
                                                                <td><c:out value="${item.marketplaceOrderId}"/></td>
                                                                <td><c:out value="${item.marketplaceSku}"/></td>
                                                                <td><c:out value="${item.marketplaceListingId}"/></td>
                                                                <td><fmt:formatDate value="${item.orderDate}" pattern="dd-MM-yyyy HH:mm:ss"/></td>
                                                                <td><c:out value="${item.customerName}"/></td>
                                                                <td><c:out value="${item.customerPhoneNo}"/></td>
                                                                <td><c:out value="${item.quantityUnshipped}"/></td>
                                                                <td><c:out value="${item.quantityShipped}"/></td>
                                                                <td><c:out value="${item.price}"/></td>                                                                
                                                                <td><c:out value="${item.fulfillmentChannel}"/></td>
                                                                <td><c:out value="${item.orderStatus}"/></td>
                                                                <c:set var="rowColor" value='background-color: none'/>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                                <c:if test="${pageCount>0}"><div class="demo2 pull-right"></div></c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <form name="form2" id="form2" action="" method="Get">
                            <input type="hidden" id="OrderId" name="marketplaceOrderId"/>   
                            <input type="hidden" name="start" value="${startDate}"/>
                        <input type="hidden" name="stop" value="${stopDate}"/>   
                        <input type="hidden" id="Sku" name="marketplaceSku"/>
                    </form>

                    <form name="excelForm" id="excelForm" method="get" action="../order/marketPlaceOrderListExcelDownload.htm">
                        <input type="hidden" name="marketplaceId" value="${marketplaceId}"><br/>
                        <input type="hidden" name="poNumber" value="${poNumber}"><br/>
                        <input type="hidden" name="marketplaceOrderId" value="${marketplaceOrderId}"><br/>
                        <input type="hidden" name="customerName" value="${customerName}"><br/>
                        <input type="hidden" name="marketplaceSku" value="${marketplaceSku}"><br/>
                        <input type="hidden" name="marketplaceListingId" value="${marketplaceListingId}"><br/>
                        <input type="hidden" name="orderStatus" value="${orderStatus}"><br/>
                        <input type="hidden" name="fulfillmentChannel" value="${fulfillmentChannel}"><br/>
                        <input type="hidden" name="start" value="${startDate}"/>   
                        <input type="hidden" name="stop" value="${stopDate}"/>   
                    </form> 

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
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>    
        <script type="text/javascript" src="../js/plugins/pagination/jquery.bootpag.min.js"></script>
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    
        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->
        <script type="text/javascript" defer="defer">

                                                $(document).ready(function() {

                                                    $('#marketplaceSku td').click(function() {
                                                        $('#Sku').val($(this).parent().data("marketplacesku"));
                                                        $('#OrderId').val($(this).parent().data("marketplaceorderid"));
                                                        $('#form2').prop('action', '../order/orderDetails.htm');
                                                        $('#form2').submit();
                                                    });
                                                });
                                                //Pagination
                                                $('.table').dataTable({
                                                    "dom": 'f<"toolbar">rt',
                                                    "order": [],
                                                    "bPaginate": false
                                                });

                                                $("div.toolbar").html('<div class="pull-left"><c:out value="${rowCount}"/> <spring:message code="rowsFound"/></div>');

                                                $('.demo2').bootpag({
                                                    total: ${pageCount},
                                                    page: ${pageNo},
                                                    maxVisible: 10
                                                }).on('page', function(event, num) {
                                                    $('#pageNo').val(num);

                                                    $('#form1').submit();
                                                });




                                                $('.select').on('change', function() {
                                                    if ($(this).val() != "-") {
                                                        $(this).valid();
                                                        $(this).next('div').addClass('valid');
                                                    } else {
                                                        $(this).next('div').removeClass('valid');
                                                    }
                                                });
                                                
                                                function clearTextFields(){
                                                    $('#poNumber').val('');
                                                    $('#marketplaceOrderId').val('');
                                                    $('#customerName').val('');
                                                    $('#marketplaceSku').val('');
                                                    $('#marketplaceListingId').val('');
//                                                    $('#orderStatus').selectpicker('refresh');
//                                                    $('#fulfillmentChannel').selectpicker('refresh');
//                                                    $('#marketplaceId').selectpicker('refresh');
                                                    $('#stopDate').val('');
                                                    $('#startDate').val('');
                                                    
                                                    document.getElementById("orderStatus").selectedIndex="";
//                                                    document.getElementById("fulfillmentChannel").selectedIndex="";
//                                                    document.getElementById("marketplaceId").selectedIndex="";
                                                }

        </script>
    </body>
</html>