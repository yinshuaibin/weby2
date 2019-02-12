package com.ferret.webservice;

import com.ferret.bean.Base64ImageEntity;
import com.ferret.bean.DynamicQueryEntity;
import com.ferret.webservice.client.QueryResult;
import org.springframework.http.ResponseEntity;

/**
 * @author cc;
 * @since 2018/1/13;
 */
public interface IImageFeatureWebService {


    /**
     * 获取动态查询服务的task id;
     * @param entity 查询实例
     * @return task id 的responseEntity
     */
    ResponseEntity queryDynamicImagesByFeature(DynamicQueryEntity entity);

    QueryResult getQueryProcessingByTask(String task);
}
