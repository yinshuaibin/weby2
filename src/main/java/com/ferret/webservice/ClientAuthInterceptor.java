package com.ferret.webservice;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * @author cc;
 * @since 2018/1/13;
 */
public class ClientAuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private static final String NAME = "hntest";

    private static final String PASSWORD = "cc03e747a6afbbcbf8be7668acfebee5";

    public ClientAuthInterceptor() {
        //准备发送阶段
        super(Phase.PREPARE_SEND);
    }
    /**
     * Intercepts a message.
     * Interceptors should NOT invoke handleMessage or handleFault
     * on the next interceptor - the interceptor chain will
     * take care of this.
     *
     * @param message
     */
    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        List<Header> headers = message.getHeaders();

        Document doc = DOMUtils.createDocument();

        Element auth = doc.createElement("TMSoapHeader");
        auth.setAttribute("xmlns","http://tempuri.org/");

        Element name = doc.createElement("UserName");
        name.setTextContent(NAME);

        Element password = doc.createElement("PassWord");
        password.setTextContent(PASSWORD);

        auth.appendChild(name);
        auth.appendChild(password);

        headers.add(new Header(new QName(""), auth));
    }
}
