<%-- 
    Document   : addNewWarehouse
    Created on : 20 Jan, 2017, 7:14:49 PM
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
    <body onLoad="document.getElementById('warehouseName').focus();">
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>
            <!-- PAGE CONTENT -->
            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Admin</a></li>
                    <li><a href="../home/home.htm">Warehouse</a></li>
                    <li><a href="#">Add Warehouse</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" commandName="warehouse" method="POST" name="form1" id="form1">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.addWarehouse"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="warehouseName"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="warehouseName" cssClass="form-control"/>
                                                <span class="help-block">Please enter warehouse name</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="warehouseAddress"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="warehouseAddress" cssClass="form-control"/>
                                                <span class="help-block">Please enter warehouse address</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="warehousePhone"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="warehousePhone" cssClass="form-control" maxlength="12"/>
                                                <span class="help-block">Please enter warehouse contact number</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="warehouseRepName"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="warehouseRepName" cssClass="form-control"/>
                                                <span class="help-block">Please enter warehouse representative name</span>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="warehouseRepEmail"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="warehouseRepEmail" cssClass="form-control"/>
                                                <span class="help-block">Please enter warehouse representative Email</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="warehouseCustServiceEmail"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="warehouseCustServiceEmail" cssClass="form-control"/>
                                                <span class="help-block">Please enter warehouse Customer Service Email</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="active"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <spring:message code="yes" /> 
                                                <form:radiobutton path="active" value="true"/>
                                                <spring:message code="no"/> 
                                                <form:radiobutton path="active" value="false"/>
                                                <span class="help-block">Please select is active?</span>
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
                    warehouseName: {
                        required: true
                    },
                    warehouseAddress: {
                        required: true

                    },
                    warehouseRepName: {
                        required: true
                    },
                    warehousePhone: {
                        required: true,
                        minlength: 12
                    }
                },
                errorPlacement: function(error, element) {
                    error.insertAfter(element);
                }
            });
        </script>
    </body>
</html>
