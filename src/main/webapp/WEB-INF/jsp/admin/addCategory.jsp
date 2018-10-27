<%-- 
    Document   : addCategory
    Created on : 23 Feb, 2016, 12:18:29 PM
    Author     : shrutika
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
                    <li><a href="../home/home.htm">Admin</a></li>
                    <li><a href="../home/home.htm">Sub Category</a></li>
                    <li><a href="#">Map Category</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">   
                        <div class="col-md-12">
                            <form:form id="form1" commandName="mainCategory" method="POST" name="form1" class="form-horizontal">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.addCategory"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="mainCategory"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <div class="form-group">
                                                    <form:select path="mainCategoryId" id="mainCategoryId" cssClass="form-control select" onchange="showSubCategoryList();">
                                                        <form:option value="" label="Nothing selected"/>
                                                        <form:options items="${mainCategoryList}" itemValue="mainCategoryId" itemLabel="mainCategoryDesc"/>
                                                    </form:select>
                                                </div>
                                                <span class="help-block">Please select main category</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="subCategory"/></label>
                                            <div class="col-md-6 col-xs-12">
                                                <div class="form-group">
                                                    <form:select path="subCategoryId" cssClass="form-control select" multiple="true">
                                                        <form:options items="${subCategoryList}" itemValue="subCategoryId" itemLabel="subCategoryDesc"/>
                                                    </form:select>
                                                </div>
                                                <span class="help-block">Please select sub category</span>
                                            </div>
                                            <div class="col-md-1 col-xs-12">
                                                <span class="add input-group-addon" title="Add sub category"><span class="fa fa-plus"></span></span>
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
        <!-- dialog box form start -->
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="exampleModalLabel">Add Sub Category</h4>
                    </div>
                    <div class="modal-body">
                        <form name="form3" id="form3">
                            <div class="form-group">
                                <label for="message-text" class="control-label req"><spring:message code="subCategory"/></label>
                                <input type="text" name="subCategoryDesc" id="subCategoryDesc" class=" req form-control"/>
                            </div>
                            <div class="form-group">
                                <label for="message-text" class="control-label req"><spring:message code="active"/></label>
                                <spring:message code="yes" /> 
                                <input type="radio" name="active" id="active" value="true" checked="checked"/>
                                <spring:message code="no"/> 
                                <input type="radio" name="active" id="active" value="false"/>
                                <span class="help-block">Please select is active?</span>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-success" onclick="addSubCategory();">Submit</button>
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

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript">

                            var jvalidate = $("#form1").validate({
                                ignore: [],
                                rules: {
                                    'mainCategoryId': {
                                        required: true
                                    },
                                    'subCategoryId': {
                                        required: true
                                    }
                                },
                                errorPlacement: function(error, element) {
                                    if (element.parent().hasClass('input-group')) {
                                        error.insertAfter(element.parent(".input-group"));
                                        element.next("div").addClass("error");
                                    } else if (element.hasClass('select')) {
                                        error.insertAfter(element.next(".bootstrap-select"));
                                        element.next("div").addClass("error");
                                    } else {
                                        error.insertAfter(element);
                                    }
                                }
                            });

                            var jvalidate = $("#form3").validate({
                                ignore: [],
                                rules: {
                                    subCategoryDesc: {
                                        required: true
                                    }
                                },
                                errorPlacement: function(error, element) {
                                    error.insertAfter(element);
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

                            $('.add').click(function(event) {
                                $('#exampleModal').modal('show');
                            });

                            //show subcategory list on select of main category
                            function showSubCategoryList() {
                                var mainCategoryId = document.getElementById("mainCategoryId").value;
                                $.ajax({
                                    data: ({'mainCategoryId': mainCategoryId}),
                                    url: "ajaxGetSubCategoryListByMainCategoryId.htm",
                                    dataType: "json",
                                    success: function(json) {
                                        $('#subCategoryId').val(json);
                                        $('#subCategoryId').selectpicker('refresh');
                                    },
                                    error: function(e) {
                                        alert("Error:  method not called ");
                                    }
                                });
                            }

                            //add Sub category
                            function addSubCategory() {
                                var form = $("#form3");
                                if (form.valid()) {
                                    $.ajax({
                                        url: "ajaxAddSubCategory.htm",
                                        data: {
                                            subCategoryDesc: $("#subCategoryDesc").val(),
                                            active: $('input[name=active]:checked', '#form3').val()
                                        },
                                        dataType: "json",
                                        async: false,
                                        success: function(json) {
                                            if (json != null) {
                                                if (json.active == 1) {
                                                    $("#subCategoryId").append($("<option></option>").attr("value", json.id).text(json.label));
                                                    $('#subCategoryId').selectpicker('refresh');
                                                }
                                            }
                                            $('#exampleModal').modal('hide');
                                        },
                                        errors: function(e) {
                                            alert('error occured');
                                        }
                                    });
                                }
                            }

                            $('#exampleModal').on('hidden.bs.modal', function() {
                                $(this).find('form').trigger('reset');
                            })

        </script>
    </body>
</html>
