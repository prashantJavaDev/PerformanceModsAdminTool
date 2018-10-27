<%-- 
    Document   : MapManufacturer
    Created on : 25 Feb, 2017, 4:08:47 PM
    Author     : Ritesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
                    <li><a href="../home/home.htm">Admin</a></li>
                    <li><a href="../warehouse/home.htm">Manufacturer</a></li>
                    <li><a href="#">Map Manufacturer</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" commandName="manufacturer" method="POST" name="form1" id="form1">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.mapManufacturer"/></h3>
                                    </div>
                                    <div class="panel-body">

                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="manufacturerName"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:select path="manufacturerId" id="manufacturerId" cssClass="form-control" onchange="getMapManufacturer();">
                                                    <form:option value="" label="Nothing selected"/>
                                                    <form:options items="${manufacturerList}" itemValue="manufacturerId" itemLabel="manufacturerName"/>
                                                </form:select>
                                                <span class="help-block">Please select manufacturer name for mapping</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="mapManufacturerName"/></label>
                                            <div class="col-md-6 col-xs-6">
                                                <form:input path="manufacturerName" cssClass="form-control"/>
                                                <span class="help-block">Please enter manufacturer name</span>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="mapmanufacturerList"/></label>
                                            <div class="col-md-6 col-xs-6 scrollable">
                                                <table class=" table table-bordered">
                                                    <thead>
                                                        <tr>
                                                            <th width="150px"><spring:message code="manufacturerCode"/></th>
                                                            <th width="200px"><spring:message code="mapManufacturerName"/></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="mapManufacturer">

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit" id="_submit" name="btnSubmit"  class="btn btn-success">Submit</button>
                                            <button type="submit" id="_cancel" name="_cancel" class="btn btn-primary" formnovalidate="formnovalidate"><spring:message code="button.Cancel"/></button>
                                        </div>  
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <%@include file="../common/messagebox.jsp" %>
        <%@include file="../common/script.jsp" %>
        <!-- START THIS PAGE PLUGINS-->
        <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>
        <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
        <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>            
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-combobox.js"></script>
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>    
        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript">
            var jvalidate = $("#form1").validate({
                ignore: [],
                rules: {
                    manufacturerName: {
                        checkLength: true,
                        required: true
                    },
                    'manufacturerId': {
                        required: true
                    }

                },
                errorPlacement: function(error, element) {
                    if (element.parent().hasClass('combobox-container')) {
                        error.insertAfter(element.parent(".combobox-container"));
                        element.next("div").children().addClass("error");
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            $('#manufacturerId').combobox();

            function getMapManufacturer() {
                var manufacturerId = $('#manufacturerId').val();
                $.ajax({
                    url: "getMappedManufacturer.htm",
                    dataType: "json",
                    data: ({'manufacturerId': manufacturerId}),
//                    async: false,
                    success: function(json) {
                        $('#mapManufacturer tr').remove();
                        for (i = 0; i < json.length; i++) {
                            var table = document.getElementById("mapManufacturer");
                            var rowCount = table.rows.length;
                            var row = table.insertRow(rowCount);
                            var cell1 = row.insertCell(0);
                            var cell2 = row.insertCell(1);
                            cell1.innerHTML = json[i].manufacturerCode;
                            cell2.innerHTML = json[i].manufacturerName;
                        }
                    },
                    errors: function(e) {
                        alert('error occured');
                    }
                });
            }
        </script>

    </body>
</html>