package com.ferret.service.common.city.impl;

import com.ferret.bean.City;
import com.ferret.dao.CityMapper;
import com.ferret.service.common.city.CityService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @pack: com.impl;
 * @auth: Administrator;
 * @since: 2017/12/15 0015;
 * @desc:
 */
@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityMapper cityMapper;

	@Override
	public List<Map<String, Object>> findAllObject() {

		List<Map<String, Object>> cityList =

				cityMapper.findAllObject();

		return cityList;
	}

	@Override
	public List<Map<String, Object>> findByCode(Integer code) {
		List<Map<String, Object>> city = null;
		if (StringUtils.equals("1", code.toString())) {
			city = cityMapper.findAllObject();
		} else {
			city = cityMapper.findByCode(code);
		}

		return city;
	}

	@Override
	public List<Map<String, Object>> findCityTest() {

		return cityMapper.findCityTest();
	}

	/**
	 * @Description 根据地区查出城市名称
	 * @param code
	 * @date 2019-01-14 09:24:09
	 * @author xieyingchao
	 */
	@Override
	public String findAreaName(Integer code) {
		City city = cityMapper.findCityNameByCode(code);
		String areaName = "";
		String name = "";
		if (null != city) {
			code = city.getCode();
			if(code.toString().length() > 2) {
				areaName = city.getName();
				name =  findAreaName(Integer.valueOf(code.toString().substring(0,code.toString().length()-2)));
				return  name + " " + areaName;
			} else {
				return city.getName()+ areaName;
			}
		}
		return  "";
	}

}
