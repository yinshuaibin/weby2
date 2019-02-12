package com.ferret.dao.provider;

import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public class AllopatryAnalyseMapperProvider {

    /**
     * 区域碰撞分析（查询该personId符合A条件，或者符合B条件的数据）
     */

    public String getAllopatryPersonInfo(@Param("personId")String personId, @Param("beginTimeA")String beginTimeA, @Param("endTimeA")String endTimeA,
                                         @Param("cameraNumbersListA")ArrayList<String> cameraNumbersListA, @Param("beginTimeB")String beginTimeB,
                                         @Param("endTimeB")String endTimeB, @Param("cameraNumbersListB")ArrayList<String> cameraNumbersListB,
                                         @Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize) {

        StringBuffer cameraA = new StringBuffer("(");
        StringBuffer cameraB = new StringBuffer("(");
        for (int i = 0; i < cameraNumbersListA.size(); i++) {
            if (i == cameraNumbersListA.size()-1) {
                cameraA.append("'").append(cameraNumbersListA.get(i)).append("')");
            } else {
                cameraA.append("'").append(cameraNumbersListA.get(i)).append("',");
            }
        }
        for (int i = 0; i < cameraNumbersListB.size(); i++) {
            if (i == cameraNumbersListB.size()-1) {
                cameraB.append("'").append(cameraNumbersListB.get(i)).append("')");
            } else {
                cameraB.append("'").append(cameraNumbersListB.get(i)).append("',");
            }
        }

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT a.person_id,a.camera_ip,a.image_path,a.start_time,a.end_time,ci.name,ci.longitude,ci.latitude from " +
                    "(select cp.person_id,cp.camera_ip,cp.image_path,cp.start_time,cp.end_time from tb_cluster_pass cp where cp.person_id='")
                .append(personId)
                .append("' and ((cp.start_time>'")
                .append(beginTimeA)
                .append("' and cp.end_time<'")
                .append(endTimeA)
                .append("' and cp.camera_ip in ")
                .append(cameraA)
                .append(") or (cp.start_time>'")
                .append(beginTimeB)
                .append("' and cp.end_time<'")
                .append(endTimeB)
                .append("' and cp.camera_ip in ")
                .append(cameraB)
                .append(")) LIMIT ")
                .append(pageNum).append(",")
                .append(pageSize)
                .append(") a LEFT JOIN tb_camera_info ci ON a.camera_ip = ci.ip");
        return sb.toString();
    }

    public String getAllopatryPersonTotal(@Param("personId")String personId, @Param("beginTimeA")String beginTimeA, @Param("endTimeA")String endTimeA,
                                         @Param("cameraNumbersListA")ArrayList<String> cameraNumbersListA, @Param("beginTimeB")String beginTimeB,
                                         @Param("endTimeB")String endTimeB, @Param("cameraNumbersListB")ArrayList<String> cameraNumbersListB) {
        StringBuffer cameraA = new StringBuffer("(");
        StringBuffer cameraB = new StringBuffer("(");
        for (int i = 0; i < cameraNumbersListA.size(); i++) {
            if (i == cameraNumbersListA.size()-1) {
                cameraA.append("'").append(cameraNumbersListA.get(i)).append("')");
            } else {
                cameraA.append("'").append(cameraNumbersListA.get(i)).append("',");
            }
        }
        for (int i = 0; i < cameraNumbersListB.size(); i++) {
            if (i == cameraNumbersListB.size()-1) {
                cameraB.append("'").append(cameraNumbersListB.get(i)).append("')");
            } else {
                cameraB.append("'").append(cameraNumbersListB.get(i)).append("',");
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT count(a.person_id) from (select cp.person_id,cp.camera_ip,cp.image_path,cp.start_time,cp.end_time from tb_cluster_pass cp where cp.person_id='")
                .append(personId)
                .append("' and ((cp.start_time>'")
                .append(beginTimeA)
                .append("' and cp.end_time<'")
                .append(endTimeA)
                .append("' and cp.camera_ip in ")
                .append(cameraA)
                .append(") or (cp.start_time>'")
                .append(beginTimeB)
                .append("' and cp.end_time<'")
                .append(endTimeB)
                .append("' and cp.camera_ip in ")
                .append(cameraB)
                .append("))) a");
        return sb.toString();
    }
}
