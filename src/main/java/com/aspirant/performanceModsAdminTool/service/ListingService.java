/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.DTO.ProductListingDTO;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import java.util.List;

/**
 *
 * @author Ritesh
 */
public interface ListingService {

    /**
     * This method is used to save multipart file in local path and load file
     * data in database for selected marketplace. File contains product
     * commission for given marketplace.
     *
     * @param uploadFeed uploadFeed object which contains multipart file
     * @param marketplaceId marketplace id for which we upload commission
     * @return 1-Success, 0-Error
     */
    public int saveMultipartFileData(UploadFeed uploadFeed, int marketplaceId);

    /**
     * This method is used to save multipart file in local path and load file
     * data in database for selected marketplace. File contains product
     * commission for given marketplace.
     *
     * @param uploadFeed uploadFeed object which contains multipart file
     * @param marketplaceId marketplace id for which we upload commission
     * @return 1-Success, 0-Error
     */
    public int saveMultipartFileData1(UploadFeed uploadFeed, int marketplaceId);

    /**
     * Method is used to get List of all marketplace listings based on given
     * parameters.
     *
     * @param marketplaceId marketplace id for which we need to display listings
     * @param warehouseId warehouse id for which we need to display listings
     * @param marketplaceSku sku for which we need to display listings
     * @param pageNo page number for pagination
     * @param active 1- active listings ,0- all listings
     * @param manufacturerId manufacturer id for which we need to display
     * listings
     * @return list of listings
     */
    public List<ProductListingDTO> getListingList(int marketplaceId, int warehouseId, String marketplaceSku, int pageNo, boolean active, int manufacturerId[]);

    /**
     * Method is used to get count of marketplace listings for pagination based
     * on given parameters.
     *
     * @param marketplaceId marketplace id for which we need to display listings
     * @param warehouseId warehouse id for which we need to display listings
     * @param marketplaceSku sku for which we need to display listings
     * @param active 1- active listings ,0- all listings
     * @param manufacturerId manufacturer id for which we need to display
     * listings
     * @return count of listings
     */
    public int getListingCount(int marketplaceId, int warehouseId, String marketplaceSku, boolean active, int manufacturerId[]);

    /**
     * method is used to update price and quantity of listing
     *
     * @param sku marketplace sku
     * @param price current listed price
     * @param quantity current listed quantity
     * @param active 1-enable, 0- disable
     * @return number of rows updated if success and 0 if error
     */
    public int updatePriceQuantity(String sku, double price, int quantity, double profit, boolean active);

    /**
     * method used to update last listed price and quantity to current listed
     * price and quantity. Also save listing history for reference.
     *
     * @param marketplaceId marketplace id for which listings needs to be
     * updated
     * @return 1-Success, 0-Error
     */
    public int UpdateListings(int marketplaceId);

    /**
     * method is used to get list of profit percentage.
     *
     * @return list of profit percentage
     */
    public List<Integer> getProfitPercentageList();

    /**
     * method is used to search sku for autocomplete
     *
     * @param term string to search sku
     * @return list of matched skus
     */
    public List<String> searchSku(String term);

    /**
     * This method will calculate and update actual price and quantity for
     * listing using warehouse price and quantity for given marketplace and
     * profit percentage.
     *
     * @param marketplaceId marketplace id to process listings
     * @return 1-Success, 0-Error
     */
    public int processListing(int marketplaceId, int manufacturerId);

    /**
     * this method used to get the exportMarketplaceFees
     *
     * @param marketplaceId
     * @param pageNo
     * @return list
     */
    public List<Listing> exportMarketplaceFees(int marketplaceId, int pageNo);

    public List<Listing> exportMarketplaceFeesForExcel(int marketplaceId);

    /**
     * this method will give the count of total number of rows in
     * exportMarketplaceFees
     *
     * @param marketplaceId
     * @return count of ExportMarketplaceFeesCount
     */
    public int getExportMarketplaceFeesCount(int marketplaceId);
}
