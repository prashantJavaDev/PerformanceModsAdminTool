<%-- 
    Document   : marketplaceFeesUpload
    Created on : 20 Mar, 2017, 12:44:06 PM
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
                    <li><a href="../home/home.htm">Order</a></li>
                    <li><a href="../home/home.htm">Tracking</a></li>
                    <li><a href="#">Bulk Tracking</a></li>
                 </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" modelAttribute="uploadFeed" name="form1" id="form1" method="post" enctype="multipart/form-data">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.uploadBulkTracking"/></h3>
                                    </div>

                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="marketplaceName"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <select id ="marketplaceId" name="marketplaceId" class="form-control select"> 
                                                    <option value="">-</option>
                                                    <c:forEach items="${marketplaceList}" var="marketplace">
                                                        <option value="${marketplace.marketplaceId}">${marketplace.marketplaceName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label">Select File</label>
                                            <div class="col-md-6 col-xs-12">
                                                <input type="file" name="multipartFile" id="multipartFile" class="fileinput btn-danger"  data-filename-placement="inside"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel">
                                        <br>
                                        <div class="col-md-12">
                                            <h3>Required file format</h3>
                                            <ul><li>File must be a csv file with the following fields.</li>
                                                <li>Please note that the file must have each field wrapped in " for it to upload correctly.</li>
                                            </ul>
                                        </div>
                                        <table class="table table-striped table-bordered">
                                            <tr >
                                                <th style="text-align: center;">Field name</th>
                                                <th style="text-align: center;">Description(Format)</th>
                                                <th style="text-align: center;">Numeric</th>
                                                <th style="text-align: center;">Required</th>
                                            </tr>
                                            <tr><td>Warehouse Name</td><td>Name Of Warehouse</td><td style="text-align: center;">Yes</td><td style="text-align: center;"><b>Yes</b></td></tr>
                                            <tr><td>Order ID</td><td>Order ID of Market Place</td><td style="text-align: center;">No</td><td style="text-align: center;"><b>Yes</b></td></tr>
                                            <tr><td>Tracking Number</td><td>Tracking Number provided by shipping carrier</td><td style="text-align: center;">No</td><td style="text-align: center;"><b>Yes</b></td></tr>
                                            <tr><td>Tracking Carrier</td><td>Package shipping carrier</td><td style="text-align: center;">No</td><td style="text-align: center;"><b>Yes</b></td></tr>
                                        </table>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit" id="_submit" name="submit"  class="btn btn-success"/>Upload</button>
                                            <button type="submit" id="_cancel" name="_cancel" class="btn btn-primary" formnovalidate="formnovalidate"><spring:message code="button.Cancel"/></button>
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
            <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-file-input.js"></script>
            <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>            
            <!-- END THIS PAGE PLUGINS-->

            <!-- START TEMPLATE -->
            <script type="text/javascript" src="../js/plugins.js"></script>
            <script type="text/javascript" src="../js/actions.js"></script>
            <!-- END TEMPLATE -->

            <script type="text/javascript">
                $(document).ready(function(){
                    $.validator.addMethod(
                    "checkFileExtension",
                    function (){
                        var filename = $('#multipartFile').val();
                    
                        if(filename.toLowerCase().lastIndexOf(".csv")==-1){
                            return false
                        }else{
                            return true;
                        }
                    }
                );
                });
        
                var jvalidate = $("#form1").validate({
                    ignore: [],
                    rules: {                                            
                        
                        multipartFile:{
                            checkFileExtension:true
                        },
                        marketplaceId:{
                            required:true
                        }
                    },
                    messages:{
                        multipartFile:"Please select .csv file."  
                    },
                    
                    errorPlacement: function(error, element) {
                        if (element.hasClass('select')) {
                            error.insertAfter(element.next(".bootstrap-select"));
                            element.next("div").addClass("error");
                        } else if(element.hasClass('fileinput')){
                            error.insertAfter($(element).parent());
                        } else {
                            error.insertAfter(element);
                        }
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
