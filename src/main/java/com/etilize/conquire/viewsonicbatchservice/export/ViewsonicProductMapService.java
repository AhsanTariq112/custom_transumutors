/*
 * #region
 * viewsonic-batch-service
 * %%
 * Copyright (C) 2022 Etilize
 * %%
 * NOTICE: All information contained herein is, and remains the property of ETILIZE.
 * The intellectual and technical concepts contained herein are proprietary to
 * ETILIZE and may be covered by U.S. and Foreign Patents, patents in process, and
 * are protected by trade secret or copyright law. Dissemination of this information
 * or reproduction of this material is strictly forbidden unless prior written
 * permission is obtained from ETILIZE. Access to the source code contained herein
 * is hereby forbidden to anyone except current ETILIZE employees, managers or
 * contractors who have executed Confidentiality and Non-disclosure agreements
 * explicitly covering such access.
 *
 * The copyright notice above does not evidence any actual or intended publication
 * or disclosure of this source code, which includes information that is confidential
 * and/or proprietary, and is a trade secret, of ETILIZE. ANY REPRODUCTION, MODIFICATION,
 * DISTRIBUTION, PUBLIC PERFORMANCE, OR PUBLIC DISPLAY OF OR THROUGH USE OF THIS
 * SOURCE CODE WITHOUT THE EXPRESS WRITTEN CONSENT OF ETILIZE IS STRICTLY PROHIBITED,
 * AND IN VIOLATION OF APPLICABLE LAWS AND INTERNATIONAL TREATIES. THE RECEIPT
 * OR POSSESSION OF THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR
 * IMPLY ANY RIGHTS TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO
 * MANUFACTURE, USE, OR SELL ANYTHING THAT IT MAY DESCRIBE, IN WHOLE OR IN PART.
 * #endregion
 */
package com.etilize.conquire.viewsonicbatchservice.export;


import com.etilize.conquire.viewsonicbatchservice.constants.CommonConstants;
import com.etilize.conquire.viewsonicbatchservice.constants.HeaderConstants;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.AttributeGroup;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.IMAGE.Image;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.Images;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.Product;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.attributeGroup.Attribute;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.bullets.Bullet;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.bullets.BulletGroup;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.bullets.bullets;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.categorydata.categorydata;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * This class generates headers to be used in csv files from input xml.
 *
 * @author Ahsan Tariq
 * @since 1.0
 */

public class ViewsonicProductMapService {

    /**
     * Generates headers for ViewsonicProduct
     *
     * @param product root element of ViewsonicProduct
     * @return Map containing headers for ViewsonicProduct as key and data as values
     */
    public Map<String, String> convertToMap(final Product product) {
        final Map<String, String> productMap = new LinkedHashMap<>();
        if (null == product) {
            return Collections.emptyMap();
        }

        if (null != product.getId()) {
            productMap.put(HeaderConstants.ID, product.getId());
        }
        if (null != product.getGenreId()) {
            productMap.put(HeaderConstants.GENRE_ID, product.getGenreId());
        }
        if (null != product.getOfdcatalogsku()) {
            productMap.put(HeaderConstants.OFD_CATALOG_SKU, product.getOfdcatalogsku());
        }
        if (null != product.getEssendantproductid()) {
            productMap.put(HeaderConstants.ESSENDANT_PRODUCT_ID, product.getEssendantproductid());
        }


        if (null != product.getSprichardssku()) {
            productMap.put(HeaderConstants.SP_RICHARD_SKU, product.getSprichardssku());
        }

        if (null != product.getCleanOdPartNo()) {
            productMap.put(HeaderConstants.CLEAN_OD_PART_NO, product.getCleanOdPartNo());
        }

        if (null != product.getPrivate_label()) {
            productMap.put(HeaderConstants.PRIVATE_LABEL, product.getPrivate_label());
        }

        if (null != product.getProduct_Name()) {
            productMap.put(HeaderConstants.PRODUCT_NAME, product.getProduct_Name());
        }

        if (null != product.getProductUrl()) {
            productMap.put(HeaderConstants.PRODUCT_URL, product.getProductUrl());
        }
        if (null != product.getImageUrl()) {
            productMap.put(HeaderConstants.IMAGE_URL, product.getImageUrl());
        }

        if (null != product.getBullets()) {
            productMap.putAll(getBulletGroups(product.getBullets()));
        }


        if (null != product.getOD_MIN_ORDER_REQUIRED_QTY()) {
            productMap.put(HeaderConstants.OD_MIN_ORDER, product.getOD_MIN_ORDER_REQUIRED_QTY());
        }
        if (null != product.getAttributeGroup()) {
            productMap.putAll(getAttributeGroups(product.getAttributeGroup()));
        }
        if (null != product.getImages()) {
            productMap.putAll(getImages(product.getImages()));
        }
        if (productMap != null) {
            productMap.entrySet().removeIf(entry ->
                    entry.getValue() == null || entry.getValue().toString().trim().isEmpty());
        }

        String privateLabel = productMap.get("PrivateLabel");
        if (!"Y".equalsIgnoreCase(privateLabel)) {
            productMap.clear();
        }
        return filterProductMap(productMap);
    }

