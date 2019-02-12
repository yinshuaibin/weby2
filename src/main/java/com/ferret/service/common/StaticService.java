package com.ferret.service.common;

import java.util.List;

import com.ferret.bean.staticBean.SeachStatic;
import com.ferret.bean.staticBean.StaticPorts;

public interface StaticService {

	void handleJPGData(String localPathZP);

	List<List<?>> searchStaticData(SeachStatic data);

	List<StaticPorts> queryPorts();
	
}
