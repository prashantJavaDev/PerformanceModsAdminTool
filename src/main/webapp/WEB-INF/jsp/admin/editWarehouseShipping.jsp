<%-- 
    Document   : editWarehouseShipping
    Created on : 23 Aug, 2017, 2:25:41 PM
    Author     : Ritesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <html lang="en">
        <head>
            <!-- META SECTION -->
            <%@include file="../common/meta.jsp"%>
            <!-- END META SECTION -->

            <!-- CSS INCLUDE -->
            <%@include file="../common/css.jsp"%>
            <!-- EOF CSS INCLUDE -->
        </head>
        <body onload="hideAllFields();">
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
                        <li><a href="#">Warehouse Shipping</a></li>
                    </ul>
                    <!-- END BREADCRUMB --> 

                    <!-- PAGE CONTENT WRAPPER -->
                    <div class="page-content-wrap">
                        <!-- MESSAGE SECTION -->
                        <%@include file="../common/message.jsp"%>
                        <!-- END MESSAGE SECTION -->

                        <div class="row">
                            <div class="col-md-12">
                                <form:form cssClass="form-horizontal" commandName="ShippingDetails" modelAttribute="shippingDetails" name="form1" id="form1" method="POST" action="../admin/editWarehouseShipping" >
                                    <form:hidden path="shippingDetailsId"/>    
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h3 class="panel-title"><spring:message code="title.editWarehouseShipping"/></h3>
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
                                                    <span class="help-block">Please select warehouse</span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="req col-md-2 col-xs-12 control-label"><spring:message code="shippingCriteria"/></label>
                                                <div class="col-md-6 col-xs-12">
                                                    <select id ="criteriaId" name="criteriaId" class="form-control select" onchange="$('#shippingCriteria').val($('#criteriaId').find('option:selected').text()); fieldsByFeedCriteria();"> 
                                                        <option value="">-</option>
                                                        <c:forEach items="${shippingCriteriaList}" var="shippingCriteria">
                                                            <option value="${shippingCriteria.shippingCriteriaId}">${shippingCriteria.shippingCriteria}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <span class="help-block">Please select shipping criteria</span>
                                                </div>
                                            </div>
                                            <div class="form-group" id="minPriceValueId">
                                                <label class="req col-md-2 col-xs-6 control-label"><spring:message code="MinimumPriceValue"/></label>
                                                <div class="col-md-2">
                                                    <form:input path="minValuePrice" cssClass="form-control"/>
                                                    <span class="help-block">Please enter min Price value</span>
                                                </div>
                                                <label class="req col-md-2 col-xs-6 control-label"><spring:message code="MinimumPriceShipping"/></label>
                                                <div class="col-md-2">
                                                    <form:input path="minValuePriceShipping" cssClass="form-control"/>
                                                    <span class="help-block">Please enter min Price Shipping</span>
                                                </div>
                                            </div>
                                            <div class="form-group" id="maxPriceValueId">
                                                <label class="req col-md-2 col-xs-6 control-label"><spring:message code="MaximumPriceValue"/></label>
                                                <div class="col-md-2">
                                                    <form:input path="maxValuePrice" cssClass="form-control"/>
                                                    <span class="help-block">Please enter max Price value</span>
                                                </div>
                                                <label class="req col-md-2 col-xs-6 control-label"><spring:message code="MaximumPriceShipping"/></label>
                                                <div class="col-md-2">
                                                    <form:input path="maxValuePriceShipping" cssClass="form-control"/>
                                                    <span class="help-block">Please enter max Price Shipping</span>
                                                </div> 
                                            </div>
                                            <div class="form-group" id="minweightValueId">
                                                <label class="req col-md-2 col-xs-6 control-label"><spring:message code="MinimumWeightValue"/></label>
                                                <div class="col-md-2">
                                                    <form:input path="minValueWeight" cssClass="form-control"/>
                                                    <span class="help-block">Please enter min Weight value</span>
                                                </div>
                                                <label class="req col-md-2 col-xs-6 control-label"><spring:message code="MinimumWeightShipping"/></label>
                                                <div class="col-md-2">
                                                    <form:input path="minValueWeightShipping" cssClass="form-control"/>
                                                    <span class="help-block">Please enter max Weight Shipping</span>
                                                </div>
                                            </div>
                                            <div class="form-group" id="maxweightValueId">
                                                <label class="req col-md-2 col-xs-6 control-label"><spring:message code="MaximumWeightValue"/></label>
                                                <div class="col-md-2">
                                                    <form:input path="maxValueWeight" cssClass="form-control"/>
                                                    <span class="help-block">Please enter max Weight value</span>
                                                </div>
                                                <label class="req col-md-2 col-xs-6 control-label"><spring:message code="MaximumWeightShipping"/></label>
                                                <div class="col-md-2">
                                                    <form:input path="maxValueWeightShipping" cssClass="form-control"/>
                                                    <span class="help-block">Please enter max Weight Shipping</span>
                                                </div>
                                            </div>
                                            <div class="form-group" id="flatRateValueId">
                                                <label class="req col-md-2 col-xs-6 control-label"><spring:message code="flatRateValue"/></label>
                                                <div class="col-md-2">
                                                    <form:input path="flatRateValue" cssClass="form-control"/>
                                                    <span class="help-block">Please enter Flat Rate value</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit" id="_submit" name="submit"  class="btn btn-success"/>Submit</button>
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
                function hideAllFields()
                {
                    $('#minPriceValueId').hide();
                    $('#maxPriceValueId').hide();
                    $('#minweightValueId').hide();
                    $('#maxweightValueId').hide();
                    $('#flatRateValueId').hide();
               
                }
            
                function fieldsByFeedCriteria(){
                    var criteriaId=$('#criteriaId').val();
                    if (criteriaId==1){
                        $('#minPriceValueId').show();
                        $('#maxPriceValueId').show();
                        $('#minweightValueId').hide();
                        $('#maxweightValueId').hide();
                        $('#flatRateValueId').hide();
                    
                    }
                    if (criteriaId==2){
                        $('#minweightValueId').show();
                        $('#maxweightValueId').show();
                        $('#flatRateValueId').hide();
                        $('#minPriceValueId').hide();
                        $('#maxPriceValueId').hide();
                    
                    }
                    if (criteriaId==3){
                        $('#flatRateValueId').show();
                        $('#minPriceValueId').hide();
                        $('#maxPriceValueId').hide();
                        $('#minweightValueId').hide();
                        $('#maxweightValueId').hide();
                    
                    }
                    if (criteriaId==6){
                    
                        $('#minweightValueId').show();
                        $('#maxweightValueId').show();
                        $('#minPriceValueId').show();
                        $('#maxPriceValueId').show();
                        $('#flatRateValueId').hide();    
                    }
                    if (criteriaId==4){
                        $('#flatRateValueId').hide();
                        $('#minPriceValueId').hide();
                        $('#maxPriceValueId').hide();
                        $('#minweightValueId').hide();
                        $('#maxweightValueId').hide();
                    
                    }
                    if (criteriaId==5){
                        $('#flatRateValueId').hide();
                        $('#minPriceValueId').hide();
                        $('#maxPriceValueId').hide();
                        $('#minweightValueId').hide();
                        $('#maxweightValueId').hide();
                    
                    }
                
                
                }
            </script>
        </body>
    </html>
