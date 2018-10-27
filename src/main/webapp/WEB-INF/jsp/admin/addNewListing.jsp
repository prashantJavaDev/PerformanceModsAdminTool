<%-- 
    Document   : addNewListing
    Created on : 8 May, 2017, 3:53:28 PM
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

    </head>
    <body onLoad="document.getElementById('productMpn').focus();">
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>
            <!-- PAGE CONTENT -->
            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Admin</a></li>
                    <li><a href="../home/home.htm">Listing</a></li>
                    <li><a href="#">Add Listing</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" method="POST" commandName="listing" name="form1" id="form1">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.addNewListing"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="mpn"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="productMpn" id="productMpn" value="${productMpn}" Class="form-control"/>
                                                <span class="help-block">Please enter Manufacturer Part Number</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="manufacturerName"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:select path="manufacturer.manufacturerId" id ="manufacturerId" class="form-control">
                                                    <form:option value="" label="Nothing selected"/>
                                                    <form:options items="${manufacturerList}" itemValue="manufacturerId" itemLabel="manufacturerName"/>
                                                </form:select>
                                                <span class="help-block">Please select Manufacturer Number</span>
                                            </div>

                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="marketplaceSku"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <div class="input-group">
                                                    <b><form:input path="sku" id="sku" cssClass="form-control generator" readonly="true"/></b>
                                                    <span class="input-group-addon" onclick="generateAutoSku()" title="Generate Marketplace Sku"><span class="fa fa-refresh"></span></span>
                                                </div>
                                                <span class="help-block">Please click for Marketplace Sku</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="listingId"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="marketplaceListingId" cssClass="form-control"/>
                                                <span class="help-block">Please enter marketplace listing Id</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="marketplaceName"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:select path="marketplace.marketplaceId" class="form-control select"> 
                                                    <form:option value="">-</form:option>
                                                    <form:options items="${marketplaceList}" itemValue="marketplaceId" itemLabel="marketplaceName"/>
                                                </form:select>
                                                <span class="help-block">Please select marketplace name</span>
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="commission"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="commission" cssClass="form-control"/>
                                                <span class="help-block">Please enter commission for listing</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="pack"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="pack" cssClass="form-control" maxlength="2"/>
                                                <span class="help-block">Please enter pack quantity for listing</span>
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
            </div>
        </div>
        <%@include file="../common/messagebox.jsp" %>
        <%@include file="../common/script.jsp" %>
        <!-- START THIS PAGE PLUGINS-->
        <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>        
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-combobox.js"></script>

        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->
        <script type="text/javascript" defer="defer">

                                                        var jvalidate = $("#form1").validate({
                                                            ignore: [],
                                                            rules: {
                                                                productMpn: {
                                                                    required: true
                                                                },
                                                                sku: {
                                                                    required: true
                                                                },
                                                                marketplaceListingId: {
                                                                    required: true
                                                                },
                                                                'marketplace.marketplaceId': {
                                                                    required: true
                                                                },
                                                                commission: {
                                                                    required: true,
                                                                    number: true
                                                                },
                                                                pack: {
                                                                    required: true,
                                                                    number: true,
                                                                    min: 1
                                                                }

                                                            },
                                                            errorPlacement: function(error, element) {
                                                                if (element.hasClass('select')) {
                                                                    error.insertAfter(element.next(".bootstrap-select"));
                                                                    element.next("div").addClass("error");
                                                                } else if (element.hasClass('generator')) {
                                                                    error.insertAfter(element.parent().parent());
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

                                                        //autocomplete for Product MPN
                                                        $(function() {
                                                            src3 = "../ajax/getProductMpnListForAutocomplete.htm";

                                                            $("#productMpn").autocomplete({
                                                                source: function(request, response) {
                                                                    $.ajax({
                                                                        url: src3,
                                                                        dataType: "json",
                                                                        data: {
                                                                            term: request.term
                                                                        },
                                                                        success: function(data) {
                                                                            response(data);
                                                                        }
                                                                    });
                                                                },
                                                                minLength: 3
                                                            });
                                                        });
                                                        //to check for sku existed in database
                                                        function generateAutoSku() {
                                                            src2 = "../ajax/ajaxGetSkuListOnMpn.htm";
                                                            $.ajax({
                                                                url: src2,
                                                                dataType: "json",
                                                                data: {
                                                                    productMpn: $("#productMpn").val(),
                                                                    manufacturerId: $("#manufacturerId").val()
                                                                },
                                                                success: function(data) {
                                                                    $("#sku").val(data.sku);
                                                                }
                                                            });
                                                        }

                                                        $('#manufacturerId').combobox();
        </script>
    </body>
</html>
