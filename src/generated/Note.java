//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.01.20 at 04:13:49 PM CET 
//


package generated;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Notes are the most common type of MusicXML data. The MusicXML format keeps the MuseData distinction between elements used for sound information and elements used for notation information (e.g., tie is used for sound, tied for notation). Thus grace notes do not have a duration element. Cue notes have a duration element, as do forward elements, but no tie elements. Having these two types of information available can make interchange considerably easier, as some programs handle one type of information much more readily than the other. 
 * 	
 * The dynamics and end-dynamics attributes correspond to MIDI 1.0's Note On and Note Off velocities, respectively. They are expressed in terms of percentages of the default forte value (90 for MIDI 1.0). The attack and release attributes are used to alter the starting and stopping time of the note from when it would otherwise occur based on the flow of durations - information that is specific to a performance. They are expressed in terms of divisions, either positive or negative. A note that starts a tie should not have a release attribute, and a note that stops a tie should not have an attack attribute. If a note is played only particular times through a repeat, the time-only attribute shows which times to play the note. The pizzicato attribute is used when just this note is sounded pizzicato, vs. the pizzicato element which changes overall playback between pizzicato and arco.
 * 
 * <p>Java class for note complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="note">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element name="grace" type="{}grace"/>
 *             &lt;group ref="{}full-note"/>
 *             &lt;element name="tie" type="{}tie" maxOccurs="2" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element name="cue" type="{}empty"/>
 *             &lt;group ref="{}full-note"/>
 *             &lt;group ref="{}duration"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;group ref="{}full-note"/>
 *             &lt;group ref="{}duration"/>
 *             &lt;element name="tie" type="{}tie" maxOccurs="2" minOccurs="0"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element name="instrument" type="{}instrument" minOccurs="0"/>
 *         &lt;group ref="{}editorial-voice"/>
 *         &lt;element name="type" type="{}note-type" minOccurs="0"/>
 *         &lt;element name="dot" type="{}empty-placement" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="accidental" type="{}accidental" minOccurs="0"/>
 *         &lt;element name="time-modification" type="{}time-modification" minOccurs="0"/>
 *         &lt;element name="stem" type="{}stem" minOccurs="0"/>
 *         &lt;element name="notehead" type="{}notehead" minOccurs="0"/>
 *         &lt;element name="notehead-text" type="{}notehead-text" minOccurs="0"/>
 *         &lt;group ref="{}staff" minOccurs="0"/>
 *         &lt;element name="beam" type="{}beam" maxOccurs="8" minOccurs="0"/>
 *         &lt;element name="notations" type="{}notations" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="lyric" type="{}lyric" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="play" type="{}play" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{}x-position"/>
 *       &lt;attGroup ref="{}font"/>
 *       &lt;attGroup ref="{}printout"/>
 *       &lt;attGroup ref="{}color"/>
 *       &lt;attribute name="dynamics" type="{}non-negative-decimal" />
 *       &lt;attribute name="end-dynamics" type="{}non-negative-decimal" />
 *       &lt;attribute name="attack" type="{}divisions" />
 *       &lt;attribute name="release" type="{}divisions" />
 *       &lt;attribute name="time-only" type="{}time-only" />
 *       &lt;attribute name="pizzicato" type="{}yes-no" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "note", propOrder = {
    "content"
})
public class Note {

