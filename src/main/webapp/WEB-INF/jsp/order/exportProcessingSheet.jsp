<%-- 
    Document   : exportProcessingSheet
    Created on : 27 Sep, 2017, 6:41:33 PM
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
                    <li><a href="#">Export Processing Sheet</a></li>
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
                                    <h3 class="panel-title"><spring:message code="title.exportProcessingSheet"/></h3>
                                    <ul class="panel-controls">
                                        <%--<sec:authorize ifAnyGranted="ROLE_BF_EXPORT_EXCEL">--%>
                                        <c:if test="${fn:length(listingResult)>0}"><li><a href="#" onclick="$('#excelDownload').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li></c:if>
                                        <%--</sec:authorize>--%>
                                        <a href="#" onclick="$('#excelForm').submit();"></a>
                                      </ul>
                                    </div>
                                    <div class="panel-body">
                                        <div class="panel panel-warning">
                                            <div class="panel-body">
                                                <form name="form1" id="form1">
                                                    <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="warehouseName"/></label>
                                                        <select id ="warehouseId" name="warehouseId" class="form-control select"> 
                                                            <option value="">-</option>
                                                            <c:forEach items="${warehouseList}" var="warehouse">
                                                                <option value="${warehouse.warehouseId}"<c:if test="${warehouseId==warehouse.warehouseId}">selected</c:if>>${warehouse.warehouseName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>                                              
                                                <div class="col-md-2 btn-filter">
                                                    <input type="submit" id="_submit" class="btn-info btn-sm" name="btnSubmit" value="<spring:message code="button.Go"/>"/>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12 scrollable">
                                            <table class="table datatable table-bordered" >
                                                <thead>
                                                    <tr>
                                                        <th><spring:message code="PONumber"/></th>
                                                        <th><spring:message code="warehouseName"/></th>
                                                        <th><spring:message code="manufacturerName"/></th>
                                                        <th><spring:message code="mpn"/></th>
                                                        <th><spring:message code="quantity"/></th>
                                                        <th><spring:message code="price"/></th>
                                                        <th><spring:message code="shipToName"/></th>
                                                        <th><spring:message code="shippingAddress"/></th>
                                                        <th><spring:message code="city"/></th>
                                                        <th><spring:message code="state"/></th>
                                                        <th><spring:message code="postalCode"/></th>
                                                        <th><spring:message code="custPhoneNumber"/></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${listingResult}" var="item">
                                                        <tr>
                                                            <td><c:out value="${item.manufacturerName}"/></td>
                                                            <td><c:out value="${item.warehouseName}"/></td>
                                                            <td><c:out value="${item.manufacturerName}"/></td>
                                                            <td><c:out value="${item.MPN}"/></td>
                                                            <td><c:out value="${item.quantity}"/></td>
                                                            <td><c:out value="${item.price}"/></td>
                                                            <td><c:out value="${item.shipToName}"/></td>
                                                            <td><c:out value="${order.shippingAddressLine1}${order.shippingAddressLine2}${order.shippingAddressLine3}"/></td>
                                                            <td><c:out value="${item.city}"/></td>
                                                            <td><c:out value="${item.state}"/></td>
                                                            <td><c:out value="${item.zip}"/></td>
                                                            <td><c:out value="${item.phoneNumber}"/></td>
                                                            
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <c:if test="${pageCount>0}"><div class="demo2 pull-right"></div></c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <form name="excelForm" id="excelForm" action="../order/processingSheetExcel.htm">
                            <input type="hidden" name="warehouseId" value="${warehouseId}"/>
                        <div id="hiddenService" style="display: none">
                            <select name="warehouseId" id="warehouseId" multiple>
                                <c:forEach items="${warehouseIds}" var="item">
                                    <option selected value="${item}">${item}</option>
                                </c:forEach>
                            </select>
                        </div>
         <!--                        <input type="hidden" name="manufacturerId" value="${manufacturerIds}"/>-->
                    </form>
                    <form name="excelDownload" id="excelDownload" method="post" action="../exportProcessingSheet/excelDownload.htm">
                       <input type="hidden" name="warehouseId" value="${warehouseId}"/>
                   </form>

                    <!-- END PAGE CONTENT WRAPPER -->
                </div>
                <!-- END PAGE CONTENT -->
            </div>
            <!-- END PAGE CONTAINER -->
        </div>

        <%@include file="../common/messagebox.jsp" %>
        <%@include file="../common/script.jsp" %>
        <!-- START THIS PAGE PLUGINS-->
        <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type="text/javascript" src="../js/plugins/pagination/jquery.bootpag.min.js"></script>
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    
        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript" defer="defer">

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
                                            
        </script>
    </body>
</html>
