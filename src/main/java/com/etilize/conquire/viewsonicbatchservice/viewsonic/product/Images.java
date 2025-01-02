package com.etilize.conquire.viewsonicbatchservice.viewsonic.product;

import com.etilize.conquire.viewsonicbatchservice.constants.XmlDataConstants;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.IMAGE.Image;


import javax.xml.bind.annotation.*;

@XmlRootElement(name = XmlDataConstants.IMAGES)
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Images {


    private Image[] image;
    @XmlElement(name = "IMAGE")
    public Image[] getImage() {
        return image;
    }

    public void setImage(Image[] image) {
        this.image = image;
    }
}
