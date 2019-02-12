package com.ferret.service.face.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ferret.bean.Base64ImageEntity;
import com.ferret.bean.ImageFace;
import com.ferret.bean.InterfaceBean.CompareOneToOne;
import com.ferret.bean.InterfaceBean.FaceExtraction;
import com.ferret.bean.InterfaceBean.FaceFeature;
import com.ferret.bean.InterfaceBean.InterfaceSearchParam;
import com.ferret.bean.clustercompare.ImageAroundSize;
import com.ferret.bean.clustercompare.WidthHeight;
import com.ferret.service.face.FaceService;
import com.ferret.utils.CutImgUtil;
import com.ferret.utils.ImageBase64Utils;
import com.ferret.utils.ImagePrefixProperties;
import com.ferret.utils.InterfaceUtils.InterfaceRequest;
import com.ferret.utils.InterfaceUtils.ResultCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class FaceServiceImp implements FaceService {


    @Value("${image.prefix.uploadDir}")
    private String imgDir;

    @Autowired
    private ImagePrefixProperties imagePrefixProperties;

    @Override
    public Float faceCompareInterface(String imageBase64_1, String imageBase64_2) {
        if(StringUtils.isNotBlank(imageBase64_1) && StringUtils.isNotBlank(imageBase64_2)){
            JSONObject jsonParam = new JSONObject();
            jsonParam.put(InterfaceSearchParam.METHOD,InterfaceSearchParam.COMPARE);
            jsonParam.put(InterfaceSearchParam.FILE_1,imageBase64_1.substring(imageBase64_1.indexOf(",")+1).replaceAll("\r|\n", ""));
            jsonParam.put(InterfaceSearchParam.FILE_2,imageBase64_2.substring(imageBase64_2.indexOf(",")+1).replaceAll("\r|\n", ""));
            // 调用工具类发送请求,拿到结果
            String rs = InterfaceRequest.interfaceRequest(imagePrefixProperties.getInterfaceUrl()+InterfaceSearchParam.FACE_SEARCH, jsonParam);
            //转换成对应的结果实体类
            CompareOneToOne compareOneToOne = JSON.parseObject(rs, new TypeReference<CompareOneToOne>() {});
            if( null != compareOneToOne){
                String message = ResultCodeUtils.errMessage(compareOneToOne.getResultcode(), compareOneToOne.getStatus());
                if(InterfaceSearchParam.SUCCESS.equals(message)){
                    String compare = compareOneToOne.getScore().substring(0,compareOneToOne.getScore().indexOf("/.")+5);
                    return Float.parseFloat(compare)*100;
                }
            }
            return 0F;
        }
        return 0F;
    }

    @Override
    public ImageFace FaceExtraction(String imageBase64) {
        if(StringUtils.isNotBlank(imageBase64)){
            ImageFace imageFace = new ImageFace();
            //判断是否是 网址 还是 base64字符
            if(imageBase64.startsWith("http://")) {
                Base64ImageEntity generateBase64ImageEntity = ImageBase64Utils.generateBase64ImageEntity(imageBase64);
                imageBase64 = generateBase64ImageEntity.getImageData();
            }
            //base64字符保存本地
            String imgLocalDir = imgDir+"/"+ (new SimpleDateFormat("yyyy_MM_dd")).format(new Date());
            File imgLocalDirFile = new File(imgLocalDir);
            if (!imgLocalDirFile.exists()) {
                boolean mkdirs = imgLocalDirFile.mkdirs();
                if(!mkdirs){
                    log.error("名称为:"+imgLocalDirFile.getName()+" 的文件夹创建失败,请检查文件路径");
                    return null;
                }
            }
            String replaceUUid = UUID.randomUUID().toString().replaceAll("-", "");
            String imgNameLocal = imgLocalDir + "/" + replaceUUid + ".jpg";
            // 存到本地,截取人脸的时候使用 (防止有data前缀,直接截取)
            ImageBase64Utils.generateImage(imageBase64.substring(imageBase64.indexOf(",")+1), imgNameLocal);
            JSONObject jsonParam = new JSONObject();
            jsonParam.put(InterfaceSearchParam.METHOD,InterfaceSearchParam.FEATURE);
            jsonParam.put(InterfaceSearchParam.FILE_1,imageBase64.substring(imageBase64.indexOf(",")+1).replaceAll("\r|\n", ""));
            // 调用工具类发送请求,拿到结果
            String rs = InterfaceRequest.interfaceRequest(imagePrefixProperties.getInterfaceUrl()+InterfaceSearchParam.FACE_SEARCH, jsonParam);
            //转换成对应的结果实体类
            FaceExtraction faceExtraction = JSON.parseObject(rs, new TypeReference<FaceExtraction>() {});
            if( null != faceExtraction){
                String message = ResultCodeUtils.errMessage(faceExtraction.getResultcode(), faceExtraction.getStatus());
                // 判断是否有人脸
                if(InterfaceSearchParam.SUCCESS.equals(message) && !"0".equals(faceExtraction.getFeature_Num())){
                    // 获取原图片的宽高
                    BufferedImage image;
                    try {
                        image = ImageIO.read(new File(imgNameLocal));
                    } catch (IOException e) {
                        log.info("无法获取获取图片的尺寸！");
                        return null;
                    }
                    // 原图片的宽，高
                    int height = image.getHeight();
                    int width = image.getWidth();

                    //获取rect  人脸位置坐标
                    ArrayList<ImageAroundSize> arrayListImg = new ArrayList<>();
                    for (int i = 0; i < faceExtraction.getFeature_List().size(); i++) {
                        FaceFeature faceFeature = faceExtraction.getFeature_List().get(i);
                        ImageAroundSize aroundSize = new ImageAroundSize();
                        // 人脸位置的宽和高
                        int faceWidth = faceFeature.getFaceRight() - faceFeature.getFaceLeft();
                        int faceHeight = faceFeature.getFaceBottom() - faceFeature.getFaceTop();
                        // 人脸位置的 左上 坐标原点
                        int faceLeft = faceFeature.getFaceLeft();
                        int faceTop = faceFeature.getFaceTop();
                        // 获取宽和高 的15% 用来截取人脸图
                        int sizeWidth = (int) (faceWidth * 0.15f);
                        int sizeHeight = (int) (faceHeight * 0.15f);
                        int left = faceLeft - sizeWidth;
                        if (left < 0) {
                            left = 0;
                        }
                        int top = faceTop - sizeHeight;
                        if (top < 0) {
                            top = 0;
                        }
                        int right = faceFeature.getFaceRight() + sizeWidth;
                        if (right > width) {
                            right = width;
                        }
                        int bottom = faceFeature.getFaceBottom() + sizeHeight;
                        if (bottom > height) {
                            bottom = height;
                        }
                        aroundSize.setLeft(left);
                        aroundSize.setTop(top);
                        aroundSize.setHeight(bottom - top);
                        aroundSize.setWidth(right- left);
                        arrayListImg.add(aroundSize);
                    }
                    /*for (int i = 0; i < faceExtraction.getFeature_List().size(); i++) {
                        FaceFeature faceFeature = faceExtraction.getFeature_List().get(i);
                        ImageAroundSize aroundSize = new ImageAroundSize();
                        aroundSize.setLeft(faceFeature.getFaceLeft());
                        aroundSize.setTop(faceFeature.getFaceTop());
                        aroundSize.setHeight(faceFeature.getFaceBottom() - faceFeature.getFaceTop());
                        aroundSize.setWidth(faceFeature.getFaceRight()- faceFeature.getFaceLeft());
                        arrayListImg.add(aroundSize);
                    }*/
                    ArrayList<String> faceImgBase64 = new ArrayList<>();
                    //获取人脸的每一个坐标  然后截图转成base64 封装结果集
                    for (int i = 0; i < arrayListImg.size(); i++) {
                        //工具类中 已有判断文件是否存在
                        String everyFaceUUID = UUID.randomUUID().toString().replaceAll("-", "");
                        String everyFaceName = imgLocalDir + "/" + everyFaceUUID + ".jpg";
                        //截取图片 保存到指定文件
                        CutImgUtil.cutPic(imgNameLocal, everyFaceName, arrayListImg.get(i).getLeft(), arrayListImg.get(i).getTop(),
                                arrayListImg.get(i).getWidth(), arrayListImg.get(i).getHeight());
                        //把图片转成base64
                        String getImageStr = ImageBase64Utils.getImageStr(everyFaceName);
                        faceImgBase64.add("data:image/jpeg;base64,"+getImageStr);
                    }
                    //如果只检测到一个人脸，则不用绘制原图人脸矩形，只返回人脸裁剪图的base64即可
                    imageFace.setFaceNum(faceImgBase64.size());
                    imageFace.setFaceBase64(faceImgBase64);
                    if(arrayListImg.size() == 1) {
                        return imageFace;
                    }
                    //检测多个人脸    原图片绘制人脸矩形
                    String originalImgSrc = UUID.randomUUID().toString().replaceAll("-", "");
                    String originalImgName = imgLocalDir + "/" + originalImgSrc + ".jpg";
                    WidthHeight drawRectangle = null;
                    try {
                        drawRectangle = CutImgUtil.drawRectangle(imgNameLocal, originalImgName, arrayListImg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String getImageStr = ImageBase64Utils.getImageStr(originalImgName);
                    imageFace.setBaseImgeKey1("data:image/jpeg;base64,"+getImageStr);
                    imageFace.setWidthHeight(drawRectangle);
                    return imageFace;
                }
            }
            return null;
        }
        return null;
    }
}
