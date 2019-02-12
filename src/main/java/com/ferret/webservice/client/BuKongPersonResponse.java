
package com.ferret.webservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="BuKongPersonResult" type="{http://tempuri.org/}BuKongResult" minOccurs="0"/&gt;
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
    "buKongPersonResult"
})
@XmlRootElement(name = "BuKongPersonResponse")
public class BuKongPersonResponse {

    @XmlElement(name = "BuKongPersonResult")
    protected BuKongResult buKongPersonResult;

    /**
     * 获取buKongPersonResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BuKongResult }
     *     
     */
    public BuKongResult getBuKongPersonResult() {
        return buKongPersonResult;
    }

    /**
     * 设置buKongPersonResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BuKongResult }
     *     
     */
    public void setBuKongPersonResult(BuKongResult value) {
        this.buKongPersonResult = value;
    }

}
