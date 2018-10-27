<%-- 
    Document   : missingProductDataList
    Created on : 24 May, 2018, 2:01:19 PM
    Author     : Pallavi
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
                    <li><a href="#">Missing Product Data</a></li>
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
                                            <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                        </ul>
                                    </div>
                                    <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12 scrollable">
                                            <table id="example" class="table datatable table-bordered" >
                                                <thead>
                                                    <tr>
                                                        <th width="100px"><spring:message code="productName"/></th>
                                                    <th width="100px"><spring:message code="manufacturerName"/></th>
                                                    <th width="100px">Title</th>
                                                    <th width="100px"><spring:message code="upc"/></th>
                                                    <th width="50px">Main Category</th>
                                                    <th width="100px">Missing Values</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:set var="rowClick" value="clickableRow"/>  
                                                <c:forEach items="${productMissing}" var="productMissing">
                                                    <tr id="product" data-product-id="${productMissing.productId}" class="${rowClick}">
                                                        <td><c:out value="${productMissing.productName}"/></td>
                                                        <td><c:out value="${productMissing.manufacturer.manufacturerName}"/></td>
                                                        <td><c:out value="${productMissing.productTitle}"/></td>
                                                        <td><c:out value="${productMissing.upc}"/></td>
                                                        <td><c:out value="${productMissing.mainCategory.mainCategoryDesc}"/></td>
                                                        <td>several fields are missing</td>
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
                        <form name="form1" id="form1" method="post">
                              <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
                        </form>

                </div>
            </div>
        </div>

        <%@include file="../common/messagebox.jsp" %>

        <%@include file="../common/script.jsp" %>
               
        <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>        
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="../js/plugins/pagination/jquery.bootpag.min.js"></script>

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
            
                                             
                                            //function for edit product page link
                                            $(document).ready(function() {
                                                $('#product td').click(function() {
                                                    //                                                    alert("hello")
                                                    $('#productId').val($(this).parent().data("product-id"));
                                                    $('#form2').prop('action', '../product/editProduct.htm');
                                                    $('#form2').submit();
                                                });
                                            });


        </script>
    </body>
</html>
