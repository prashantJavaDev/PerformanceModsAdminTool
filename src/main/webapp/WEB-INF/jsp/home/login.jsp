<%-- 
    Document   : login
    Created on : 3 Sep, 2015, 10:08:21 AM
    Author     : gaurao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="body-full-height">
    <head><!-- META SECTION -->
        <%@include file="../common/meta.jsp"%>
        <!-- END META SECTION -->
        <!-- CSS INCLUDE -->        
        <%@include file="../common/css.jsp"%>
        <!-- EOF CSS INCLUDE -->                                    
    </head>
    <body onload="document.getElementById('j_username').focus();">
        <div class="login-container col-md-12">
            <div class="col-md-6" style="float: left"> 
                <div class="login-container col-md-6" style="float: left;">
                    <h1 style="color: white; font-weight: bolder; ">Performance Mods Admin Tool</h1>
                </div>
                <div style="margin-top: 150px"><img src="../images/pm.jpeg" height="250px" width="500px"/></div>
                    
                
            </div>
            <div class="col-md-6" style="float: left"><div class="login-container col-md-6" style="float: left">
                    <div class=" animated fadeInDown">
                        <a href="#"><div></div></a>

                        <div class="login-body col-md-12" style="margin-top: 20px; width: 100%">
                            <!--<div class="login-title"><strong> Admin Tool</strong></div>-->

                            <!-- START LOGIN FORM HERE -->
                            <form name="form1" action="../j_spring_security_check" class="form-horizontal" method="post" >

                                <div class="form-group col-md-6" style="float: left">
                                    <div class="col-md-12" >
                                        <input type="text" class="form-control" placeholder="Username" id="j_username" name="j_username" value="${sessionScope[SPRING_SECURITY_LAST_USERNAME]}"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6" style="float: left">
                                    <div class="col-md-12" >
                                        <input type="password" class="form-control" placeholder="Password" id="j_password" name="j_password"/>
                                    </div>
                                </div>
                                    <div class="form-group col-md-6" style="float: left"></div>
                                <div class="form-group col-md-6">
                                    <!--<div class="col-md-6"></div>-->
                                    <div class="col-md-6 pull-right">
                                        <button class="btn btn-info btn-block ">Log In</button>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <c:if test="${param.login_error=='true'}">
                                            <span class="text-danger">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</span>
                                        </c:if>
                                    </div>
                                </div>
                            </form>
                        </div>
                        
                        <!-- MESSAGE SECTION -->
                        <%@include file="../common/message.jsp"%>
                        <!-- END MESSAGE SECTION -->
                    </div>
                </div> </div>
        </div>



    </body>
</html>