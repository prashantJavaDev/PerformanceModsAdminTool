<%-- 
    Document   : companyList
    Created on : 11 May, 2017, 6:10:37 PM
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
                        <li><a href="../home/home.htm">Company</a></li>
                        <li><a href="#">Company List</a></li>
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
                                        <h3 class="panel-title"><spring:message code="title.companyList"/></h3>
                                        <ul class="panel-controls">
                                            <sec:authorize ifAnyGranted="ROLE_BF_ADD_COMPANY">
                                                <li><a href="../admin/addCompany.htm"><span class="fa fa-plus"></span></a></li>
                                                    </sec:authorize>
                                            <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                        </ul>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-12 scrollable">
                                                <table class="table datatable table-bordered" >
                                                    <thead>
                                                        <tr>
                                                            <th width="200px"><spring:message code="companyName"/></th>
                                                            <th width="200px"><spring:message code="ownerName"/></th>
                                                            <th width="200px"><spring:message code="companyContactNumber"/></th>
                                                            <th width="200px"><spring:message code="companyCountryName"/></th>
                                                            <th width="200px"><spring:message code="active"/></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${companyList}" var="companyItem">
                                                            <sec:authorize ifAnyGranted="ROLE_BF_EDIT_COMPANY">
                                                                <c:set var="rowClick" value="clickableRow "/>  
                                                            </sec:authorize>
                                                            <c:choose>
                                                                <c:when test="${companyItem.active}"><c:set var="rowStyle" value=""/></c:when>
                                                                <c:otherwise><c:set var="rowStyle" value="rowColor1"/></c:otherwise>
                                                            </c:choose>
                                                            <tr id="company" data-company-id="${companyItem.companyId}" class="${rowClick}${rowStyle}">
                                                                <td><c:out value="${companyItem.companyName}"/></td>
                                                                <td><c:out value="${companyItem.ownerName}"/></td>
                                                                <td><c:out value="${companyItem.contactNumber}"/></td>
                                                                <td><c:out value="${companyItem.countryName}"/></td>
                                                                <td><c:if test="${companyItem.active==true}"><spring:message code="yes"/></c:if>
                                                                    <c:if test="${companyItem.active==false}"><spring:message code="no"/></c:if>
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
                            <input type="hidden" id="companyId" name="companyId"/>
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
                <sec:authorize ifAnyGranted="ROLE_BF_EDIT_COMPANY">
                $('.clickableRow td').click(function() {
                    $('#companyId').val($(this).parent().data("company-id"));
                    $('#form2').prop('action', '../admin/editCompany.htm');
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
