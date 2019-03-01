<%-- 
    Document   : deleteMarketplaceListing
    Created on : 15 Feb, 2019, 11:08:25 PM
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
                    <li><a href="../home/home.htm">Listing</a></li>
                    <li><a href="#">Delete Listing</a></li>
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
                                    <h3 class="panel-title">Delete Listing</h3>
                                    <ul class="panel-controls">
                                        <%--<sec:authorize ifAnyGranted="ROLE_BF_EXPORT_EXCEL">--%>
                                        <%--<c:if test="${fn:length(productToDelete)>0}"><li><a href="#" onclick="$('#excelForm').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li></c:if>--%>
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
                                                        <label><spring:message code="marketplaceSku"/></label>
                                                        <input type="text" name="marketplaceSku" id="marketplaceSku" value="${marketplaceSku}" Class="form-control"/>
                                                    </div>
                                                </div>



                                                <div class="col-md-2 btn-filter">
                                                    <input type="submit"  class="btn-info btn-sm" name="btnSubmit" value="<spring:message code="button.Go"/>"/>
                                                </div>
                                            </form>
                                        </div>
                                    </div>

                                    <%--<c:if test="${fn:length(productToDelete)>0}">--%>
                                    <div class="row">
                                        <div class="col-md-12 scrollable">
                                            <table class="table datatable table-bordered" >
                                                <thead>
                                                    <tr>
                                                        <th width="100px"><spring:message code="marketplaceName"/></th>
                                                        <th width="100px"><spring:message code="marketplaceListingId"/></th>
                                                        <th width="100px"><spring:message code="marketplaceSku"/></th>
                                                        <th width="100px"><spring:message code="manufacturerMpn"/></th>
                                                        <th width="100px"><spring:message code="currentPrice"/></th>
                                                        <th width="50px"><spring:message code="currentQuantity"/></th>
                                                        <th width="50px">Delete Listing</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <sec:authorize ifAnyGranted="ROLE_BF_EDIT_PRODUCT">
                                                        <c:set var="rowClick" value="clickableRow"/>  
                                                    </sec:authorize>
                                                    <%--<c:forEach items="${productToDelete}" var="productItem">--%>
                                                    <tr id="sku" data-sku-id="${productToDelete.sku}" class="${rowClick}">
                                                        <td><c:out value="${productToDelete.marketplace.marketplaceName}"/></td>
                                                        <td><c:out value="${productToDelete.marketplaceListingId}"/></td>
                                                        <td><c:out value="${productToDelete.sku}"/></td>
                                                        <td><c:out value="${productToDelete.productMpn}"/></td>
                                                        <td><c:out value="${productToDelete.currentPrice}"/></td>
                                                        <td><c:out value="${productToDelete.currentQunatity}"/></td>
                                                        <td><button onclick="functionchange(<c:out value="'${productToDelete.sku}'"/>);" id="deletebtn" type="button" class="btn btn-primary">Delete</button></td>
                                                    </tr>
                                                    <%--</c:forEach>--%>
                                                </tbody>
                                            </table>
                                            <%--<c:if test="${pageCount>0}"><div class="demo2 pull-right"></div></c:if>--%>
                                        </div>
                                    </div>
                                    <%--</c:if>--%>
                                </div>

                            </div>
                        </div>
                    </div>
                    <!-- edit product form -->
                    <!--                        <form name="form2" id="form2" action="" method="get">
                                                <input type="hidden" id="productId" name="productId"/>
                                            </form>-->
                    <form name="form3" id="form3" action="../product/deleteListingBySku.htm" method="get">
                        <input type="hidden" id="sku" name="sku" />
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

            function functionchange(sku) {
                $.ajax({
                    url: "../ajax/deleteListingBySku.htm",
                    data: ({'sku': sku}),
                    dataType: "json",
                    success: function (json) {
                        if (json == "Success") {
                            alert("Listing Deleted Successfully");
                            window.location.reload();
                        } else {
                            alert("Fail");
                        }
                    },
                    error: function (e) {
                        alert("Error:  method not called ");
                    }
                });
            }

        </script>
    </body>
</html>
