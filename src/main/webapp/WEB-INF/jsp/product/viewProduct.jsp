<%-- 
    Document   : viewProduct
    Created on : 30 Mar, 2016, 6:10:57 PM
    Author     : shrutika
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
    <body onLoad="document.getElementById('performanceModsMpn').focus();">
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
                    <li><a href="#">View Product</a></li>
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
                                    <h3 class="panel-title"><spring:message code="title.viewProduct"/></h3>
                                </div>
                                <div class="panel-body">
                                    <div class="panel panel-warning">
                                        <div class="panel-body">
                                            <form method="POST" name="form1" id="form1">
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label><spring:message code="performanceModsMpn"/></label>
                                                        <input type="text" name="performanceModsMpn" id="performanceModsMpn" value="${performanceModsMpn}" Class="form-control"/>
                                                        <span class="help-block">Please enter performanceMods MPN</span>
                                                    </div>
                                                </div>
                                                <div class="col-md-2 btn-filter">
                                                    <button type="submit"  class="btn-info btn-sm" name="btnSubmit">Go</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h3 class="panel-title">Warehouse List of Product</h3>
                                                </div>
                                                <div class="panel-body">
                                                    <c:if test="${fn:length(productList)>0}">
                                                        <div class="row">
                                                            <div class="col-md-12 scrollable">
                                                                <table class="table datatable table-bordered" >
                                                                    <thead>
                                                                        <tr class="">
                                                                            <th width="200px"><spring:message code="warehouseName"/></th>
                                                                            <th width="200px"><spring:message code="warehouseMpn"/></th>
                                                                            <th width="200px">Current Feed Date</th>
                                                                            <th width="200px">Current Price</th>
                                                                            <th width="200px">Current Quantity</th>
                                                                            <th width="200px">Shipping</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach items="${productList}" var="productItem">
                                                                            <tr>
                                                                                <td><c:out value="${productItem.warehouseName}"/></td>
                                                                                <td><c:out value="${productItem.warehouseMpn}"/></td>
                                                                                <td><c:out value="${productItem.currentFeedDate}"/></td>
                                                                                <td ${priceStyle}><c:out value="${productItem.currentPrice}"/></td>
                                                                                <td ${quantityStyle}><c:out value="${productItem.currentQuantity}"/></td>
                                                                                <td><c:out value="${productItem.shipping}"/></td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div><br>
                                                    </c:if>  
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h3 class="panel-title">Marketplace Listings of Product ${performanceModsMpn}</h3>
                                                </div>
                                                <div class="panel-body">
                                                    <c:choose>
                                                        <c:when test="${fn:length(marketplaceSkuList)>0}">
                                                            <div class="row">
                                                                <div class="col-md-12 scrollable">
                                                                    <table class="table datatable table-bordered" >
                                                                        <thead>
                                                                            <tr class="">
                                                                                <th width="200px"><spring:message code="marketplaceName"/></th>
                                                                                <th width="200px"><spring:message code="listingId"/></th>
                                                                                <th width="200px"><spring:message code="marketplaceSku"/></th>
                                                                                <th width="200px"><spring:message code="currentPrice"/></th>
                                                                                <th width="200px"><spring:message code="currentQuantity"/></th>
                                                                                <th width="200px"><spring:message code="currentListedDate"/></th>
                                                                                <th width="200px"><spring:message code="warehouseName"/></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <c:forEach items="${marketplaceSkuList}" var="marketplaceSkuItem">
                                                                                <tr>
                                                                                    <td><c:out value="${marketplaceSkuItem.marketplaceName}"/></td>
                                                                                    <td><c:out value="${marketplaceSkuItem.listingId}"/></td>
                                                                                    <td><c:out value="${marketplaceSkuItem.marketplaceSku}"/></td>
                                                                                    <td><c:out value="${marketplaceSkuItem.currentPrice}"/></td>
                                                                                    <td><c:out value="${marketplaceSkuItem.currentQuantity}"/></td>
                                                                                    <td><fmt:formatDate value="${marketplaceSkuItem.currentListedDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                                                                                    <td><c:out value="${marketplaceSkuItem.warehouseName}"/></td>
                                                                                </tr>
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </c:when>
                                                        <c:when test="${performanceModsMpn!=''}">
                                                            No Listing available.<a href="../admin/addNewListing.htm" target="_blank">Click here</a> to create listing.
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row" style="margin-bottom:50px">
                                        <c:if test="${productInfo!=null}">
                                            <div class="panel">
                                                <div class="row" style="margin-top: 10px">
                                                    <div class="col-md-12"><div class="col-md-12"><h3 class="panel-title"><c:out value="${productInfo.productTitle}"/></h3></div></div>
                                                    <div class="col-md-6" style="margin-top: 10px">
                                                        <div class="col-md-12">
                                                            <b><spring:message code="mpn"/> : </b>
                                                            <c:out value="${productInfo.manufacturerMpn}"/>  
                                                        </div>
                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <a href="${productInfo.largeImageUrl1}" target="_blank">
                                                                <img src="${productInfo.largeImageUrl1}" style="height: 300px; width: 449px"/></a>
                                                        </div>
                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <a href="${productInfo.resizeImageUrl}" target="_blank">
                                                                <img src="${productInfo.resizeImageUrl}"  alt="" style="height: 70px; width: 110px"/></a>

                                                            <a href="${productInfo.largeImageUrl2}" target="_blank">
                                                                <img src="${productInfo.largeImageUrl2}" alt="" style="height: 70px; width: 110px"/></a>

                                                            <a href="${productInfo.largeImageUrl3}" target="_blank">
                                                                <img src="${productInfo.largeImageUrl3}" alt="" style="height: 70px; width: 110px"/></a>

                                                            <a href="${productInfo.largeImageUrl4}" target="_blank">
                                                                <img src="${productInfo.largeImageUrl4}" alt="" style="height: 70px; width: 110px"/></a>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="manufacturerName"/> : </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.manufacturer.manufacturerName}"/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="map"/> : </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.productMap}"/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="msrp"/> : </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.productMsrp}"/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="upc"/> : </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.upc}"/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="mainCategory"/> : </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.mainCategory.mainCategoryDesc}"/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="subCategory"/> : </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.subCategory1.subCategoryDesc}"/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="childCategory"/>: </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.childCategory.childCategoryDesc}"/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="weight"/> : </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.productWeight}"/>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group" style="margin-bottom: 30px">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="estimatedShipWeight"/> : </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.estShippingWt}"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="length"/> : </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.productLength}"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="width"/> : </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.productWidth}"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-12" style="margin-top: 10px">
                                                            <div class="form-group">
                                                                <label class="col-md-4 col-xs-6 control-label"><spring:message code="height"/> : </label>        
                                                                <div class="col-md-6 col-xs-6">
                                                                    <c:out value="${productInfo.productHeight}"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">   
                                                        <div class="col-md-12 ">
                                                            <div class="form-group" style="margin-top: 30px"> 
                                                                <label class="col-md-2 col-xs-6 control-label"><spring:message code="shortDesc"/></label>
                                                                <div class="col-md-6 col-xs-6">
                                                                    ${productInfo.shortDesc}
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12 ">
                                                            <div class="form-group" style="margin-top: 30px"> 
                                                                <label class="col-md-2 col-xs-6 control-label"><spring:message code="longDesc"/></label>
                                                                <div class="col-md-6 col-xs-6">
                                                                    ${productInfo.longDesc}
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>  
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
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