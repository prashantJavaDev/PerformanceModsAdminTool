<%-- 
    Document   : userList
    Created on : 10 Sep, 2015, 5:53:45 PM
    Author     : gaurao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../common/meta.jsp"%>
        <%@include file="../common/css.jsp"%>
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
                        <li><a href="../home/home.htm">User</a></li>
                        <li><a href="#">List User</a></li>
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
                                        <h3 class="panel-title"><spring:message code="title.userList"/></h3>
                                        <ul class="panel-controls">
                                            <sec:authorize ifAnyGranted="ROLE_BF_CREATE_USER">
                                                <li><a href="../admin/addUser.htm"><span class="fa fa-plus"></span></a></li>
                                            </sec:authorize>
                                            <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                        </ul>
                                    </div>
                                    <div class="panel-body">
                                        <!-- START FILTER PANEL -->
                                        <div class="panel panel-warning">
                                            <div class="panel-body">
                                                <form name="form1" id="form1">
                                                    <div class="row">
                                                        <div class="col-md-2">
                                                            <div class="form-group">
                                                                <label><spring:message code="role"/></label>
                                                                <select name="roleId" Class="form-control select">
                                                                    <option value="">-All-</option>
                                                                    <c:forEach items="${roleList}" var="item">
                                                                        <option value="${item.roleId}" <c:if test="${roleId==item.roleId}">selected</c:if>>${item.roleName}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-2 btn-filter">
                                                            <button type="submit"  class="btn-info btn-sm" name="btnSubmit"><spring:message code="button.Go"/></button>
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
                                                            <th width="200px"><spring:message code="username"/></th>
                                                            <th width="200px"><spring:message code="role"/></th>
                                                            <th width="50px"><spring:message code="active"/></th>
                                                            <th width="50px"><spring:message code="outsideAccess"/></th>
                                                            <th width="150px"><spring:message code="expiresOn"/></th>
                                                            <th width="50px"><spring:message code="failedAttempts"/></th>
                                                            <th width="150px"><spring:message code="lastLoginDate"/></th>
                                                            <th width="50px"></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${userList}" var="userItem">
                                                            <c:choose>
                                                                <c:when test="${userItem.active}"><c:set var="rowStyle" value="clickableRow"/></c:when>
                                                                <c:otherwise><c:set var="rowStyle" value="rowColor clickableRow"/></c:otherwise>
                                                            </c:choose>
                                                            <tr id="user" data-user-id="${userItem.userId}" class="${rowStyle}">
                                                                <td><c:out value="${userItem.username}"/></td>
                                                                <td><c:out value="${userItem.role.roleName}"/></td>
                                                                <td><c:if test="${userItem.active==true}"><spring:message code="yes"/></c:if>
                                                                    <c:if test="${userItem.active==false}"><spring:message code="no"/></c:if>
                                                                    </td>
                                                                    <td><c:choose><c:when test="${userItem.outsideAccess}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td>
                                                                <td><fmt:formatDate value="${userItem.expiresOn}" pattern="dd-MM-yyyy"/></td>
                                                                <td><c:out value="${userItem.failedAttempts}"/>
                                                                    <c:if test="${userItem.failedAttempts >0 }">
                                                                        <a href="#" class="resetLink pull-right" title="Reset failed attempts"><img src="../images/reset.png"/></a>
                                                                        </c:if>
                                                                </td>
                                                                <td><fmt:formatDate value="${userItem.lastLoginDate}" pattern="dd-MM-yyyy"/></td>
                                                                <td>
                                                                    <c:if test="${userItem.active==true}">
                                                                        <a href="#" class="resetPassword" title="Reset Password"><img src="../images/change-password-icon.png"/></a>
                                                                        </c:if>
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
                            <input type="hidden" id="userId" name="userId"/>
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
            <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
            <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    
            <!-- END THIS PAGE PLUGINS-->        

            <!-- START TEMPLATE -->
            <script type="text/javascript" src="../js/plugins.js"></script>        
            <script type="text/javascript" src="../js/actions.js"></script>
            <!-- END TEMPLATE -->

            <script type="text/javascript" defer="defer">
                //functions for edit user and reset failed attempts links
                <sec:authorize ifAnyGranted="ROLE_BF_EDIT_USER">
                    $('#user td').click(function() {
                        $('#userId').val($(this).parent().data("user-id"));
                        $('#form2').prop('action', '../admin/showEditUser.htm');
                        $('#form2').submit();
                    });
                    $('a.resetLink').click(function(event) {
                        event.stopPropagation();
                        $('#userId').val($(this).parent().parent().data('user-id'));
                        $('#form2').prop('action', '../admin/userFailedAttemptsReset.htm');
                        $('#form2').submit();
                    });

                    $('a.resetPassword').click(function(event) {
                        event.stopPropagation();
                        var check = confirm("Are you sure you want to reset password?");
                        if (check == true) {
                            $('#userId').val($(this).parent().parent().data('user-id'));
                            $('#form2').prop('action', '../admin/userPasswordReset.htm');
                            $('#form2').submit();
                        } else {
                            return null;
                        }
                    });
                </sec:authorize>

                    //disble automatic table sorting
                    $('.table').dataTable({
                        "order": []
                    });
            </script>
    </body>
</html>