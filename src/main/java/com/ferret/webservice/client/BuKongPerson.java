
package com.ferret.webservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="imgData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="extImag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idnum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="alarmMethod" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="alarmAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "imgData",
    "extImag",
    "idnum",
    "name",
    "desc",
    "alarmMethod",
    "alarmAddr"
})
@XmlRootElement(name = "BuKongPerson")
public class BuKongPerson {

    protected byte[] imgData;
    protected String extImag;
    protected String idnum;
    protected String name;
    protected String desc;
    protected int alarmMethod;
    protected String alarmAddr;

    /**
     * 获取imgData属性的值。
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImgData() {
        return imgData;
    }

    /**
     * 设置imgData属性的值。
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImgData(byte[] value) {
        this.imgData = value;
    }

    /**
     * 获取extImag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtImag() {
        return extImag;
    }

    /**
     * 设置extImag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtImag(String value) {
        this.extImag = value;
    }

    /**
     * 获取idnum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdnum() {
        return idnum;
    }

    /**
     * 设置idnum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdnum(String value) {
        this.idnum = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取desc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置desc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesc(String value) {
        this.desc = value;
    }

    /**
     * 获取alarmMethod属性的值。
     * 
     */
    public int getAlarmMethod() {
        return alarmMethod;
    }

    /**
     * 设置alarmMethod属性的值。
     * 
     */
    public void setAlarmMethod(int value) {
        this.alarmMethod = value;
    }

    /**
     * 获取alarmAddr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlarmAddr() {
        return alarmAddr;
    }

    /**
     * 设置alarmAddr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlarmAddr(String value) {
        this.alarmAddr = value;
    }

}
