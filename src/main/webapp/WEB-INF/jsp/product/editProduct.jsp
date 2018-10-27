<%-- 
    Document   : editProduct
    Created on : 8 Feb, 2016, 4:24:20 PM
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
    <body onload="subCategoryOnload();
            imageOnload();">
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>

            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Admin</a></li>
                    <li><a href="../home/home.htm">Products</a></li>
                    <li><a href="#">Edit Product</a></li>
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
                                        <h3 class="panel-title"><spring:message code="title.editProduct"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="performanceModsMpn"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="performanceModsMpn" cssClass="form-control" readonly="true"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="manufacturerName"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="manufacturer.manufacturerName" cssClass="form-control" readonly="true"/>
                                                    </div>  
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 20px">
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
                                                        <span class="help-block">Please enter product est shipping weight</span>
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
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="width"/> (Inches)</label>
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
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="height"/> (Inches)</label>
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
                                                        <form:input path="upc" minlength="12" cssClass="form-control" onchange="showaddUpcButton();"/>
                                                        <span class="help-block">Please enter product UPC</span>
                                                        <button id="upcButtonDiv" type="button" class="btn-info btn-sm" onclick="showAddUPCDiv();" >Add New UPC</button>
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
                                                        <form:input path="asin" minlength="10" cssClass="form-control" onchange="showaddAsinButton();"/>
                                                        <span class="help-block">Please enter product ASIN</span>
                                                        <button id="asinButtonDiv" type="button" class="btn-info btn-sm" onclick="showAddAsinDiv();" >Add New ASIN</button>
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
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="neweggItemId"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="neweggItemId" minlength="14" cssClass="form-control" onchange="showaddNewEggButton();"/>
                                                        <span class="help-block">Please enter product NewEgg Item Id</span>
                                                        <button id="NewEggButtonDiv" type="button" class="btn-info btn-sm" onclick="showAddNewEggDiv();" >Add New Newegg Item Id</button>
                                                    </div>
                                                    <div id="addAnotherNewEggDiv" class="col-md-6 col-xs-6" style="display: none">
                                                        <input type="text" id="addNewEgg" class="form-control"/> <button id="addNewEgg" type="button" class="btn-info btn-sm" onclick="addNewNewEgg();">Add</button>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="neweggB2BItemId"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:input path="neweggB2BItemId" minlength="14" cssClass="form-control" onchange="showaddNewEggB2BButton();"/>
                                                        <span class="help-block">Please enter product NewEgg Business Item Id</span>
                                                        <button id="NewEggB2BButtonDiv" type="button" class="btn-info btn-sm" onclick="showAddNewEggB2BDiv();" >Add New Newegg Business Item Id</button>
                                                    </div>
                                                    <div id="addAnotherNewEggB2BDiv" class="col-md-6 col-xs-6" style="display: none">
                                                        <input type="text" id="addNewEggB2B" class="form-control"/> <button id="addNewEggB2B" type="button" class="btn-info btn-sm" onclick="addNewNewEggB2B();">Add</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="mainCategory"/></label>
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
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="subCategory"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <form:select path="subCategory1.subCategoryId" id="subCategoryId1" cssClass="form-control select" onchange="showChildCategoryList();">
                                                            <form:options items="${subCategoryList}" itemLabel="subCategoryDesc" itemValue="subCategoryId"/>
                                                        </form:select>
                                                        <span class="help-block">Please select sub category 1</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="childCategory"/></label>
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
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="subChildCategory"/></label>
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
                                                <label class="col-md-4 col-xs-2 control-label"><spring:message code="shortDesc"/></label>
                                                <div class="col-md-6 col-xs-6">
                                                    <form:textarea path="shortDesc" rows="4" cols="50" Class="form-control"></form:textarea>
                                                        <span class="help-block">Please enter short description</span>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="col-md-4 col-xs-2 control-label"><spring:message code="longDesc"/></label>
                                                <div class="col-md-6 col-xs-6">
                                                    <form:textarea path="longDesc" rows="4" cols="50" Class="form-control"></form:textarea>
                                                        <span class="help-block">Please enter long description</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row" style="margin-top: 10px">
                                                <div class="col-md-6">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="keywords"/></label>
                                                <div class="col-md-6 col-xs-6">
                                                    <form:textarea path="keywords" rows="4" cols="50" Class="form-control"></form:textarea>
                                                        <span class="help-block">Please enter keywords</span>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="notes"/></label>
                                                <div class="col-md-6 col-xs-6">
                                                    <form:textarea path="notes" rows="4" cols="50" Class="form-control"></form:textarea>
                                                        <span class="help-block">Please enter notes</span>
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
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="resizeImage"/></label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <div id="resizeImageFileDiv">
                                                            <form:input path="resizeImageFile" type="file" id="resizeImageFile" class="fileinput btn-danger" data-filename-placement="inside"/>
                                                            <span class="help-block">Please select resize image</span>
                                                        </div>
                                                        <form:hidden path="resizeImageUrl" id = "resizeImageUrl"/>
                                                        <a id="resizeImage" href="${product.resizeImageUrl}" target="_blank">
                                                            <img src="${product.resizeImageUrl}" style="height: 200px; width: 200px"/></a><br>
                                                        <input id="resizeImageButton" type="button" onclick="replaceImage('resizeImage')" value="Remove" class="btn-info"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 col-xs-6 control-label"><spring:message code="largeImage"/>1</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <div id="largeImage1FileDiv">
                                                            <form:input path="largeImageFile1" type="file" id="largeImage1File" cssClass="fileinput btn-danger"  data-filename-placement="inside"/>
                                                            <span class="help-block">Please select large image 1</span>
                                                        </div>
                                                        <form:hidden path="largeImageUrl1" id = "largeImage1Url"/>
                                                        <a id="largeImage1" href="${product.largeImageUrl1}" target="_blank">
                                                            <img src="${product.largeImageUrl1}" style="height: 200px; width: 200px"/></a><br>
                                                        <input id="largeImage1Button" type="button" onclick="replaceImage('largeImage1')" value="Remove" class="btn-info"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="largeImage"/>2</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <div id="largeImage2FileDiv">
                                                            <form:input path="largeImageFile2" type="file" id="largeImage2File" class="fileinput btn-danger"  data-filename-placement="inside"/>
                                                            <span class="help-block">Please select large image 2</span>
                                                        </div>
                                                        <form:hidden path="largeImageUrl2" id = "largeImage2Url"/>
                                                        <a id="largeImage2" href="${product.largeImageUrl2}" target="_blank">
                                                            <img src="${product.largeImageUrl2}" style="height: 200px; width: 200px"/></a><br>
                                                        <input id="largeImage2Button" type="button" onclick="replaceImage('largeImage2')" value="Remove" class="btn-info"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="largeImage"/>3</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <div id="largeImage3FileDiv">
                                                            <form:input path="largeImageFile3" type="file" id="largeImage3File" class="fileinput btn-danger"  data-filename-placement="inside" onclick="return checkLargeImage('largeImage2File','largeImage2Url');"/>
                                                            <span class="help-block">Please select large image 3</span>
                                                        </div>
                                                        <form:hidden path="largeImageUrl3" id = "largeImage3Url"/>
                                                        <a id="largeImage3" href="${product.largeImageUrl3}" target="_blank">
                                                            <img src="${product.largeImageUrl3}" style="height: 200px; width: 200px"/></a><br>
                                                        <input id="largeImage3Button" type="button" onclick="replaceImage('largeImage3')" value="Remove" class="btn-info"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6 control-label"><spring:message code="largeImage"/>4</label>
                                                    <div class="col-md-6 col-xs-6">
                                                        <div id="largeImage4FileDiv">
                                                            <form:input path="largeImageFile4" type="file" id="largeImage4File" class="fileinput btn-danger"  data-filename-placement="inside" onclick="return checkLargeImage('largeImage3File','largeImage3Url');"/>
                                                            <span class="help-block">Please select large image 4</span>
                                                        </div>
                                                        <form:hidden path="largeImageUrl4" id = "largeImage4Url"/>
                                                        <a id="largeImage4" href="${product.largeImageUrl4}" target="_blank">
                                                            <img src="${product.largeImageUrl4}" style="height: 200px; width: 200px"/></a><br>
                                                        <input id="largeImage4Button" type="button" onclick="replaceImage('largeImage4')" value="Remove" class="btn-info"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-6"></label>
                                                    <div class="col-md-8 col-xs-8">
                                                        <label style="color: red"">**Please select large images in proper sequence.</label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit" id="_submit" name="submit"  class="btn btn-success"/><spring:message code="button.Submit"/></button>
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
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-file-input.js"></script>
        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript" defer="defer">

                                                            //function to set subcategory on page load
                                                            function subCategoryOnload() {
                                                                document.getElementById('subCategoryId1').options.length = 0;
                                                                document.getElementById('subCategoryId2').options.length = 0;
                                                                document.getElementById('subCategoryId3').options.length = 0;
                                                                $("#subCategoryId1").append($("<option></option>").attr("value", ${product.subCategory1.subCategoryId}).text("${product.subCategory1.subCategoryDesc}"));
                                                                $("#subCategoryId2").append($("<option></option>").attr("value", ${product.childCategory.childCategoryId}).text("${product.childCategory.childCategoryDesc}"));
                                                                $("#subCategoryId3").append($("<option></option>").attr("value", ${product.childOfChildCategory.subChildCategoryId}).text("${product.childOfChildCategory.subChildCategoryDesc}"));
                                                                $('#subCategoryId1').selectpicker('refresh');
                                                                $('#subCategoryId2').selectpicker('refresh');
                                                                $('#subCategoryId3').selectpicker('refresh');
                                                            }

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
//                                                                                $("#subCategoryId2").append($("<option></option>").attr("value", json[i].subCategoryId).text(json[i].subCategoryDesc));
//                                                                                $("#subCategoryId3").append($("<option></option>").attr("value", json[i].subCategoryId).text(json[i].subCategoryDesc));
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


                                                            //function to display image if image present and hide browse button and vice versa
                                                            function imageOnload() {
                                                                var resizeImageUrl = document.getElementById("resizeImageUrl").value;
                                                                var largeImage1Url = document.getElementById("largeImage1Url").value;
                                                                var largeImage2Url = document.getElementById("largeImage2Url").value;
                                                                var largeImage3Url = document.getElementById("largeImage3Url").value;
                                                                var largeImage4Url = document.getElementById("largeImage4Url").value;
                                                                if (resizeImageUrl == "" || resizeImageUrl == null) {
                                                                    $('#resizeImageFileDiv').show();
                                                                    $('#resizeImageButton').hide();
                                                                    $('#resizeImage').hide();
                                                                } else {
                                                                    $('#resizeImageFileDiv').hide();
                                                                    $('#resizeImageButton').show();
                                                                    $('#resizeImage').show();
                                                                }

                                                                if (largeImage1Url == "" || largeImage1Url == null) {
                                                                    $('#largeImage1FileDiv').show();
                                                                    $('#largeImage1Button').hide();
                                                                    $('#largeImage1').hide();
                                                                } else {
                                                                    $('#largeImage1FileDiv').hide();
                                                                    $('#largeImage1Button').show();
                                                                    $('#largeImage1').show();
                                                                }

                                                                if (largeImage2Url == "" || largeImage2Url == null) {
                                                                    $('#largeImage2FileDiv').show();
                                                                    $('#largeImage2Button').hide();
                                                                    $('#largeImage2').hide();
                                                                } else {
                                                                    $('#largeImage2FileDiv').hide();
                                                                    $('#largeImage2Button').show();
                                                                    $('#largeImage2').show();
                                                                }

                                                                if (largeImage3Url == "" || largeImage3Url == null) {
                                                                    $('#largeImage3FileDiv').show();
                                                                    $('#largeImage3Button').hide();
                                                                    $('#largeImage3').hide();
                                                                } else {
                                                                    $('#largeImage3FileDiv').hide();
                                                                    $('#largeImage3Button').show();
                                                                    $('#largeImage3').show();
                                                                }

                                                                if (largeImage4Url == "" || largeImage4Url == null) {
                                                                    $('#largeImage4FileDiv').show();
                                                                    $('#largeImage4Button').hide();
                                                                    $('#largeImage4').hide();
                                                                } else {
                                                                    $('#largeImage4FileDiv').hide();
                                                                    $('#largeImage4Button').show();
                                                                    $('#largeImage4').show();
                                                                }
                                                            }


                                                            //function to replace image
                                                            function replaceImage(imageName)
                                                            {
                                                                var imageButton = imageName + "Button";
                                                                var imageFile = imageName + "FileDiv";
                                                                var imageUrl = imageName + "Url";
                                                                function replaceOrRemove() {
                                                                    document.getElementById(imageButton).style.display = 'none';
                                                                    document.getElementById(imageFile).style.display = 'block';
                                                                    document.getElementById(imageName).style.display = 'none';
                                                                    $('#' + imageUrl).val(null);
                                                                }
                                                                var check1 = confirm("Do you want to remove image press Ok to continue?");
                                                                if (check1 == true) {
                                                                    replaceOrRemove();
                                                                } else {
                                                                    return null;
                                                                }
                                                            }

                                                            //function to check large image sequence
                                                            function checkLargeImage(name, url) {
                                                                var imageUrl = document.getElementById(url).value;
                                                                if (imageUrl == "" || imageUrl == null) {
                                                                    var file = document.getElementById(name).value;
                                                                    if (!file == "") {
                                                                        return true;
                                                                    } else {
                                                                        alert('Please Select ' + name + ' first.');
                                                                        return false;
                                                                    }
                                                                } else {
                                                                    return true;
                                                                }
                                                            }

                                                            //validations

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
                                                                    productTitle: {
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
                                                                        required: function(element) {
                                                                            return ($('#resizeImageUrl').val() == '' || $('#resizeImageUrl').val() == null)
                                                                        },
                                                                        checkFileExtension: true
                                                                    },
                                                                    largeImageFile1: {
                                                                        required: function(element) {
                                                                            return ($('#largeImage1Url').val() == '' || $('#largeImage1Url').val() == null)
                                                                        },
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
                                                                    },
                                                                    upc: {
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
                                                                    if (element.hasClass('fileinput')) {
                                                                        error.insertAfter($(element).parent());
                                                                    } else {
                                                                        error.insertAfter(element);
                                                                    }
                                                                }
                                                            });

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

                                                            function showaddUpcButton() {
                                                                $('#upcButtonDiv').show();
                                                            }

                                                            function showaddAsinButton() {
                                                                $('#asinButtonDiv').show();
                                                            }

                                                            function showaddNewEggButton() {
                                                                $('#NewEggButtonDiv').show();
                                                            }
                                                            function showaddNewEggB2BButton() {
                                                                $('#NewEggB2BButtonDiv').show();
                                                            }

        </script>
    </body>
</html>