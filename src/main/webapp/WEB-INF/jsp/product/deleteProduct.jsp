<%-- 
    Document   : deleteProduct
    Created on : 29 May, 2018, 4:25:04 PM
    Author     : Ritesh
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
                    <li><a href="#">Delete Product</a></li>
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
                                    <h3 class="panel-title">Delete Product</h3>
                                    <ul class="panel-controls">
                                        <%--<sec:authorize ifAnyGranted="ROLE_BF_EXPORT_EXCEL">--%>
                                        <c:if test="${fn:length(productToDelete)>0}"><li><a href="#" onclick="$('#excelForm').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li></c:if>
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
                                                        <label>Select Manufacturer MPN</label>
                                                        <input type="text" name="marketplaceMpn" id="marketplaceMpn" value="${marketplaceMpn}" Class="form-control"/>
                                                    </div>
                                                </div>



                                                <div class="col-md-2 btn-filter">
                                                    <input type="submit"  class="btn-info btn-sm" name="btnSubmit" value="<spring:message code="button.Go"/>"/>
                                                </div>
                                            </form>
                                        </div>
                                    </div>

                                    <c:if test="${fn:length(productToDelete)>0}">
                                        <div class="row">
                                            <div class="col-md-12 scrollable">
                                                <table class="table datatable table-bordered" >
                                                    <thead>
                                                        <tr>
                                                            <th width="100px"><spring:message code="mpn"/></th>
                                                            <th width="100px"><spring:message code="manufacturerName"/></th>
                                                            <th width="100px"><spring:message code="performanceModsMpn"/></th>
                                                            <th width="100px"><spring:message code="warehouseName"/></th>
                                                            <th width="100px">Warehouse Part Number</th>
                                                            <th width="100px"><spring:message code="productStatus"/></th>
                                                            <th width="100px">Delete</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <sec:authorize ifAnyGranted="ROLE_BF_EDIT_PRODUCT">
                                                            <c:set var="rowClick" value="clickableRow"/>  
                                                        </sec:authorize>
                                                        <c:forEach items="${productToDelete}" var="item">
                                                            <tr>
                                                                <td><c:out value="${item.MPN}"/></td>
                                                                <td><c:out value="${item.MANUFACTURER_NAME}"/></td>
                                                                <td><c:out value="${item.ADMIN_TOOL_MPN}"/></td>
                                                                <td><c:out value="${item.WAREHOUSE_NAME}"/></td>
                                                                <td><c:out value="${item.WAREHOUSE_IDENTIFICATION_NO}"/></td>
                                                                <td><c:out value="${item.PRODUCT_STATUS_DESC}"/></td>
                                                                <td><button id="deletebtn" type="button" class="btn btn-primary" onclick="functionchange('${item.WAREHOUSE_IDENTIFICATION_NO}');">Delete</button></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                                <%--<c:if test="${pageCount>0}"><div class="demo2 pull-right"></div></c:if>--%>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>

                            </div>
                        </div>
                    </div>
                    <!-- edit product form -->
                    <!--                        <form name="form2" id="form2" action="" method="get">
                                                <input type="hidden" id="productId" name="productId"/>
                                            </form>-->
                    <form name="form3" id="form3" action="../product/deleteProductByID.htm" method="get">
                        <input type="hidden" id="productId" name="productId" />
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
        <script type="text/javascript">

        </script>
        <script type="text/javascript">
            //TEL-MPN autocomplete
            $(function () {
                src1 = "getMpnListForAutocomplete.htm";
                $("#performanceModsMpn").autocomplete({
                    source: function (request, response) {
                        $.ajax({
                            url: src1,
                            dataType: "json",
                            data: {
                                term: request.term
                            },
                            success: function (data) {
                                response(data);
                            }
                        });
                    },
                    minLength: 5
                });
            });

            function functionchange(warehouse_part_num) {

                var ans = confirm('Are you sure you want to continue?');
                if (ans == true) {
                    $.ajax({
                        url: "../ajax/deleteProductByID.htm",
                        data: ({'warehouse_part_num': warehouse_part_num}),
                        dataType: "json",
                        success: function(json) {
                            if (json == "Success") {
                                alert("Product Deleted Successfully");
                                window.location.reload();
                            } else {
                                alert("Fail");
                            }
                        },
                        error: function(e) {
                            alert("Error:  method not called ");
                        }
                    });
                }

            }

        </script>
    </body>
</html>