<%-- 
    Document   : tempUploadResult
    Created on : 12 Jun, 2014, 1:55:46 PM
    Author     : sushma
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
                    <li><a href="../home/home.htm">Product</a></li>
                    <li><a href="#">Upload Feeds</a></li>
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
                                <form action="saveFeed.htm" name="form1" method="post" onsubmit="_submit1.disabled=true">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Bad data summary</h3>
                                        <ul class="panel-controls">
                                            <li><a href="#" onclick="$('#excelForm').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li>
                                        </ul>
                                    </div>
                                    <div class="panel-body">

                                        <input type="hidden" name="warehouseId" value="${warehouseId}">

                                        <div class="col-md-4 scrollable">
                                            <c:set var="badCnt" value="0"/>
                                            <c:set var="goodCnt" value="0"/>
                                            <c:forEach items="${lst}" var="item">
                                                <c:choose>
                                                    <c:when test="${item.status==false}">
                                                        <c:set var="badCnt" value="${item.productCount}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="goodCnt" value="${item.productCount}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <table class="table table-bordered" >
                                                <thead>
                                                    <tr>
                                                        <th width="80px">Total data</th>
                                                        <th width="80px">Good</th>
                                                        <th width="80px">Bad</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>${goodCnt+badCnt}</td>
                                                        <td>${goodCnt}</td>
                                                        <td>${badCnt}</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <c:if test="${badCnt>0}"><div class="col-md-12"><h6 style="color: red">Please note only the good data will be uploaded. Please export the Bad data list now. You will need to fix this and upload it again later.</h6></div></c:if>

                                            <div class="panel-footer">
                                                <div class="pull-right">
                                                <c:if test="${goodCnt>0}"><button type="submit" name="_submit1"  class="btn btn-success"/>Upload</button></c:if>
                                                <button type="submit" id="_cancel" name="_cancel" class="btn btn-primary"><spring:message code="button.Cancel"/></button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- END PAGE CONTENT WRAPPER -->
            </div>
            <!-- END PAGE CONTENT -->
        </div>
        <!-- END PAGE CONTAINER -->
        <form name="excelForm" id="excelForm" method="post" action="../warehouse/badProductExcel.htm">
            <input type="hidden" name="warehouseName" value="${warehouseName}">
        </form>
        <%@include file="../common/messagebox.jsp" %>

        <%@include file="../common/script.jsp" %>
        <!-- START THIS PAGE PLUGINS-->        
        <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>        
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <!-- END THIS PAGE PLUGINS-->        

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>        
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->
    </body>
</html>
