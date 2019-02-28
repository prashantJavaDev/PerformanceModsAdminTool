<%-- 
    Document   : deleteMarketplace
    Created on : 6 May, 2017, 6:24:22 PM
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
                    <li><a href="../home/home.htm">Manufacturer</a></li>
                    <li><a href="#">Delete Manufacturer </a></li>
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
                                    <h3 class="panel-title">Select manufacturer for delete</h3>
                                    <ul class="panel-controls">
                                        <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                    </ul>
                                </div>
                                <form name="form1" id="form1" method="post">
                                    <div class="panel-body">
                                        <div class="panel panel-warning">
                                            <div class="panel-body">
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="manufacturerName"/></label>
                                                        <select name="manufacturerId" id="manufacturerId" Class="form-control">
                                                            <option value="">-All- </option>
                                                            <c:forEach items="${manufacturerList}" var="item">
                                                                <option value="${item.manufacturerId}" <c:if test="${manufacturerId==item.manufacturerId}">selected</c:if>>${item.manufacturerName}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <input type="hidden" id="marketplaceName" name="marketplaceName"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-3 btn-filter">
                                                    <input type="submit" id="_submit" class="btn-info btn-sm" name="go" value="Go" />
                                                </div>
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

        <%@include file="../common/messagebox.jsp" %>
        <%@include file="../common/script.jsp" %>
        <!-- START THIS PAGE PLUGINS-->
        <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>    

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript" defer="defer">
                                                            var jvalidate = $("#form1").validate({
                                                                ignore: [],
                                                                rules: {
                                                                    marketplaceId: {
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
                                                            $('.select').on('change', function() {
                                                                if ($(this).val() != "-") {
                                                                    $(this).valid();
                                                                    $(this).next('div').addClass('valid');
                                                                } else {
                                                                    $(this).next('div').removeClass('valid');
                                                                }
                                                            });

        </script>
    </body>
</html>
