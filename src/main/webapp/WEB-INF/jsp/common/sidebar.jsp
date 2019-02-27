<%-- 
    Document   : sidebar
    Created on : 1 Oct, 2016, 11:06:11 AM
    Author     : shrutika
--%>

<!-- START PAGE SIDEBAR -->
<div class="page-sidebar toggled">
    <!-- START X-NAVIGATION -->
    <ul class="x-navigation x-navigation-minimized">
        <li class="xn-logo">
            <a href="../home/home.htm"><div class=""></div></a>
            <a href="#" class="x-navigation-control"></a>
        </li>
        <li class="xn-profile">
            <a href="#" class="profile-mini">
                <img src="../assets/images/users/no-image.jpg" alt='<c:out value="${curUser.username}"/>'/>
            </a>
            <div class="profile">
                <div class="profile-image">
                    <img src="../assets/images/users/no-image.jpg" alt='<c:out value="${curUser.username}"/>'/>
                </div>
                <div class="profile-data">
                    <div class="profile-data-name"><c:out value="${curUser.username}"/></div>
                </div>
            </div>
        </li>

        <li class="active">
            <a href="../home/home.htm"><span class="fa fa-home"></span> <span class="xn-text">Home</span></a>
        </li>


        <sec:authorize ifAnyGranted="ROLE_BF_RELOAD_APPLICATION,ROLE_BF_CREATE_USER,ROLE_BF_LIST_USER,ROLE_BF_CREATE_CATEGORY,ROLE_BF_ADD_WAREHOUSE,ROLE_BF_LIST_WAREHOUSE,ROLE_BF_ADD_MANUFACTURER,ROLE_BF_MAP_MANUFACTURER">
            <li class="xn-openable">
                <a href="#" title="Admin"><span class="fa fa-user"></span><span class="xn-text">Admin</span></a>
                <ul>
                    <sec:authorize ifAnyGranted="ROLE_BF_RELOAD_APPLICATION">
                        <li>
                            <a href="../admin/reloadApplicationLayer.htm"><span class="fa fa-refresh fa-spin"></span>Reload Application Layer</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_CREATE_USER,ROLE_BF_LIST_USER">
                        <li class="xn-openable">
                            <a href="#"><span class="fa fa-user"></span>User</a>
                            <ul>
                                <sec:authorize ifAnyGranted="ROLE_BF_CREATE_USER">
                                    <li><a href="../admin/addUser.htm"><span class="fa fa-plus"></span>Add User</a></li>
                                </sec:authorize>
                                <sec:authorize ifAnyGranted="ROLE_BF_LIST_USER">
                                    <li><a href="../admin/userList.htm"><span class="fa fa-list-alt"></span>List User</a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_CREATE_CATEGORY">
                        <li class="xn-openable">
                            <a href="#"><span class="fa fa-sitemap"></span>Categories</a>
                            <ul>
                                <sec:authorize ifAnyGranted="ROLE_BF_CREATE_CATEGORY">
                                    <li><a href="../admin/addCategory.htm"><span class="fa fa-link"></span>Map Sub-Category</a></li>
                                </sec:authorize>
                                <sec:authorize ifAnyGranted="ROLE_BF_CREATE_CATEGORY">
                                    <li><a href="../admin/addChildCategory.htm"><span class="fa fa-link"></span>Map Child Category</a></li>
                                </sec:authorize>
                                <sec:authorize ifAnyGranted="ROLE_BF_CREATE_CATEGORY">
                                    <li><a href="../admin/addSubChildCategory.htm"><span class="fa fa-link"></span>Map Sub-Child Category</a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_ADD_WAREHOUSE,ROLE_BF_LIST_WAREHOUSE">
                        <li class="xn-openable">
                            <a href="#"><span class="fa fa-home"></span>Warehouse</a>
                            <ul>
                                <sec:authorize ifAnyGranted="ROLE_BF_ADD_WAREHOUSE">
                                    <li>
                                        <a href="../admin/addNewWarehouse.htm"><span class="fa fa-plus"></span>Add Warehouse</a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize ifAnyGranted="ROLE_BF_LIST_WAREHOUSE">
                                    <li>
                                        <a href="../admin/warehouseList.htm"><span class="fa fa-list-alt"></span>List Warehouse</a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize ifAnyGranted="ROLE_BF_SUPER">
                                    <li class="xn-openable">
                                        <a href="#"><span class="fa fa-truck"></span>Warehouse Shipping</a>
                                        <ul>
                                            <sec:authorize ifAnyGranted="ROLE_BF_ADD_WAREHOUSE">
                                                <li>
                                                    <a href="../admin/addWarehouseShipping.htm"><span class="fa fa-plus"></span>Add Warehouse Shipping</a>
                                                </li>   
                                            </sec:authorize>
                                            <sec:authorize ifAnyGranted="ROLE_BF_ADD_WAREHOUSE">
                                                <li>
                                                    <a href="../admin/editWarehouseShipping.htm"><span class="fa fa-edit"></span>Edit Warehouse Shipping</a>
                                                </li>   
                                            </sec:authorize>
                                        </ul>
                                    </li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_ADD_MANUFACTURER,ROLE_BF_MAP_MANUFACTURER">
                        <li class="xn-openable">
                            <a href="#"><span class="fa fa-industry"></span>Manufacturer</a>
                            <ul>
                                <sec:authorize ifAnyGranted="ROLE_BF_ADD_MANUFACTURER">
                                    <li>
                                        <a href="../admin/addManufacturer.htm"><span class="fa fa-plus"></span>Add Manufacturer</a>
                                    </li>
                                </sec:authorize>
                                <sec:authorize ifAnyGranted="ROLE_BF_MAP_MANUFACTURER">
                                    <li>
                                        <a href="../admin/mapManufacturer.htm"><span class="fa fa-link"></span>Map Manufacturer</a>
                                    </li>
                                </sec:authorize>

                            </ul>
                        </li>
                    </sec:authorize>
                    <!--                    <sec:authorize ifAnyGranted="ROLE_BF_ADD_MARKETPLACE,ROLE_BF_LIST_MARKETPLACE">
                                            <li class="xn-openable">
                                                <a href="#"><span class="fa fa-shopping-cart"></span>Marketplace</a>
                                                <ul>
                                                    <sec:authorize ifAnyGranted="ROLE_BF_ADD_MARKETPLACE">
                                                        <li>
                                                            <a href="../admin/addMarketplace.htm"><span class="fa fa-plus"></span>Add Marketplace</a>
                                                        </li>
                                                    </sec:authorize>
                                                    <sec:authorize ifAnyGranted="ROLE_BF_LIST_MARKETPLACE">
                                                        <li>
                                                            <a href="../admin/listMarketplace.htm"><span class="fa fa-list-alt"></span>List Marketplace</a>
                                                        </li>
                                                    </sec:authorize>
                    
                                                </ul>
                                            </li>
                                        </sec:authorize>-->
                    <!--                    <sec:authorize ifAnyGranted="ROLE_BF_CREATE_NEW_LISTINGS">
                                            <li class="xn-openable">
                                                <a href="#"><span class="fa fa-list-ol"></span>Listings</a>
                                                <ul>
                                                    <sec:authorize ifAnyGranted="ROLE_BF_CREATE_NEW_LISTINGS">
                                                        <li><a href="../admin/addNewListing.htm"><span class="fa fa-plus"></span>Add New Listing</a></li>
                                                    </sec:authorize>
                                                </ul>
                                            </li>
                                        </sec:authorize>-->
                    <!--                    <sec:authorize ifAnyGranted="ROLE_BF_ADD_COMPANY,ROLE_BF_LIST_COMPANY">
                                            <li class="xn-openable">
                                                <a href="#"><span class="fa fa-globe"></span>Company</a>
                                                <ul>
                                                    <sec:authorize ifAnyGranted="ROLE_BF_ADD_COMPANY">
                                                        <li><a href="../admin/addCompany.htm"><span class="fa fa-plus"></span>Add Company</a></li>
                                                    </sec:authorize>
                                                    <sec:authorize ifAnyGranted="ROLE_BF_LIST_COMPANY">
                                                        <li>
                                                            <a href="../admin/companyList.htm"><span class="fa fa-list-alt"></span>List Company</a>
                                                        </li>
                                                    </sec:authorize>
                                                </ul>
                                            </li>
                                        </sec:authorize>-->
                    <!--                    <sec:authorize ifAnyGranted="ROLE_BF_UPLOAD_INVOICE">
                                            <li class="xn-openable">
                                                <a href="#"><span class="fa fa-money"></span>Accounting</a>
                                                <ul>
                                                    <sec:authorize ifAnyGranted="ROLE_BF_UPLOAD_INVOICE">
                                                        <li><a href="../admin/uploadInvoice.htm"><span class="fa fa-upload"></span>Invoice Upload</a></li>
                                                    </sec:authorize>
                                                </ul>
                                            </li>
                                        </sec:authorize>-->
                </ul>
            </li>
        </sec:authorize>

        <sec:authorize ifAnyGranted="ROLE_BF_CREATE_PRODUCT,ROLE_BF_LIST_PRODUCT,ROLE_BF_UPLOAD_FEEDS,ROLE_BF_VIEW_PRODUCT">
            <li class="xn-openable">
                <a href="#" title="Product"><span class="fa fa-suitcase"></span><span class="xn-text">Product</span></a>
                <ul>
                    <sec:authorize ifAnyGranted="ROLE_BF_CREATE_PRODUCT">
                        <li>
                            <a href="../product/addProduct.htm"><span class="fa fa-plus"></span>Add Product</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_LIST_PRODUCT">
                        <li>
                            <a href="../product/listProduct.htm"><span class="fa fa-list-alt"></span>List Product</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_UPLOAD_FEEDS">
                        <li>
                            <a href="../warehouse/uploadFeed.htm"><span class="fa fa-upload"></span>Upload Feeds</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_VIEW_PRODUCT">
                        <li>
                            <a href="../product/viewProduct.htm"><span class="fa fa-eye"></span>View Product</a>
                        </li>
                    </sec:authorize>
                    <!--<sec:authorize ifAnyGranted="ROLE_BF_VIEW_PRODUCT_MISSING_DATA">
                        <li>
                            <a href="../product/missingProductDataList.htm"><span class="fa fa-edit"></span>Product Missing Data</a>
                        </li>
                    </sec:authorize>
                    -->                    <sec:authorize ifAnyGranted="ROLE_BF_VIEW_DELETE_PRODUCT">
                        <li>
                            <a href="../product/deleteProduct.htm"><span class="fa fa-eraser"></span>Delete Product</a>
                        </li>
                    </sec:authorize><!--
                    <sec:authorize ifAnyGranted="ROLE_BF_MAP_ADMIN_TOOL_MPN">
                        <li>
                            <a href="../product/mapProduct.htm"><span class="fa fa-link"></span>Map Product</a>
                        </li>
                    </sec:authorize>
                                        <sec:authorize ifAnyGranted="ROLE_BF_EXPORT_PRODUCT_DATA">
                                            <li>
                                                <a href="../product/exportProductData.htm"><span class="glyphicon glyphicon-export"></span>Export Data</a>
                                            </li>
                                        </sec:authorize>-->
                </ul>
            </li>
        </sec:authorize>

        <sec:authorize ifAnyGranted="ROLE_BF_UPLOAD_MARKETPLACE_FEE,ROLE_BF_PROCESS_LISTINGS,ROLE_BF_EXPORT_LISTINGS">
            <li class="xn-openable">
                <a href="#" title="Listing"><span class="fa fa-list-ol"></span><span class="xn-text">Listing</span></a>
                <ul>
                    <sec:authorize ifAnyGranted="ROLE_BF_UPLOAD_MARKETPLACE_FEE">
                        <li>
                            <a href="../listing/marketplaceFeesUpload.htm"><span class="fa fa-upload"></span>Upload Marketplace Fees</a>
                        </li>

                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_PROCESS_LISTINGS">
                        <li>
                            <a href="../listing/selectMarketplace.htm"><span class="glyphicon glyphicon-ok-circle"></span>Marketplace Listings</a>
                        </li>

                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_EXPORT_LISTINGS">
                        <li>
                            <a href="../listing/exportMarketplaceListing.htm"><span class="fa fa-download"></span>Export Listings</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_SUPER">
                        <li>
                            <a href="../listing/exportMarketPlaceFees.htm"><span class="fa fa-download"></span>Export MarketPlace Fees</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_EXPORT_MARKETPLACE_FEES">
                        <li>
                            <a href="../listing/uploadMarketplaceListing.htm"><span class="fa fa-upload"></span>Upload MarketPlace Listing</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_EXPORT_MARKETPLACE_FEES">
                        <li>
                            <a href="../listing/deleteMarketplaceListing.htm"><span class="fa fa-upload"></span>Delete MarketPlace Listing</a>
                        </li>
                    </sec:authorize>
                </ul>
            </li>
        </sec:authorize>

        <sec:authorize ifAnyGranted="ROLE_BF_VIEW_ORDER_TRACKING,ROLE_BF_VIEW_ORDER_LIST,ROLE_BF_UPLOAD_BULK_TRACKING">
            <li class="xn-openable">
                <a href="#" title="Orders"><span class="fa fa-shopping-cart"></span><span class="xn-text">Orders</span></a>
                <ul>
                    <sec:authorize ifAnyGranted="ROLE_BF_VIEW_ORDER_LIST">
                        <li>
                            <a href="../order/marketplaceOrderList.htm"><span class="fa fa-list-alt"></span>Order List</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_SUPER">
                        <li>
                            <a href="../order/marketplaceOrderUpload.htm"><span class="fa fa-upload"></span>MarketPlace Orders Upload</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_SUPER">
                        <li>
                            <a href="../order/exportProcessingSheet.htm"><span class="fa fa-download"></span>Export Processing Sheet</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_UPLOAD_BULK_TRACKING">
                        <li class="xn-openable">
                            <a href=""><span class="fa fa-truck"></span>Tracking</a>
                            <ul>
                                <sec:authorize ifAnyGranted="ROLE_BF_VIEW_ORDER_TRACKING">
                                    <li><a href="../order/orderTracking.htm"><span class="fa fa-truck"></span>Order Tracking</a></li>
                                </sec:authorize>
                                <sec:authorize ifAnyGranted="ROLE_BF_UPLOAD_BULK_TRACKING">
                                    <li><a href="../order/bulkOrderTracking.htm"><span class="fa fa-truck"></span>Bulk Tracking</a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                </ul>
            </li>
        </sec:authorize>
        <sec:authorize ifAnyGranted="ROLE_BF_SUPER">
            <li class="xn-openable">
                <a href="#" title="Website"><span class="fa fa-globe"></span><span class="xn-text">Website</span></a>
                <ul>
                    <!--                     <sec:authorize ifAnyGranted="ROLE_BF_UPLOAD_FOR_WEBSITE">
                                            <li>
                                                <a href="../website/uploadWebsiteProduct.htm"><span class="fa fa-upload"></span>Upload Products for website</a>
                                            </li>
                                        </sec:authorize>-->
                    <sec:authorize ifAnyGranted="ROLE_BF_DOWNLOAD_WEBSITE_PRODUCT">
                        <li>
                            <a href="../product/downloadProduct.htm"><span class="fa fa-download"></span>Download Product</a>
                        </li>
                    </sec:authorize>
                </ul>
            </li>
        </sec:authorize>
        <sec:authorize ifAnyGranted="ROLE_BF_SUPER">
            <li class="xn-openable">
                <a href="#" title="Tickets"><span class="fa fa-tasks"></span><span class="xn-text">Tickets</span></a>
                <ul>
                    <sec:authorize ifAnyGranted="ROLE_BF_CREATE_TICKET">
                        <li>
                            <a href="../tickets/createTicket.htm"><span class="fa fa-plus"></span>Create Ticket</a>
                        </li>
                    </sec:authorize>                    
                    <sec:authorize ifAnyGranted="ROLE_BF_LIST_ALL_TICKETS">
                        <li>
                            <a href="../tickets/listAllTickets.htm"><span class="fa fa-list-alt"></span>View All Tickets</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_LIST_OPEN_TICKETS">
                        <li>
                            <a href="../tickets/listOpenTickets.htm"><span class="fa fa-list-alt"></span>My Ticket List</a>
                        </li>
                    </sec:authorize>
                </ul>
            </li> 
        </sec:authorize>
        <sec:authorize ifAnyGranted="ROLE_BF_SUPER">
            <li class="xn-openable">
                <a href="#" title="Reports"><span class="fa fa-file-text-o"></span><span class="xn-text">Reports</span></a>
                <ul>
                    <sec:authorize ifAnyGranted="ROLE_BF_REPORT_ACCESS_LOG">
                        <li>
                            <a href="../report/reportAccessLog.htm"><span class="fa fa-th-list"></span>Access Log</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_BF_REPORT_ACCESS_LOG">
                        <li>
                            <a href="../report/agentLoginAndBreakLog.htm"><span class="fa fa-th-list"></span>Login And Break Log</a>
                        </li>
                    </sec:authorize>
                </ul>
            </li>
        </sec:authorize>    
    </ul>
    <!-- END X-NAVIGATION -->
</div>
<!-- END PAGE SIDEBAR -->
