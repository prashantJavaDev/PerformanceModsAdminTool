<%-- 
    Document   : exportMarketplaceListing
    Created on : 6 Apr, 2017, 3:43:15 PM
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
                    <li><a href="../home/home.htm">Listing</a></li>
                    <li><a href="#">Export Marketplace Listing</a></li>
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
                                    <h3 class="panel-title"><spring:message code="title.exportUpdateListing"/></h3>
                                    <ul class="panel-controls">
                                        <%--<sec:authorize ifAnyGranted="ROLE_BF_EXPORT_EXCEL">--%>
                                        <c:if test="${fn:length(listingResult)>0}"><li><a href="#" onclick="$('#excelForm').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li></c:if>
                                        <%--</sec:authorize>--%>
                                        </ul>
                                    </div>

                                    <div class="panel-body">
                                        <div class="panel panel-warning">
                                            <div class="panel-body">
                                                <form name="form1" id="form1">
                                                    <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="marketplaceName"/></label>
                                                        <select id ="marketplaceId" name="marketplaceId" class="form-control select"> 
                                                            <option value="0">-</option>
                                                            <c:forEach items="${marketplaceList}" var="marketplace">
                                                                <option value="${marketplace.marketplaceId}"<c:if test="${marketplaceId==marketplace.marketplaceId}">selected</c:if>>${marketplace.marketplaceName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><spring:message code="manufacturerName"/></label>
                                                        <select id="manufacturerIds" name="manufacturerIds" class="form-control select" data-actions-box="true" data-live-search="true" title="-All-"  multiple="true">
                                                            <c:forEach items="${manufacturerList}" var="manufacturer">

                                                                <c:set var="found" value="0"/>
                                                                <!--<option value="0">-</option>-->

                                                                <c:forEach items="${selectedManufacturersList}" var="m">
                                                                    <c:if test="${m.manufacturerId==manufacturer.manufacturerId}"><c:set var="found" value="1"/></c:if>
                                                                </c:forEach>
                                                                <option <c:if test="${found==1}"> selected </c:if>value="${manufacturer.manufacturerId}">${manufacturer.manufacturerName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-2 btn-filter">
                                                    <input type="submit" id="_submit" class="btn-info btn-sm" name="btnSubmit" value="<spring:message code="button.Go"/>"/>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12 scrollable">
                                            <table class="table datatable table-bordered" >
                                                <thead>
                                                    <tr>
                                                        <th><spring:message code="manufacturerName"/></th>
                                                        <th><spring:message code="manufacturerMpn"/></th>
                                                        <th><spring:message code="marketplaceSku"/></th>
                                                        <th><spring:message code="listingId"/></th>
                                                        <th><spring:message code="currentQuantity"/></th>
                                                        <th><spring:message code="warehouseQuantity"/></th>
                                                        <th><spring:message code="currentPrice"/></th>
                                                        <th><spring:message code="warehousePrice"/></th>
                                                        <th><spring:message code="shipping"/></th>
                                                        <th><spring:message code="commission"/></th>
                                                        <th><spring:message code="profit"/></th>
                                                        <th><spring:message code="pack"/></th>
                                                        <th><spring:message code="warehouseName"/></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${listingResult}" var="item">
                                                        <tr>
                                                            <td><c:out value="${item.manufacturerName}"/></td>
                                                            <td><c:out value="${item.manufacturerMpn}"/></td>
                                                            <td><c:out value="${item.sku}"/></td>
                                                            <td><c:out value="${item.listingId}"/></td>
                                                            <td><c:out value="${item.currentQuantity}"/></td>
                                                            <td><c:out value="${item.warehouseQuantity}"/></td>
                                                            <td><c:out value="${item.currentPrice}"/></td>
                                                            <td><c:out value="${item.warehousePrice}"/></td>
                                                            <td><c:out value="${item.shipping}"/></td>
                                                            <td><c:out value="${item.commission}"/></td>
                                                            <td><c:out value="${item.profit}"/></td>
                                                            <td><c:out value="${item.pack}"/></td>
                                                            <td><c:out value="${item.warehouse}"/></td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <c:if test="${pageCount>0}"><div class="demo2 pull-right"></div></c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <form name="excelForm" id="excelForm" action="../listing/listingExcel.htm">
                        <input type="hidden" name="marketplaceId" value="${marketplaceId}"/>
                        <div id="hiddenService" style="display: none">
                            <select name="manufacturerId" id="manufacturerId" multiple>
                                <c:forEach items="${manufacturerIds}" var="item">
                                    <option selected value="${item}">${item}</option>
                                </c:forEach>
                            </select>
                        </div>
         <!--                        <input type="hidden" name="manufacturerId" value="${manufacturerIds}"/>-->
                    </form>

                    <!-- END PAGE CONTENT WRAPPER -->
                </div>
                <!-- END PAGE CONTENT -->
            </div>
            <!-- END PAGE CONTAINER -->
        </div>

        <%@include file="../common/messagebox.jsp" %>
        <%@include file="../common/script.jsp" %>
        <!-- START THIS PAGE PLUGINS-->
        <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type="text/javascript" src="../js/plugins/pagination/jquery.bootpag.min.js"></script>
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    
        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript" defer="defer">

            $('.table').dataTable({
                "dom": 'f<"toolbar">rt',
                "order": [],
                "bPaginate": false
            });

            $("div.toolbar").html('<div class="pull-left"><c:out value="${rowCount}"/> <spring:message code="rowsFound"/></div>');

            $('.demo2').bootpag({
                total: ${pageCount},
                page: ${pageNo},
                maxVisible: 10
            }).on('page', function(event, num) {
                $('#pageNo').val(num);
                $('#form1').submit();
            });
                                            
        </script>
    </body>
</html>

