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
@XmlRootElement(name= XmlDataConstants.SUB_HEADER)
@XmlAccessorType(XmlAccessType.PROPERTY)
@EqualsAndHashCode
public class SubHeader {

        private String value;

        @XmlElement
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

