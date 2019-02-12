package com.ferret.service.config;

import java.util.List;

import com.ferret.bean.Config;

/**
 * CRUD系统配置项
 * @author zs
 *
 */
public interface ConfigService {
	
	/**
	 * 创建一条配置
	 * @param config
	 */
    void create(Config config);
	
	/**
	 * 查询出所有配置项
	 * @return
	 */
    List<Config> retrieveAll();
	
	/**
	 * 更新配置项
	 * @param config
	 */
    void update(Config config);
	
	/**
	 * 删除配置项
	 * @param config
	 */
    void delete(Config config);
	
	
}
