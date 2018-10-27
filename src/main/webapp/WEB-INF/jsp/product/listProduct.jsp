<%-- 
    Document   : productList
    Created on : 10 Sep, 2015, 5:53:45 PM
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
    <body>
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>

            <!-- PAGE CONTENT -->
            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Product</a></li>
                    <li><a href="#">List Product</a></li>
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
                                    <h3 class="panel-title"><spring:message code="title.listProduct"/></h3>
                                    <ul class="panel-controls">
                                        <sec:authorize ifAnyGranted="'ROLE_BF_EXPORT_PRODUCT'">
                                        <c:if test="${fn:length(productList)>0}"><li><a href="#" onclick="$('#excelForm').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li></c:if>
                                        </sec:authorize>
                                        <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                    </ul>
                                </div>
                                <div class="panel-body">
                                    <div class="panel panel-warning">
                                        <div class="panel-body">
                                            <form name="form1" id="form1" method="post">
                                                <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>

                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="mpn"/></label>
                                                        <input type="text" name="productMpn" id="productMpn" value="${productMpn}" Class="form-control"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label>Warehouse Part Number</label>
                                                        <input type="text" name="warehouseMPN" id="warehouseMPN" value="${warehouseMPN}" Class="form-control"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="performanceModsMpn"/></label>
                                                        <input type="text" name="performanceModsMpn" id="performanceModsMpn" value="${performanceModsMpn}" Class="form-control"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="productName"/></label>
                                                        <input type="text" name="productName" id="productName" value="${productName}" Class="form-control"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="manufacturerName"/></label>
                                                        <select name="manufacturerId" id="manufacturerId" Class="form-control">
                                                            <option value="">-All- </option>
                                                            <c:forEach items="${manufacturerList}" var="item">
                                                                <option value="${item.manufacturerId}" <c:if test="${manufacturerId==item.manufacturerId}">selected</c:if>>${item.manufacturerName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="productStatus"/></label>
                                                        <select name="productStatusId" Class="form-control select">
                                                            <option value="0">-All-</option>
                                                            <c:forEach items="${productStatusList}" var="item">
                                                                <option value="${item.productStatusId}" <c:if test="${productStatusId==item.productStatusId}">selected</c:if>>${item.productStatusDesc}</option>
                                                            </c:forEach>
                                                        </select><br>
                                                    </div>
                                                </div>
                                                <div class="col-md-2" style="margin-top: 20px">
                                                    <div class="form-group">
                                                        <label>Product Date from</label>
                                                        <input type="text" name="startDate" value="${startDate}" Class="form-control datepicker"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2" style="margin-top: 20px">
                                                    <div class="form-group">
                                                        <label>Product Date to</label>
                                                        <input type="text" name="stopDate" value="${stopDate}" Class="form-control datepicker"/>
                                                    </div>
                                                </div>        

                                                <div class="col-md-2 btn-filter pull-right">
                                                    <input type="submit"  class="btn-info btn-sm pull-right" name="btnSubmit" value="<spring:message code="button.Go"/>"/>
                                                </div>
                                            </form>
                                        </div>
                                    </div>


                                    <div class="row">
                                        <div class="col-md-12 scrollable">
                                                <table class="table datatable table-bordered" >
                                                <thead>
                                                    <tr>
                                                        <th width="100px"><spring:message code="productName"/></th>
                                                        <th width="100px"><spring:message code="performanceModsMpn"/></th>
                                                        <th width="100px"><spring:message code="manufacturerName"/></th>
                                                        <th width="100px"><spring:message code="mpn"/></th>
                                                        <th width="50px"><spring:message code="map"/></th>
                                                        <th width="50px"><spring:message code="weight"/></th>
                                                        <th width="100px"><spring:message code="upc"/></th>
                                                        <th width="100px"><spring:message code="warehouseName"/></th>
                                                        <th width="100px">Warehouse Part Number</th>
                                                        <th width="100px"><spring:message code="productStatus"/></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <sec:authorize ifAnyGranted="ROLE_BF_EDIT_PRODUCT">
                                                        <c:set var="rowClick" value="clickableRow"/>  
                                                    </sec:authorize>
                                                    <c:forEach items="${productList}" var="productItem">
                                                        <tr id="product" data-product-id="${productItem.productId}" class="${rowClick}">
                                                            <td><c:out value="${productItem.productName}"/></td>
                                                            <td><c:out value="${productItem.performanceModsMpn}"/></td>
                                                            <td><c:out value="${productItem.manufacturer.manufacturerName}"/></td>
                                                            <td><c:out value="${productItem.manufacturerMpn}"/></td>
                                                            <td><c:out value="${productItem.productMap}"/></td>
                                                            <td><c:out value="${productItem.productWeight}"/></td>
                                                            <td><c:out value="${productItem.upc}"/></td>
                                                            <td><c:out value="${productItem.warehouseName}"/></td>
                                                            <td><c:out value="${productItem.warehouseMpn}"/></td>
                                                            <td><c:out value="${productItem.productStatus.productStatusDesc}"/></td>
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
                    <!-- edit product form -->
                    <form name="form2" id="form2" action="" method="get">
                        <input type="hidden" id="productId" name="productId"/>
                    </form>
                    <form name="excelForm" id="excelForm" method="get" action="../product/productListExcel.htm">
                        <input type="hidden" name="productMpn" value="${productMpn}"><br/>
                        <input type="hidden" name="warehouseMPN" value="${warehouseMPN}"><br/>
                        <input type="hidden" name="performanceModsMpn" value="${performanceModsMpn}"><br/>
                        <input type="hidden" name="productName" value="${productName}"><br/>
                        <input type="hidden" name="manufacturerId" value="${manufacturerId}"><br/>
                        <input type="hidden" name="productStatusId" value="${productStatusId}"><br/>
                    </form>

                </div>
            </div>
        </div>

        <%@include file="../common/messagebox.jsp" %>

        <%@include file="../common/script.jsp" %>
        <!-- START THIS PAGE PLUGINS-->        
        <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>        
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type="text/javascript" src="../js/plugins/pagination/jquery.bootpag.min.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-combobox.js"></script>
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    
        <!-- END THIS PAGE PLUGINS-->        

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>        
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->
        <script type="text/javascript" defer="defer">
            //Pagination
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

            function getPageNo(pageNo) {
                   $('#pageNo').val(pageNo);
                   $('#form1').submit();
               }
               
            //function for edit product page link
            $(document).ready(function() {
            <sec:authorize ifAnyGranted="ROLE_BF_EDIT_PRODUCT">
                    $('#product td').click(function() {
                        $('#productId').val($(this).parent().data("product-id"));
                        $('#form2').prop('action', '../product/editProduct.htm');
                        $('#form2').submit();
                    });
            </sec:authorize>
                });

                //Product MPN autocomplete
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

                //MPN autocomplete
                $(function() {
                    src2 = "getWareHouseMpnListForAutocomplete.htm";
                    $("#warehouseMPN").autocomplete({
                        source: function(request, response) {
                            $.ajax({
                                url: src2,
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

                //TEL-MPN autocomplete
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

                //Product name autocomplete    
                $(function() {
                    src = "getProductNameListForAutocomplete.htm";
                    $("#productName").autocomplete({
                        source: function(request, response) {
                            $.ajax({
                                url: src,
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

                $('#manufacturerId').combobox();
                
                 
        </script>
    </body>
</html>