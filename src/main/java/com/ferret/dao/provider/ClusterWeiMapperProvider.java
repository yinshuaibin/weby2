package com.ferret.dao.provider;


public class ClusterWeiMapperProvider {

    /**
     * 查询最新的6条新进维族人sql拼接
     *   y  1012
     * @return
     */
    public static   String getNewClusterWei(){ StringBuffer sql=new StringBuffer("SELECT c.*,d.longitude,d.latitude, d.name from (SELECT a.person_id, a.image_path, a.camera_ip, a.create_time FROM tb_cluster_image a, ( SELECT person_id FROM tb_cluster_wei ");
        sql.append("order by person_id DESC limit 0,6) b WHERE a.person_id = b.person_id GROUP BY a.person_id )c,tb_camera_info d WHERE c.camera_ip = d.ip ");
        sql.append("ORDER BY c.create_time desc");
        return sql.toString();
    }
}
