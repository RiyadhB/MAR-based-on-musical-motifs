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
 * <p>Java class for symbol-size.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="symbol-size">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="full"/>
 *     &lt;enumeration value="cue"/>
 *     &lt;enumeration value="large"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "symbol-size")
@XmlEnum
public enum SymbolSize {

    @XmlEnumValue("full")
    FULL("full"),
    @XmlEnumValue("cue")
    CUE("cue"),
    @XmlEnumValue("large")
    LARGE("large");
    private final java.lang.String value;

    SymbolSize(java.lang.String v) {
        value = v;
    }

    public java.lang.String value() {
        return value;
    }

    public static SymbolSize fromValue(java.lang.String v) {
        for (SymbolSize c: SymbolSize.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
