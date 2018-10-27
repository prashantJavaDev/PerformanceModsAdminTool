<%-- 
    Document   : changePassword
    Created on : 11 Sep, 2015, 1:12:12 PM
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
        <script>
            //function to match new password and confirm password
            function checkPassword() {
                if (document.getElementById("oldPassword").value == document.getElementById("newPassword").value) {
                    alert("<spring:message code="msg.oldPasswordMatch"/>");
                    return false;
                } else {
                    if (document.getElementById("newPassword").value != document.getElementById("confirmPassword").value || document.getElementById("newPassword").value == "") {
                        alert("<spring:message code="msg.passwordMatch"/>");
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        </script>
    </head>
    <body onload="document.getElementById('oldPassword').focus();">
        <!-- START PAGE CONTAINER -->
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>

            <!-- PAGE CONTENT -->
            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="#">Change Password</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" commandName="password" method="POST" name="form1" id="form1">
                                <div class="panel panel-default">
                                    <form:hidden path="userId"/>
                                    <form:hidden path="username"/>

                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.updatePassword"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="oldPassword"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:password path="oldPassword" cssClass="form-control"/>
                                                <span class="help-block">Please enter old password</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="newPassword"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <form:password path="newPassword" cssClass="form-control" maxlength="8"/>
                                                <span class="help-block">Please enter new password</span>
                                            </div>
                                        </div>
                                         
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="confirmNewPassword"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <input type="password" id="confirmPassword" Class="form-control" maxlength="8" minlength="8"/>
                                                <span class="help-block">Please enter new password again</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-2 col-xs-12 control-label">
                                                <spring:hasBindErrors name="password">
                                                    <span class="text-danger">
                                                        <form:errors path="*"/>
                                                    </span>
                                                </spring:hasBindErrors>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit" name="btnSubmit" class="btn btn-success" onclick="return checkPassword();"><spring:message code="button.Update"/></button>
                                            <button type="submit" name="_cancel" class="btn btn-primary"><spring:message code="button.Cancel" /></button>
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
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->
        <script type="text/javascript">
             var jvalidate = $("#form1").validate({
            ignore: [],
            rules: {
                newPassword: {
                    required: true,
                    minlength: "8"
                } 
            },
            message:{
                
                newPassword:{"password length must be of 8 and alphanumeric"}
            },
            errorPlacement: function(error, element) {
                error.insertAfter(element);
            }
        });
 
        </script>
    </body>
</html>
