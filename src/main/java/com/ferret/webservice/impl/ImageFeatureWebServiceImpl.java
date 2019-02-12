package com.ferret.webservice.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.Base64ImageEntity;
import com.ferret.bean.DynamicQueryEntity;
import com.ferret.exception.BusinessException;
import com.ferret.dao.CameraInfoMapper;
import com.ferret.utils.ImageBase64Utils;
import com.ferret.webservice.ClientAuthInterceptor;
import com.ferret.webservice.IImageFeatureWebService;
import com.ferret.webservice.client.CommitQueryCmd;
import com.ferret.webservice.client.DynamicServiceSoap;
import com.ferret.webservice.client.QueryResult;
import com.ferret.webservice.dao.DynamicQueryConvert;
import com.ferret.utils.ImageFeatureUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.*;


/**
 * @author cc;
 * @since 2018/1/13;
 */
@Slf4j
@Service
public class ImageFeatureWebServiceImpl implements IImageFeatureWebService {
    @Value("${webservice.wsdlUrl}")
    private String wsdlUrl;

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders httpHeaders = null;
    private DynamicServiceSoap dynamicServiceSoap = null;

    @Autowired
    private CameraInfoMapper cameraInfoMapper;
    @Autowired
    private DynamicQueryConvert dynamicQueryConvert;
        @Override
    public ResponseEntity queryDynamicImagesByFeature(DynamicQueryEntity entity) {
        Assert.notNull(entity,"请求数据不能为空!");
        Map<String, Object> map = new HashMap<>();
        // 将数据转换成接口需要的数据.
        CommitQueryCmd queryCmd = dynamicQueryConvert.convertCommitQueryCmd(entity);
        log.debug("请求数据: {}", queryCmd);

        if(dynamicServiceSoap == null){
            dynamicServiceSoap = this.initWebService();
        }

        // 调用接口
        String task = dynamicServiceSoap.commitQueryCmd(queryCmd.getFeature1(), queryCmd.getFeature2(), queryCmd.getCamIDs(), queryCmd
                .getDtBegin(), queryCmd.getDtEnd(), queryCmd.getFLimit(), queryCmd.getNWantNum());

        if (task != null){
           // 如果返回结果不为空
            map.put("task",task);
        }else{
            throw new BusinessException(103, "查询任务task为空,请联系开发人员!");
        }
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @Override
    public QueryResult getQueryProcessingByTask(String task) {
        if(dynamicServiceSoap == null){
            dynamicServiceSoap = this.initWebService();
        }

        QueryResult queryResult = dynamicServiceSoap.getQueryResult(task);

        Assert.notNull(queryResult,"后台查询服务返回结果为空");

        log.debug("异步查询任务结果:{}",queryResult);

        return queryResult;
    }


    public DynamicServiceSoap initWebService(){
        // 创建webservice工厂实例
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        // 设置wsdl地址
        factoryBean.setAddress(wsdlUrl);
        // 添加操作,此处是添加客户端发送请求的header权限
        factoryBean.getOutInterceptors().add(new ClientAuthInterceptor());

        //通过接口指定请求方法名称/返回类型/参数
        factoryBean.setServiceClass(DynamicServiceSoap.class);
        dynamicServiceSoap = (DynamicServiceSoap) factoryBean.create();

        //超时设置
        configTimeout(dynamicServiceSoap);

        return dynamicServiceSoap;
    }


    /**
     * 客户端调用请求时超时设置
     * @param service
     */
    public static void configTimeout(Object service) {
        Client proxy = ClientProxy.getClient(service);
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(8*1000);//8S 请求时间
        policy.setReceiveTimeout(10*1000);//10S 连接时间
        conduit.setClient(policy);
    }


}
