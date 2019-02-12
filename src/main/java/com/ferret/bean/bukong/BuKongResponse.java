package com.ferret.bean.bukong;

import com.ferret.bean.BuKong;

import lombok.Data;
@Deprecated
@Data
public class BuKongResponse {
	private String error;
	private String response;
	private BuKong reqInfo;
	private String Status;
}
