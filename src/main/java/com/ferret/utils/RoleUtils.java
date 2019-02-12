package com.ferret.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ferret.bean.Authc;

public class RoleUtils {
	public static ArrayList<Authc> getChiled(List<Authc> authcs) {
		ArrayList<Authc> first = new ArrayList();
		ArrayList<Authc> second = new ArrayList();
		String str1;
		Authc pAuthc = null;
		for (Authc ac : authcs) {
			str1 = ac.getAuthorityId();
			if (str1.endsWith("00000") && !str1.endsWith("00000000")) {
				for (Authc ax : authcs) {
					if (StringUtils.equals(str1, ax.getParentId())) {
						ac.getChildren().add(ax);
					}
				}
				second.add(ac);
			}
		}

		for (Authc ac : authcs) {
			str1 = ac.getAuthorityId();
			if (str1.endsWith("00000000")) {
				for (Authc ax : second) {
					if (StringUtils.equals(str1, ax.getParentId())) {
						ac.getChildren().add(ax);
					}
				}
				first.add(ac);
			}
		}
		return first;
	}
}
