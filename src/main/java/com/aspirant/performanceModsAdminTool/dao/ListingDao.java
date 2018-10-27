/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao;

import com.aspirant.performanceModsAdminTool.model.DTO.ProductListingDTO;
import com.aspirant.performanceModsAdminTool.model.Listing;

import java.util.List;

/**
 *
 * @author Ritesh
 */
public interface ListingDao {

    /**
     * Method will load Amazon commission file data in database for pricing
     *
     * @param path path in which we save uploaded commission file
     * @param marketplaceId marketplace id for which we upload commission
     */
    public void loadFeesDataLocally(String path, int marketplaceId);
    
    /**
     * Method will load Amazon commission file data in database for pricing
     *
     * @param path path in which we save uploaded commission file
     * @param marketplaceId marketplace id for which we upload commission
     */
    public void loadFeesDataLocally1(String path, int marketplaceId);

    /**
     * Method is used to get list of all marketplace listings based on given
     * parameters.
     *
     * @param marketplaceId marketplace id for which we need to display listings
     * @param warehouseId Warehouse id for which we need to display listings
     * @param marketplaceSku Sku for which we need to display listing
     * @param pageNo page number for pagination
     * @param active 1- active listings ,0- all listings
     * @param manufacturerId manufacturer id for which we need to display
     * listings
     * @return list of listings
     */
    public List<ProductListingDTO> getListingList(int marketplaceId, int warehouseId, String marketplaceSku, int pageNo, boolean active, int[] manufacturerId);

    /**
     * Method is used to get count of marketplace listings for pagination based
     * on given parameters.
     *
     * @param marketplaceId marketplace id for which we need to display listings
     * @param warehouseId Warehouse id for which we need to display listings
     * @param marketplaceSku sku for which we need to display listings
     * @param active 1- active listings ,0- all listings
     * @param manufacturerId manufacturer id for which we need to display
     * listings
     * @return count of listings
     */
    public int getListingCount(int marketplaceId, int warehouseId, String marketplaceSku, boolean active, int[] manufacturerId);

    /**
     * method is used to update price and quantity of listing
     *
     * @param sku marketplace Sku
     * @param price current listed price
     * @param quantity current listed quantity
     * @param active 1-enable, 0- disable
     * @return number of rows updated
     */
    public int updatePriceQuantity(String sku, double price, int quantity, double profit, boolean active);

    /**
     * method used to update last listed price and quantity to current listed
     * price and quantity. Also save listing history for reference.
     *
     * @param marketplaceId marketplace id for which listing needs to be updated
     */
    public void UpdateListings(int marketplaceId);

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
     * @param profitPercentage profit percentage to calculate listed price
     * @return number of rows updated
     */
    public int processListing(int marketplaceId, int manufacturerId);

    /**
     * this is method is used to set default value in status
     */
    public void flushFeesStatus();
    
    /**
     * this method return list of fees of particular market place
     * @param marketplaceId
     * @param pageNo
     * @return 
     */
    public List<Listing> exportMarketplaceFees(int marketplaceId,int pageNo);
    
    public List<Listing> exportMarketplaceFeesForExcel(int marketplaceId);
    
    /**
     * this method will give the count of total number of rows in exportMarketplaceFees 
     * @return 
     */
    public int getExportMarketplaceFeesCount(int marketplaceId);
}
