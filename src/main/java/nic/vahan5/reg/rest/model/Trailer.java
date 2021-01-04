package nic.vahan5.reg.rest.model;




import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for trailer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="trailer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TRAILER_RLW" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TRAILER_ULW" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trailer", propOrder = {
    "trailerrlw",
    "trailerulw"
})
public class Trailer {

    @XmlElement(name = "TRAILER_RLW")
    protected Integer trailerrlw;
    @XmlElement(name = "TRAILER_ULW")
    protected Integer trailerulw;

    /**
     * Gets the value of the trailerrlw property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTRAILERRLW() {
        return trailerrlw;
    }

    /**
     * Sets the value of the trailerrlw property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTRAILERRLW(Integer value) {
        this.trailerrlw = value;
    }

    /**
     * Gets the value of the trailerulw property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTRAILERULW() {
        return trailerulw;
    }

    /**
     * Sets the value of the trailerulw property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTRAILERULW(Integer value) {
        this.trailerulw = value;
    }

}
