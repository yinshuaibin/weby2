
package com.ferret.webservice.client;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 3.2.1
 * 2018-01-17T11:27:43.877+08:00
 * Generated source VERSION: 3.2.1
 * 
 */
public final class DynamicServiceSoap_DynamicServiceSoap_Client {

    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "DynamicService");

    private DynamicServiceSoap_DynamicServiceSoap_Client() {
    }

    public static void main(String args[]) {
        URL wsdlURL = DynamicService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        DynamicService ss = new DynamicService(wsdlURL, SERVICE_NAME);
        DynamicServiceSoap port = ss.getDynamicServiceSoap();  
        
        {
        System.out.println("Invoking queryHistoryFaceByImage...");
        byte[] _queryHistoryFaceByImage_image = new byte[0];
        java.lang.String _queryHistoryFaceByImage_imageExt = "";
        javax.xml.datatype.XMLGregorianCalendar _queryHistoryFaceByImage_dtBegin = null;
        javax.xml.datatype.XMLGregorianCalendar _queryHistoryFaceByImage_dtEnd = null;
        float _queryHistoryFaceByImage_fLimit = 0.0f;
        int _queryHistoryFaceByImage_nWantNum = 0;
        com.ferret.webservice.client.ArrayOfQueryResult _queryHistoryFaceByImage__return = port.queryHistoryFaceByImage(_queryHistoryFaceByImage_image, _queryHistoryFaceByImage_imageExt, _queryHistoryFaceByImage_dtBegin, _queryHistoryFaceByImage_dtEnd, _queryHistoryFaceByImage_fLimit, _queryHistoryFaceByImage_nWantNum);
        System.out.println("queryHistoryFaceByImage.result=" + _queryHistoryFaceByImage__return);


        }
        {
        System.out.println("Invoking queryHistoryImage...");
        java.lang.String _queryHistoryImage_cams = "";
        javax.xml.datatype.XMLGregorianCalendar _queryHistoryImage_dtBegin = null;
        javax.xml.datatype.XMLGregorianCalendar _queryHistoryImage_dtEnd = null;
        com.ferret.webservice.client.ArrayOfHistoryImageInfo _queryHistoryImage__return = port.queryHistoryImage(_queryHistoryImage_cams, _queryHistoryImage_dtBegin, _queryHistoryImage_dtEnd);
        System.out.println("queryHistoryImage.result=" + _queryHistoryImage__return);


        }
        {
        System.out.println("Invoking getQueryResult...");
        java.lang.String _getQueryResult_taskid = "";
        com.ferret.webservice.client.QueryResult _getQueryResult__return = port.getQueryResult(_getQueryResult_taskid);
        System.out.println("getQueryResult.result=" + _getQueryResult__return);


        }
        {
        System.out.println("Invoking buKongPerson...");
        byte[] _buKongPerson_imgData = new byte[0];
        java.lang.String _buKongPerson_extImag = "";
        java.lang.String _buKongPerson_idnum = "";
        java.lang.String _buKongPerson_name = "";
        java.lang.String _buKongPerson_desc = "";
        int _buKongPerson_alarmMethod = 0;
        java.lang.String _buKongPerson_alarmAddr = "";
        com.ferret.webservice.client.BuKongResult _buKongPerson__return = port.buKongPerson(_buKongPerson_imgData, _buKongPerson_extImag, _buKongPerson_idnum, _buKongPerson_name, _buKongPerson_desc, _buKongPerson_alarmMethod, _buKongPerson_alarmAddr);
        System.out.println("buKongPerson.result=" + _buKongPerson__return);


        }
//        {
//        System.out.println("Invoking commitQueryCmd...");
//        byte[] _commitQueryCmd_feature1 = new byte[0];
//        byte[] _commitQueryCmd_feature2 = new byte[0];
//        java.lang.String _commitQueryCmd_camIDs = "";
//        javax.xml.datatype.XMLGregorianCalendar _commitQueryCmd_dtBegin = null;
//        javax.xml.datatype.XMLGregorianCalendar _commitQueryCmd_dtEnd = null;
//        float _commitQueryCmd_fLimit = 0.0f;
//        int _commitQueryCmd_nWantNum = 0;
//        java.lang.String _commitQueryCmd__return = port.commitQueryCmd(_commitQueryCmd_feature1, _commitQueryCmd_feature2, _commitQueryCmd_camIDs, _commitQueryCmd_dtBegin, _commitQueryCmd_dtEnd, _commitQueryCmd_fLimit, _commitQueryCmd_nWantNum);
//        System.out.println("commitQueryCmd.result=" + _commitQueryCmd__return);
//
//
//        }
        {
        System.out.println("Invoking userLogin...");
        java.lang.String _userLogin__return = port.userLogin();
        System.out.println("userLogin.result=" + _userLogin__return);


        }
        {
        System.out.println("Invoking cancelBuKong...");
        long _cancelBuKong_id = 0;
        com.ferret.webservice.client.CancelBuKongResult _cancelBuKong__return = port.cancelBuKong(_cancelBuKong_id);
        System.out.println("cancelBuKong.result=" + _cancelBuKong__return);


        }
        {
        System.out.println("Invoking cancelQueryCmd...");
        java.lang.String _cancelQueryCmd_taskid = "";
        port.cancelQueryCmd(_cancelQueryCmd_taskid);


        }

        System.exit(0);
    }

}
