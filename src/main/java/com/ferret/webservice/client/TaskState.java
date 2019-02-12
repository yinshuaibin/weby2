
package com.ferret.webservice.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>TaskState的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="TaskState"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Init_State"/&gt;
 *     &lt;enumeration value="Waiting_State"/&gt;
 *     &lt;enumeration value="Processing_State"/&gt;
 *     &lt;enumeration value="Finish_State"/&gt;
 *     &lt;enumeration value="Unkown_State"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "TaskState")
@XmlEnum
public enum TaskState {

    @XmlEnumValue("Init_State")
    INIT_STATE("Init_State"),
    @XmlEnumValue("Waiting_State")
    WAITING_STATE("Waiting_State"),
    @XmlEnumValue("Processing_State")
    PROCESSING_STATE("Processing_State"),
    @XmlEnumValue("Finish_State")
    FINISH_STATE("Finish_State"),
    @XmlEnumValue("Unkown_State")
    UNKOWN_STATE("Unkown_State");
    private final String value;

    TaskState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TaskState fromValue(String v) {
        for (TaskState c: TaskState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
