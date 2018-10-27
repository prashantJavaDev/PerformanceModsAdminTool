<%-- 
    Document   : mapProduct
    Created on : 30 May, 2018, 4:14:57 PM
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
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Admin</a></li>
                    <li><a href="../home/home.htm">Product</a></li>
                    <li><a href="#">Map Product</a></li>
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
                                    <h3 class="panel-title"><spring:message code="title.mapProduct"/></h3>
                                </div>
                                <div class="panel-body">
                                    <div class="panel panel-warning">
                                        <div class="panel-body">
                                            <form method="POST" name="form1" id="form1">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="col-md-3">
                                                            <label><spring:message code="performanceModsMpnOriginal"/></label>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <input type="text" name="performanceModsMpn" id="performanceModsMpn" value="${performanceModsMpn}" Class="form-control"/>
                                                            <span class="help-block">Please enter Original performanceMods MPN</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <br><br>
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="col-md-3">
                                                            <label><spring:message code="performanceModsMpnMap"/></label>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <input type="text" name="performanceModsMpn" id="performanceModsMpn" value="${performanceModsMpn}" Class="form-control"/>
                                                            <span class="help-block">Please enter Map performanceMods MPN</span>
                                                        </div>
                                                    </div>
                                                </div>

                                            </form>
                                        </div>
                                    </div>


                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit" id="_submit" name="submit"  class="btn btn-success"/>MAP</button>
                                            <button type="submit" id="_cancel" name="_cancel" class="btn btn-primary" formnovalidate="formnovalidate"><spring:message code="button.Cancel"/></button>
                                        </div>
                                    </div>

                                </div>

                            </div>
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
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>
        <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>            
        <!-- END THIS PAGE PLUGINS-->        

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>        
        <script type="text/javascript" src="../js/actions.js"></script>
        <script type="text/javascript" >
            //MPN autocomplete
            $(function() {
                src1 = "getMpnListForAutocomplete.htm";
                $("#performanceModsMpn").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: src1,
                            dataType: "json",
                            data: {
                                term: request.term
                            },
                            success: function(data) {
                                response(data);
                            }
                        });
                    },
                    minLength: 5
                });
            });


            var jvalidate = $("#form1").validate({
                ignore: [],
                rules: {
                    performanceModsMpn: {
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