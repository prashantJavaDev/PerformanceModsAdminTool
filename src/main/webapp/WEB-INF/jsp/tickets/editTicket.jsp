<%-- 
    Document   : editTicket
    Created on : 15 May, 2017, 3:52:09 PM
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
    <body onload="fieldsDisplay();">
        <!-- START PAGE CONTAINER -->
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>

            <!-- PAGE CONTENT -->
            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home.htm">Home</a></li>
                    <c:if test="${uniqueCode!=1}"><li><a href="../tickets/listAllTickets.htm">View All Tickets</a></li></c:if>
                    <c:if test="${uniqueCode==2}"><li><a href="../tickets/listOpenTickets.htm">open Ticket List</a></li></c:if>
                        <li><a href="../tickets/editTicket.htm">Edit Ticket</a></li>
                    </ul>
                    <!-- END BREADCRUMB --> 

                    <!-- PAGE CONTENT WRAPPER -->
                    <div class="page-content-wrap">
                        <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <strong style="color: #1caf9a;">Ticket details for Ticket No. : ${ticket.ticketNo} </strong><br/><br/>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><spring:message code="title.editTicket"/></h3>
                                </div>
                                <form:form  commandName="ticket" method="POST" name="form1" id="form1" onkeypress="return event.keyCode != 13;">
                                    <div class="panel-body">
                                        <div class="panel panel-warning">
                                            <div class="panel-body">
                                                <div class="row">
                                                    <!--Hidden Values-->
                                                    <form:hidden path="ticketNo"/>
                                                    <form:hidden path="ticketType.ticketTypeId"/>
                                                    <form:hidden path="createdBy.userId" id="createdBy"/>
                                                    <div class="row">
                                                        <div class="col-md-2">
                                                            <div class="form-group">
                                                                <label><spring:message code="companyName"/></label>
                                                                <form:input path="company.companyName" cssClass="form-control" readonly="true" style="color:black"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <div class="form-group">
                                                                <label><spring:message code="marketplaceName"/></label>
                                                                <form:input path="marketplace.marketplaceName" cssClass="form-control" readonly="true" style="color:black"/><br>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <div class="form-group">
                                                                <label><spring:message code="ticketType"/></label>
                                                                <form:input path="ticketType.ticketTypeDesc" cssClass="form-control" readonly="true" style="color:black"/><br>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <div class="form-group">
                                                                <label><spring:message code="priority"/></label>
                                                                <form:input path="ticketPriority.priorityName" cssClass="form-control" readonly="true" style="color:black"/><br>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <div class="form-group">
                                                                <label><spring:message code="orderId"/></label>
                                                                <form:input path="orderId" cssClass="form-control" readonly="true" style="color:black"/><br>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <div class="form-group">
                                                                <label><spring:message code="customerName"/></label>
                                                                <form:input path="customerName" cssClass="form-control" readonly="true" style="color:black"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-2">
                                                            <div class="form-group">
                                                                <label><spring:message code="custPhoneNumber"/></label>
                                                                <form:input path="custPhoneNumber" cssClass="form-control" readonly="true" style="color:black"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <div class="form-group">
                                                                <label><spring:message code="custEmailId"/></label>
                                                                <form:input path="custEmailId" cssClass="form-control" readonly="true" style="color:black"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <div class="form-group">
                                                                <label><spring:message code="title"/></label>
                                                                <form:input path="description"  cssClass="form-control" readonly="true" style="color:black"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <div class="form-group">
                                                                <label><spring:message code="details"/></label>
                                                                <form:textarea path="details"  cssClass="form-control" readonly="true" style="color:black"/><br>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <!--TABLES-->
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h3 class="panel-title">Ticket History</h3>
                                                    </div>
                                                    <div class="panel-body">
                                                        <div class="row">
                                                            <div class="col-md-12 scrollable">
                                                                <table class="table datatable table-bordered">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>Transaction Date</th>
                                                                            <th><spring:message code="assignedTo"/></th>
                                                                            <th><spring:message code="status"/></th>
                                                                            <th><spring:message code="ticketType"/></th>
                                                                            <th><spring:message code="priority"/></th>
                                                                            <c:if test="${ticket.ticketType.ticketTypeId==3}">
                                                                                <th><spring:message code="trackingId"/></th>
                                                                                <th><spring:message code="trackingCarrierName"/></th>
                                                                            </c:if>
                                                                            <th width ="300px"><spring:message code="notes"/></th>
                                                                            <th width ="300px"><spring:message code="notesby"/></th>
                                                                        </tr>  
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach items="${ticketHistoryList}" var="tt">
                                                                            <tr>
                                                                                <td><fmt:formatDate value="${tt.transDate}" pattern="dd-MMM-yyyy HH:mm"/></td>
                                                                                <td><c:out value="${tt.assignedTo.username}"/></td>
                                                                                <td><c:out value="${tt.ticketStatus.statusDesc}"/></td>
                                                                                <td><c:out value="${tt.ticketType.ticketTypeDesc}"/></td>
                                                                                <td><c:out value="${tt.priorityName}"/></td>
                                                                                <c:if test="${ticket.ticketType.ticketTypeId==3}">
                                                                                    <td><c:out value="${tt.trackingId}"/></td>
                                                                                    <td><c:out value="${tt.trackingCarrierName}"/></td>
                                                                                </c:if>
                                                                                <td>${tt.notes}</td>
                                                                                <td><c:out value="${tt.lastModifiedBy.username}"/></td>
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

                                        <div class="panel panel-warning">
                                            <div class="panel-body form-horizontal">
                                                <div class="row">
                                                    <div class="col-md-6" id="statusDiv">
                                                        <div class="form-group">
                                                            <label class="req col-md-4 control-label"><spring:message code="status"/></label>
                                                            <div class="col-md-6">
                                                                <form:select path="ticketStatus.statusId" id="statusId" cssClass="form-control select" onchange="fieldsDisplay();">
                                                                    <form:options items="${ticketStatusList}" itemLabel="statusDesc" itemValue="statusId"/>
                                                                </form:select>
                                                                <span class="help-block">Please select ticket status</span>
                                                            </div>
                                                        </div>
                                                        <div class="form-group" id="assignedToDiv">
                                                            <label class="req col-md-4 control-label"><spring:message code="assignedTo"/></label>
                                                            <div class="col-md-6">
                                                                <form:select path="assignedTo.userId" id="assignedToId" cssClass="form-control select">
                                                                    <form:option value="" label="Nothing selected"/>
                                                                    <form:options items="${assignedToList}" itemLabel="username" itemValue="userId"/>
                                                                </form:select>
                                                                <span class="help-block">Please select assigned to</span>
                                                            </div>
                                                        </div>
                                                        <div class="form-group" id="completedOnDiv">
                                                            <label class="req col-md-4 control-label"><spring:message code="completedOn"/></label>
                                                            <div class="col-md-6">
                                                                <form:input path="completedOn" id="completedOn" cssClass="form-control" readonly="true"/>
                                                                <span class="help-block">Please select completed on</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="form-group" id="trackingIdDiv" style="display:none">
                                                            <label class="col-md-4 control-label"><spring:message code="trackingId"/></label>
                                                            <div class="col-md-6">
                                                                <form:input path="trackingId" cssClass="form-control"/>
                                                                <span class="help-block">Please enter tracking Id</span>
                                                            </div>
                                                        </div>
                                                        <div class="form-group" id="trackingCarDiv" style="display:none">
                                                            <label class="col-md-4 control-label"><spring:message code="trackingCarrierName"/></label>
                                                            <div class="col-md-6">
                                                                <form:input path="trackingCarrierName" cssClass="form-control"/>
                                                                <span class="help-block">Please enter tracking Carrier Name</span>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-md-4 control-label"><spring:message code="notes"/></label>
                                                            <div class="col-md-6">
                                                                <textarea name="notes" id="notes" class="form-control"></textarea>
                                                                <span class="help-block">Please enter notes</span>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <div class="col-md-2 col-xs-12 control-label">
                                                                <spring:hasBindErrors name="ticket">
                                                                    <div class="text-danger">
                                                                        <form:errors path="*"/>
                                                                    </div>
                                                                </spring:hasBindErrors>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel-footer">
                                            <div class="pull-right">
                                                <button type="submit" name="submit"  class="btn btn-success">Submit</button>
                                                <button type="submit" name="_cancel" class="btn btn-primary" formnovalidate="formnovalidate">Back</button>
                                            </div>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
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
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-datetimepicker.js"></script>
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    
        <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script> 

        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript">
            //Datepicker and datetimepicker
            $(document).ready(function() {
                $('#completedOn').datetimepicker({
                    startDate: new Date('${ticket.createdDateStr}'),
                    autoclose: true,
                    endDate: new Date(),
                    minuteStep: 1,
                    pickerPosition: 'top-left'
                });
            });

            var jvalidate = $("#form1").validate({
                ignore: [],
                rules: {
                    'assignedTo.userId': {
                        required: function(element) {
                            return ($('#statusId').val() != 1 && $('#statusId').val() != 5)
                        }
                    },
                    completedOn: {
                        required: function(element) {
                            return ($('#statusId').val() == 4)
                        }
                    }
                },
                errorPlacement: function(error, element) {
                    if (element.hasClass('select')) {
                        error.insertAfter(element.next(".bootstrap-select"));
                        element.next("div").addClass("error");
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            <sec:authentication property="principal" var="curUser" scope="request" />
                function fieldsDisplay() {
                    var statusId = $('#statusId').val();
                    var ticketTypeId =${ticket.ticketType.ticketTypeId};
                    var assignedTo = $('#assignedToId').val();
                    var createdBy = $('#createdBy').val();
                    if(assignedTo != ${curUser.userId}  && createdBy != ${curUser.userId} && ${curUser.role.roleId == 'ROLE_AGENT'})
                    {
                        $('#assignedToDiv').hide();
                        $('#statusDiv').hide();
                        //document.getElementById("statusId").disabled = true;
                    }
                    if(${curUser.role.roleId == 'ROLE_SUPERVISOR' || curUser.role.roleId == 'ROLE_MANAGER' || curUser.role.roleId == 'ROLE_ADMIN'})
                    {
                        $('#assignedToDiv').show();
                        $('#statusDiv').show();
                        document.getElementById("assignedToId").disabled = false;
                    }
                    
//                    if()
//                    {
//                        $('#assignedToDiv').hide();
//                        $('#statusDiv').hide();
//                        document.getElementById("assignedToId").disabled = true;
//                    }
                    if (ticketTypeId == 3) {
                        $('#trackingIdDiv').show();
                        $('#trackingCarDiv').show();
                    }
                    if (statusId == 2) {
                        $('#assignedToDiv').show();
                        $('#completedOnDiv').hide();
                        //document.getElementById("assignedToId").disabled = false;
                    } else if (statusId == 3) {
                        $('#assignedToDiv').show();
                        $('#completedOnDiv').hide();
                        document.getElementById("assignedToId").disabled = false;
                    } else if (statusId == 4) {
                        $('#assignedToDiv').show();
                        $('#completedOnDiv').show();
                        if (assignedTo == "") {
                            document.getElementById("assignedToId").disabled = false;
                        } else {
                            document.getElementById("assignedToId").disabled = true;
                        }
                    } else {
                        $('#assignedToDiv').hide();
                        $('#completedOnDiv').hide();
                    }
                }
                //to diable table sorting
                $('.table').dataTable({
                    "order": []
                });

        </script> 
    </body>
</html>
