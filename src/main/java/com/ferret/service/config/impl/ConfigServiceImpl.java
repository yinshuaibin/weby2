package com.ferret.service.config.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ferret.bean.Config;
import com.ferret.dao.ConfigMapper;
import com.ferret.service.config.ConfigService;

@Service("configService")
public class ConfigServiceImpl implements ConfigService{

	@Resource
	private ConfigMapper configMapper;
	
	@Override
	public void create(Config config) {
		config = new Config();
		config.setDescription("qweqwe");
		config.setName("asd");
		config.setSysName("asdasd");
		config.setValue("poiuyt");
		
		int i = configMapper.insert(config);
		
		//插入失败
		if(i < 0){
			return;
		}
	}

	@Override
	public List<Config> retrieveAll() {
		List<Config> config = configMapper.selectAll();
		System.out.println(config);
		return config;
	}

	@Override
	public void update(Config config) {
		config = new Config();
		config.setDescription("lkjhgf");
		config.setName("kjhgf");
		config.setSysName("poiuyt");
		config.setValue("qert");
		config.setId(6);
		
		int i = configMapper.updateById(config);
		
		//更新失败
		if(i < 1){
			return;
		}
	}

	@Override
	public void delete(Config config) {
		config = new Config();
		config.setId(8);
		
		int i = configMapper.deleteById(config);
		
		//删除失败
		if(i < 1){
			return;
		}
	}
	
}