    /**
     * Generates headers for Items
     *
     * @param items
     * @return Map containing headers for Items as key and data as values
     */
    private Map<String, String> getCategories(final categorydata[] items) {
        final Map<String, String> productcategoryItemMap = new LinkedHashMap<>();
        int index = 0;

        if (null != items) {
            for (final categorydata item : items) {
                if (null != item.getID()) {
                    productcategoryItemMap.put(String.format(HeaderConstants.CATEORY_ID, ++index), item.getID());
                }
                if (null != item.getCATEGORYNAME()) {
                    productcategoryItemMap.put(String.format(HeaderConstants.CATEGORY_NAME, index), item.getCATEGORYNAME());
                }
            }
        }

        return productcategoryItemMap;
    }

    private Map<String, String> getAttributeGroups(final AttributeGroup[] attributeGroups) {
        final Map<String, String> attributeGroupsMap = new LinkedHashMap<>();
        if (attributeGroups != null && attributeGroups.length > 0) {
            int index = 0;
            for (final AttributeGroup attributeGroup : attributeGroups) {
                index++;
                if (attributeGroup != null && attributeGroup.getAttributes() != null) {
                    attributeGroupsMap.putAll(getUniqueAttributes(attributeGroup));
                }
            }
        }
        return attributeGroupsMap;
    }

    private Map<String, String> getUniqueAttributes(AttributeGroup attributeGroup) {
        Map<String, String> uniqueAttributesMap = new LinkedHashMap<>();

        if (attributeGroup != null) {
            Set<String> uniqueAttributeNames = new HashSet<>();

            for (Attribute attribute : attributeGroup.getAttributes()) {
                if (attribute != null && attribute.getNAME() != null) {
                    // Normalize attribute name (convert to lowercase and trim whitespaces)
                    String attributeName = attribute.getNAME().toLowerCase().trim();

                    // Check for uniqueness based on normalized attribute name
                    if (!uniqueAttributeNames.contains(attributeName)) {
                        uniqueAttributeNames.add(attributeName);
                        uniqueAttributesMap.put(attributeName, attribute.getAttribute());
                    }
                }
            }
        }

        return uniqueAttributesMap;
    }


    /**
     * Filters out all null and empty values from map
     *
     * @param productMap
     * @return filtered map
     */
    private Map<String, String> filterProductMap(final Map<String, String> productMap) {
        if (null == productMap || productMap.isEmpty()) {
            return Collections.emptyMap();
        }
        final Map<String, String> map = new LinkedHashMap<>();
        for (final Map.Entry<String, String> entry : productMap.entrySet()) {
            if (!StringUtils.isBlank(entry.getValue())
                    && !entry.getValue().equalsIgnoreCase(CommonConstants.NULL)) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }



    private Map<String, String> getBulletGroups(final bullets bullets) {
        final Map<String, String> bulletGroupsMap = new LinkedHashMap<>();

        if (bullets != null) {
            BulletGroup bulletGroup = bullets.getBULLETGROUP();
            int index = 0;
            StringBuilder allBulletCopies = new StringBuilder(); // New StringBuilder to store all BULLETCOPY values

            if (bulletGroup != null) {
                if (null != bulletGroup.getSUBHEADER()) {
                    bulletGroupsMap.put(String.format(HeaderConstants.SUB_HEADER), bulletGroup.getSUBHEADER());
                }
                Bullet[] bulletsArray = bulletGroup.getBULLET();
                if (bulletsArray != null) {
                    for (int i = 0; i < bulletsArray.length; i++) {
                        index++;
                        Bullet bullet = bulletsArray[i];

                        if (null != bullet.getBULLETCOPY()) {
                            // Concatenate BULLETCOPY values into StringBuilder
                            allBulletCopies.append("<li>");
                            allBulletCopies.append(bullet.getBULLETCOPY()).append("</li>");
                        }
                        if (null != bullet.getBULLETSEQ()) {
                            bulletGroupsMap.put(String.format(HeaderConstants.BULLET_SEQ, index), bullet.getBULLETSEQ());
                        }
                    }
                }
            }

            // Add the concatenated BULLETCOPY values to the map after the loop
            bulletGroupsMap.put(String.format(HeaderConstants.BULLET_COPY), allBulletCopies.toString().trim());
        }

        return bulletGroupsMap;
    }

    private Map<String, String> getImages(final Images images) {
        final Map<String, String> getImageMap = new LinkedHashMap<>();
        if (images != null && images.getImage() != null) {
            Image[] allImages = images.getImage();
            int index = 0;
            for (int i = 0; i < allImages.length; i++) {
                index++;

                Image img = allImages[i];

                if (null != img.getOrder()) {
                    getImageMap.put(String.format(HeaderConstants.IMAGES_ORDER+ "_" + index), img.getOrder());
                }
                if (null != img.getImageFileName()) {
                    getImageMap.put(String.format(HeaderConstants.IMAGES_FILENAME+ "_" + index), img.getImageFileName());
                }
            }
        }

        return getImageMap;
    }
}