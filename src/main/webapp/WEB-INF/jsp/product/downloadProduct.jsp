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
                    <li><a href="#">Product Download</a></li>
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
                                    <h3 class="panel-title"><spring:message code="title.productDownload"/></h3>
                                    <ul class="panel-controls">
                                        <%--<sec:authorize ifAnyGranted="ROLE_BF_EXPORT_EXCEL">--%>
                                        <c:if test="${fn:length(productDownloadList)>0}"><li><a href="#" onclick="$('#excelForm').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li></c:if>
                                        <%--</sec:authorize>--%>
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
                                                        <select id="productStatusId" name="productStatusId" Class="form-control select">
                                                            <option value="0">-All-</option>
                                                            <c:forEach items="${productStatusList}" var="item">
                                                                <option value="${item.productStatusId}" <c:if test="${productStatusId==item.productStatusId}">selected</c:if>>${item.productStatusDesc}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="mainCategory"/></label>
                                                        <select id="mainCategoryId" name="mainCategoryId" Class="form-control select">
                                                            <option value="0">-Nothing Selected-</option>
                                                            <c:forEach items="${mainCategoryList}" var="item">
                                                                <option value="${item.mainCategoryId}" <c:if test="${mainCategoryId==item.mainCategoryId}">selected</c:if>>${item.mainCategoryDesc}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="subCategory"/></label>
                                                        <select id="subCategoryId" name="subCategoryId" Class="form-control select">
                                                            <option value="0">-Nothing Selected-</option>
                                                            <c:forEach items="${subCategoryList}" var="item">
                                                                <option value="${item.subCategoryId}" <c:if test="${subCategoryId==item.subCategoryId}">selected</c:if>>${item.subCategoryDesc}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="childCategory"/></label>
                                                        <select id="childCategoryId" name="childCategoryId" Class="form-control select">
                                                            <option value="0">-Nothing Selected-</option>
                                                            <c:forEach items="${childCategoryList}" var="item">
                                                                <option value="${item.childCategoryId}" <c:if test="${childCategoryId==item.childCategoryId}">selected</c:if>>${item.childCategoryDesc}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
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
                                                        <th width="100px">Product Name</th>
                                                        <th width="100px">Manufacturer Name</th>
                                                        <th width="100px">MPN</th>
                                                        <th width="100px">Weight</th>
                                                        <th width="50px">Category</th>
                                                        <th width="50px">Sub-Category</th>
                                                        <th width="100px">Child Category</th>
                                                        <th width="100px">Short Desc</th>
                                                        <th width="100px">Long Desc</th>
                                                        <th width="100px">Small Image Url</th>
                                                        <th width="100px">Large Image Url</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <sec:authorize ifAnyGranted="ROLE_BF_EDIT_PRODUCT">
                                                        <c:set var="rowClick" value="clickableRow"/>  
                                                    </sec:authorize>
                                                    <c:forEach items="${productDownloadList}" var="item">
                                                        <tr id="product" data-product-id="${productItem.productId}" class="${rowClick}">
                                                            <td><c:out value="${item.productName}"/></td>
                                                            <td><c:out value="${item.manufacturer.manufacturerName}"/></td>
                                                            <td><c:out value="${item.manufacturerMpn}"/></td>
                                                            <td><c:out value="${item.productWeight}"/></td>
                                                            <td><c:out value="${item.mainCategory.mainCategoryDesc}"/></td>
                                                            <td><c:out value="${item.subCategory1.subCategoryDesc}"/></td>
                                                            <td><c:out value="${item.childCategory.childCategoryDesc}"/></td>
                                                            <td><c:out value="${item.shortDesc}"/></td>
                                                            <td><c:out value="${item.longDesc}"/></td>
                                                            <td><c:out value="${item.resizeImageUrl}"/></td>
                                                            <td><c:out value="${item.largeImageUrl1}"/></td>
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
 
                        <form name="excelForm" id="excelForm" method="get" action="../product/productDownloadListExcel.htm">
                        <input type="hidden" name="productName" value="${productName}"><br/>
                        <input type="hidden" name="manufacturerId" value="${manufacturerId}"><br/>
                        <input type="hidden" name="productStatusId" value="${productStatusId}"><br/>
                        <input type="hidden" name="mainCategoryId" value="${mainCategoryId}"><br/>
                        <input type="hidden" name="subCategoryId" value="${subCategoryId}"><br/>
                        <input type="hidden" name="childCategoryId" value="${childCategoryId}"><br/>
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