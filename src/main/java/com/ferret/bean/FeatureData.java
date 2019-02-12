package com.ferret.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.CharEncoding;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng;
 * @date 2018/4/16;
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FeatureData {

    public static String FILE_TYPE = ".tk";

    public static String DB_TYPE = ".db";

    public static String VERSION = "v1.0";

    private static int FEATURE_BYTE_SIZE = 2056;

    private Integer count;

    private Integer fileSize;

    private List<byte[]> features;


    public FeatureData(List<byte[]> features) {
        this.count = features.size();
        this.fileSize = 1024 + FEATURE_BYTE_SIZE * count;
        this.features = features;
    }

    /**
     * 文件头: 总大小1024字节, 描述信息按一定长度分割,剩下的留空扩容</br>
     * 文件头信息如下:<br>
     * [1]. 8字节,描述文件类型,即后缀名<br>
     * [2]. 8字节,描述当前版本号. <br>
     * [3]. 4字节,描述特征个数N. <br>
     * [4]. 4字节,描述特征文件总大小. <br>
     * [5]. 1000字节,留空方便扩容. <br>
     * 文件体: 2056固定大小的特征数据 * N(特征个数) <br>
     */
    public byte[] convertToBytes() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.fileSize);
        try {
            byte[] buffer = new byte[16];
            byte[] fileTypeBytes = FILE_TYPE.getBytes(CharEncoding.UTF_8);
            byte[] versionBytes = VERSION.getBytes(CharEncoding.UTF_8);
            System.arraycopy(fileTypeBytes, 0, buffer, 0, fileTypeBytes.length);
            System.arraycopy(versionBytes, 0, buffer, 8, versionBytes.length);

            // 写入特征文件头,16字节+ 4字节+ 4字节 + 1000字节
            byteBuffer.put(buffer).putInt(this.count).putInt(this.fileSize).put(new byte[1000]);
            // 写入特征文件体, FEATURE_BYTE_SIZE * N 字节
            this.getFeatures().forEach(f -> {
                byteBuffer.put(Base64.decodeBase64(f));
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] bytes = byteBuffer.array();
        byteBuffer.clear();
        return bytes;
    }


    /**
     * 读取特征文件的字节返回特征list
     * @param bytes
     * @return
     */
    public static List<byte[]> bytesToBean(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        List<byte[]> list = new ArrayList<>();

        // 获取文件头中的 count 和 fileSize数据
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        int nCount = byteBuffer.getInt(16);

        // 特征文件体,图片特征数据
        for (int i = 0; i < nCount; i++) {
            byte[] fBytes = new byte[FEATURE_BYTE_SIZE];
            System.arraycopy(bytes, 1024 + FEATURE_BYTE_SIZE * i, fBytes,0, FEATURE_BYTE_SIZE);
            list.add(fBytes);
        }
        return list;
    }
}
