/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import java.util.List;

/**
 *
 * @author Ritesh
 */
public interface ManufacturerService {

    /**
     * Method used to create new manufacturer
     *
     * @param manufacturer manufacturer object
     * @return created manufacturer id if success and 0 if error
     */
    public int addManufacturer(Manufacturer manufacturer);

    /**
     * Method is used to map same manufacturer with different spelling with the
     * existing manufacturer
     *
     * @param manufacturer manufacturer object
     * @return 1 if success and 0 if error
     */
    public int mapManufacturer(Manufacturer manufacturer);

    /**
     * Method is used to get list of manufacturer codes matching with the given
     * manufacturer code to check if code is existing or new
     *
     * @param manufacturerCode manufacturer code
     * @return list of matching manufacturer codes
     */
    public List<String> GetListofMatchingManufacturerCode(String manufacturerCode);

    /**
     * Method is used to get the list of all mapped manufacturers with the
     * selected manufacturer
     *
     * @param manufacturerId selected manufacturer id
     * @return list of mapped manufacturers
     */
    public List<Manufacturer> mapManufacturerList(int manufacturerId);

}
