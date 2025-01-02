package com.etilize.conquire.viewsonicbatchservice.export.viewsonicHeader.impl;

import com.etilize.conquire.viewsonicbatchservice.export.viewsonicHeader.IViewsonicAttributesService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class implements service for {@link }.
 *
 * @author Ahasn Tariq
 * @since 1.0
 */
@Service
public class IViewsonicAttributesServiceImpl implements IViewsonicAttributesService {

    /**
     * Attribute map to store attributes from FEED element.
     */
    public static final Map<String, String> ATTRIBUTES = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    public void saveAttribute(final String key, final String value){
        ATTRIBUTES.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    public String getAttribute(final String key){
        if(ATTRIBUTES.containsKey(key)) {
            return ATTRIBUTES.get(key);
        }
        return null;
    }
}
