package com.ferret.bean.InterfaceBean;

public class InterfaceSearchParam {
    /**
     * 接口调用路径参数
     */
    public static final String FACE_SEARCH = "/FaceSearch";

    /*
     *接口调用方法
     */
    public static  final  String METHOD="method";

    /**
     * 工具类用来判断接口返回至是否正确
     */
    public static final String  SUCCESS = "success";

    /**
     * 1:1接口请求参数
     */
    public static  final String COMPARE = "Compare";

    /**
     * 人脸提取接口请求参数
     */
    public static final String FEATURE = "Feature";

    /**
     * 人员信息修改接口请求参数
     */
    public static final String EDIT_BY_BUSINESS_ID = "editByBusinessid";

    /**
     * 人员库唯一业务id
     */
    public static final String BUSINESSID = "businessid";

    /**
     * 人员库批量新增请求
     */
    public static final String ADD_LIST = "addlist";

    /**
     * 人员库单个删除请求
     */
    public static final String REMOVE_BY_BUSINESS_ID = "removeByBusinessid";

    /**
     * 人员库自增id批量删除
     */
    public static final String REMOVE_BY_IDS = "remove";

    /**
     * 批量删除接口请求参数
     */
    public static final String REMOVE_IDS = "ids";

    /**
     *人脸搜索请求参数
     */
    public static final String FILE_1 = "file1";

    /**
     *人脸搜索请求参数
     */
    public static final String FILE_2 = "file2";

    /**
     * 人员库批量新增请求参数
     */
    public static final String ADD_DATA_LIST = "datalist";

    /**
     * 阈值
     */
    public static final String THRESHOLD = "threshold";
    /**
     * 动态检索重新加载请求参数
     */
    public static final String FEATURE_RE_LOAD = "FeatureReload";
    public static final String LOAD_TYPE = "LoadType";
    public static final String LOAD_TYPE_VALUE_ALL = "ALL";
    public static final String LOAD_TYPE_VALUE_REAL = "Real";
    /**
     * 动态检索
     */
    public static final String TRAILS = "Trails";
    public static final String CAMERA_ID_LIST = "CameraID";  // 相机列表
    public static final String PROJECT_NAME = "ProjectName"; // 项目名称
    public static final String YEAR_BEGIN = "YearBegin";     // 开始年份
    public static final String MONTH_BEGIN = "MonthBegin";   // 开始月份
    public static final String DAY_BEGIN = "DayBegin";       // 开始日期
    public static final String YEAR_END = "YearEnd";         // 结束年份
    public static final String MONTH_END = "MonthEnd";       // 结束月份
    public static final String DAY_END = "DayEnd";           // 结束日期
    /**
     * 人脸比对服务重新加载人脸库请求
     */
    public static final String MEMORY_FEATURE_RELOAD_FORM_DATABASE = "MemoryFeatureReloadFormDatabase";

}
