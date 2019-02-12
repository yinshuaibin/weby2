package com.ferret.bean.searchimage;

import lombok.Data;

@Data
public class PeopleResult  implements Comparable{
	private String taskId;
	private String sysId;
	private String idCard;
	private Float similay;
	private int listNum;
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		PeopleResult rs = (PeopleResult) o;
		int i=0;
		if (this.getSimilay()>rs.getSimilay()) {
			i=1;
		}else if (this.getSimilay()<rs.getSimilay()) {
			i=-1;
		}
		return 0;
	}
}
