package tm.com;

public class TmClusterQryLib {
	private String faceLibPath;
	static 
	{
		try 
		{
			//System.loadLibrary("tmJavaClusterQryLib"); // call dll
			//TmClusterQryLib.init("D:\\FaceProgram\\dycluster",1);
		}
		catch (UnsatisfiedLinkError e) 
		{
			System.out.println("The load problem");
			e.printStackTrace();
		}
	}
	
	
	//
	public static native int test(int i, int n);

	/**
	 * JNI 初始化方法,必须提前加载
	 * @param strpath
	 * @param num
	 * @return
	 */
	public static native int init(String strpath,int num);
		
	public static native int uninit();

	/**
	 * JNI提取图片特征的接口
	 * @param strfile 文件地址
	 * @return 包含特征信息的json字符串
	 */
	public static native String faceextract(int chanel,String strfile);
	
	public static native float facecomparefeat(String strFeat1, String strFeat2);
	
	public static native float facecompareimage(String strfile1, String strfile2);
	
	public static native String qrybyimage(String strfile, float filimt, int total);
	
	public static native String qrybyfeat(String strFeat, float filimt, int total);

	
	public static void main(String[] args) 
	{
		// System.out.println(test(6, 5));
		String s= TmClusterQryLib.faceextract(0,"C:\\Users\\cheng\\Pictures\\2017-11\\030.jpg");
		System.out.println(s);
		}
}
