package com.ferret.utils.staticUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;
import net.sf.json.JSONObject;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.log4j.Logger;


public class ConfigUtil {
	// 读取配置文件
	public static PropertiesConfiguration config = null;

	// 获取项目host地址
	private static String hostname = getLocalHost();	// 本机IP
	private static String port ;					// 项目端口
	private static String urlPrefix;					// IP + port

	// 常量
	// 上传文件的绝对地址目录
	public static String img_abs_path;				

	// 访问文件相对地址
	public static String img_rel_path;

	// 项目相对地址
	public static String project_name;		// 项目名称,一般默认设置为face
	public static String project_addr;		// urlPrefix + project_name

	// 服务器或用户所在区域,local一般是省厅,remote则是县市地区
	public static String user_district;

	// 人像对比服务tcp接口
	public static String socket_conn;

	// 图片库id也是用户权限id
	public static String library;	// 配置库
	public static String library_ids;	// 库id,对应省id,也对应用户权限
	public static String library_json;	// 库id:库名称的json格式数据

	// 备用地址
	public static String upload_dir;
	public static String replace_dir;

	// 数据库连接参数
	public static String driverClass;
	public static String jdbcUrl;
	public static String user;
	public static String password;

	public static int maxStatements;
	public static int maxStatementsPerConnection;
	public static int acquireIncrement;
	public static int initialPoolSize;
	public static int maxPoolSize;
	public static int maxIdleTime;
	public static int minPoolSize;
	public static int checkoutTimeout;


	//分享图片保存目录
	public static String shareRelativeDir = "share/"+ DateUtil.getYear();
	public static String shareDir = img_abs_path +"/"+shareRelativeDir;

	// 初始化对应的静态变量
	public static void initParams(){
		// 获取项目host地址
		port = config.getString("port");
		// tomcat服务绑定80端口
		urlPrefix = "http://" + hostname ;

		// 常量
		// 上传文件的绝对地址目录
		img_abs_path = config.getString("img_abs_path");

		// 替换绝对路径的相对地址
		img_rel_path = urlPrefix + ":"+ port + "/"+ "static";

		// 项目相对地址
		project_name = config.getString("project_name");
		project_addr = urlPrefix +":80/"+ project_name;

		// 服务器或用户所在区域,local一般是省厅,remote则是县市地区
		user_district = config.getString("user_district");

		// 人像对比服务tcp接口
		socket_conn = config.getString("socket_conn");

		// 图片库id也是用户权限id
		library = config.getString("library");
		String[] libs = getIds(library).split("&");
		library_ids = libs[0];
		library_json = libs[1];

		// 备用地址
		upload_dir = config.getString("upload_dir");
		replace_dir = config.getString("replace_dir");

		// 数据库连接参数
		jdbcUrl = config.getString("jdbcUrl");

		driverClass = config.getString("driverClass");
		user = config.getString("user");
		password = config.getString("password");
		maxStatements = config.getInt("maxStatements");
		maxStatementsPerConnection = config.getInt("maxStatementsPerConnection");
		acquireIncrement = config.getInt("acquireIncrement");
		initialPoolSize = config.getInt("initialPoolSize");
		maxPoolSize = config.getInt("maxPoolSize");
		maxIdleTime = config.getInt("maxIdleTime");
		minPoolSize = config.getInt("minPoolSize");
		checkoutTimeout = config.getInt("checkoutTimeout");
	}
	
	// 获取权限id和库类别json字符串
	public static String getIds(String library) {
		String ids = "";
		Map<String, String> map = new TreeMap<String, String>();
		String[] lib = library.split(",");
		for (int i = 0; i < lib.length; i++) {
			String[] obj = lib[i].split(":");
			map.put(obj[0], obj[1]);
			ids += obj[0] + ",";
		}
		String json = JSONObject.fromObject(map).toString();
		ids = ids.substring(0, ids.length() - 1);
		return ids + "&" + json;
	}

	// 获取本机IP地址
	public static String getLocalHost() {
		String addr = "";
		try {
			addr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return addr;
	}
}
