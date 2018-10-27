<%-- 
    Document   : listOpenTickets
    Created on : 13 May, 2017, 1:26:06 PM
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
                    <li><a href="#">Open Ticket List</a></li>
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
                                                            </form:select>
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
                                                            <label><spring:message code="customerName"/></label>
                                                            <form:input path="customerName" id="customerName" cssClass="form-control"/><br>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="ticketNo"/></label>
                                                            <form:input path="ticketNo" id="ticketNo" cssClass="form-control"/>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label><spring:message code="marketplaceOrderId"/></label>
                                                            <form:input path="orderId" id="orderId" cssClass="form-control"/><br>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <div class="form-group">
                                                            <label>Is read?</label>
                                                            <form:select path="read" cssClass="form-control select">
                                                                <form:option value="0">-All-</form:option>
                                                                <form:option value="1">Yes</form:option>
                                                                <form:option value="2">No</form:option>
                                                            </form:select>
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
                                                        <th style="display:none;">read?</th>
                                                    </tr>
                                                </thead>
                                                <tbody> 
                                                    <c:forEach items="${ticketList}" var="tt">
                                                        <c:choose>
                                                            <c:when test="${tt.read==false}"><c:set var="rowStyle" value="bold"/></c:when>
                                                            <c:otherwise><c:set var="rowStyle" value=""/></c:otherwise>
                                                        </c:choose>
                                                        <tr class="clickableRow ${rowStyle}" id="editRow" data-ticket-id="${tt.ticketId}">                    
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
                                                            <td>${tt.ticketStatus.statusDesc}</td>
                                                            <td style="display:none;">${tt.read}</td>
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

                    <form name="form2" id="form2" action="" method="get">
                        <input type="hidden" id="ticketId" name="ticketId"/>
                        <input type="hidden" id="uniqueCode" name="uniqueCode" value="2"/>
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
            <sec:authorize ifAnyGranted="ROLE_BF_EDIT_TICKET">
                $('#editRow td').click(function() {
                    $('#ticketId').val($(this).parent().data("ticket-id"));
                    $('#form2').prop('action', '../tickets/editTicket.htm');
                    $('#form2').submit();
                });
            </sec:authorize>

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

                //disble automatic table sorting
                $('.table').dataTable({
                    "order": [[13, "asc"]]
                });
        </script>
    </body>
</html>
