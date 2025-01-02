package com.etilize.conquire.viewsonicbatchservice.viewsonic.product.IMAGE;
import com.etilize.conquire.viewsonicbatchservice.constants.XmlDataConstants;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.IMAGE.Image;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = XmlDataConstants.IMAGE)
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Image {

        private String order;


        private String imageFileName;
    @XmlAttribute(name = "IMAGE_ORDER")
        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }
    @XmlElement(name = "IMAGE_FILE_NAME")
        public String getImageFileName() {
            return imageFileName;
        }

        public void setImageFileName(String imageFileName) {
            this.imageFileName = imageFileName;
        }
    }


