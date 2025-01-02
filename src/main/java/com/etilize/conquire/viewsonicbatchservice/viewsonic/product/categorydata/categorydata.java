package com.etilize.conquire.viewsonicbatchservice.viewsonic.product.categorydata;

import com.etilize.conquire.viewsonicbatchservice.constants.XmlDataConstants;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement(name= XmlDataConstants.Category)
@XmlAccessorType(XmlAccessType.PROPERTY)
@EqualsAndHashCode


public class categorydata {

    private String ID;
    private String CATEGORYNAME;

    @XmlAttribute(name = "ID")
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    @XmlElement(name = "CATEGORYNAME")
    public String getCATEGORYNAME() {
        return CATEGORYNAME;
    }

    public void setCATEGORYNAME(String CATEGORYNAME) {
        this.CATEGORYNAME = CATEGORYNAME;
    }
}
