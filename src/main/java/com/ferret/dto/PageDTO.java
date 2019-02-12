package com.ferret.dto;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 对分页对象的DTO封装
 * @author cc;
 * @since 2018/1/25;
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageDTO<T> {
    /**
     * 分页查询的记录数据
     */
    private List<T> list;
    /**
     * 总条数
     */
    private Long total;


    public PageDTO(PageInfo<T> pageInfo){
        this.list = pageInfo.getList();
        this.total = pageInfo.getTotal();
    }


}






