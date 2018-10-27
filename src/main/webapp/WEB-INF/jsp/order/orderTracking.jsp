<%-- 
    Document   : orderTracking
    Created on : 8 Mar, 2018, 4:10:58 PM
    Author     : Ritesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Admin</a></li>
                    <li><a href="../home/home.htm">Order</a></li>
                    <li><a href="#">Order Tracking</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" commandName="order" method="POST" name="form1" id="form1">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.orderTracking"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="warehouseName"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <select id ="warehouseId" name="warehouseId" class="form-control select" onchange="$('#warehouseName').val($('#warehouseId').find('option:selected').text());"> 
                                                    <option value="">-</option>
                                                    <c:forEach items="${warehouseList}" var="warehouse">
                                                        <option value="${warehouse.warehouseId}">${warehouse.warehouseName}</option>
                                                    </c:forEach>
                                                </select>
                                                <input type="hidden" id="warehouseName" name="warehouseName"/>
                                            </div>
                                        </div>
                                            <br>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="marketplaceOrderId"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="marketplaceOrderId" cssClass="form-control"/>
                                                <span class="help-block">Please enter marketplace order Id</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="trackingId"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="trackingId" cssClass="form-control"/>
                                                <span class="help-block">Please enter tracking Id</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="trackingCarrierName"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="trackingCarrier" cssClass="form-control"/>
                                                <span class="help-block">Please select tracking carrier</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit" id="_submit" name="btnSubmit"  class="btn btn-success">Submit</button>
                                            <button type="submit" id="_cancel" name="_cancel" class="btn btn-primary" formnovalidate="formnovalidate"><spring:message code="button.Cancel"/></button>
                                        </div>  
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../common/messagebox.jsp" %>
        <%@include file="../common/script.jsp" %>
         <!-- START THIS PAGE PLUGINS-->
            <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>
            <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
            <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
            <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-file-input.js"></script>
            <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>            

            <!-- END THIS PAGE PLUGINS-->

            <!-- START TEMPLATE -->
            <script type="text/javascript" src="../js/plugins.js"></script>
            <script type="text/javascript" src="../js/actions.js"></script>
            <!-- END TEMPLATE -->
        <script type="text/javascript">
            var jvalidate = $("#form1").validate({
                ignore: [],
                rules: {
                    marketplaceOrderId: {
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
            
            $('.select').on('change', function() {
                    if($(this).val()!=""){
                        $(this).valid();
                        $(this).next('div').addClass('valid');
                    } else {
                        $(this).next('div').removeClass('valid');
                    }
                });
        </script>
    </body>
</html>
