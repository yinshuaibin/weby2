package com.ferret.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Authc {

	private String authorityId;
	private String authorityName;
	private String authorityUrl;
	private String authorityDesc;
	private Integer authoritySequence;
	private boolean enabled;
	private String menuName;
	private String iconType;
	private String title;
	private boolean expand;
	private String parentId;
	private String moduleUrl;
	private List<Authc> children;
	
	public Authc() {
	}
	public Authc(String authorityId) {
		this.authorityId = authorityId;
	}
	
	
	public void copy2Fields() {
		this.title=this.authorityName;
		this.expand=false;
		this.children=new ArrayList();
	}
}
