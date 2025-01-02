package com.etilize.conquire.viewsonicbatchservice.viewsonic.product;

import com.etilize.conquire.viewsonicbatchservice.constants.CollectionConstants;
import com.etilize.conquire.viewsonicbatchservice.constants.XmlDataConstants;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.attributeGroup.Attribute;
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

@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@XmlRootElement(name = XmlDataConstants.PRODUCTS)
@XmlAccessorType(XmlAccessType.PROPERTY)
@EqualsAndHashCode
@Document(collection = CollectionConstants.VIEWSONIC_PRODUCT_LIST_COLLECTION_NAME)
public class Products {
    private Product[] Product;

    @XmlElement (name = XmlDataConstants.PRODUCT)
    public Product[] getProduct() {
        return Product;
    }

    public void setProduct(com.etilize.conquire.viewsonicbatchservice.viewsonic.product.Product[] product) {
        Product = product;
    }
}
