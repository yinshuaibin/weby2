package com.ferret.utils;


public class ReadCamera {
	public static native  void cameraInit();
	public static native int getCameraInfo();
	public static native void cameraClose();
	static {
		System.loadLibrary("ReadCamera");
	}
	public static void main(String[] args) {
		ReadCamera.cameraInit();
		int ret=ReadCamera.getCameraInfo();
		System.out.println(ret);
		ReadCamera.cameraClose();
	}
}
