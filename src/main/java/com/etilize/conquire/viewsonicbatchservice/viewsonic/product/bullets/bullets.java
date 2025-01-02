package com.etilize.conquire.viewsonicbatchservice.viewsonic.product.bullets;

import com.etilize.conquire.viewsonicbatchservice.constants.XmlDataConstants;
import com.etilize.conquire.viewsonicbatchservice.viewsonic.product.attributeGroup.Attribute;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class implements pojo for Viewsonic AttributeGroup
 * @author Ahsan Tariq
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement(name= XmlDataConstants.BULLETS)
@XmlAccessorType(XmlAccessType.PROPERTY)
@EqualsAndHashCode
public class bullets {

    private BulletGroup BULLETGROUP;

    @XmlElement
    public BulletGroup getBULLETGROUP() {
        return BULLETGROUP;
    }

    public void setBULLETGROUP(BulletGroup BULLETGROUP) {
        this.BULLETGROUP = BULLETGROUP;
    }
}