    @XmlElementRefs({
        @XmlElementRef(name = "unpitched", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "play", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "voice", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "rest", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "instrument", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "lyric", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "cue", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "notehead-text", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "dot", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "stem", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "pitch", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "grace", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "accidental", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "beam", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "duration", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "notehead", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "tie", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "level", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "chord", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "notations", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "footnote", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "time-modification", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "staff", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "type", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> content;
    @XmlAttribute(name = "dynamics")
    protected BigDecimal dynamics;
    @XmlAttribute(name = "end-dynamics")
    protected BigDecimal endDynamics;
    @XmlAttribute(name = "attack")
    protected BigDecimal attack;
    @XmlAttribute(name = "release")
    protected BigDecimal release;
    @XmlAttribute(name = "time-only")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected java.lang.String timeOnly;
    @XmlAttribute(name = "pizzicato")
    protected YesNo pizzicato;
    @XmlAttribute(name = "default-x")
    protected BigDecimal defaultX;
    @XmlAttribute(name = "default-y")
    protected BigDecimal defaultY;
    @XmlAttribute(name = "relative-x")
    protected BigDecimal relativeX;
    @XmlAttribute(name = "relative-y")
    protected BigDecimal relativeY;
    @XmlAttribute(name = "font-family")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected java.lang.String fontFamily;
    @XmlAttribute(name = "font-style")
    protected FontStyle fontStyle;
    @XmlAttribute(name = "font-size")
    protected java.lang.String fontSize;
    @XmlAttribute(name = "font-weight")
    protected FontWeight fontWeight;
    @XmlAttribute(name = "print-dot")
    protected YesNo printDot;
    @XmlAttribute(name = "print-lyric")
    protected YesNo printLyric;
    @XmlAttribute(name = "print-object")
    protected YesNo printObject;
    @XmlAttribute(name = "print-spacing")
    protected YesNo printSpacing;
    @XmlAttribute(name = "color")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected java.lang.String color;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "Chord" is used by two different parts of a schema. See: 
     * line 5290 of file:/C:/Users/ng152/Documents/NetBeansProjects/FirstOpenCVProg/musicxml30/musicxml.xsd
     * line 5290 of file:/C:/Users/ng152/Documents/NetBeansProjects/FirstOpenCVProg/musicxml30/musicxml.xsd
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Unpitched }{@code >}
     * {@link JAXBElement }{@code <}{@link Play }{@code >}
     * {@link JAXBElement }{@code <}{@link java.lang.String }{@code >}
     * {@link JAXBElement }{@code <}{@link Rest }{@code >}
     * {@link JAXBElement }{@code <}{@link Instrument }{@code >}
     * {@link JAXBElement }{@code <}{@link Lyric }{@code >}
     * {@link JAXBElement }{@code <}{@link Empty }{@code >}
     * {@link JAXBElement }{@code <}{@link NoteheadText }{@code >}
     * {@link JAXBElement }{@code <}{@link EmptyPlacement }{@code >}
     * {@link JAXBElement }{@code <}{@link Stem }{@code >}
     * {@link JAXBElement }{@code <}{@link Pitch }{@code >}
     * {@link JAXBElement }{@code <}{@link Grace }{@code >}
     * {@link JAXBElement }{@code <}{@link Accidental }{@code >}
     * {@link JAXBElement }{@code <}{@link Beam }{@code >}
     * {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * {@link JAXBElement }{@code <}{@link Notehead }{@code >}
     * {@link JAXBElement }{@code <}{@link Tie }{@code >}
     * {@link JAXBElement }{@code <}{@link Level }{@code >}
     * {@link JAXBElement }{@code <}{@link Empty }{@code >}
     * {@link JAXBElement }{@code <}{@link Notations }{@code >}
     * {@link JAXBElement }{@code <}{@link FormattedText }{@code >}
     * {@link JAXBElement }{@code <}{@link TimeModification }{@code >}
     * {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * {@link JAXBElement }{@code <}{@link NoteType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getContent() {
        if (content == null) {
            content = new ArrayList<JAXBElement<?>>();
        }
        return this.content;
    }

    /**
     * Gets the value of the dynamics property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDynamics() {
        return dynamics;
    }

    /**
     * Sets the value of the dynamics property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDynamics(BigDecimal value) {
        this.dynamics = value;
    }

    /**
     * Gets the value of the endDynamics property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEndDynamics() {
        return endDynamics;
    }

    /**
     * Sets the value of the endDynamics property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEndDynamics(BigDecimal value) {
        this.endDynamics = value;
    }

    /**
     * Gets the value of the attack property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAttack() {
        return attack;
    }

    /**
     * Sets the value of the attack property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAttack(BigDecimal value) {
        this.attack = value;
    }

    /**
     * Gets the value of the release property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRelease() {
        return release;
    }

    /**
     * Sets the value of the release property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRelease(BigDecimal value) {
        this.release = value;
    }

    /**
     * Gets the value of the timeOnly property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getTimeOnly() {
        return timeOnly;
    }

    /**
     * Sets the value of the timeOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setTimeOnly(java.lang.String value) {
        this.timeOnly = value;
    }

    /**
     * Gets the value of the pizzicato property.
     * 
     * @return
     *     possible object is
     *     {@link YesNo }
     *     
     */
    public YesNo getPizzicato() {
        return pizzicato;
    }

    /**
     * Sets the value of the pizzicato property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesNo }
     *     
     */
    public void setPizzicato(YesNo value) {
        this.pizzicato = value;
    }

    /**
     * Gets the value of the defaultX property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDefaultX() {
        return defaultX;
    }

    /**
     * Sets the value of the defaultX property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDefaultX(BigDecimal value) {
        this.defaultX = value;
    }

    /**
     * Gets the value of the defaultY property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDefaultY() {
        return defaultY;
    }

    /**
     * Sets the value of the defaultY property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDefaultY(BigDecimal value) {
        this.defaultY = value;
    }

    /**
     * Gets the value of the relativeX property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRelativeX() {
        return relativeX;
    }

    /**
     * Sets the value of the relativeX property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRelativeX(BigDecimal value) {
        this.relativeX = value;
    }

    /**
     * Gets the value of the relativeY property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRelativeY() {
        return relativeY;
    }

    /**
     * Sets the value of the relativeY property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRelativeY(BigDecimal value) {
        this.relativeY = value;
    }

    /**
     * Gets the value of the fontFamily property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getFontFamily() {
        return fontFamily;
    }

    /**
     * Sets the value of the fontFamily property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setFontFamily(java.lang.String value) {
        this.fontFamily = value;
    }

    /**
     * Gets the value of the fontStyle property.
     * 
     * @return
     *     possible object is
     *     {@link FontStyle }
     *     
     */
    public FontStyle getFontStyle() {
        return fontStyle;
    }

    /**
     * Sets the value of the fontStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link FontStyle }
     *     
     */
    public void setFontStyle(FontStyle value) {
        this.fontStyle = value;
    }

    /**
     * Gets the value of the fontSize property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getFontSize() {
        return fontSize;
    }

    /**
     * Sets the value of the fontSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setFontSize(java.lang.String value) {
        this.fontSize = value;
    }

    /**
     * Gets the value of the fontWeight property.
     * 
     * @return
     *     possible object is
     *     {@link FontWeight }
     *     
     */
    public FontWeight getFontWeight() {
        return fontWeight;
    }

    /**
     * Sets the value of the fontWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link FontWeight }
     *     
     */
    public void setFontWeight(FontWeight value) {
        this.fontWeight = value;
    }

    /**
     * Gets the value of the printDot property.
     * 
     * @return
     *     possible object is
     *     {@link YesNo }
     *     
     */
    public YesNo getPrintDot() {
        return printDot;
    }

    /**
     * Sets the value of the printDot property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesNo }
     *     
     */
    public void setPrintDot(YesNo value) {
        this.printDot = value;
    }

    /**
     * Gets the value of the printLyric property.
     * 
     * @return
     *     possible object is
     *     {@link YesNo }
     *     
     */
    public YesNo getPrintLyric() {
        return printLyric;
    }

    /**
     * Sets the value of the printLyric property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesNo }
     *     
     */
    public void setPrintLyric(YesNo value) {
        this.printLyric = value;
    }

    /**
     * Gets the value of the printObject property.
     * 
     * @return
     *     possible object is
     *     {@link YesNo }
     *     
     */
    public YesNo getPrintObject() {
        return printObject;
    }

    /**
     * Sets the value of the printObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesNo }
     *     
     */
    public void setPrintObject(YesNo value) {
        this.printObject = value;
    }

    /**
     * Gets the value of the printSpacing property.
     * 
     * @return
     *     possible object is
     *     {@link YesNo }
     *     
     */
    public YesNo getPrintSpacing() {
        return printSpacing;
    }

    /**
     * Sets the value of the printSpacing property.
     * 
     * @param value
     *     allowed object is
     *     {@link YesNo }
     *     
     */
    public void setPrintSpacing(YesNo value) {
        this.printSpacing = value;
    }

    /**
     * Gets the value of the color property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setColor(java.lang.String value) {
        this.color = value;
    }

}
