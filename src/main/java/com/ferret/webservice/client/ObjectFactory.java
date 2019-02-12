
package com.ferret.webservice.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ferret.webservice.client package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TMSoapHeader_QNAME = new QName("http://tempuri.org/", "TMSoapHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ferret.webservice.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryHistoryImage }
     * 
     */
    public QueryHistoryImage createQueryHistoryImage() {
        return new QueryHistoryImage();
    }

    /**
     * Create an instance of {@link QueryHistoryImageResponse }
     * 
     */
    public QueryHistoryImageResponse createQueryHistoryImageResponse() {
        return new QueryHistoryImageResponse();
    }

    /**
     * Create an instance of {@link ArrayOfHistoryImageInfo }
     * 
     */
    public ArrayOfHistoryImageInfo createArrayOfHistoryImageInfo() {
        return new ArrayOfHistoryImageInfo();
    }

    /**
     * Create an instance of {@link TMSoapHeader }
     * 
     */
    public TMSoapHeader createTMSoapHeader() {
        return new TMSoapHeader();
    }

    /**
     * Create an instance of {@link QueryHistoryFaceByImage }
     * 
     */
    public QueryHistoryFaceByImage createQueryHistoryFaceByImage() {
        return new QueryHistoryFaceByImage();
    }

    /**
     * Create an instance of {@link QueryHistoryFaceByImageResponse }
     * 
     */
    public QueryHistoryFaceByImageResponse createQueryHistoryFaceByImageResponse() {
        return new QueryHistoryFaceByImageResponse();
    }

    /**
     * Create an instance of {@link ArrayOfQueryResult }
     * 
     */
    public ArrayOfQueryResult createArrayOfQueryResult() {
        return new ArrayOfQueryResult();
    }

    /**
     * Create an instance of {@link UserLogin }
     * 
     */
    public UserLogin createUserLogin() {
        return new UserLogin();
    }

    /**
     * Create an instance of {@link UserLoginResponse }
     * 
     */
    public UserLoginResponse createUserLoginResponse() {
        return new UserLoginResponse();
    }

    /**
     * Create an instance of {@link BuKongPerson }
     * 
     */
    public BuKongPerson createBuKongPerson() {
        return new BuKongPerson();
    }

    /**
     * Create an instance of {@link BuKongPersonResponse }
     * 
     */
    public BuKongPersonResponse createBuKongPersonResponse() {
        return new BuKongPersonResponse();
    }

    /**
     * Create an instance of {@link BuKongResult }
     * 
     */
    public BuKongResult createBuKongResult() {
        return new BuKongResult();
    }

    /**
     * Create an instance of {@link CancelBuKong }
     * 
     */
    public CancelBuKong createCancelBuKong() {
        return new CancelBuKong();
    }

    /**
     * Create an instance of {@link CancelBuKongResponse }
     * 
     */
    public CancelBuKongResponse createCancelBuKongResponse() {
        return new CancelBuKongResponse();
    }

    /**
     * Create an instance of {@link CancelBuKongResult }
     * 
     */
    public CancelBuKongResult createCancelBuKongResult() {
        return new CancelBuKongResult();
    }

    /**
     * Create an instance of {@link CommitQueryCmd }
     * 
     */
    public CommitQueryCmd createCommitQueryCmd() {
        return new CommitQueryCmd();
    }

    /**
     * Create an instance of {@link CommitQueryCmdResponse }
     * 
     */
    public CommitQueryCmdResponse createCommitQueryCmdResponse() {
        return new CommitQueryCmdResponse();
    }

    /**
     * Create an instance of {@link GetQueryResult }
     * 
     */
    public GetQueryResult createGetQueryResult() {
        return new GetQueryResult();
    }

    /**
     * Create an instance of {@link GetQueryResultResponse }
     * 
     */
    public GetQueryResultResponse createGetQueryResultResponse() {
        return new GetQueryResultResponse();
    }

    /**
     * Create an instance of {@link QueryResult }
     * 
     */
    public QueryResult createQueryResult() {
        return new QueryResult();
    }

    /**
     * Create an instance of {@link CancelQueryCmd }
     * 
     */
    public CancelQueryCmd createCancelQueryCmd() {
        return new CancelQueryCmd();
    }

    /**
     * Create an instance of {@link CancelQueryCmdResponse }
     * 
     */
    public CancelQueryCmdResponse createCancelQueryCmdResponse() {
        return new CancelQueryCmdResponse();
    }

    /**
     * Create an instance of {@link HistoryImageInfo }
     * 
     */
    public HistoryImageInfo createHistoryImageInfo() {
        return new HistoryImageInfo();
    }

    /**
     * Create an instance of {@link SimpleCamInfo }
     * 
     */
    public SimpleCamInfo createSimpleCamInfo() {
        return new SimpleCamInfo();
    }

    /**
     * Create an instance of {@link ArrayOfPersonResult }
     * 
     */
    public ArrayOfPersonResult createArrayOfPersonResult() {
        return new ArrayOfPersonResult();
    }

    /**
     * Create an instance of {@link PersonResult }
     * 
     */
    public PersonResult createPersonResult() {
        return new PersonResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TMSoapHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "TMSoapHeader")
    public JAXBElement<TMSoapHeader> createTMSoapHeader(TMSoapHeader value) {
        return new JAXBElement<TMSoapHeader>(_TMSoapHeader_QNAME, TMSoapHeader.class, null, value);
    }

}
