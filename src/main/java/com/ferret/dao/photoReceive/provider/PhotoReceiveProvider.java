package com.ferret.dao.photoReceive.provider;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public class PhotoReceiveProvider {

    public String delByIds(@Param("ids")List<Integer> ids){
        StringBuffer sb = new StringBuffer("delete from tb_token_task where id in (");
        for (int id: ids){
            sb.append(id).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");
        return sb.toString();
    }
}
