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
package com.etilize.conquire.viewsonicbatchservice.viewsonic.product;

import com.etilize.conquire.viewsonicbatchservice.constants.CollectionConstants;
import com.etilize.conquire.viewsonicbatchservice.constants.XmlDataConstants;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.bullets.bullets;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.text.StringEscapeUtils;


/**
 * This class implements pojo for ODF product
 * @author Ahsan Tariq
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@XmlRootElement(name = XmlDataConstants.PRODUCT)
@XmlAccessorType(XmlAccessType.PROPERTY)
@EqualsAndHashCode
@Document(collection = CollectionConstants.VIEWSONIC_PRODUCT_LIST_COLLECTION_NAME)
public class Product {

    private String id;

    private String genreId;

    private String ofdcatalogsku;

    private String essedantproductid;

    private String sprichardssku;

    private String cleanOdPartNo;

    private String Private_label;

    private String Product_Name;

    private String ProductUrl;

    private String ImageUrl;

    private String OD_MIN_ORDER_REQUIRED_QTY;


    private AttributeGroup[] attributeGroup;

    private bullets Bullets;

    private Images images;


    private Set<Long> jobInstanceId = new HashSet<>();

    private Set<Long> stepExecutionIds = new HashSet<>();

    public void addJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId.add(jobInstanceId);
    }

    public void addStepExecutionId(Long stepExecutionId) {
        this.stepExecutionIds.add(stepExecutionId);
    }

    @XmlAttribute(name = XmlDataConstants.ID)
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute(name = XmlDataConstants.GENRE_ID)
    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }
    @XmlAttribute(name = XmlDataConstants.OFD_CATALOG_SKU)
    public String getOfdcatalogsku() {
        return ofdcatalogsku;
    }

    public void setOfdcatalogsku(String ofdcatalogsku) {
        this.ofdcatalogsku = ofdcatalogsku;
    }

    @XmlAttribute(name = XmlDataConstants.ESSEDANT_PRODUCT_ID)
    public String getEssendantproductid() {
        return essedantproductid;
    }

    public void setEssendantproductid(String essendantproductid) {
        this.essedantproductid = essendantproductid;
    }
    @XmlAttribute(name = XmlDataConstants.SP_RICHARDS_SKU)
    public String getSprichardssku() {
        return sprichardssku;
    }

    public void setSprichardssku(String sprichardssku) {
        this.sprichardssku = sprichardssku;
    }
    @XmlAttribute(name = XmlDataConstants.CLEAN_OD_PART_NO)
    public String getCleanOdPartNo() {
        return cleanOdPartNo;
    }

    public void setCleanOdPartNo(String cleanOdPartNo) {
        this.cleanOdPartNo = cleanOdPartNo;
    }
    @XmlAttribute(name = XmlDataConstants.PRIVATE_LABEL)
    public String getPrivate_label() {
        return Private_label;
    }

    public void setPrivate_label(String private_label) {
        Private_label = private_label;
    }

    @XmlElement(name = XmlDataConstants.PRODUCT_NAME)
    public String getProduct_Name() {
        return removeSpecialCharacters(Product_Name);
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    @XmlElement(name = XmlDataConstants.ESSEDANT_PRODUCT_ID)
    public String getEssedantproductid() {
        return essedantproductid;
    }

    public void setEssedantproductid(String essedantproductid) {
        this.essedantproductid = essedantproductid;
    }

    @XmlElement(name = XmlDataConstants.PRODUCT_URL)
    public String getProductUrl() {
        return ProductUrl;
    }

    public void setProductUrl(String productUrl) {
        ProductUrl = productUrl;
    }
    @XmlElement(name = XmlDataConstants.IMAGE_URL)
    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
    @XmlElement(name = XmlDataConstants.OD_MIN_ORDER_REQUIRED_QTY)
    public String getOD_MIN_ORDER_REQUIRED_QTY() {
        return OD_MIN_ORDER_REQUIRED_QTY;
    }

    public void setOD_MIN_ORDER_REQUIRED_QTY(String OD_MIN_ORDER_REQUIRED_QTY) {
        this.OD_MIN_ORDER_REQUIRED_QTY = OD_MIN_ORDER_REQUIRED_QTY;
    }


    @XmlElement(name = XmlDataConstants.ATTRIBUTES)
    public AttributeGroup[] getAttributeGroup() {
        return attributeGroup;
    }

    public void setAttributeGroup(AttributeGroup[] attributeGroup) {
        this.attributeGroup = attributeGroup;
    }

    @XmlElement(name = XmlDataConstants.BULLETS)
    public bullets getBullets() {
        return Bullets;
    }

    public void setBullets(bullets bullets) {
        Bullets = bullets;
    }

    @XmlElement(name = XmlDataConstants.IMAGES)
    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    private String removeSpecialCharacters(String input) {
        return StringEscapeUtils.unescapeXml(input);
    }
}
