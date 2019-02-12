
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
 *         &lt;element name="QueryHistoryFaceByImageResult" type="{http://tempuri.org/}ArrayOfQueryResult" minOccurs="0"/&gt;
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
    "queryHistoryFaceByImageResult"
})
@XmlRootElement(name = "QueryHistoryFaceByImageResponse")
public class QueryHistoryFaceByImageResponse {

    @XmlElement(name = "QueryHistoryFaceByImageResult")
    protected ArrayOfQueryResult queryHistoryFaceByImageResult;

    /**
     * 获取queryHistoryFaceByImageResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfQueryResult }
     *     
     */
    public ArrayOfQueryResult getQueryHistoryFaceByImageResult() {
        return queryHistoryFaceByImageResult;
    }

    /**
     * 设置queryHistoryFaceByImageResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfQueryResult }
     *     
     */
    public void setQueryHistoryFaceByImageResult(ArrayOfQueryResult value) {
        this.queryHistoryFaceByImageResult = value;
    }

}
