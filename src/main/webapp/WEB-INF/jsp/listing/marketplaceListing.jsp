<%-- 
    Document   : marketplaceListing
    Created on : 27 Mar, 2017, 6:25:42 PM
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
                    <li><a href="../home/home.htm">Listing</a></li>
                    <li><a href="#">Marketplace Listing</a></li>
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
                                    <h3 class="panel-title"><spring:message code="title.createUpdateListing"/> for <b>${marketplaceName}</b></h3>
                                    <ul class="panel-controls">
                                        <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
                                    </ul>
                                </div>
                                <form name="form1" id="form1" method="post" action="marketplaceListing.htm">
                                    <div class="panel-body">
                                        <b><input type="button" class="processList col-md-3 btn-success btn-sm" value="Process Listings" style="margin-bottom: 15px"/></b>

                                        <div class="panel panel-warning">
                                            <div class="panel-body">
                                                <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
                                                <input type="hidden" name="marketplaceId" id="marketplaceId" value="${marketplaceId}"/>
                                                <input type="hidden" name="marketplaceName" id="marketplaceName" value="${marketplaceName}"/>

                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="warehouseName"/></label>
                                                        <select id ="warehouseId" name="warehouseId" class="form-control select"> 
                                                            <option value="">-</option>
                                                            <c:forEach items="${warehouseList}" var="warehouse">
                                                                <option value="${warehouse.warehouseId}"<c:if test="${warehouseId==warehouse.warehouseId}">selected</c:if>>${warehouse.warehouseName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><spring:message code="marketplaceSku"/></label>
                                                        <input type="text" name="marketplaceSku" id="marketplaceSku" value="${marketplaceSku}" Class="form-control"/>
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><spring:message code="manufacturerName"/></label>
                                                        <select id="manufacturerIds" name="manufacturerIds" class="form-control select" data-actions-box="true" data-live-search="true" title="-All-"  multiple="true">
                                                            <c:forEach items="${manufacturerList}" var="manufacturer">

                                                                <c:set var="found" value="0"/>
                                                                <!--<option value="0">-</option>-->

                                                                <c:forEach items="${selectedManufacturersList}" var="m">
                                                                    <c:if test="${m.manufacturerId==manufacturer.manufacturerId}"><c:set var="found" value="1"/></c:if>
                                                                </c:forEach>
                                                                <option <c:if test="${found==1}"> selected </c:if>value="${manufacturer.manufacturerId}">${manufacturer.manufacturerName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-3 btn-filter pull-right">
                                                    <input type="submit" id="_submit" class="btn-info btn-sm" name="go" value="Show Listings" style="margin-left: 45px"/>
                                                    <b><input type="submit" id="btnSubmit" class="btn-success btn-sm pull-right" 
                                                              name="btnSubmit" value="Confirm Listings"/></b>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="row">
                                            <div class="col-md-12 scrollable">
                                                <table class="table datatable table-bordered" >
                                                    <thead>
                                                        <tr>
                                                            <th><spring:message code="manufacturerName"/></th>
                                                            <th><spring:message code="manufacturerMpn"/></th>
                                                            <th><spring:message code="marketplaceSku"/></th>
                                                            <th><spring:message code="listingId"/></th>
                                                            <th><spring:message code="listedPrice"/></th>
                                                            <th><spring:message code="listedQuantity"/></th>
                                                            <th><spring:message code="lastListedDate"/></th>
                                                            <th><spring:message code="currentQuantity"/></th>
                                                            <th><spring:message code="warehouseQuantity"/></th>
                                                            <th><spring:message code="currentPrice"/></th>
                                                            <th><spring:message code="warehousePrice"/></th>
                                                            <th><spring:message code="shipping"/></th>
                                                            <th><spring:message code="commission"/></th>
                                                            <th><spring:message code="profit"/></th>
                                                            <th><spring:message code="pack"/></th>
                                                            <!--<th><spring:message code="commissionPercentage"/></th> -->
                                                            <!--<th><spring:message code="profitPercentage"/></th>profit percent heading -->
                                                            <th><spring:message code="warehouseName"/></th>
                                                            <th><spring:message code="currentProcessedDate"/></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${listingResult}" var="item">
                                                            <c:set var="titleValue" value=''/>
                                                            <c:choose>
                                                                <c:when test="${item.active==false}">
                                                                    <c:set var="rowColor" value='bgcolor="#C6C6C6"'/>
                                                                    <c:set var="priceStyle" value=''/>
                                                                    <c:set var="titleValue" value='Inactive Listing'/>
                                                                </c:when>
                                                                <c:when test="${item.warehouseId==''}">
                                                                    <c:set var="rowColor" value='bgcolor="#FC0C08"'/>
                                                                    <c:set var="priceStyle" value=''/>
                                                                    <c:set var="titleValue" value='No product found in feed'/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:set var="rowColor" value=''/>
                                                                    <c:choose>
                                                                        <c:when test="${item.listedPrice!=item.currentPrice}">
                                                                            <c:set var="priceStyle" value='bgcolor="#FFFF62"'/>
                                                                            <c:set var="titleValue" value='Cahange in price'/>
                                                                        </c:when>
                                                                    </c:choose>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <tr class="updatepriQty clickableListing" ${rowColor} data-sku="${item.sku}" data-price="${item.currentPrice}" data-profit="${item.profit}"
                                                                data-quantity="${item.currentQuantity}" data-active="${item.active}" title="${titleValue}">
                                                                <td><c:out value="${item.manufacturerName}"/></td>
                                                                <td><c:out value="${item.manufacturerMpn}"/></td>
                                                                <td><c:out value="${item.sku}"/></td>
                                                                <td><c:out value="${item.listingId}"/></td>
                                                                <td><c:out value="${item.listedPrice}"/></td>
                                                                <td><c:out value="${item.listedQuantity}"/></td>
                                                                <td><fmt:formatDate value="${item.lastListedDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                                                                <td><b><c:out value="${item.currentQuantity}"/></b></td>
                                                                <td><c:out value="${item.warehouseQuantity}"/></td>
                                                                <td ${priceStyle}><b><c:out value="${item.currentPrice}"/></b></td>
                                                                <td><c:out value="${item.warehousePrice}"/></td>
                                                                <td><c:out value="${item.shipping}"/></td>
                                                                <td><c:out value="${item.commission}"/></td>
                                                                <td><c:out value="${item.profit}"/></td>
                                                                <td><c:out value="${item.pack}"/></td>
                                                                <!--<td><c:out value="${item.commissionPercentage}"/></td> -->
                                                                <!--<td><c:out value="${item.profitPercentage}"/></td> profit percent-->
                                                                <td><c:out value="${item.warehouse}"/></td>
                                                                <td><fmt:formatDate value="${item.currentListedDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                                                            </c:forEach>
                                                    </tbody>
                                                </table>
                                                <c:if test="${pageCount>0}"><div class="demo2 pull-right"></div></c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>

                                <!-- dialog box form start -->
                                <div class="modal fade" id="exampleModalList" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                <h4 class="modal-title" id="exampleModalLabel">Process Listing</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form name="form4" id="form4">
<!--                                                  <div class="form-group">
                                                        <label><spring:message code="profitPercentage"/></label>
                                                        <select name="profitPercentage" id="profitPercentage" class="form-control select"> 
                                                        <option value="" >-</option>
                                                        <c:forEach items="${profitPercentageList}" var="profit">
                                                            <option value="${profit}">${profit}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <span class="help-block">Please select profit %</span>
                                                </div>-->
                                                <div class="form-group">
                                                    <label><spring:message code="manufacturerName"/></label>
                                                    <select id="manufacturerIdProcess" name="manufacturerId" class="form-control"> 
                                                        <option value="0">-All-</option>
                                                        <c:forEach items="${manufacturerList}" var="manufacturer">
                                                            <option value="${manufacturer.manufacturerId}"<c:if test="${manufacturerId==manufacturer.manufacturerId}">selected</c:if>>${manufacturer.manufacturerName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                    <span id="spinClass" style="margin-left: 265px"></span>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                                            <button type="button" class="btn btn-success" onclick="processListing();">Submit</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- dialog box form end -->

                            <!-- dialog box form start -->
                            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="exampleModalLabel">Update Price and Quantity</h4>
                                        </div>
                                        <div class="modal-body">
                                            <form name="form3" id="form3">
                                                <div class="form-group">
                                                    <label for="message-text" class="control-label"><spring:message code="marketplaceSku"/></label>
                                                    <input class="form-control" id="sku" name="sku" readonly="true"/>
                                                    <input  type="hidden" id="prft" name="prft"/>
                                                </div>
                                                <div class="form-group">
                                                    <label for="message-text" class="control-label"><spring:message code="currentPrice"/></label>
                                                    <input class="form-control" id="price" name="price" />
                                                    <span class="help-block">Please enter new price</span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="message-text" class="control-label"><spring:message code="currentQuantity"/></label>
                                                    <input class="form-control" id="quantity" name="quantity" />
                                                    <span class="help-block">Please enter new quantity</span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="message-text" class="req control-label"><spring:message code="active"/></label>
                                                    <spring:message code="yes" /> 
                                                    <input type="radio" name="active" id="active" value="true"/>
                                                    <spring:message code="no"/> 
                                                    <input type="radio" name="active" id="active" value="false"/>
                                                    <span class="help-block">Please select is active?</span>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                                            <button type="button" class="btn btn-success" onclick="updatePriceQuantity($('#prft').val());">Submit</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- dialog box form end -->
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
        <script type='text/javascript' src='../js/plugins/jquery-validation/jquery.validate.js'></script>    
        <script type="text/javascript" src="../js/plugins/pagination/jquery.bootpag.min.js"></script>
        <script type="text/javascript" src="../js/plugins/datatables/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="../js/plugins/bootstrap/bootstrap-combobox.js"></script>
        <!-- END THIS PAGE PLUGINS-->

        <!-- START TEMPLATE -->
        <script type="text/javascript" src="../js/plugins.js"></script>
        <script type="text/javascript" src="../js/actions.js"></script>
        <!-- END TEMPLATE -->

        <script type="text/javascript" defer="defer">

            //Marketplace SKU autocomplete
            $(function() {
                src3 = "getMarketplaceSkuListForAutocomplete.htm";
                $("#marketplaceSku").autocomplete({
                    source: function(request, response) {
                        $.ajax({
                            url: src3,
                            dataType: "json",
                            data: {
                                term: request.term
                            },
                            success: function(data) {
                                response(data);
                            }
                        });
                    },
                    minLength: 3
                });
            });

            //Pagination
            $('.table').dataTable({
                "dom": 'f<"toolbar">rt',
                "order": [],
                "bPaginate": false
            });

            $("div.toolbar").html('<div class="pull-left"><c:out value="${rowCount}"/> <spring:message code="rowsFound"/></div>');

            $('.demo2').bootpag({
                total: ${pageCount},
                page: ${pageNo},
                maxVisible: 10
            }).on('page', function(event, num) {
                $('#pageNo').val(num);
                $('#form1').submit();
            });


            $('.select').on('change', function() {
                if ($(this).val() != "-") {
                    $(this).valid();
                    $(this).next('div').addClass('valid');
                } else {
                    $(this).next('div').removeClass('valid');
                }
            });

            //Process Listing dialog box
            $('.processList').click(function(event) {
                var check = confirm("Are you sure you want to process listing ?")
                if (check) {
                    $('#exampleModalList').modal('show');
                }
            });
            function processListing() {
                var form = $("#form4");
                if (form.valid()) {
                    $('#spinClass').addClass('fa fa-spinner fa-pulse fa-2x');
                    $.ajax({
                        url: "../ajax/ajaxProcessListing.htm",
                        data: {
                            processingMarketplaceId: ${marketplaceId},
                            profitPercentage: $("#profitPercentage").val(),
                            manufacturerId: $("#manufacturerIdProcess").val()
                        },
                        dataType: "json",
                        async: true,
                        success: function(json) {
                            if (json.id != 0) {
                                $('#spinClass').removeClass('fa fa-spinner fa-pulse fa-2x');
                                var msg = "Listing created successfully. Please click on 'Show Listings' button to see updated listings";
                                $('#exampleModalList').modal('hide');
                                location.href = '../listing/marketplaceListing.htm?marketplaceId=' + $("#marketplaceId").val() + '&marketplaceName=' + $("#marketplaceName").val() + '&go=Go&msg=' + msg;
                            } else {
                                alert('Error occured');
                                $('#spinClass').removeClass('fa fa-spinner fa-pulse fa-2x');
                                $('#exampleModalList').modal('hide');
                            }
                        },
                        errors: function(e) {
                            alert('error occured');
                        }
                    });
                }
            }

            var jvalidate = $("#form4").validate({
                ignore: [],
                rules: {
                    processingMarketplaceId: {
                        required: true
                    }
//                    profitPercentage: {
//                        required: true
//                    }
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
            //Dialog box code end

            //Update price/quantity dialog box code start
            $('.updatepriQty').click(function(event) {
                $('#exampleModal').modal('show');
                $('#sku').val($(this).data("sku"));
                $('#price').val($(this).data("price"));
                $('#quantity').val($(this).data("quantity"));
                $('#profit').val($(this).data("profit"));    
                $('#prft').val($(this).data("profit"));    
                $("input[name=active][value=" + $(this).data("active") + "]").prop('checked', true);
            });
            function updatePriceQuantity(profit) {
                var form = $("#form3");
                if (form.valid()) {
                    $.ajax({
                        url: "../ajax/ajaxUpdatePriceQuantity.htm",
                        data: {
                            sku: $("#sku").val(),
                            price: $("#price").val(),
                            quantity: $("#quantity").val(),
                            profit: profit,
                            active: $('input[name=active]:checked', '#form3').val()
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
            }

            var jvalidate = $("#form3").validate({
                ignore: [],
                rules: {
                    quantity: {
                        required: true,
                        number: true
                    },
                    price: {
                        required: true,
                        number: true
                    }
                },
                errorPlacement: function(error, element) {
                    error.insertAfter(element);
                }
            });

            //Dialog box code end


            $('#manufacturerIdProcess').combobox();
            
        </script>
    </body>
</html>

