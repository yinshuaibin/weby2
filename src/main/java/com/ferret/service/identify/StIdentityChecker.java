package com.ferret.service.identify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.Cluster;
import com.ferret.bean.searchimage.People;
import com.ferret.bean.searchimage.SearchImage;
import com.ferret.bean.searchimage.SearchResult;
import com.ferret.dao.ClusterComparisonDao;
import com.ferret.dao.ClusterImgMapper;
import com.ferret.dao.ComparisonClusterMapper;
import com.ferret.utils.ImageBase64Utils;
import com.ferret.utils.ImagePrefixProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 省厅身份落地实现
 */
@Component
public class StIdentityChecker implements IdentityChecker {

    @Autowired
    private ClusterImgMapper clusterImgMapper;

    @Autowired
    private ImagePrefixProperties imagePrefixProperties;

    @Autowired
    private ClusterComparisonDao clusterComparisonDao;


    private RestTemplate rest = new RestTemplate();

    private boolean executingByTime = false;

    @Autowired
    private ComparisonClusterMapper comparisonClusterMapper;
    private static final int ONEHOUR = 60 * 60;
    /**
     * 省厅服务接口url
     */
    @Value("${getAsynchronousLdentityInterfaceUrl}")
    private String getAsynchronousLdentityInterfaceUrl;

    /**
     * 根据时间完善任务都同时只允许执行一个
     */
    //正在执行的根据时间完善的任务的已经提交的条数
    private Integer perfectinformationByTimeSize = 0;
    //正在执行的根据时间完善的任务的总条数
    private Integer perfectinformationByTimeTotal = 0;


    private final static String BY_TIME = "bytime";
    private final static String BY_PERSON_ID ="bypersonid";

    /**
     * 省厅服务接口url
     */
    @Value("${asynchronousLdentityInterfaceUrl}")
    private String asynchronousLdentityInterfaceUrl;

    @Override
    public void check(Date startTime, Date endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stime = null;
        String etime = null;
        try{
           stime = sdf.format(startTime);
           etime = sdf.format(endTime);
        }catch (Exception e){
            System.out.println("请传入正确的时间参数");
        }
        if(stime != null && etime != null){
            if(!executingByTime){
                executingByTime=true;
                try {
                    Map resultMap = perfectinformation(clusterImgMapper.findClusterByCreateTime(stime, etime),BY_TIME);
                    executingByTime=false;
                    this.perfectinformationByTimeTotal=0;
                    this.perfectinformationByTimeSize=0;
                    System.out.println(resultMap);
                }catch (Exception e){
                    executingByTime=false;
                    this.perfectinformationByTimeTotal=0;
                    this.perfectinformationByTimeSize=0;
                    e.printStackTrace();
                    System.out.println("服务器遇到错误!");
                }
            }
            if(perfectinformationByTimeTotal !=0){
                System.out.println("正在提交任务,时间可能较长,已提交任务数:"+perfectinformationByTimeSize+",总任务数:"+perfectinformationByTimeTotal);
            }{
                System.out.println("正在执行,时间可能较长,根据数据多少速度不同,请不要重复操作,且每次只能执行一个任务");
            }
        }else{
            System.out.println("请传入正确的参数");
        }
    }

    @Override
    public void check(String personID) {
        perfectinformation(clusterImgMapper.findClusterByPersonId(personID),BY_PERSON_ID);
        System.out.println("st check : " + this.hashCode());
    }

    @Override
    public void check() {
        getclusterComparison();
    }

    private Map perfectinformation(List<Cluster> clusterList,String type){
        Map resultMap = new HashMap();
        if(clusterList != null && clusterList.size()>0){
            int size = clusterList.size();
            if(type.equals(BY_TIME)){
                this.perfectinformationByTimeTotal=size;
            }
            if(clusterList.size()<=100){
                //如果小于100条,则一次完成
                perfectinformationClusterList(clusterList,type);
            }else if(clusterList.size()<=1000){
                int pageSize =size/10;
                //如果大于100条小于1000条,则分10次
                for(int x =0; x<10; x++){
                    if(x<9){
                        //sublit方法获得的子数据,子数据修改以后会影响到父数据
                        List<Cluster> pageCluster = clusterList.subList(x*pageSize,(x+1)*pageSize);
                        perfectinformationClusterList(pageCluster,type);
                    }else {
                        List<Cluster> pageCluster = clusterList.subList(x*pageSize,size);
                        perfectinformationClusterList(pageCluster,type);
                    }
                }
            }else {
                //如果大与1000条,每1000条分一次
                int pageNum = size%1000 == 0? size/1000 :size/1000 +1;
                for(int x = 0; x < pageNum; x++){
                    if(x == pageNum-1){
                        List<Cluster> pageCluster = clusterList.subList(x*1000,size);
                        perfectinformationClusterList(pageCluster,type);
                    }else {
                        List<Cluster> pageCluster = clusterList.subList(x*1000,(x+1)* 1000);
                        perfectinformationClusterList(pageCluster,type);
                    }
                }
            }
            resultMap.put("msg","信息补充任务全部提交完成");
        }else {
            resultMap.put("msg","未查询到需要补充信息的数据,数据补充任务已经全部提交完成,请查看数据库信息!");
        }
        return resultMap;
    }

