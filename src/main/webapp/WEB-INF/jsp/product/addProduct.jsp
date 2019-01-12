<%-- 
Document   : searchProducts
Created on : 27 Jan, 2016, 11:25:24 AM
Author     : shrutika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../common/meta.jsp"%>
        <%@include file="../common/css.jsp"%>

        <script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
        <script>tinymce.init({selector: '#shortDesc, #longDesc'});</script>
    </head>
    <body onLoad="document.getElementById('performanceModsMpn').focus();">
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>

            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Admin</a></li>
                    <li><a href="../home/home.htm">Products</a></li>
                    <li><a href="#">Add Product</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->
                    <div class="row">
                        <div class="col-md-12">
                            <form:form name="form1" id="form1" commandName="product" method="POST" enctype="multipart/form-data">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.addProduct"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="mpn"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="manufacturerMpn" cssClass="form-control" />
                                                        <span class="help-block">Please enter manufacturer mpn</span>
                                                    </div>
                                                </div>
                                            </div> 
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="manufacturerName"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:select path="manufacturer.manufacturerId" id="manufacturerId" cssClass="form-control">
                                                            <form:option value="" label="Nothing selected" />
                                                            <form:options items="${manufacturerList}" itemLabel="manufacturerName" itemValue="manufacturerId"/>
                                                        </form:select>
                                                        <span class="help-block">Please select manufacturer name</span>
                                                    </div>  
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="productTitle"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="productTitle" cssClass="form-control"/>
                                                        <span class="help-block">Please enter title of product</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="productName"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="productName" cssClass="form-control"/>
                                                        <span class="help-block">Please enter product name</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="map"/> ($)</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="productMap" cssClass="form-control"/>
                                                        <span class="help-block">Please enter MAP value</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="msrp"/> ($)</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="productMsrp" cssClass="form-control"/>
                                                        <span class="help-block">Please enter product MSRP</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="weight"/> (lbs)</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="productWeight" cssClass="form-control"/>
                                                        <span class="help-block">Please enter product weight</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="estimatedShipWeight"/> (lbs)</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="estShippingWt" cssClass="form-control"/>
                                                        <span class="help-block">Please enter product est ship weight</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="length"/> (Inches)</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="productLength" cssClass="form-control"/>
                                                        <span class="help-block">Please enter product length</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="rel col-md-4 col-xs-6 control-label"><spring:message code="width"/> (Inches)</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="productWidth" cssClass="form-control"/>
                                                        <span class="help-block">Please enter product width</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="rel col-md-4 col-xs-6 control-label"><spring:message code="height"/> (Inches)</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="productHeight" cssClass="form-control"/>
                                                        <span class="help-block">Please enter product height</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="upc"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="upc" minlength="12" cssClass="form-control"/>
                                                        <span class="help-block">Please enter product UPC</span>
                                                        <button id="upcButtonDiv" type="button" class="btn-info btn-sm" onclick="showAddUPCDiv();" style="display: none">Add New UPC</button>
                                                    </div>
                                                    <div id="addAnotherUPCDiv" class="col-md-6 col-xs-6" style="display: none">
                                                        <input type="text" id="addNewUPC" class="form-control"/> <button id="addUPC" type="button" class="btn-info btn-sm" onclick="addNewUpc();">Add</button>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="asin"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="asin" minlength="10" cssClass="form-control" />
                                                        <span class="help-block">Please enter product ASIN</span>
                                                        <button id="asinButtonDiv" type="button" class="btn-info btn-sm" onclick="showAddAsinDiv();" style="display: none">Add New ASIN</button>
                                                    </div>
                                                    <div id="addAnotherASINDiv" class="col-md-6 col-xs-6" style="display: none">
                                                        <input type="text" id="addNewASIN" class="form-control"/> <button id="addASIN" type="button" class="btn-info btn-sm" onclick="addNewAsin();">Add</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>



                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="mainCategory"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:select path="mainCategory.mainCategoryId" cssClass="form-control select" onchange="showSubCategoryList();">
                                                            <form:option value="0" label="Nothing selected" />
                                                            <form:options items="${mainCategoryList}" itemLabel="mainCategoryDesc" itemValue="mainCategoryId"/>
                                                        </form:select>
                                                        <span class="help-block">Please select main category</span>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="subCategory"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:select path="subCategory1.subCategoryId" id="subCategoryId1" cssClass="form-control select" onchange="showChildCategoryList();">
                                                            <form:options items="${subCategoryList}" itemLabel="subCategoryDesc" itemValue="subCategoryId"/>
                                                        </form:select>
                                                        <span class="help-block">Please select sub category</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="childCategory"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:select path="childCategory.childCategoryId" id="subCategoryId2" cssClass="form-control select" onchange="showSubChildCategoryList();">
                                                            <form:options items="${childCategoryList}" itemLabel="childCategoryDesc" itemValue="childCategoryId"/>
                                                        </form:select>
                                                        <span class="help-block">Please select child category</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="subChildCategory"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:select path="childOfChildCategory.subChildCategoryId" id="subCategoryId3" cssClass="form-control select">
                                                            <form:options items="${subChildCategoryList}" itemLabel="subChildCategoryDesc" itemValue="subChildCategoryId"/>
                                                        </form:select>
                                                        <span class="help-block">Please select sub-child category</span>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-2 control-label"><spring:message code="shortDesc"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:textarea path="shortDesc" rows="4" cols="50" Class="form-control"></form:textarea>
                                                            <span class="help-block">Please enter short description</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="col-md-4 col-xs-2 control-label"><spring:message code="longDesc"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:textarea path="longDesc" rows="4" cols="50" Class="form-control"></form:textarea>
                                                            <span class="help-block">Please enter long description</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row" style="margin-top: 10px">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="col-md-4 col-xs-6 control-label"><spring:message code="keywords"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:textarea path="keywords" rows="4" cols="50" Class="form-control"></form:textarea>
                                                            <span class="help-block">Please enter keywords</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="col-md-4 col-xs-6 control-label"><spring:message code="notes"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:textarea path="notes" rows="4" cols="50" Class="form-control"></form:textarea>
                                                            <span class="help-block">Please enter notes</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row" style="margin-top: 10px">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="req col-md-4 col-xs-6 control-label"><spring:message code="returnable"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <spring:message code="yes" /> 
                                                        <form:radiobutton path="returnable" value="true"/>
                                                        <spring:message code="no"/> 
                                                        <form:radiobutton path="returnable" value="false"/>
                                                        <span class="help-block">Please select is returnable?</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="active"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <spring:message code="yes" /> 
                                                        <form:radiobutton path="active" value="true"/>
                                                        <spring:message code="no"/> 
                                                        <form:radiobutton path="active" value="false"/>
                                                        <span class="help-block">Please select is active?</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="resizeImage"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="resizeImageFile" type="file" id="resizeImageFile" class="fileinput btn-danger"  data-filename-placement="inside"/>
                                                        <span class="help-block">Please select resize image</span>
                                                        <form:hidden path="productStatus.productStatusId" value="1"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="largeImage"/>1</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="largeImageFile1" type="file" id="largeImageFile1" class="fileinput btn-danger"  data-filename-placement="inside"/>
                                                        <span class="help-block">Please select large image 1</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="largeImage"/>2</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="largeImageFile2" type="file" id="largeImageFile2" class="fileinput btn-danger"  data-filename-placement="inside"/>
                                                        <span class="help-block">Please select large image 2</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="largeImage"/>3</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="largeImageFile3" type="file" id="largeImageFile3" class="fileinput btn-danger"  data-filename-placement="inside" onclick="return checkLargeImage('largeImageFile2');"/>
                                                        <span class="help-block">Please select large image 3</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="largeImage"/>4</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="largeImageFile4" type="file" id="largeImageFile4" class="fileinput btn-danger"  data-filename-placement="inside" onclick="return checkLargeImage('largeImageFile3');"/>
                                                        <span class="help-block">Please select large image 4</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6"></label>
                                                    <div class="col-md-8 col-xs-8">
                                                        <label style="color: red;">**Please select large images in proper sequence.</label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit"  class="btn btn-success" name="btnSubmit"><spring:message code="button.Submit"/></button>
                                            <button type="submit"  class="btn btn-primary" name="_cancel" formnovalidate="formnovalidate"><spring:message code="button.Cancel"/></button>
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
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-file-input.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-combobox.js"></script>
        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript" defer="defer">

            //function to show subcategory list on select of main category
            function showSubCategoryList() {
                var mainCategoryId = document.getElementById("mainCategory.mainCategoryId").value;
                $.ajax({
                    data: ({'mainCategoryId': mainCategoryId}),
                    url: "ajaxGetSubCategoryListForMainCategory.htm",
                    dataType: "json",
                    success: function(json) {
                        if (json != null) {
                            document.getElementById('subCategoryId1').options.length = 0;
                            document.getElementById('subCategoryId2').options.length = 0;
                            document.getElementById('subCategoryId3').options.length = 0;
                            $("#subCategoryId1").append($("<option></option>").attr("value", "").text("Nothing Selected"));
                            $("#subCategoryId2").append($("<option></option>").attr("value", "").text("Nothing Selected"));
                            $("#subCategoryId3").append($("<option></option>").attr("value", "").text("Nothing Selected"));
                            for (i = 0; i < json.length; i++) {
                                $("#subCategoryId1").append($("<option></option>").attr("value", json[i].subCategoryId).text(json[i].subCategoryDesc));
                                $("#subCategoryId2").append($("<option></option>").attr("value", json[i].subCategoryId).text(json[i].subCategoryDesc));
                                $("#subCategoryId3").append($("<option></option>").attr("value", json[i].subCategoryId).text(json[i].subCategoryDesc));
                            }
                            $('#subCategoryId1').selectpicker('refresh');
                            $('#subCategoryId2').selectpicker('refresh');
                            $('#subCategoryId3').selectpicker('refresh');
                        }
                    }
                });
            }

            //function to show Child category list on select of sub category 
            function showChildCategoryList() {
                //                                                                
                var subCategoryId = document.getElementById("subCategoryId1").value;
                //                                                                alert(subCategoryId);

                $.ajax({
                    data: ({'subCategoryId': subCategoryId}),
                    url: "ajaxGetChildCategoryListForSubCategory.htm",
                    dataType: "json",
                    success: function(json) {
                        if (json != null) {
                            document.getElementById('subCategoryId2').options.length = 0;
                            $("#subCategoryId2").append($("<option></option>").attr("value", "").text("Nothing Selected"));

                            for (i = 0; i < json.length; i++) {
                                $("#subCategoryId2").append($("<option></option>").attr("value", json[i].childCategoryId).text(json[i].childCategoryDesc));
                            }
                            $('#subCategoryId2').selectpicker('refresh');
                        }
                    }
                });
            }

            //function to show Child category list on select of sub category 
            function showSubChildCategoryList() {
                                                                          
                var childCategoryId = document.getElementById("subCategoryId2").value;
                //alert(subCategoryId);

                $.ajax({
                    data: ({'childCategoryId': childCategoryId}),
                    url: "ajaxGetSubChildCategoryListForSubCategory.htm",
                    dataType: "json",
                    success: function(json) {
                        if (json != null) {
                            document.getElementById('subCategoryId3').options.length = 0;
                            $("#subCategoryId3").append($("<option></option>").attr("value", "").text("Nothing Selected"));

                            for (i = 0; i < json.length; i++) {
                                $("#subCategoryId3").append($("<option></option>").attr("value", json[i].subChildCategoryId).text(json[i].subChildCategoryDesc));
                            }
                            $('#subCategoryId3').selectpicker('refresh');
                        }
                    }
                });
            }

            //validation
            $(document).ready(function() {
                $.validator.addMethod(
                "checkFileExtension",
                function(value) {
                    if (value == "") {
                        return true;
                    } else {
                        var fileName = value.split(".");
                        if ((fileName[fileName.length - 1] == "png") || (fileName[fileName.length - 1] == "jpg") || (fileName[fileName.length - 1] == "jpeg") || (fileName[fileName.length - 1] == "gif")) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            );
            });


            var jvalidate = $("#form1").validate({
                ignore: [],
                rules: {
                    'manufacturer.manufacturerId': {
                        required: true
                    },
                    'subCategory1.subCategoryId':{
                        required: true
                    },
                    'childCategory.childCategoryId':{
                        required: true
                    },
                    productTitle: {
                        required: true
                    },
                    performanceModsMpn: {
                        required: true
                    },
                    productName: {
                        required: true
                    },
                    productWeight: {
                        required: true,
                        min: 0.0 + Number.MIN_VALUE,
                        number: true
                    },
                    resizeImageFile: {
                        required: true,
                        checkFileExtension: true
                    },
                    largeImageFile1: {
                        required: true,
                        checkFileExtension: true
                    },
                    largeImageFile2: {
                        checkFileExtension: true
                    },
                    largeImageFile3: {
                        checkFileExtension: true
                    },
                    largeImageFile4: {
                        checkFileExtension: true
                    },
                    productMap: {
                        number: true
                    },
                    productMsrp: {
                        number: true
                    },
                    estShippingWt: {
                        number: true
                    },
                    productLength: {
                        number: true
                    },
                    productHeight: {
                        number: true
                    },
                    productWidth: {
                        number: true
                    }
                },
                messages: {
                    productWeight: "Please enter a value greater than 0",
                    resizeImageFile: {checkFileExtension: "Please select only 'png','jpg','jpeg','gif' file."},
                    largeImageFile1: {checkFileExtension: "Please select only 'png','jpg','jpeg','gif' file."},
                    largeImageFile2: {checkFileExtension: "Please select only 'png','jpg','jpeg','gif' file."},
                    largeImageFile3: {checkFileExtension: "Please select only 'png','jpg','jpeg','gif' file."},
                    largeImageFile4: {checkFileExtension: "Please select only 'png','jpg','jpeg','gif' file."}
                },
                errorPlacement: function(error, element) {
                    if (element.hasClass('select')) {
                        error.insertAfter(element.next(".bootstrap-select"));
                        element.next("div").addClass("error");
                    } else if (element.hasClass('fileinput')) {
                        error.insertAfter($(element).parent());
                    } else {
                        error.insertAfter(element);
                    }
                    if (element.parent().hasClass('combobox-container')) {
                        error.insertAfter(element.parent(".combobox-container"));
                        element.next("div").children().addClass("error");
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


            //function to check large image sequence
            function checkLargeImage(name) {
                var file = document.getElementById(name).value;
                if (!file == "") {
                    return true;
                } else {
                    alert('Please Select ' + name + ' first.');
                    return false;
                }
            }

            $('#manufacturerId').combobox();

            function showAddUPCDiv() {
                $('#addAnotherUPCDiv').show();
                $('#upcButtonDiv').hide();
            }

            function addNewUpc() {
                var up = $('#upc').val();
                var u = up.concat(",").concat($("#addNewUPC").val());
                $('#upc').val(u);
                $('#addAnotherUPCDiv').hide();
                $('#upcButtonDiv').show();
                $("#addNewUPC").val("");
            }

            function showAddAsinDiv() {
                $('#addAnotherASINDiv').show();
                $('#asinButtonDiv').hide();
            }

            function addNewAsin() {
                var up = $('#asin').val();
                var u = up.concat(",").concat($("#addNewASIN").val());
                $('#asin').val(u);
                $('#addAnotherASINDiv').hide();
                $('#asinButtonDiv').show();
                $("#addNewASIN").val("");
            }

            function showAddNewEggDiv() {
                $('#addAnotherNewEggDiv').show();
                $('#NewEggButtonDiv').hide();
            }

            function addNewNewEgg() {
                var up = $('#neweggItemId').val();
                var u = up.concat(",").concat($("#addNewEgg").val());
                $('#neweggItemId').val(u);
                $('#addAnotherNewEggDiv').hide();
                $('#NewEggButtonDiv').show();
                $("#addNewEgg").val("");
            }
            function showAddNewEggB2BDiv() {
                $('#addAnotherNewEggB2BDiv').show();
                $('#NewEggB2BButtonDiv').hide();
            }

            function addNewNewEggB2B() {
                var up = $('#neweggB2BItemId').val();
                var u = up.concat(",").concat($("#addNewEggB2B").val());
                $('#neweggB2BItemId').val(u);
                $('#addAnotherNewEggB2BDiv').hide();
                $('#NewEggB2BButtonDiv').show();
                $("#addNewEggB2B").val("");
            }


        </script>
    </body>
</html>
