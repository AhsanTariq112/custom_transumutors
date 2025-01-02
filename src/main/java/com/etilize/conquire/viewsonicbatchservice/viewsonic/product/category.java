package com.etilize.conquire.viewsonicbatchservice.viewsonic.product;


import com.etilize.conquire.viewsonicbatchservice.constants.XmlDataConstants;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.categorydata.categorydata;

import javax.xml.bind.annotation.*;


@XmlRootElement(name = "CATEGORIES")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class category {

    private categorydata CATEGORY;

    @XmlElement(name = "CATEGORY")
    public categorydata getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(categorydata CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

}



