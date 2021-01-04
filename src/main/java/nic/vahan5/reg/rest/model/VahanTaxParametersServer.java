package nic.vahan5.reg.rest.model;




import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vahanTaxParametersServer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vahanTaxParametersServer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arrTaxDuePeriods" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="CALENDAR_MONTH" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="COMPLETE_QTR" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DELAY_DAYS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="DELAY_MONTHS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="FINANCIAL_QTR" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="FINANCIAL_YEAR" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="FIN_VCH_AGE" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="HOLIDAYS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="INTEREST" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="mapTagFields">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="NET_TAX_AMT" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PENALTY" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PENALTY_FROM_FIRST_TAX_DUE_DATE" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PURCHASE_DT_VCH_AGE" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="REBATE" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SURCHARGE" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TAX_AMT" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TAX_AMT_ROUND_TO" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TAX_BREAKUP_FROM_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TAX_BREAKUP_UPTO_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TAX_MONTH_DURATION" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TAX_PERIOD_SR_NO" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="VCH_AGE" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="VCH_AGE_MONTHS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vahanTaxParametersServer", propOrder = {
    "arrTaxDuePeriods",
    "calendarmonth",
    "completeqtr",
    "delaydays",
    "delaymonths",
    "financialqtr",
    "financialyear",
    "finvchage",
    "holidays",
    "interest",
    "mapTagFields",
    "nettaxamt",
    "penalty",
    "penaltyfromfirsttaxduedate",
    "purchasedtvchage",
    "rebate",
    "surcharge",
    "taxamt",
    "taxamtroundto",
    "taxbreakupfromdate",
    "taxbreakupuptodate",
    "taxmonthduration",
    "taxperiodsrno",
    "vchage",
    "vchagemonths"
})
@XmlSeeAlso({
    VahanTaxParameters.class
})
public class VahanTaxParametersServer {

    @XmlElement(nillable = true)
    protected List<Object> arrTaxDuePeriods;
    @XmlElement(name = "CALENDAR_MONTH")
    protected boolean calendarmonth;
    @XmlElement(name = "COMPLETE_QTR")
    protected boolean completeqtr;
    @XmlElement(name = "DELAY_DAYS")
    protected Integer delaydays;
    @XmlElement(name = "DELAY_MONTHS")
    protected Integer delaymonths;
    @XmlElement(name = "FINANCIAL_QTR")
    protected boolean financialqtr;
    @XmlElement(name = "FINANCIAL_YEAR")
    protected boolean financialyear;
    @XmlElement(name = "FIN_VCH_AGE")
    protected Integer finvchage;
    @XmlElement(name = "HOLIDAYS")
    protected Integer holidays;
    @XmlElement(name = "INTEREST")
    protected Double interest;
    @XmlElement(required = true)
    protected VahanTaxParametersServer.MapTagFields mapTagFields;
    @XmlElement(name = "NET_TAX_AMT")
    protected Double nettaxamt;
    @XmlElement(name = "PENALTY")
    protected Double penalty;
    @XmlElement(name = "PENALTY_FROM_FIRST_TAX_DUE_DATE")
    protected boolean penaltyfromfirsttaxduedate;
    @XmlElement(name = "PURCHASE_DT_VCH_AGE")
    protected Integer purchasedtvchage;
    @XmlElement(name = "REBATE")
    protected Double rebate;
    @XmlElement(name = "SURCHARGE")
    protected Double surcharge;
    @XmlElement(name = "TAX_AMT")
    protected Double taxamt;
    @XmlElement(name = "TAX_AMT_ROUND_TO")
    protected Double taxamtroundto;
    @XmlElement(name = "TAX_BREAKUP_FROM_DATE")
    protected String taxbreakupfromdate;
    @XmlElement(name = "TAX_BREAKUP_UPTO_DATE")
    protected String taxbreakupuptodate;
    @XmlElement(name = "TAX_MONTH_DURATION")
    protected Integer taxmonthduration;
    @XmlElement(name = "TAX_PERIOD_SR_NO")
    protected Integer taxperiodsrno;
    @XmlElement(name = "VCH_AGE")
    protected Integer vchage;
    @XmlElement(name = "VCH_AGE_MONTHS")
    protected Integer vchagemonths;

    /**
     * Gets the value of the arrTaxDuePeriods property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arrTaxDuePeriods property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArrTaxDuePeriods().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getArrTaxDuePeriods() {
        if (arrTaxDuePeriods == null) {
            arrTaxDuePeriods = new ArrayList<Object>();
        }
        return this.arrTaxDuePeriods;
    }

    /**
     * Gets the value of the calendarmonth property.
     * 
     */
    public boolean isCALENDARMONTH() {
        return calendarmonth;
    }

    /**
     * Sets the value of the calendarmonth property.
     * 
     */
    public void setCALENDARMONTH(boolean value) {
        this.calendarmonth = value;
    }

    /**
     * Gets the value of the completeqtr property.
     * 
     */
    public boolean isCOMPLETEQTR() {
        return completeqtr;
    }

    /**
     * Sets the value of the completeqtr property.
     * 
     */
    public void setCOMPLETEQTR(boolean value) {
        this.completeqtr = value;
    }

    /**
     * Gets the value of the delaydays property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDELAYDAYS() {
        return delaydays;
    }

    /**
     * Sets the value of the delaydays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDELAYDAYS(Integer value) {
        this.delaydays = value;
    }

    /**
     * Gets the value of the delaymonths property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDELAYMONTHS() {
        return delaymonths;
    }

    /**
     * Sets the value of the delaymonths property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDELAYMONTHS(Integer value) {
        this.delaymonths = value;
    }

    /**
     * Gets the value of the financialqtr property.
     * 
     */
    public boolean isFINANCIALQTR() {
        return financialqtr;
    }

