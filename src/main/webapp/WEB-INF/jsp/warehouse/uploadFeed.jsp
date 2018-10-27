<%-- 
    Document   : uploadFeed
    Created on : 8 Jan, 2016, 5:15:27 PM
    Author     : shrutika
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
                            <form:form cssClass="form-horizontal" modelAttribute="uploadFeed" name="form1" id="form1" method="post" enctype="multipart/form-data" onsubmit="_submit.disabled=true">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.uploadFeed"/></h3>
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
                                                <li>Date formats must be in the specified format only.</li>
                                            </ul>
                                        </div>
                                        <table class="table table-striped table-bordered">
                                            <tr >
                                                <th style="text-align: center;">Field name</th>
                                                <th style="text-align: center;">Description(Format)</th>
                                                <th style="text-align: center;">Numeric</th>
                                                <th style="text-align: center;">Required</th>

                                            </tr>
                                            <tr><td>Manufacturer Name</td><td>Name of the product Manufacturer</td><td style="text-align: center;">No</td><td style="text-align: center;"><b>Yes</b></td></tr>
                                            <tr><td>MPN</td><td>Manufacturer Part Number</td><td style="text-align: center;">No</td><td style="text-align: center;"><b>Yes</b></td></tr>
                                            <tr><td>MAP</td><td>Minimum Advertised Price (decimal)</td><td style="text-align: center;">Yes</td><td style="text-align: center;">No</td></tr>
                                            <tr><td>MSRP</td><td>Manufacturer Suggest Retail Price (decimal)</td><td style="text-align: center;">Yes</td><td style="text-align: center;">No</td></tr>
                                            <tr><td>Price</td><td>Warehouse price (decimal)</td><td style="text-align: center;">Yes</td><td style="text-align: center;"><b>Yes</b></td></tr>
                                            <tr><td>Quantity</td><td>Available quantity in warehouse (integer)</td><td style="text-align: center;">Yes</td><td style="text-align: center;"><b>Yes</b></td></tr>
                                            <tr><td>Condition</td><td>Condition of the product (New/Used/Refurbished/Open Box)</td><td style="text-align: center;">No</td><td style="text-align: center;"><b>Yes</b></td></tr>
                                            <tr><td>Warehouse identification number (WPN)</td><td>Warehouse unique identification number for product / Warehouse Part Number</td><td style="text-align: center;">No</td><td style="text-align: center;"><b>Yes</b></td></tr>
                                            <tr><td>Weight</td><td>Actual weight of the product (decimal)</td><td style="text-align: center;">Yes</td><td style="text-align: center;"><b>Yes</b></td></tr>
                                            <tr><td>Estimated Shipping Weight</td><td>Estimated shipping weight of the product including box and packaging (decimal)</td><td style="text-align: center;">Yes</td><td style="text-align: center;">No</td></tr>
                                            <tr><td>Length</td><td>Length of the product (decimal)</td><td style="text-align: center;">Yes</td><td style="text-align: center;">No</td></tr>
                                            <tr><td>Width</td><td>Width of the product (decimal)</td><td style="text-align: center;">Yes</td><td style="text-align: center;">No</td></tr>
                                            <tr><td>Height</td><td>Height of the product (decimal)</td><td style="text-align: center;">Yes</td><td style="text-align: center;">No</td></tr>
                                            <tr><td>UPC</td><td>Universal Product Code of the product (must be greater than or equal to 11 digits)</td><td style="text-align: center;">Yes</td><td style="text-align: center;">No</td></tr>
                                            <tr><td>Short Description</td><td>Short description or the key features of the product</td><td style="text-align: center;">No</td><td style="text-align: center;">No</td></tr>
                                            <tr><td>Long Description</td><td>Long description of the product including features, all the specification and warranty of the product </td><td style="text-align: center;">No</td><td style="text-align: center;">No</td></tr>
                                            <tr><td>Resize Image</td><td>Small image url of the product for thumbnail purpose. The size must be 500X500 or greater </td><td style="text-align: center;">No</td><td style="text-align: center;">No</td></tr>
                                            <tr><td>Large Image</td><td>Large Image url of the product. Use high resolution image the size must be 800X800 or greater </td><td style="text-align: center;">No</td><td style="text-align: center;">No</td></tr>
                                            <tr><td>Shipping</td><td>Shipping provided by the warehouse </td><td style="text-align: center;">Yes</td><td style="text-align: center;">No</td></tr>
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
                        
                        if(filename.toLowerCase().lastIndexOf(".csv")==-1 && filename.toLowerCase().lastIndexOf(".xls")==-1 && filename.toLowerCase().lastIndexOf(".xlsx")==-1){
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
                        warehouseId:{
                            required: true
                        },
                        multipartFile:{
                            checkFileExtension:true
                        }
                    },
                    messages:{
                        multipartFile:"Please select .xlsx or .csv or .xls file."  
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
                //for dropdown 
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