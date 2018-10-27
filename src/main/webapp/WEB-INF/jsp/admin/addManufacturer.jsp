<%-- 
    Document   : addManufacturer
    Created on : 24 Feb, 2017, 4:08:47 PM
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

        <style>
            .green{
                color:#008000;
            }
            .red{
                color:red;
            }
        </style>

    </head>
    <body onLoad="document.getElementById('manufacturerName').focus();">
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>
            <!-- PAGE CONTENT -->
            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Admin</a></li>
                    <li><a href="../home/home.htm">Manufacturer</a></li>
                    <li><a href="#">Add Manufacturer</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" commandName="manufacturer" method="POST" name="form1" id="form1">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.addManufacturer"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="manufacturerName"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="manufacturerName" cssClass="form-control"/>
                                                <span class="help-block">Please enter manufacturer name</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="manufacturerCode"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="manufacturerCode" id="manufacturerCode" cssClass="form-control" minLength="3" maxlength="4"/>
                                                <span class="help-block">Please enter manufacturer code</span>
                                            </div>
                                            <label id="codeStatus"></label>
                                            <input type="hidden" name="codeLength" id="codeLength">
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
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript">
        var jvalidate = $("#form1").validate({
            ignore: [],
            rules: {
                manufacturerName: {
                    required: true
                },
                manufacturerCode: {
                    required: true,
                    checkLength: true
                }

            },
            errorPlacement: function(error, element) {
                error.insertAfter(element);
            }
        });

        $(document).ready(function() {
            $.validator.addMethod(
                    "checkLength",
                    function() {
                        var length = $('#codeLength').val();
                        if (length == 0) {
                            return true;
                        } else {
                            return false;
                        }
                    },
                    "Code already used for other manufacturer."
                    );
        });

        $(function() {
            src2 = "getCodeListForAutocomplete.htm";
            $("#manufacturerCode").autocomplete({
                source: function(request, response) {
                    $.ajax({
                        url: src2,
                        dataType: "json",
                        data: {
                            term: request.term
                        },
                        success: function(data) {
                            $('#codeStatus').text(data.label);
                            $('#codeLength').val(data.length);
                            $('#codeStatus').removeClass();
                            $('#codeStatus').addClass(data.className);
                        }
                    });
                },
                minLength: 0
            });
        });
        </script>
    </body>
</html>
