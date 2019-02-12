
package com.ferret.webservice.client;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>QueryResult complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="QueryResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="state" type="{http://tempuri.org/}TaskState"/&gt;
 *         &lt;element name="arrayNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="percentNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="result" type="{http://tempuri.org/}ArrayOfPersonResult" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryResult", propOrder = {
    "state",
    "arrayNum",
    "percentNum",
    "result"
})
@Data
public class QueryResult {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TaskState state;
    protected int arrayNum;
    protected int percentNum;
    protected ArrayOfPersonResult result;

    /**
     * 获取state属性的值。
     * 
     * @return
     *     possible object is
     *     {@link TaskState }
     *     
     */
    public TaskState getState() {
        return state;
    }

    /**
     * 设置state属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link TaskState }
     *     
     */
    public void setState(TaskState value) {
        this.state = value;
    }

    /**
     * 获取arrayNum属性的值。
     * 
     */
    public int getArrayNum() {
        return arrayNum;
    }

    /**
     * 设置arrayNum属性的值。
     * 
     */
    public void setArrayNum(int value) {
        this.arrayNum = value;
    }

    /**
     * 获取percentNum属性的值。
     * 
     */
    public int getPercentNum() {
        return percentNum;
    }

    /**
     * 设置percentNum属性的值。
     * 
     */
    public void setPercentNum(int value) {
        this.percentNum = value;
    }

    /**
     * 获取result属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPersonResult }
     *     
     */
    public ArrayOfPersonResult getResult() {
        return result;
    }

    /**
     * 设置result属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPersonResult }
     *     
     */
    public void setResult(ArrayOfPersonResult value) {
        this.result = value;
    }

}
