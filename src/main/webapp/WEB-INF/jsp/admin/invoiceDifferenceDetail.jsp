 <%-- 
    Document   : sample
    Created on : 15 May, 2018, 12:39:37 PM
    Author     : Pallavi
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
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>

            <!-- PAGE CONTENT -->
            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home">Home</a></li>
                    <li><a href="../home/home">Admin</a></li>
                    <li><a href="#">Invoice Difference Detail</a></li>
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
                                    <h3 class="panel-title">Invoice Difference Detail</h3>
                                    
                                    <ul class="panel-controls">
                                        <li><a href="#" class="panel-fullscreen" title="Expand"><span class="fa fa-expand"></span></a></li>
                                        <li><a href="#" class="panel-refresh" title="Refresh"><span class="fa fa-refresh"></span></a></li>
                                        <%--<sec:authorize ifAnyGranted="ROLE_BF_EXPORT_EXCEL">--%>
                                          <c:if test="${fn:length(invoice)>0}"><li><a href="#" onclick="$('#excelDownload').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li></c:if>  
                                        <%--</sec:authorize>--%>
                                        
                                    </ul>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12 scrollable">
                                            <table class="table datatable table-bordered">
                                                    <thead>
                                                        <tr>
                                                            <th width="10%">PO_Number</th>
                                                            <th width="10%">Wareouse Name</th>
                                                            <th width="10%">MPN</th>
                                                            <th width="10%">Feed Price</th>
                                                            <th width="10%">Sheeping</th>
                                                            <th width="10%">Invoice Price</th>
                                                            <th width="10%">Difference</th>
                                                        </tr> 
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${invoice}" var="item">
                                                            <c:set value="rowColor" var="rowStyle"/>
                                                            <tr class="${rowStyle}">
                                                                <td><c:out value="${item.poNumber}"/></td>
                                                                <td><c:out value="${item.warehouse.warehouseName}"/></td>
                                                                <td><c:out value="${item.mpn}"/></td>
                                                                <td><c:out value="${item.cwp.price}"/></td>
                                                                <td><c:out value="${item.cwp.shipping}"/></td>
                                                                <td><c:out value="${item.invoicePrice}"/></td> 
                                                                <td><c:out value="${item.invoiceDifferene}"/></td> 
                                                            </tr>
                                                        </c:forEach> 
                                                    </tbody>
                                                </table> 
                                        </div>
                                    </div>
                                </div>
                                <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="button" onclick="backToHome();" class="btn btn-primary">Back</button>
                                        </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- edit form page-->           
                     
                </div>
                                         
               <form name="excelDownload" id="excelDownload" method="post" action="../admin/invoiceDownload.htm">
               </form>

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
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    
        <!-- END THIS PAGE PLUGINS-->        

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>        
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript" defer="defer">
            //function ll take control to home page
            function backToHome(){ 
                window.location.href = '/performanceModsAdminTool/home/home.htm';
            }
 

        </script>
    </body>
</html>
