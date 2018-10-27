<%-- 
    Document   : agentLoginAndBreakLog
    Created on : 24 Oct, 2017, 5:30:32 PM
    Author     : ritesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
                    <li><a href="../home/home.htm">Reports</a></li>
                    <li><a href="#">Agent Login And Break Report</a></li>
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
                                    <h3 class="panel-title"><spring:message code="title.agentLoginAndBreakLog"/></h3>
                                    <ul class="panel-controls">
                                        <%--<sec:authorize ifAnyGranted="ROLE_BF_EXPORT_EXCEL">--%>
                                        <c:if test="${fn:length(reportList)>0}"><li><a href="#" onclick="$('#excelForm').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li></c:if>
                                        <%--</sec:authorize>--%>    
                                        <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                        </ul>
                                    </div>
                                    <div class="panel-body">
                                        <!-- START FILTER PANEL -->
                                        <div class="panel panel-warning">
                                            <div class="panel-body">
                                                <form name="form1" id="form1" method="post">
                                                    <div class="row">
                                                        <div class="col-md-2">
                                                            <div class="form-group">
                                                                <label><spring:message code="startDt"/></label>
                                                            <input name="startDate" id="startDate" value="${startDate}" class="form-control datepicker"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="stopDt"/></label>
                                                            <input  name="stopDate"  id="stopDate" value="${stopDate}" class="form-control datepicker"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="username"/></label>
                                                            <select id="userId" name="userId" class="form-control select">
                                                                <option value="-1">-All-</option>
                                                                <c:forEach items="${userList}" var="user">
                                                                    <option value="${user.userId}" <c:if test="${userId==user.userId}">selected</c:if>>${user.username}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-2 btn-filter">
                                                        <button type="submit" class="btn-info btn-sm"><spring:message code="button.Go"/></button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <!-- END FILTER PANEL -->
                                    <div class="row">
                                        <div class="col-md-12 scrollable">
                                            <table class="table datatable table-bordered" >
                                                <thead>
                                                    <tr>
                                                        <th width="200px"><spring:message code="accessDate"/></th>
                                                        <th width="200px"><spring:message code="ip"/></th>
                                                        <th width="200px"><spring:message code="username"/></th>
                                                        <th width="200px"><spring:message code="outcome"/></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${reportList}" var="access">
                                                        <tr>
                                                            <td><fmt:formatDate value="${access.accessDate}" pattern="dd-MM-yyyy HH:mm:ss"/></td>
                                                            <td>${access.ipAddress}</td>
                                                            <td>${access.username}</td>
                                                            <td>${access.outcome}</td>
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
                    <form name="excelForm" id="excelForm" method="post" action="../report/reportAccessLogExcel.htm">
                        <input type="hidden" name="startDate" value="${startDate}"/>
                        <input type="hidden" name="stopDate" value="${stopDate}"/>
                        <input type="hidden" name="userId" value="${userId}"/>
                        
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
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    
        <!-- END THIS PAGE PLUGINS-->        

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>        
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript">
            //disable automatic table sorting
            $('.table').dataTable({
                "order": []
            });
        </script>
    </body>
</html>