    /**
     * Sets the value of the financialqtr property.
     * 
     */
    public void setFINANCIALQTR(boolean value) {
        this.financialqtr = value;
    }

    /**
     * Gets the value of the financialyear property.
     * 
     */
    public boolean isFINANCIALYEAR() {
        return financialyear;
    }

    /**
     * Sets the value of the financialyear property.
     * 
     */
    public void setFINANCIALYEAR(boolean value) {
        this.financialyear = value;
    }

    /**
     * Gets the value of the finvchage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFINVCHAGE() {
        return finvchage;
    }

    /**
     * Sets the value of the finvchage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFINVCHAGE(Integer value) {
        this.finvchage = value;
    }

    /**
     * Gets the value of the holidays property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHOLIDAYS() {
        return holidays;
    }

    /**
     * Sets the value of the holidays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHOLIDAYS(Integer value) {
        this.holidays = value;
    }

    /**
     * Gets the value of the interest property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getINTEREST() {
        return interest;
    }

    /**
     * Sets the value of the interest property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setINTEREST(Double value) {
        this.interest = value;
    }

    /**
     * Gets the value of the mapTagFields property.
     * 
     * @return
     *     possible object is
     *     {@link VahanTaxParametersServer.MapTagFields }
     *     
     */
    public VahanTaxParametersServer.MapTagFields getMapTagFields() {
        return mapTagFields;
    }

    /**
     * Sets the value of the mapTagFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link VahanTaxParametersServer.MapTagFields }
     *     
     */
    public void setMapTagFields(VahanTaxParametersServer.MapTagFields value) {
        this.mapTagFields = value;
    }

    /**
     * Gets the value of the nettaxamt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNETTAXAMT() {
        return nettaxamt;
    }

    /**
     * Sets the value of the nettaxamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNETTAXAMT(Double value) {
        this.nettaxamt = value;
    }

    /**
     * Gets the value of the penalty property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPENALTY() {
        return penalty;
    }

    /**
     * Sets the value of the penalty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPENALTY(Double value) {
        this.penalty = value;
    }

    /**
     * Gets the value of the penaltyfromfirsttaxduedate property.
     * 
     */
    public boolean isPENALTYFROMFIRSTTAXDUEDATE() {
        return penaltyfromfirsttaxduedate;
    }

    /**
     * Sets the value of the penaltyfromfirsttaxduedate property.
     * 
     */
    public void setPENALTYFROMFIRSTTAXDUEDATE(boolean value) {
        this.penaltyfromfirsttaxduedate = value;
    }

    /**
     * Gets the value of the purchasedtvchage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPURCHASEDTVCHAGE() {
        return purchasedtvchage;
    }

    /**
     * Sets the value of the purchasedtvchage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPURCHASEDTVCHAGE(Integer value) {
        this.purchasedtvchage = value;
    }

    /**
     * Gets the value of the rebate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getREBATE() {
        return rebate;
    }

    /**
     * Sets the value of the rebate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setREBATE(Double value) {
        this.rebate = value;
    }

    /**
     * Gets the value of the surcharge property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSURCHARGE() {
        return surcharge;
    }

    /**
     * Sets the value of the surcharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSURCHARGE(Double value) {
        this.surcharge = value;
    }

    /**
     * Gets the value of the taxamt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTAXAMT() {
        return taxamt;
    }

    /**
     * Sets the value of the taxamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTAXAMT(Double value) {
        this.taxamt = value;
    }

    /**
     * Gets the value of the taxamtroundto property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTAXAMTROUNDTO() {
        return taxamtroundto;
    }

    /**
     * Sets the value of the taxamtroundto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTAXAMTROUNDTO(Double value) {
        this.taxamtroundto = value;
    }

    /**
     * Gets the value of the taxbreakupfromdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTAXBREAKUPFROMDATE() {
        return taxbreakupfromdate;
    }

    /**
     * Sets the value of the taxbreakupfromdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTAXBREAKUPFROMDATE(String value) {
        this.taxbreakupfromdate = value;
    }

    /**
     * Gets the value of the taxbreakupuptodate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTAXBREAKUPUPTODATE() {
        return taxbreakupuptodate;
    }

    /**
     * Sets the value of the taxbreakupuptodate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTAXBREAKUPUPTODATE(String value) {
        this.taxbreakupuptodate = value;
    }

    /**
     * Gets the value of the taxmonthduration property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTAXMONTHDURATION() {
        return taxmonthduration;
    }

    /**
     * Sets the value of the taxmonthduration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTAXMONTHDURATION(Integer value) {
        this.taxmonthduration = value;
    }

    /**
     * Gets the value of the taxperiodsrno property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTAXPERIODSRNO() {
        return taxperiodsrno;
    }

    /**
     * Sets the value of the taxperiodsrno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTAXPERIODSRNO(Integer value) {
        this.taxperiodsrno = value;
    }

    /**
     * Gets the value of the vchage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVCHAGE() {
        return vchage;
    }

    /**
     * Sets the value of the vchage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVCHAGE(Integer value) {
        this.vchage = value;
    }

    /**
     * Gets the value of the vchagemonths property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVCHAGEMONTHS() {
        return vchagemonths;
    }

    /**
     * Sets the value of the vchagemonths property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVCHAGEMONTHS(Integer value) {
        this.vchagemonths = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class MapTagFields {

        protected List<VahanTaxParametersServer.MapTagFields.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link VahanTaxParametersServer.MapTagFields.Entry }
         * 
         * 
         */
        public List<VahanTaxParametersServer.MapTagFields.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<VahanTaxParametersServer.MapTagFields.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected Object key;
            protected Object value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setKey(Object value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setValue(Object value) {
                this.value = value;
            }

        }

    }

}
