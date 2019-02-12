package com.ferret.bean.bukong;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RevokeBuKong {
	
	 private String bkId;
	 
	 private String cancelUser;

	 private String cancelReason;
	    
	 private String cancelContact;
	
}
