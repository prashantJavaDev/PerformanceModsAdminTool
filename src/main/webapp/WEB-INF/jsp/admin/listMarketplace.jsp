<%-- 
    Document   : listMarketplace
    Created on : 6 Mar, 2017, 3:55:23 PM
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
        <div id="page_wrapper">
            <div class="page-container page-navigation-toggled page-container-wide">
                <%@include file="../common/sidebar.jsp" %>
                <!-- PAGE CONTENT -->
                <div class="page-content">
                    <%@include file="../common/topbar.jsp" %>
                    <ul class="breadcrumb">
                        <li><a href="../home/home.htm">Home</a></li>
                        <li><a href="../home/home.htm">Admin</a></li>
                        <li><a href="../home/home.htm">Marketplace</a></li>
                        <li><a href="#">Marketplace List</a></li>
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
                                        <h3 class="panel-title"><spring:message code="title.listMarketplace"/></h3>
                                        <ul class="panel-controls">
                                            <sec:authorize ifAnyGranted="ROLE_BF_ADD_MARKETPLACE">
                                                <li><a href="../admin/addMarketplace.htm"><span class="fa fa-plus"></span></a></li>
                                                    </sec:authorize>
                                            <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                        </ul>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-6 scrollable">
                                                <table class="table datatable table-bordered" >
                                                    <thead>
                                                        <tr>
                                                            <th width="200px"><spring:message code="marketplaceName"/></th>
                                                            <th width="200px"><spring:message code="active"/></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${marketplaceList}" var="marketplace">
                                                            <sec:authorize ifAnyGranted="ROLE_BF_EDIT_MARKETPLACE">
                                                                <c:set var="rowClick" value="clickableRow "/>  
                                                            </sec:authorize>
                                                            <c:choose>
                                                                <c:when test="${marketplace.active}"><c:set var="rowStyle" value=""/></c:when>
                                                                <c:otherwise><c:set var="rowStyle" value="rowColor1"/></c:otherwise>
                                                            </c:choose>
                                                            <tr id="marketplace" data-marketplace-id="${marketplace.marketplaceId}" class="${rowClick}${rowStyle}">
                                                                <td><c:out value="${marketplace.marketplaceName}"/></td>
                                                                <td><c:if test="${marketplace.active==true}"><spring:message code="yes"/></c:if>
                                                                    <c:if test="${marketplace.active==false}"><spring:message code="no"/></c:if>
                                                                    </td>
                                                                </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- edit form page-->
                        <form name="form2" id="form2" action="" method="get">
                            <input type="hidden" id="marketplaceId" name="marketplaceId"/>
                        </form>
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
            <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    
            <!-- END THIS PAGE PLUGINS-->        

            <!-- START TEMPLATE -->
            <script type="text/javascript" src="../js/plugins.js"></script>        
            <script type="text/javascript" src="../js/actions.js"></script>
            <!-- END TEMPLATE -->

            <script type="text/javascript" defer="defer">
                <sec:authorize ifAnyGranted="ROLE_BF_EDIT_MARKETPLACE">
                $('.clickableRow td').click(function() {
                    $('#marketplaceId').val($(this).parent().data("marketplace-id"));
                    $('#form2').prop('action', '../admin/editMarketplace.htm');
                    $('#form2').submit();
                });
                </sec:authorize>

                //disable automatic table sorting
                $('.table').dataTable({
                    "order": []
                });
            </script>
    </body>
</html>

