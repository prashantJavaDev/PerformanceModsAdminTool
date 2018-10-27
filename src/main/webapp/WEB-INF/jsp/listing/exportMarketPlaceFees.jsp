<%-- 
    Document   : exportMarketPlaceFees
    Created on : 25 May, 2018, 3:02:37 PM
    Author     : Pallavi
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
                    <li><a href="../home/home.htm">Listing</a></li>
                    <li><a href="#">Export MarketPlace Fees</a></li>
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
                                    <h3 class="panel-title">Export MarketPlace Fees List</h3>
                                    <ul class="panel-controls">
                                        <li><a href="#" class="panel-fullscreen" title="Expand"><span class="fa fa-expand"></span></a></li>
                                        <li><a href="#" class="panel-refresh" title="Refresh"><span class="fa fa-refresh"></span></a></li>
                                        <%--<sec:authorize ifAnyGranted="ROLE_BF_EXPORT_EXCEL">--%>
                                        <c:if test="${fn:length(feesList)>0}"><li><a href="#" onclick="$('#excelDownload').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li></c:if>
                                        <%--</sec:authorize>--%>
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
                                                <div class="col-md-2 btn-filter">
                                                    <input type="submit"  class="btn-info btn-sm" name="btnSubmit" value="<spring:message code="button.Go"/>"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12 scrollable">
                                                <table class="table dataTable table-bordered" >
                                                    <thead>
                                                        <tr>
                                                            <th width="10%">MarketPlace Listing ID</th>
                                                            <th width="10%">MarketPlace SKU</th>
                                                            <th width="10%">Current Price</th>
                                                            <th width="10%">Commission</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${feesList}" var="item">
                                                            <c:set value="rowColor" var="rowStyle"/>
                                                            <tr class="${rowStyle}">
                                                                <td> <c:out value="${item.marketplaceListingId}"/></td>
                                                                <td><c:out value="${item.sku}"/></td>
                                                                <td><c:out value="${item.currentPrice}"/></td>
                                                                <td><c:out value="${item.commission}"/></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                                <c:if test="${pageCount>0}"><div class="demo2 pull-right"></div></c:if>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-footer">
                                            <div class="pull-right">
                                                <button type="button" onclick="backToHome();" class="btn btn-primary">Back</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <form name="form2" id="form2" action="" method="Get">
                            <input type="hidden" id="OrderId" name="marketplaceOrderId"/>   
                        </form>
                        <form name="excelDownload" id="excelDownload" method="post" action="../listing/exportMarketPlaceFeesExel.htm">
                            <input type="hidden" id="OrderId" name="marketplaceId" value="${marketplaceId}"/>   
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
                                                
                                                
                                                function backToHome(){ 
                                                            window.location.href = '/performanceModsAdminTool/listing/exportMarketPlaceFees.htm';
                                                    }



                                                $('.select').on('change', function() {
                                                    if ($(this).val() != "-") {
                                                        $(this).valid();
                                                        $(this).next('div').addClass('valid');
                                                    } else {
                                                        $(this).next('div').removeClass('valid');
                                                    }
                                                });
        </script>
    </body>
</html>