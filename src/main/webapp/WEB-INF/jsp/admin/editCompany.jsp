<%-- 
    Document   : editCompany
    Created on : 11 May, 2017, 7:09:15 PM
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
                    <li><a href="../home/home.htm">Company</a></li>
                    <li><a href="#">Edit Company</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" commandName="company" method="POST" name="form1" id="form1">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.editCompany"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="companyName"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="companyName" cssClass="form-control"/>
                                                <span class="help-block">Please enter company name</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="ownerName"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="ownerName" cssClass="form-control"/>
                                                <span class="help-block">Please enter company owner name</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="companyContactNumber"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="contactNumber" cssClass="form-control" maxlength="12"/>
                                                <span class="help-block">Please enter company contact number</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="companyCountryName"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="countryName" cssClass="form-control"/>
                                                <span class="help-block">Please enter company country name</span>
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
                companyName: {
                    required: true
                },
                ownerName: {
                    required: true

                },
                countryName: {
                    required: true
                },
                contactNumber: {
                    required: true
                }
            },
            errorPlacement: function(error, element) {
                error.insertAfter(element);
            }
        });
        </script>
    </body>
</html>

