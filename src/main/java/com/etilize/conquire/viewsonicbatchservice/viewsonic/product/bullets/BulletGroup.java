package com.etilize.conquire.viewsonicbatchservice.viewsonic.product.bullets;

import com.etilize.conquire.viewsonicbatchservice.constants.XmlDataConstants;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement(name= XmlDataConstants.BULLET_GROUP)
@XmlAccessorType(XmlAccessType.PROPERTY)
@EqualsAndHashCode
public class BulletGroup {

    private String SUBHEADER;
    private Bullet[] BULLET;

    @XmlElement(name = XmlDataConstants.SUB_HEADER)
    public String getSUBHEADER() {
        return SUBHEADER;
    }

    public void setSUBHEADER(String SUBHEADER) {
        this.SUBHEADER = SUBHEADER;
    }
    @XmlElement
    public Bullet[] getBULLET() {
        return BULLET;
    }

    public void setBULLET(Bullet[] BULLET) {
        this.BULLET = BULLET;
    }
}
