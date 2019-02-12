package com.ferret.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SubImageInfo {
	@JsonProperty("SubImageInfoObject")
	private List<SubImageInfoObject> subImageInfoObject;
}
