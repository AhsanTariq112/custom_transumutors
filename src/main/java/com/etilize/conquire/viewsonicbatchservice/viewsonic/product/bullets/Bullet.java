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
@XmlRootElement(name= XmlDataConstants.BULLET)
@XmlAccessorType(XmlAccessType.PROPERTY)
@EqualsAndHashCode
public class Bullet {
    private String BULLETCOPY;
    private String BULLETSEQ;

    @XmlElement(name = XmlDataConstants.BULLET_COPY)
    public String getBULLETCOPY() {
        return BULLETCOPY;
    }

    public void setBULLETCOPY(String BULLETCOPY) {
        this.BULLETCOPY = BULLETCOPY;
    }
    @XmlElement(name = XmlDataConstants.BULLET_SEQ)
    public String getBULLETSEQ() {
        return BULLETSEQ;
    }

    public void setBULLETSEQ(String BULLETSEQ) {
        this.BULLETSEQ = BULLETSEQ;
    }
}
