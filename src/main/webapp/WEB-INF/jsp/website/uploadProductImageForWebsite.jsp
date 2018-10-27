<%-- 
    Document   : uploadProductImageForWebsite
    Created on : 12 Sep, 2018, 4:26:42 PM
    Author     : Pallavi
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
                    <li><a href="../home/home.htm">Website</a></li>
                    <li><a href="#">Upload Image for website</a></li>
                </ul>
                <!-- END BREADCRUMB --> 

                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                    <!-- MESSAGE SECTION -->
                    <%@include file="../common/message.jsp"%>
                    <!-- END MESSAGE SECTION -->

                    <div class="row">
                        <div class="col-md-12">
                            <form:form cssClass="form-horizontal" modelAttribute="uploadFeed" name="form1" id="form1" method="post" enctype="multipart/form-data" onsubmit="_submit.disabled=true">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h3 class="panel-title"><spring:message code="title.imageUpload"/></h3>
                                    </div>

                                    <div class="panel-body">

                                        <div class="form-group">
                                            <label class="req col-md-2 col-xs-12 control-label">Select Image</label>
                                            <div class="col-md-6 col-xs-12">
                                                <input type="file" name="multipartFile" id="multipartFile" class="fileinput btn-danger"  data-filename-placement="inside"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel">
                                        <br>
                                        <div class="col-md-12">
                                            <h3>Required file format</h3>
                                            <ul>
                                                <li>Image format must be .png, .jpg, .jpeg  Or  .gif </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="pull-right">
                                            <button type="submit" id="_submit" name="submit"  class="btn btn-success"/>Upload</button>
                                            <button type="submit" id="_cancel" name="_cancel" class="btn btn-primary" formnovalidate="formnovalidate"><spring:message code="button.Cancel"/></button>
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
            <%@include file="../common/messagebox.jsp" %>

            <%@include file="../common/script.jsp" %>
            <!-- START THIS PAGE PLUGINS-->
            <script type='text/javascript' src='../js/plugins/icheck/icheck.min.js'></script>
            <script type="text/javascript" src="../js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
            <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-select.js"></script>
            <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-file-input.js"></script>
            <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>            

            <!-- END THIS PAGE PLUGINS-->

            <!-- START TEMPLATE -->
            <script type="text/javascript" src="../js/plugins.js"></script>
            <script type="text/javascript" src="../js/actions.js"></script>
            <!-- END TEMPLATE -->

            <script type="text/javascript">
                $(document).ready(function(){
                    $.validator.addMethod(
                    "checkFileExtension",
                    function (){
                        var filename = $('#multipartFile').val();
                        
                        if ((fileName[fileName.length - 1] == "png") || (fileName[fileName.length - 1] == "jpg") || (fileName[fileName.length - 1] == "jpeg") || (fileName[fileName.length - 1] == "gif")) {
                            return false
                        }else{
                            return true;
                        }
                    }
                );
                });
        
                var jvalidate = $("#form1").validate({
                    ignore: [],
                    rules: {                                            
                        multipartFile:{
                            checkFileExtension:true
                        }
                    },
                    messages:{
                        multipartFile:"Please select only 'png','jpg','jpeg','gif' file."  
                    },
                    errorPlacement: function(error, element) {
                        if (element.hasClass('select')) {
                            error.insertAfter(element.next(".bootstrap-select"));
                            element.next("div").addClass("error");
                        } else if(element.hasClass('fileinput')){
                            error.insertAfter($(element).parent());
                        } else {
                            error.insertAfter(element);
                        }
                    }
                });
                //for dropdown 
                $('.select').on('change', function() {
                    if($(this).val()!=""){
                        $(this).valid();
                        $(this).next('div').addClass('valid');
                    } else {
                        $(this).next('div').removeClass('valid');
                    }
                });
            
            </script>
    </body>
</html>

