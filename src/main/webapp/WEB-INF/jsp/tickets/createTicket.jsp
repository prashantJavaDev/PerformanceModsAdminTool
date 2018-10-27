<%-- 
    Document   : createTicket
    Created on : 9 May, 2017, 14:00:03 PM
    Author     : shrutika
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
    <body onload="fieldsByStatus();
        fieldsByType();">
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
                    <li><a href="#">Create Ticket</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" commandName="ticket" method="POST" name="form1" id="form1" onkeypress="return event.keyCode != 13;">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.addNewTicket"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="panel panel-warning"  id="displayTable" style="display:none">
                                            <div class="panel-body">
                                                <div class="row">
                                                    <div class="col-md-12 scrollable">
                                                        <table class="table datatable table-bordered" id="tableTicketList">
                                                            <thead>
                                                                <tr>
                                                                    <th width="102px"><spring:message code="ticketNo"/></th> 
                                                                    <th><spring:message code="createdBy"/></th>
                                                                    <th><spring:message code="marketplaceName"/></th>
                                                                    <th><spring:message code="ticketType"/></th>
                                                                    <th><spring:message code="priority"/></th>
                                                                    <th><spring:message code="orderId"/></th>
                                                                    <th><spring:message code="trackingId"/></th>
                                                                    <th><spring:message code="desc"/></th>
                                                                    <th><spring:message code="companyName"/></th>
                                                                    <th><spring:message code="customerName"/></th>
                                                                    <th><spring:message code="custPhoneNumber"/></th>
                                                                    <th><spring:message code="assignedTo"/></th>
                                                                    <th><spring:message code="ticketStatus"/></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="tbodyId"> 

                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 control-label"><spring:message code="marketplaceName"/></label>
                                                    <div class="col-md-6">
                                                        <form:select path="marketplace.marketplaceId" cssClass="form-control select">
                                                            <form:option value="" label="Nothing selected"/>
                                                            <form:options items="${marketplaceList}" itemLabel="marketplaceName" itemValue="marketplaceId"/>
                                                        </form:select>
                                                        <span class="help-block">Please select marketplace name</span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="req col-md-4 control-label"><spring:message code="ticketType"/></label>
                                                    <div class="col-md-6">
                                                        <form:select path="ticketType.ticketTypeId" id="ticketTypeId" cssClass="form-control select" onchange="fieldsByType();getAssignListOnTicketTypeId();">
                                                            <form:option value="" label="Nothing selected"/>
                                                            <form:options items="${ticketTypeList}" itemLabel="ticketTypeDesc" itemValue="ticketTypeId"/>
                                                        </form:select>
                                                        <span class="help-block">Please select ticket type</span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 control-label" id="orderIdLabel"><spring:message code="orderId"/></label>
                                                    <div class="col-md-6">
                                                        <form:input path="orderId" id="orderId" cssClass="form-control" oninput="$('#displayTable').hide();"/>
                                                        <span class="help-block">Please enter order id</span>
                                                    </div>
                                                </div>
                                                <div class="form-group" id="trackingIdDiv">
                                                    <label class="col-md-4 control-label"><spring:message code="trackingId"/></label>
                                                    <div class="col-md-6">
                                                        <form:input path="trackingId" cssClass="form-control"/>
                                                        <span class="help-block">Please enter tracking id</span>
                                                    </div>
                                                </div>
                                                <div class="form-group" id="trackingCarrierNameDiv">
                                                    <label class="col-md-4 control-label"><spring:message code="trackingCarrierName"/></label>
                                                    <div class="col-md-6">
                                                        <form:input path="trackingCarrierName" cssClass="form-control"/>
                                                        <span class="help-block">Please enter tracking carrier name</span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="req col-md-4 control-label"><spring:message code="title"/></label>
                                                    <div class="col-md-6">
                                                        <form:input path="description" cssClass="form-control"/>
                                                        <span class="help-block">Please enter Title</span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="req col-md-4 control-label"><spring:message code="details"/></label>
                                                    <div class="col-md-6">
                                                        <form:textarea path="details" cssClass="form-control"/>
                                                        <span class="help-block">Please enter description</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="req col-md-4 control-label"><spring:message code="companyName"/></label>
                                                    <div class="col-md-6">
                                                        <form:select path="company.companyId" cssClass="form-control select">
                                                            <form:option value="" label="Nothing selected"/>
                                                            <form:options items="${companyList}" itemLabel="companyName" itemValue="companyId"/>
                                                        </form:select>
                                                        <span class="help-block">Please select company name</span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="req col-md-4 control-label"><spring:message code="customerName"/></label>
                                                    <div class="col-md-6">
                                                        <form:input path="customerName" cssClass="form-control"/>
                                                        <span class="help-block">Please enter customer name</span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="req col-md-4 control-label"><spring:message code="custPhoneNumber"/></label>
                                                    <div class="col-md-6">
                                                        <form:input path="custPhoneNumber" cssClass="form-control" maxlength="12"/>
                                                        <span class="help-block">Please enter customer phone number</span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 control-label"><spring:message code="custEmailId"/></label>
                                                    <div class="col-md-6">
                                                        <form:input path="custEmailId" cssClass="form-control"/>
                                                        <span class="help-block">Please enter customer email id</span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="req col-md-4 control-label"><spring:message code="ticketStatus"/></label>
                                                    <div class="col-md-6">
                                                        <form:select path="ticketStatus.statusId" id="ticketStatusId" cssClass="form-control select" onchange="fieldsByStatus();">
                                                            <form:option value="" label="Nothing selected"/>
                                                            <form:options items="${ticketStatusList}" itemLabel="statusDesc" itemValue="statusId"/>
                                                        </form:select>
                                                        <span class="help-block">Please select ticket status</span>
                                                    </div>
                                                </div>
                                                <div class="form-group" id="assignedToDiv">
                                                    <label class="req col-md-4 control-label"><spring:message code="assignedTo"/></label>
                                                    <div class="col-md-6">
                                                        <form:select path="assignedTo.userId" id="assignedTo" cssClass="form-control select">
                                                            <form:option value="" label="Nothing selected"/>
                                                            <form:options items="${assignedToList}" itemLabel="username" itemValue="userId"/>
                                                        </form:select>
                                                        <span class="help-block">Please select assigned to</span>
                                                    </div>
                                                </div>
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

                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit" name="submit" id="button1" class="btn btn-success">Submit</button>
                                            <button type="submit" name="_cancel" class="btn btn-primary" formnovalidate="formnovalidate">Cancel</button>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
                <!-- END PAGE CONTENT WRAPPER -->
            </div>
            <!-- END PAGE CONTENT -->
        </div>
        <!-- END PAGE CONTAINER -->

        <form name="form2" id="form2" action="" method="get">
            <input type="hidden" id="ticketId" name="ticketId"/>
            <input type="hidden" id="uniqueCode" name="uniqueCode" value="3"/>
        </form>

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

        <%@include file="../common/messagebox.jsp" %>

        <%@include file="../common/script.jsp" %>
        <!-- START THIS PAGE PLUGINS-->
        <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>            
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>
        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript">
            var jvalidate = $("#form1").validate({
                ignore: [],
                rules: {
                    'marketplace.marketplaceId': {
                        required: true
                    },
                    'ticketType.ticketTypeId': {
                        required: true
                    },
                    orderId: {
                        required: function(element) {
                            return ($('#ticketTypeId').val() == 2 || $('#ticketTypeId').val() == 3)
                        }
                    },
                    description: {
                        required: true
                    },
                    details: {
                        required: true
                    },
                    'ticketStatus.statusId': {
                        required: true
                    },
                    'assignedTo.userId': {
                        required: function(element) {
                            return ($('#ticketStatusId').val() == 2 || $('#ticketStatusId').val() == 4)
                        }
                    },
                    'company.companyId': {
                        required: true
                    },
                    customerName: {
                        required: true
                    },
                    custPhoneNumber: {
                        required: true
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
            //for dropdown 
            $('.select').on('change', function() {
                if ($(this).val() != "") {
                    $(this).valid();
                    $(this).next('div').addClass('valid');
                } else {
                    $(this).next('div').removeClass('valid');
                }
            });

            function fieldsByStatus() {
                var statusId = $('#ticketStatusId').val();
                if (statusId == 2 || statusId == 4) {
                    $('#assignedToDiv').show();
                } else {
                    $('#assignedToDiv').hide();
                }
            }


            function getAssignListOnTicketTypeId(){
                var typeId = $('#ticketTypeId').val();
                $.ajax({
                    data: ({'ticketTypeId': typeId}),
                    url: "../ajax/getAssignListOnTicketType.htm",
                    dataType: "json",
                    success: function(json) {
                        if (json != null) {
                            document.getElementById('assignedTo').options.length = 0;
                            $("#assignedTo").append($("<option></option>").attr("value", "").text("Nothing Selected"));
                            for (i = 0; i < json.length; i++) {
                                $("#assignedTo").append($("<option></option>").attr("value", json[i].userId).text(json[i].username));
                                
                            }
                            $('#assignedTo').selectpicker('refresh');
                            
                        }
                    }
                });
            }
            function fieldsByType() {
                var typeId = $('#ticketTypeId').val();
                if (typeId == 3) {
                    $('#trackingIdDiv').show();
                    $('#trackingCarrierNameDiv').show();
                    $('#orderIdLabel').addClass('req');
                } else {
                    if (typeId == 2) {
                        $('#orderIdLabel').addClass('req');
                    } else {
                        $('#orderIdLabel').removeClass('req');
                    }
                    $('#trackingIdDiv').hide();
                    $('#trackingCarrierNameDiv').hide();
                }
            }

            //Ticket number autocomplete
            $(function() {
                src2 = "../ajax/getOrderIdListForAutocomplete.htm";
                $("#orderId").autocomplete({
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
                }).on("autocompleteclose", function(event, e) {
                    getTicketListByOrderId();
                });
            });

            function getTicketListByOrderId() {
                var orderId = $('#orderId').val();
                //alert($('#orderId').val());
                $.ajax({
                    url: "../ajax/getTicketListByOrderId.htm",
                    dataType: "json",
                    data: ({'orderId': orderId}),
                    success: function(json) {
                        if (json.length > 0) {
                            $('#displayTable').show();
                            var t = $('#tableTicketList').DataTable();
                            t.clear();
                            for (i = 0; i < json.length; i++) {
                                var reopenIcon;
                                if (json[i].ticketStatus.statusId == 4 || json[i].ticketStatus.statusId == 5) {
                                    reopenIcon = " <sec:authorize ifAnyGranted='ROLE_BF_REOPEN_TICKET'><img class='reopen pull - right' data-toggle='modal' data-target='#exampleModal' title='Reopen Ticket' src='../images/Reset_Reopen_Icon.jpg' style='border: 0;'/></sec:authorize>";
                                } else {
                                    reopenIcon = "";
                                }
                                var j = t.row.add([
                                    json[i].ticketNo,
                                    json[i].createdBy.username,
                                    json[i].marketplace.marketplaceName,
                                    json[i].ticketType.ticketTypeDesc,
                                    json[i].ticketPriority.priorityName,
                                    json[i].orderId,
                                    json[i].trackingId,
                                    json[i].description,
                                    json[i].company.companyName,
                                    json[i].customerName,
                                    json[i].custPhoneNumber,
                                    json[i].assignedTo.username,
                                    json[i].ticketStatus.statusDesc + reopenIcon
                                ]).draw(false).node();
                                $(j).eq(0).attr('class', 'clickableRow');
                                $(j).eq(0).attr('data-ticket-id', json[i].ticketId);
                                $(j).eq(0).attr('data-ticket-type', json[i].ticketType.ticketTypeId);
                                $(j).find('td').eq(0).css('font-family', 'monospace');
                                document.getElementById("button1").disabled = true;
                            }
                        }
                    },
                    errors: function(e) {
                        alert('error occured');
                    }
                });
            }

            <sec:authorize ifAnyGranted="ROLE_BF_EDIT_TICKET">
                $('#tableTicketList').on('click', '.clickableRow', function(e) {
                    $('#ticketId').val($(this).data("ticket-id"));
                    $('#form2').prop('action', '../tickets/editTicket.htm');
                    $('#form2').submit();
                });
            </sec:authorize>

                //Dialog box code start
                //                                                    $('#tableTicketList').on('click', '#tbodyId tr', function(e) {
                $('#tableTicketList').on('click', '.reopen', function(event) {
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
                        //                                                                async: false,
                        success: function(json) {
                            if (json.id != 0) {
                                getTicketListByOrderId();
                                $('#exampleModal').modal('hide');
                            }
                        },
                        errors: function(e) {
                            alert('error occured');
                        }
                    });
                }
                //Dialog box code end
        </script>
    </body>
</html>
