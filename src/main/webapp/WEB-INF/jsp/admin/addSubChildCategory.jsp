<%-- 
    Document   : addSubChildCategory
    Created on : 3 Sep, 2018, 11:55:44 AM
    Author     : Pallavi
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
                    <li><a href="../home/home.htm">Child Category</a></li>
                    <li><a href="#">Map Sub-Child Category</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">   
                        <div class="col-md-12">
                            <form:form id="form1" commandName="childCategory" method="POST" name="form1" class="form-horizontal">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.addSubChildCategory"/></h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="childCategory"/> </label>
                                            <div class="col-md-6 col-xs-12">
                                                <div class="form-group">
                                                    <form:select path="childCategoryId" id="childCategoryId" cssClass="form-control select" onchange="showChildCategoryList();">
                                                        <form:option value="" label="Nothing selected"/>
                                                        <form:options items="${childCategoryList}" itemValue="childCategoryId" itemLabel="childCategoryDesc"/>
                                                    </form:select>
                                                    <span class="help-block">Please select Child category</span>
                                                </div>
                                            </div>
                                        </div>
                                            <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label"><spring:message code="subChildCategory"/>  </label>
                                            <div class="col-md-6 col-xs-12" >
                                                <div class="form-group">
                                                    <form:select path="subChildCategoryId"  cssClass="form-control select" multiple="true">
                                                        <form:options items="${subChildCategoryList}" itemValue="subChildCategoryId" itemLabel="subChildCategoryDesc"/>
                                                    </form:select>
                                                    <span class="help-block">Please select child category</span>
                                                </div>
                                                <!--<span class="add input-group-addon" title="Add child category"><span class="fa fa-plus"></span></span>-->
                                            </div>
                                            <div class="col-md-1">
                                                <span class="add input-group-addon" title="Add child category"><span class="fa fa-plus"></span></span>  
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
                        <h4 class="modal-title" id="exampleModalLabel">Add Sub-Child Category</h4>
                    </div>
                    <div class="modal-body">
                        <form name="form3" id="form3">
                            <div class="form-group">
                                <label for="message-text" class="control-label req"><spring:message code="subChildCategory"/></label>
                                <input type="text" name="subChildCategoryDesc" id="subChildCategoryDesc" class=" req form-control"/>
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
                        <button type="button" class="btn btn-success" onclick="addSubChildCategory();">Submit</button>
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
                                    'childCategoryId': {
                                        required: true
                                    },
                                    'subChildCategoryId': {
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
                                    childCategoryDesc: {
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

                            //show child category list on select of sub category
                            function showChildCategoryList() {
                                var childCategoryId = document.getElementById("childCategoryId").value;
                                $.ajax({
                                    data: ({'childCategoryId': childCategoryId}),
                                    url: "ajaxGetSubChildCategoryListByChildCategoryId.htm",
                                    dataType: "json",
                                    success: function(json) {
                                        $('#subChildCategoryId').val(json);
                                        $('#subChildCategoryId').selectpicker('refresh');
                                    },
                                    error: function(e) {
                                        alert("Error:  method not called ");
                                    }
                                });
                            }

                            //add Child category
                            function addSubChildCategory() {
                                var form = $("#form3");
                                if (form.valid()) {
                                    $.ajax({
                                        url: "../admin/ajaxAddSubChildCategory.htm",
                                        data: {
                                            subChildCategoryDesc: $("#subChildCategoryDesc").val(),
                                            active: $('input[name=active]:checked', '#form3').val()
                                        },
                                        dataType: "json",
                                        async: false,
                                        success: function(json) {
                                            if (json != null) {
                                                if (json.active == 1) {
                                                    $("#subChildCategoryId").append($("<option></option>").attr("value", json.id).text(json.label));
                                                    $('#subChildCategoryId').selectpicker('refresh');
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