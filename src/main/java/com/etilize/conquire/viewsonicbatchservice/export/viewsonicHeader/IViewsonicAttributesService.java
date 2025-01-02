package com.etilize.conquire.viewsonicbatchservice.export.viewsonicHeader;


/**
 * Provides implementations for saving and getting attributes coming from FEED element.
 *
 * @author Ahsan Tariq
 * @since 1.0
 */
public interface IViewsonicAttributesService {

    /**
     * To save attribute
     * @param key
     * @param value
     */
    void saveAttribute(String key,String value);

    /**
     * To get attribute
     * @param key
     * @return
     */
    String getAttribute(String key);
}
