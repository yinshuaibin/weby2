package com.ferret.utils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author cc;
 * @since 2018/1/17;
 */
public class DateUtils {

    // 将日期转换为XMLGregorianCalendar
    public static XMLGregorianCalendar convertXMLGregorianCalendar(Date date) {
        // 创建一个 GregorianCalendar
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+8"));
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gc;
    }

}



