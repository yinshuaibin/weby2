package com.ferret.utils.readCluster.base;

import com.ferret.utils.readCluster.bean.Sysconf;
import com.ferret.utils.readCluster.tools.CalendTools;

public class ConfBase {
	public static Sysconf config=new Sysconf();
	public static int customTotal=0;
	public static int lastTotal=0;
	public static String stimeStr=CalendTools.getTimeString("yyyy-MM-dd HH:mm:ss");
}
