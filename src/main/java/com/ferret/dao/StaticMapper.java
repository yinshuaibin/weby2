package com.ferret.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.ferret.bean.staticBean.FaceData;
import com.ferret.bean.staticBean.StaticPorts;
import com.ferret.dao.provider.StaticMapperProvider;

@Repository
public interface StaticMapper {

	@SelectProvider(type=StaticMapperProvider.class,method="placeQs")
	List<FaceData> findById(Set<Long> ids);

	@Select("SELECT * FROM tb_category")// WHERE pId = '0'
	List<StaticPorts> queryPorts();
	
}
