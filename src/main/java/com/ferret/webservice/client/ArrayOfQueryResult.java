
package com.ferret.webservice.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfQueryResult complex type?? Java ?à??
 * 
 * <p>???????????????¨°ü???????à?????¤????????
 * 
 * <pre>
 * &lt;complexType name="ArrayOfQueryResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="QueryResult" type="{http://tempuri.org/}QueryResult" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfQueryResult", propOrder = {
    "queryResult"
})
public class ArrayOfQueryResult {

    @XmlElement(name = "QueryResult", nillable = true)
    protected List<QueryResult> queryResult;

    /**
     * Gets the value of the queryResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the queryResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQueryResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueryResult }
     * 
     * 
     */
    public List<QueryResult> getQueryResult() {
        if (queryResult == null) {
            queryResult = new ArrayList<QueryResult>();
        }
        return this.queryResult;
    }

}
