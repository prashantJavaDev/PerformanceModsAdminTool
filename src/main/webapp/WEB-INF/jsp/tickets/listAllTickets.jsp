<%-- 
    Document   : listAllTickets
    Created on : 15 May, 2017, 12:16:48 PM
    Author     : Ritesh
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <body>
        <!-- START PAGE CONTAINER -->
        <div class="page-container page-navigation-toggled page-container-wide">
            <%@include file="../common/sidebar.jsp" %>

            <!-- PAGE CONTENT -->
            <div class="page-content">
                <%@include file="../common/topbar.jsp" %>

                <!-- START BREADCRUMB -->
                <ul class="breadcrumb">
                    <li><a href="../home/home.htm">Home</a></li>
                    <li><a href="../home/home.htm">Tickets</a></li>
                    <li><a href="#">View All Tickets</a></li>
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
                                    <h3 class="panel-title"><spring:message code="title.listTicket"/></h3>
                                    <ul class="panel-controls">
                                        <sec:authorize ifAnyGranted="ROLE_BF_CREATE_TICKET">
                                            <li><a href="../tickets/createTicket.htm"><span class="fa fa-plus"></span></a></li>
                                        </sec:authorize>
                                        <%--<sec:authorize ifAnyGranted="ROLE_BF_EXPORT_EXCEL">--%>
                                        <c:if test="${fn:length(ticketList)>0}"><li><a href="#" onclick="$('#excelForm').submit();" title="Export to excel"><span class="fa fa-file-excel-o"></span></a></li></c:if>
                                        <%--</sec:authorize>--%>   
                                                <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                        </ul>
                                    </div>
                                    <div class="panel-body">
                                        <!-- START FILTER PANEL -->
                                        <div class="panel panel-warning">
                                            <div class="panel-body">
                                            <form:form commandName="ticketFilterDTO" name="form1" id="form1">
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label>Created On Start Dt</label>
                                                            <input type="text" name="startDate" value="${startDate}" Class="form-control datepicker"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label>Created On Stop Dt</label>
                                                            <input type="text" name="stopDate" value="${stopDate}" Class="form-control datepicker"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="marketplaceName"/></label>
                                                            <form:select path="marketplaceId" cssClass="form-control select">
                                                                <form:option value="0" label="-All-" />
                                                                <form:options items="${marketplaceList}" itemLabel="marketplaceName" itemValue="marketplaceId"/>
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="ticketType"/></label>
                                                            <form:select path="ticketTypeId" cssClass="form-control select">
                                                                <form:option value="0" label="-All-" />
                                                                <form:options items="${ticketTypeList}" itemLabel="ticketTypeDesc" itemValue="ticketTypeId"/>
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="companyName"/></label>
                                                            <form:select path="companyId" cssClass="form-control select">
                                                                <form:option value="0" label="-All-" />
                                                                <form:options items="${companyList}" itemLabel="companyName" itemValue="companyId"/>
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="assignedTo"/></label>
                                                            <form:select path="assignedToId" cssClass="form-control select">
                                                                <form:option value="0" label="-All-" />
                                                                <form:options items="${assignedToList}" itemLabel="username" itemValue="userId"/>
                                                            </form:select><br><br>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="ticketStatus"/></label>
                                                            <form:select path="ticketStatusId" cssClass="form-control select">
                                                                <form:option value="0" label="-All-" />
                                                                <form:options items="${ticketStatusList}" itemLabel="statusDesc" itemValue="statusId"/>
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="marketplaceOrderId"/></label>
                                                            <form:input path="orderId" id="orderId" cssClass="form-control"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="customerName"/></label>
                                                            <form:input path="customerName" id="customerName" cssClass="form-control"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="ticketNo"/></label>
                                                            <form:input path="ticketNo" id="ticketNo" cssClass="form-control"/>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-2 btn-filter">
                                                        <button type="submit"  class="btn-info btn-sm" name="btnSubmit"><spring:message code="button.Go"/></button>
                                                    </div>
                                                </div>
                                            </form:form>
                                        </div>
                                    </div>
                                    <!-- END FILTER PANEL -->
                                    <div class="row">
                                        <div class="col-md-12 scrollable">
                                            <table class="table datatable table-bordered" >
                                                <thead>
                                                    <tr>
                                                        <th width="95px"><spring:message code="ticketNo"/></th> 
                                                        <th><spring:message code="createdBy"/></th>
                                                        <th><spring:message code="marketplaceName"/></th>
                                                        <th><spring:message code="ticketType"/></th>
                                                        <th><spring:message code="priority"/></th>
                                                        <th><spring:message code="orderId"/></th>
                                                        <th><spring:message code="title"/></th>
                                                        <th><spring:message code="companyName"/></th>
                                                        <th><spring:message code="customerName"/></th>
                                                        <th><spring:message code="custPhoneNumber"/></th>
                                                        <th><spring:message code="assignedTo"/></th>
                                                        <th><spring:message code="assignedOn"/></th>
                                                        <th><spring:message code="ticketStatus"/></th>
                                                        <th><spring:message code="completedOn"/></th>
                                                    </tr>
                                                </thead>
                                                <tbody> 
                                                    <c:forEach items="${ticketList}" var="tt">
                                                        <tr class="clickableRow" id="editRow" data-ticket-id="${tt.ticketId}" data-ticket-type="${tt.ticketType.ticketTypeId}">                    
                                                            <td style="font-family: monospace;">${tt.ticketNo}</td>
                                                            <td>${tt.createdBy.username}</td>
                                                            <td>${tt.marketplace.marketplaceName}</td>
                                                            <td>${tt.ticketType.ticketTypeDesc}</td>
                                                            <td>${tt.ticketPriority.priorityName}</td>
                                                            <td>${tt.orderId}</td>
                                                            <td>${tt.description}</td>
                                                            <td>${tt.company.companyName}</td>
                                                            <td>${tt.customerName}</td>
                                                            <td>${tt.custPhoneNumber}</td>
                                                            <td>${tt.assignedTo.username}</td>
                                                            <td><fmt:formatDate value="${tt.assignedOn}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                                                            <td>${tt.ticketStatus.statusDesc}
                                                                <c:if test="${tt.ticketStatus.statusId==4 || tt.ticketStatus.statusId==5}">
                                                                    <sec:authorize ifAnyGranted="ROLE_BF_REOPEN_TICKET">
                                                                        <img class="reopen pull-right" data-toggle="modal" data-target="#exampleModal" title="Reopen Ticket" src="../images/Reset_Reopen_Icon.jpg" style="border: 0;"/>
                                                                    </sec:authorize>
                                                                </c:if>
                                                            </td>
                                                            <td><fmt:formatDate value="${tt.completedOn}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
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

                    <!-- dialog box form start -->
                    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="exampleModalLabel">Reopen Ticket</h4>
                                </div>
                                <div class="modal-body">
                                    <form name="form3" id="form3">
                                        <div class="form-group">
                                            <input type="hidden" id="ticketId1" name="ticketId1"/>
                                            <label for="message-text" class="control-label">Reason:</label>
                                            <textarea class="form-control" id="notes" name="notes"></textarea>
                                            <span class="help-block">Please enter notes</span>
                                        </div>
                                        <div class="form-group">
                                            <label for="message-text" class="control-label"><spring:message code="ticketType"/></label>
                                            <select id ="reopenTicketTypeId" name="ticketTypeId" class="form-control select"> 
                                                <option value="">-</option>
                                                <c:forEach items="${ticketTypeList}" var="ticketType">
                                                    <option value="${ticketType.ticketTypeId}">${ticketType.ticketTypeDesc}</option>
                                                </c:forEach>
                                            </select>
                                            <span class="help-block">Please select ticket type</span>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                                    <button type="button" class="btn btn-success" onclick="reopenTicket();">Submit</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- dialog box form end -->

                    <form name="form2" id="form2" action="" method="get">
                        <input type="hidden" id="ticketId" name="ticketId"/>
                        <input type="hidden" id="uniqueCode" name="uniqueCode" value="1"/>
                    </form>

                    <form:form commandName="ticketFilterDTO" name="excelForm" id="excelForm" method="post" action="../report/allTicketListExcel.htm">
                        <input type="hidden" name="startDate" value="${startDate}"/>
                        <input type="hidden" name="stopDate" value="${stopDate}"/>
                        <form:hidden path="marketplaceId"/>
                        <form:hidden path="ticketTypeId"/>
                        <form:hidden path="companyId"/>
                        <form:hidden path="assignedToId"/>
                        <form:hidden path="ticketStatusId"/>
                        <form:hidden path="customerName"/>
                        <form:hidden path="ticketNo"/>
                    </form:form>
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
            <sec:authorize ifAnyGranted="ROLE_BF_EDIT_TICKET">
                $('#editRow td').click(function() {
                    $('#ticketId').val($(this).parent().data("ticket-id"));
                    $('#form2').prop('action', '../tickets/editTicket.htm');
                    $('#form2').submit();
                });
            </sec:authorize>

                //Dialog box code start
                $('.reopen').click(function(event) {
                    event.stopPropagation();
                    $('#exampleModal').modal('show');
                    $('#ticketId1').val($(this).parent().parent().data("ticket-id"));
                    $("#reopenTicketTypeId").val($(this).parent().parent().data("ticket-type"));
                    $('#reopenTicketTypeId').selectpicker('refresh');
                });

                function reopenTicket() {
                    $.ajax({
                        url: "../ajax/ajaxReopenTicket.htm",
                        data: {
                            notes: $("#notes").val(),
                            ticketId: $("#ticketId1").val(),
                            ticketTypeId: $("#reopenTicketTypeId").val()
                        },
                        dataType: "json",
                        async: false,
                        success: function(json) {
                            if (json.id != 0) {
                                location.reload();
                            }
                        },
                        errors: function(e) {
                            alert('error occured');
                        }
                    });
                }
                //Dialog box code end

                //disble automatic table sorting
                $('.table').dataTable({
                    "order": []
                });


                //Customer name autocomplete
                $(function() {
                    src1 = "../ajax/getCustomerNameListForAutocomplete.htm";

                    $("#customerName").autocomplete({
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
                        minLength: 2
                    });
                });

                //Ticket number autocomplete
                $(function() {
                    src2 = "../ajax/getTicketNoListForAutocomplete.htm";

                    $("#ticketNo").autocomplete({
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
                        minLength: 2
                    });
                });
        </script>
    </body>
</html>
