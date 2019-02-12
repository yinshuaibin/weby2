package com.ferret.dao.provider;

import java.util.Set;

public class StaticMapperProvider {
	
	/*
	 * @see 拼接字符
	 */
	public String placeQs(Set<Long> ids){
		StringBuilder sqlString = new StringBuilder();
		int i = 1;
		if (ids.size()>0) {
			sqlString.append("SELECT tb_persons.idcard,tb_featurelibrary.LibraryName,tb_persons.name,tb_persons.record,tb_persons.lasttime,tb_persons.featureid "
					+ "FROM tb_persons,tb_featurelibrary WHERE tb_persons.LibraryNameInline=tb_featurelibrary.LibraryNameInline and tb_persons.featureid in(");
			for (Long long1 : ids) {
				sqlString.append(long1);
				if(i+1 < ids.size()){
			    	sqlString.append(",");
			    }
			}
			sqlString.append(")");
		}
		return sqlString.toString();
	}
	
}
