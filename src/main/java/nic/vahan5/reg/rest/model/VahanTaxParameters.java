package nic.vahan5.reg.rest.model;




import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vahanTaxParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vahanTaxParameters">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.tax.vahan.nic/}vahanTaxParametersServer">
 *       &lt;sequence>
 *         &lt;element name="AC_FITTED" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AUDIO_FITTED" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CC" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="DISTANCE_RUN_IN_QTR" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="DOMAIN_CD" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="FLOOR_AREA" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="FUEL" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="GCW" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="HP" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="IDV" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LD_WT" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LOGIN_OFF_CD" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NEW_VCH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NORMS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NO_OF_ATTACHED_TRAILERS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NO_OF_AXLE" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NO_OF_REGIONS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NO_OF_ROUTES" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NO_OF_TRIPS_PER_DAY" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NO_OF_TYRES" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="OFF_CD" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="OTHER_CRITERIA" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="OWNER_CD" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="OWNER_CD_DEPT" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PAYMENT_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PERMIT_SUB_CATG" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PERMIT_TYPE" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PM_SALE_AMT" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PURCHASE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PUR_CD" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="REGN_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="REGN_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ROUTE_CLASS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ROUTE_LENGTH" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SALE_AMT" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SEAT_CAP" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SERVICE_TYPE" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SLEEPAR_CAP" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="STAND_CAP" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="STATE_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TAX_CEASE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TAX_DUE_FROM_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TAX_MODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TAX_MODE_NO_ADV" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TRAILERS" type="{http://service.tax.vahan.nic/}trailer" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TRANSACTION_PUR_CD" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="UNLD_WT" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="VCH_CATG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VCH_IMPORTED" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VCH_TYPE" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="VEH_PURCHASE_AS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VH_CLASS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="VIDEO_FITTED" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vahanTaxParameters", propOrder = {
    "acfitted",
    "audiofitted",
    "cc",
    "distanceruninqtr",
    "domaincd",
    "floorarea",
    "fuel",
    "gcw",
    "hp",
    "idv",
    "ldwt",
    "loginoffcd",
    "newvch",
    "norms",
    "noofattachedtrailers",
    "noofaxle",
    "noofregions",
    "noofroutes",
    "nooftripsperday",
    "nooftyres",
    "offcd",
    "othercriteria",
    "ownercd",
    "ownercddept",
    "paymentdate",
    "permitsubcatg",
    "permittype",
    "pmsaleamt",
    "purchasedate",
    "purcd",
    "regndate",
    "regntype",
    "routeclass",
    "routelength",
    "saleamt",
    "seatcap",
    "servicetype",
    "sleeparcap",
    "standcap",
    "statecd",
    "taxceasedate",
    "taxduefromdate",
    "taxmode",
    "taxmodenoadv",
    "trailers",
    "transactionpurcd",
    "unldwt",
    "vchcatg",
    "vchimported",
    "vchtype",
    "vehpurchaseas",
    "vhclass",
    "videofitted"
})
public class VahanTaxParameters
    extends VahanTaxParametersServer
{

    @XmlElement(name = "AC_FITTED")
    protected String acfitted;
    @XmlElement(name = "AUDIO_FITTED")
    protected String audiofitted;
    @XmlElement(name = "CC")
    protected Float cc;
    @XmlElement(name = "DISTANCE_RUN_IN_QTR")
    protected Integer distanceruninqtr;
    @XmlElement(name = "DOMAIN_CD")
    protected Integer domaincd;
    @XmlElement(name = "FLOOR_AREA")
    protected Float floorarea;
    @XmlElement(name = "FUEL")
    protected Integer fuel;
    @XmlElement(name = "GCW")
    protected Integer gcw;
    @XmlElement(name = "HP")
    protected Float hp;
    @XmlElement(name = "IDV")
    protected Integer idv;
    @XmlElement(name = "LD_WT")
    protected Integer ldwt;
    @XmlElement(name = "LOGIN_OFF_CD")
    protected Integer loginoffcd;
    @XmlElement(name = "NEW_VCH")
    protected String newvch;
    @XmlElement(name = "NORMS")
    protected Integer norms;
    @XmlElement(name = "NO_OF_ATTACHED_TRAILERS")
    protected Integer noofattachedtrailers;
    @XmlElement(name = "NO_OF_AXLE")
    protected Integer noofaxle;
    @XmlElement(name = "NO_OF_REGIONS")
    protected Integer noofregions;
    @XmlElement(name = "NO_OF_ROUTES")
    protected Integer noofroutes;
    @XmlElement(name = "NO_OF_TRIPS_PER_DAY")
    protected Integer nooftripsperday;
    @XmlElement(name = "NO_OF_TYRES")
    protected Integer nooftyres;
    @XmlElement(name = "OFF_CD")
    protected Integer offcd;
    @XmlElement(name = "OTHER_CRITERIA")
    protected Integer othercriteria;
    @XmlElement(name = "OWNER_CD")
    protected Integer ownercd;
    @XmlElement(name = "OWNER_CD_DEPT")
    protected Integer ownercddept;
    @XmlElement(name = "PAYMENT_DATE")
    protected String paymentdate;
    @XmlElement(name = "PERMIT_SUB_CATG")
    protected Integer permitsubcatg;
    @XmlElement(name = "PERMIT_TYPE")
    protected Integer permittype;
    @XmlElement(name = "PM_SALE_AMT")
    protected Integer pmsaleamt;
    @XmlElement(name = "PURCHASE_DATE")
    protected String purchasedate;
    @XmlElement(name = "PUR_CD")
    protected Integer purcd;
    @XmlElement(name = "REGN_DATE")
    protected String regndate;
    @XmlElement(name = "REGN_TYPE")
    protected String regntype;
    @XmlElement(name = "ROUTE_CLASS")
    protected Integer routeclass;
    @XmlElement(name = "ROUTE_LENGTH")
    protected Double routelength;
    @XmlElement(name = "SALE_AMT")
    protected Integer saleamt;
    @XmlElement(name = "SEAT_CAP")
    protected Integer seatcap;
    @XmlElement(name = "SERVICE_TYPE")
    protected Integer servicetype;
    @XmlElement(name = "SLEEPAR_CAP")
    protected Integer sleeparcap;
    @XmlElement(name = "STAND_CAP")
    protected Integer standcap;
    @XmlElement(name = "STATE_CD")
    protected String statecd;
    @XmlElement(name = "TAX_CEASE_DATE")
    protected String taxceasedate;
    @XmlElement(name = "TAX_DUE_FROM_DATE")
    protected String taxduefromdate;
    @XmlElement(name = "TAX_MODE")
    protected String taxmode;
    @XmlElement(name = "TAX_MODE_NO_ADV")
    protected Integer taxmodenoadv;
    @XmlElement(name = "TRAILERS", nillable = true)
    protected List<Trailer> trailers;
    @XmlElement(name = "TRANSACTION_PUR_CD")
    protected Integer transactionpurcd;
    @XmlElement(name = "UNLD_WT")
    protected Integer unldwt;
    @XmlElement(name = "VCH_CATG")
    protected String vchcatg;
    @XmlElement(name = "VCH_IMPORTED")
    protected String vchimported;
    @XmlElement(name = "VCH_TYPE")
    protected Integer vchtype;
    @XmlElement(name = "VEH_PURCHASE_AS")
    protected String vehpurchaseas;
    @XmlElement(name = "VH_CLASS")
    protected Integer vhclass;
    @XmlElement(name = "VIDEO_FITTED")
    protected String videofitted;

    /**
     * Gets the value of the acfitted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACFITTED() {
        return acfitted;
    }

    /**
     * Sets the value of the acfitted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACFITTED(String value) {
        this.acfitted = value;
    }

    /**
     * Gets the value of the audiofitted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAUDIOFITTED() {
        return audiofitted;
    }

    /**
     * Sets the value of the audiofitted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAUDIOFITTED(String value) {
        this.audiofitted = value;
    }

    /**
     * Gets the value of the cc property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getCC() {
        return cc;
    }

    /**
     * Sets the value of the cc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setCC(Float value) {
        this.cc = value;
    }

    /**
     * Gets the value of the distanceruninqtr property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDISTANCERUNINQTR() {
        return distanceruninqtr;
    }

    /**
     * Sets the value of the distanceruninqtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDISTANCERUNINQTR(Integer value) {
        this.distanceruninqtr = value;
    }

    /**
     * Gets the value of the domaincd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDOMAINCD() {
        return domaincd;
    }

    /**
     * Sets the value of the domaincd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDOMAINCD(Integer value) {
        this.domaincd = value;
    }

    /**
     * Gets the value of the floorarea property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getFLOORAREA() {
        return floorarea;
    }

    /**
     * Sets the value of the floorarea property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setFLOORAREA(Float value) {
        this.floorarea = value;
    }

    /**
     * Gets the value of the fuel property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFUEL() {
        return fuel;
    }

    /**
     * Sets the value of the fuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFUEL(Integer value) {
        this.fuel = value;
    }

    /**
     * Gets the value of the gcw property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGCW() {
        return gcw;
    }

    /**
     * Sets the value of the gcw property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGCW(Integer value) {
        this.gcw = value;
    }

    /**
     * Gets the value of the hp property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getHP() {
        return hp;
    }

    /**
     * Sets the value of the hp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setHP(Float value) {
        this.hp = value;
    }

    /**
     * Gets the value of the idv property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIDV() {
        return idv;
    }

    /**
     * Sets the value of the idv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIDV(Integer value) {
        this.idv = value;
    }

    /**
     * Gets the value of the ldwt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLDWT() {
        return ldwt;
    }

    /**
     * Sets the value of the ldwt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLDWT(Integer value) {
        this.ldwt = value;
    }

    /**
     * Gets the value of the loginoffcd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLOGINOFFCD() {
        return loginoffcd;
    }

    /**
     * Sets the value of the loginoffcd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLOGINOFFCD(Integer value) {
        this.loginoffcd = value;
    }

    /**
     * Gets the value of the newvch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNEWVCH() {
        return newvch;
    }

    /**
     * Sets the value of the newvch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNEWVCH(String value) {
        this.newvch = value;
    }

    /**
     * Gets the value of the norms property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNORMS() {
        return norms;
    }

    /**
     * Sets the value of the norms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNORMS(Integer value) {
        this.norms = value;
    }

    /**
     * Gets the value of the noofattachedtrailers property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNOOFATTACHEDTRAILERS() {
        return noofattachedtrailers;
    }

    /**
     * Sets the value of the noofattachedtrailers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNOOFATTACHEDTRAILERS(Integer value) {
        this.noofattachedtrailers = value;
    }

    /**
     * Gets the value of the noofaxle property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNOOFAXLE() {
        return noofaxle;
    }

    /**
     * Sets the value of the noofaxle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNOOFAXLE(Integer value) {
        this.noofaxle = value;
    }

    /**
     * Gets the value of the noofregions property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNOOFREGIONS() {
        return noofregions;
    }

    /**
     * Sets the value of the noofregions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNOOFREGIONS(Integer value) {
        this.noofregions = value;
    }

    /**
     * Gets the value of the noofroutes property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNOOFROUTES() {
        return noofroutes;
    }

    /**
     * Sets the value of the noofroutes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNOOFROUTES(Integer value) {
        this.noofroutes = value;
    }

    /**
     * Gets the value of the nooftripsperday property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNOOFTRIPSPERDAY() {
        return nooftripsperday;
    }

    /**
     * Sets the value of the nooftripsperday property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNOOFTRIPSPERDAY(Integer value) {
        this.nooftripsperday = value;
    }

    /**
     * Gets the value of the nooftyres property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNOOFTYRES() {
        return nooftyres;
    }

    /**
     * Sets the value of the nooftyres property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNOOFTYRES(Integer value) {
        this.nooftyres = value;
    }

    /**
     * Gets the value of the offcd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOFFCD() {
        return offcd;
    }

    /**
     * Sets the value of the offcd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOFFCD(Integer value) {
        this.offcd = value;
    }

    /**
     * Gets the value of the othercriteria property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOTHERCRITERIA() {
        return othercriteria;
    }

    /**
     * Sets the value of the othercriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOTHERCRITERIA(Integer value) {
        this.othercriteria = value;
    }

    /**
     * Gets the value of the ownercd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOWNERCD() {
        return ownercd;
    }

    /**
     * Sets the value of the ownercd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOWNERCD(Integer value) {
        this.ownercd = value;
    }

    /**
     * Gets the value of the ownercddept property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOWNERCDDEPT() {
        return ownercddept;
    }

    /**
     * Sets the value of the ownercddept property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOWNERCDDEPT(Integer value) {
        this.ownercddept = value;
    }

    /**
     * Gets the value of the paymentdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAYMENTDATE() {
        return paymentdate;
    }

    /**
     * Sets the value of the paymentdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAYMENTDATE(String value) {
        this.paymentdate = value;
    }

    /**
     * Gets the value of the permitsubcatg property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPERMITSUBCATG() {
        return permitsubcatg;
    }

    /**
     * Sets the value of the permitsubcatg property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPERMITSUBCATG(Integer value) {
        this.permitsubcatg = value;
    }

    /**
     * Gets the value of the permittype property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPERMITTYPE() {
        return permittype;
    }

    /**
     * Sets the value of the permittype property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPERMITTYPE(Integer value) {
        this.permittype = value;
    }

    /**
     * Gets the value of the pmsaleamt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPMSALEAMT() {
        return pmsaleamt;
    }

    /**
     * Sets the value of the pmsaleamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPMSALEAMT(Integer value) {
        this.pmsaleamt = value;
    }

    /**
     * Gets the value of the purchasedate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPURCHASEDATE() {
        return purchasedate;
    }

    /**
     * Sets the value of the purchasedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPURCHASEDATE(String value) {
        this.purchasedate = value;
    }

    /**
     * Gets the value of the purcd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPURCD() {
        return purcd;
    }

    /**
     * Sets the value of the purcd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPURCD(Integer value) {
        this.purcd = value;
    }

    /**
     * Gets the value of the regndate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGNDATE() {
        return regndate;
    }

    /**
     * Sets the value of the regndate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGNDATE(String value) {
        this.regndate = value;
    }

    /**
     * Gets the value of the regntype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGNTYPE() {
        return regntype;
    }

    /**
     * Sets the value of the regntype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGNTYPE(String value) {
        this.regntype = value;
    }

    /**
     * Gets the value of the routeclass property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getROUTECLASS() {
        return routeclass;
    }

    /**
     * Sets the value of the routeclass property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setROUTECLASS(Integer value) {
        this.routeclass = value;
    }

    /**
     * Gets the value of the routelength property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getROUTELENGTH() {
        return routelength;
    }

    /**
     * Sets the value of the routelength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setROUTELENGTH(Double value) {
        this.routelength = value;
    }

    /**
     * Gets the value of the saleamt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSALEAMT() {
        return saleamt;
    }

    /**
     * Sets the value of the saleamt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSALEAMT(Integer value) {
        this.saleamt = value;
    }

    /**
     * Gets the value of the seatcap property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSEATCAP() {
        return seatcap;
    }

    /**
     * Sets the value of the seatcap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSEATCAP(Integer value) {
        this.seatcap = value;
    }

    /**
     * Gets the value of the servicetype property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSERVICETYPE() {
        return servicetype;
    }

    /**
     * Sets the value of the servicetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSERVICETYPE(Integer value) {
        this.servicetype = value;
    }

    /**
     * Gets the value of the sleeparcap property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSLEEPARCAP() {
        return sleeparcap;
    }

    /**
     * Sets the value of the sleeparcap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSLEEPARCAP(Integer value) {
        this.sleeparcap = value;
    }

    /**
     * Gets the value of the standcap property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSTANDCAP() {
        return standcap;
    }

    /**
     * Sets the value of the standcap property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSTANDCAP(Integer value) {
        this.standcap = value;
    }

    /**
     * Gets the value of the statecd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATECD() {
        return statecd;
    }

    /**
     * Sets the value of the statecd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATECD(String value) {
        this.statecd = value;
    }

    /**
     * Gets the value of the taxceasedate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTAXCEASEDATE() {
        return taxceasedate;
    }

    /**
     * Sets the value of the taxceasedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTAXCEASEDATE(String value) {
        this.taxceasedate = value;
    }

    /**
     * Gets the value of the taxduefromdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTAXDUEFROMDATE() {
        return taxduefromdate;
    }

    /**
     * Sets the value of the taxduefromdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTAXDUEFROMDATE(String value) {
        this.taxduefromdate = value;
    }

    /**
     * Gets the value of the taxmode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTAXMODE() {
        return taxmode;
    }

    /**
     * Sets the value of the taxmode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTAXMODE(String value) {
        this.taxmode = value;
    }

    /**
     * Gets the value of the taxmodenoadv property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTAXMODENOADV() {
        return taxmodenoadv;
    }

    /**
     * Sets the value of the taxmodenoadv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTAXMODENOADV(Integer value) {
        this.taxmodenoadv = value;
    }

    /**
     * Gets the value of the trailers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trailers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTRAILERS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Trailer }
     * 
     * 
     */
    public List<Trailer> getTRAILERS() {
        if (trailers == null) {
            trailers = new ArrayList<Trailer>();
        }
        return this.trailers;
    }

    /**
     * Gets the value of the transactionpurcd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTRANSACTIONPURCD() {
        return transactionpurcd;
    }

    /**
     * Sets the value of the transactionpurcd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTRANSACTIONPURCD(Integer value) {
        this.transactionpurcd = value;
    }

    /**
     * Gets the value of the unldwt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUNLDWT() {
        return unldwt;
    }

    /**
     * Sets the value of the unldwt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUNLDWT(Integer value) {
        this.unldwt = value;
    }

    /**
     * Gets the value of the vchcatg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVCHCATG() {
        return vchcatg;
    }

    /**
     * Sets the value of the vchcatg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVCHCATG(String value) {
        this.vchcatg = value;
    }

    /**
     * Gets the value of the vchimported property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVCHIMPORTED() {
        return vchimported;
    }

    /**
     * Sets the value of the vchimported property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVCHIMPORTED(String value) {
        this.vchimported = value;
    }

    /**
     * Gets the value of the vchtype property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVCHTYPE() {
        return vchtype;
    }

    /**
     * Sets the value of the vchtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVCHTYPE(Integer value) {
        this.vchtype = value;
    }

    /**
     * Gets the value of the vehpurchaseas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVEHPURCHASEAS() {
        return vehpurchaseas;
    }

    /**
     * Sets the value of the vehpurchaseas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVEHPURCHASEAS(String value) {
        this.vehpurchaseas = value;
    }

    /**
     * Gets the value of the vhclass property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVHCLASS() {
        return vhclass;
    }

    /**
     * Sets the value of the vhclass property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVHCLASS(Integer value) {
        this.vhclass = value;
    }

    /**
     * Gets the value of the videofitted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVIDEOFITTED() {
        return videofitted;
    }

    /**
     * Sets the value of the videofitted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVIDEOFITTED(String value) {
        this.videofitted = value;
    }

}
