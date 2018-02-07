//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.01.20 at 04:13:49 PM CET 
//


package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tip-direction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tip-direction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="up"/>
 *     &lt;enumeration value="down"/>
 *     &lt;enumeration value="left"/>
 *     &lt;enumeration value="right"/>
 *     &lt;enumeration value="northwest"/>
 *     &lt;enumeration value="northeast"/>
 *     &lt;enumeration value="southeast"/>
 *     &lt;enumeration value="southwest"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tip-direction")
@XmlEnum
public enum TipDirection {

    @XmlEnumValue("up")
    UP("up"),
    @XmlEnumValue("down")
    DOWN("down"),
    @XmlEnumValue("left")
    LEFT("left"),
    @XmlEnumValue("right")
    RIGHT("right"),
    @XmlEnumValue("northwest")
    NORTHWEST("northwest"),
    @XmlEnumValue("northeast")
    NORTHEAST("northeast"),
    @XmlEnumValue("southeast")
    SOUTHEAST("southeast"),
    @XmlEnumValue("southwest")
    SOUTHWEST("southwest");
    private final java.lang.String value;

    TipDirection(java.lang.String v) {
        value = v;
    }

    public java.lang.String value() {
        return value;
    }

    public static TipDirection fromValue(java.lang.String v) {
        for (TipDirection c: TipDirection.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}