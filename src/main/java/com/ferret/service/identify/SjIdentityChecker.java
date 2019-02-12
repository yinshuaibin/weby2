package com.ferret.service.identify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ferret.bean.Cluster;
import com.ferret.dao.ClusterImgMapper;
import com.ferret.service.identify.bean.SJInterfaceResp;
import com.ferret.utils.ImageBase64Utils;
import com.ferret.utils.ImagePrefixProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SjIdentityChecker implements IdentityChecker{
    @Autowired
    private ClusterImgMapper clusterImgMapper;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ImagePrefixProperties imagePrefixProperties;

    private SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 市局服务接口url
     */
    @Value("${synchronizeLdentityInterfaceUrl}")
    private String synchronizeLdentityInterfaceUrl;


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void check(Date startTime, Date endTime) {
        String stime = null;
        String etime = null;
        try{
            stime = sdf.format(startTime);
            etime = sdf.format(endTime);
        }catch (Exception e){
            System.out.println("请传入正确的时间参数");
        }
        if(stime != null && etime != null){
            List<Cluster> clusterByCreateTime = clusterImgMapper.findClusterByCreateTime(stime, etime);
            if(clusterByCreateTime !=null ){
                for (Cluster cluster:clusterByCreateTime){
                    checkPerson(cluster);
                }
            }
        }else {
            System.out.println("请传入正确的参数");
        }
    }

    /**
     * 根据personId完善聚类人员信息
     * y 0114 改
     * @param personId
     */
    @Override
    public void check(String personId) {
        // 该方法其实只查出了该personId对应的代表图,且只有一条数据
        List<Cluster> clusterByPersonId = clusterImgMapper.findClusterByPersonId(personId);
        if(clusterByPersonId !=null && clusterByPersonId.size()>0 ){
            Cluster cluster = clusterByPersonId.get(0);
            cluster.setPersonId(personId);
            checkPerson(cluster);
        }
        System.out.println("sj check : " + this.hashCode());
    }

    @Override
    public void check() {

    }

    private void checkPerson(Cluster cluster) {
        String format = formatDay.format(new Date());
        String imgDir = imagePrefixProperties.getHistoryImageDir() + format;
        File file = new File(imgDir);
        if (!file.exists()) {//如果文件夹不存在
            boolean mkdirs = file.mkdirs();
            if(!mkdirs){
                log.error("名称为:"+file.getName()+" 的文件夹创建失败,请检查文件路径");
                return;
            }
        }
        if (StringUtils.isNotBlank(cluster.getImagePath())) {
            // 文件实际本地路径
            String clusterimageDir = imagePrefixProperties.getHistoryImageDir()+cluster.getImagePath();
            try {
                JSONObject json = new JSONObject();
                String base64 = ImageBase64Utils.getImageStr(clusterimageDir);
                json.put("ImageBase64", base64.replace("/r/n", ""));
                //调用接口,提交任务请求
                String resultString = restTemplate.postForObject(synchronizeLdentityInterfaceUrl, json, String.class);
                if(resultString !=null ){
                    List<SJInterfaceResp> list = JSON.parseArray(resultString, SJInterfaceResp.class);
                    if(list !=null &&list.size()>1){
                        SJInterfaceResp rest = list.get(0);
                        //将返回的图片base64格式化存入本地,图片名称为:身份证号_姓名_性别.jpg
                        String imgPath = imgDir + "\\" + rest.getID() + "_" + rest.getName() + "_" + rest.getSex() + ".jpg";
                        ImageBase64Utils.generateImage(rest.getImagepath(), imgPath);
                        //将本地路径转换为数据库同样类型的路径
                        String mysqlImagePath = imgPath.replace(imagePrefixProperties.getHistoryImageDir(), imagePrefixProperties.getHistoryDir()).replace("/", "\\");
                        //修改数据库对应的数据
                        clusterImgMapper.perfectinformation(rest.getName(), mysqlImagePath, rest.getIdcard(), cluster.getPersonId());
                    }
                }
            } catch (NullPointerException e) {
                // 如果图片转码失败,将会抛出FileNotFoundException以及空指针异常,这是因为在工具类中会抛出
                // FileNotFoundException,但我将其注释掉了,所以只会抛出空指针异常,此处只处理空指针异常
                //如果出现空指针异常,说明本地没有找到对应的图片,将没有图片的人的person_id
                e.printStackTrace();
            }
        }
    }
}
