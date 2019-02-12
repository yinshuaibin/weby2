package com.ferret.bean.staticBean;

import lombok.Data;

@Data
public class FaceData {
	private String name;//姓名
	private String idcard;//身份证号
	private String LibraryName;
	private String record;
	private String lasttime;
	private String featureid;
	@Override
	public String toString() {
		return "FaceData [name=" + name + ", idcard=" + idcard + ", LibraryName=" + LibraryName + ", record=" + record
				+ ", lasttime=" + lasttime + ", featureid=" + featureid + "]";
	}
	public FaceData(String name, String idcard, String libraryName, String record, String lasttime, String featureid) {
		super();
		this.name = name;
		this.idcard = idcard;
		LibraryName = libraryName;
		this.record = record;
		this.lasttime = lasttime;
		this.featureid = featureid;
	}
	
}