    /**
     * 将list<cluster>通过接口完善信息后存入数据库
     * @param clusterList
     * @return
     */
    private  Map perfectinformationClusterList(List<Cluster> clusterList,String type){
        List<Cluster> newClusters= new ArrayList<>();
        newClusters.addAll(clusterList);
        for (int x =0; x<newClusters.size(); x++){
            Cluster cluster = clusterList.get(x);
            String imageDir = null;
            if(StringUtils.isNotBlank(imageDir)){
                String replace = imageDir.replace("\\","/").replace(imagePrefixProperties.getHistoryDir(), imagePrefixProperties.getHistoryDir2());
                try{
                    String base64 = ImageBase64Utils.getImageStr(replace);
                    SearchImage s = new SearchImage();
                    s.setImageBase64(base64.replace("/r/n",""));
                    //调用接口,提交任务请求
                    String uuid = rest.postForObject(asynchronousLdentityInterfaceUrl, JSONObject.toJSON(s), String.class);
                    cluster.setTxId(uuid);
                    Thread.sleep(15000);
                    //存入数据库
                    clusterComparisonDao.insertTaskPerson(cluster);
                }catch (NullPointerException e){
                    // 如果图片转码失败,将会抛出FileNotFoundException以及空指针异常,这是因为在工具类中会抛出
                    // FileNotFoundException,但我将其注释掉了,所以只会抛出空指针异常,此处只处理空指针异常
                    //如果出现空指针异常,说明本地没有找到对应的图片,将没有图片的人的person_id
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(type.equals(BY_TIME)){
            this.perfectinformationByTimeSize+=clusterList.size();
        }
        return null;
    }

    /**
     * 查询tb_cluster_comparison表获取数据，请求结果服务接口，处理结果
     */
    public void getclusterComparison() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<SearchResult> searchResults = comparisonClusterMapper.getclusterComparison();
        // System.err.println(JSONObject.toJSONString(searchResults));
        if (searchResults != null && searchResults.size() >0) {
            for (SearchResult everySearcgResult:searchResults) {
                try {
                    SearchImage searchImage =  new SearchImage();
                    searchImage.setTaskId(everySearcgResult.getTaskId());
                    String result = rest.postForObject(getAsynchronousLdentityInterfaceUrl, searchImage, String.class);
                    List<People> peopleList = JSON.parseArray(result, People.class);
                    // 获取结果后，取数据的第一条，更新cluster表，并删除查询tb_cluster_comparison表中的该条数据
                    if (peopleList != null && peopleList.size() > 0) {
                        People people = peopleList.get(0);
                        //删除原来的业务编号
                        comparisonClusterMapper.deleteClusterComparison(everySearcgResult.getSysId());
                        // 判断返回的相识度是否有2个厂家以上
                        if((people.getKedas()+people.getHaikangs()+people.getYuncongs()+people.getYitus()+people.getTumings()) > 1){
                            //更新tb_cluster表记录
                            comparisonClusterMapper.updateClusterByPersonId(everySearcgResult.getResult(),people.getIdCard(),people.getName(),people.getImgUrl());
                        }
                    }else {
                        // 删除脏数据
                        String createTime = everySearcgResult.getCreateTime();
                        if (createTime.contains(".")){
                            createTime = createTime.substring(0, createTime.lastIndexOf("."));
                        }
                        if (new Date().getTime() - sdf.parse(createTime).getTime() > ONEHOUR){
                            //删除原来的业务编号
                            comparisonClusterMapper.deleteClusterComparison(everySearcgResult.getSysId());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }
}
