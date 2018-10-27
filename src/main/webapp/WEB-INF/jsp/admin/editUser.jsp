<%--
    Document   : userEdit
    Created on : 18 Aug, 2014, 10:23:38 AM
    Author     : Akil Mahimwala
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
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Admin</a></li>
                    <li><a href="../home/home.htm">User</a></li>
                    <li><a href="#">Edit User</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" commandName="user" method="POST" name="form1" id="form1">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.editUser"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="username"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:input path="username" cssClass="form-control"/>
                                                <span class="help-block">Please enter username</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="email"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:input path="emailId" cssClass="form-control"/>
                                                <span class="help-block">Please enter email</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="role"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:select path="role.roleId" id="roleId" cssClass="form-control select">
                                                    <form:option value="" label="Nothing selected"/>
                                                    <form:options items="${roleList}" itemLabel="roleName" itemValue="roleId"/>
                                                </form:select>
                                                <span class="help-block">Please select role</span>
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
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="outsideAccess"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <spring:message code="yes"/> <form:radiobutton path="outsideAccess" value="true"/>
                                                <spring:message code="no"/> <form:radiobutton path="outsideAccess" value="false"/>
                                                <span class="help-block">Please select is outside access?</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-2 col-xs-12 control-label">
                                                <spring:hasBindErrors name="user">
                                                    <span class="text-danger">
                                                        <form:errors path="*"/>
                                                    </span>
                                                </spring:hasBindErrors>
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
        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript">
            var jvalidate = $("#form1").validate({
                ignore: [],
                rules: {
                    'role.roleId': {
                        required: true
                    },
                    username: {
                        required: true
                    },
                    password: {
                        required: true
                    }
                },
                errorPlacement: function(error, element) {
                    if (element.hasClass('select')) {
                        error.insertAfter(element.next(".bootstrap-select"));
                        element.next("div").addClass("error");
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
            //for dropdown 
            $('.select').on('change', function() {
                if ($(this).val() != "") {
                    $(this).valid();
                    $(this).next('div').addClass('valid');
                } else {
                    $(this).next('div').removeClass('valid');
                }
            });

        </script>
    </body>
</html>