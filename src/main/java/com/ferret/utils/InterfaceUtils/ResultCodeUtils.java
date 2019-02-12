package com.ferret.utils.InterfaceUtils;

/**
 * 接口返回错误码条件判断
 */
public class ResultCodeUtils {
    public static String errMessage(String resultCode,String Status){
        switch (resultCode+"-"+Status){
            case "3-901": return "content-type只允许使用application/json，不得包含其他字符，使用UTF-8字符编码";
            case "3-902": return "没有CONTENT_LENGTH变量";
            case "3-903": return "CONTENT_LENGTH格式错误";
            case "3-904": return "POST数据太大";
            case "4-905": return "JSON解析错误";
            case "4-910": return "没有method字段";
            case "3-921": return "进程锁打开错误";
            case "3-922": return "进程锁加锁错误";
            case "3-923": return "有进程在解锁前种植";
            case "3-945": return "加载人员库为0条或者正在重新加载人员库";
            case "3-971": return "没有配置数据库类型或者不支持的数据库类型";
            case "4-999": return "不支持的method";
            case "4-801": return "没有找到第一张图片";
            case "4-802": return "第一张图片BASE64解码错误";
            case "4-811": return "没有找到第二张图片或者没找到threshold";
            case "4-812": return "第二张图片BASE64解码错误";
            case "4-831": return "人员库增加没有找到图片列表";
            case "4-832": return "人员库增加图片列表格式错误";
            case "4-833": return "人员库增加图片列表项空";
            case "4-834": return "人员库增加图片列表项没有图片";
            case "4-835": return "人员库增加图片列表项没有业务ID";
            case "4-836": return "人员库增加图片列表项没有文件名";
            case "3-837": return "人员库增加图片保存错误";
            case "4-841": return "没找到删除列表";
            case "4-851": return "没找到需要修改记录id";
            case "4-855": return "没有值";
            case "4-861": return "没找到查询pagesize";
            case "4-862": return "没找到查询pagenum";
            case "5-301": return "第一张图片格式错误";
            case "5-311": return "第一张图片没有找到人脸";
            case "5-313": return "第一张图片分配人员内存错误";
            case "5-315": return "图片质量太差";
            case "5-316": return "图片人脸位置不准";
            case "5-317": return "图片人脸偏转角度太大";
            case "5-321": return "第二张图片格式错误";
            case "5-331": return "第二张图片没有找到人脸";
            case "5-332": return "第二张图片不止一张脸";
            case "5-333": return "第二张图片分配人员内存错误";
            case "5--199": return "第一张图片invalid license.";
            case "5--101": return "第一张图片nChannelID is invalid or SDK is not initialize";
            case "5--102": return "第一张图片image data is invalid,please check function parameter:pImage,bpp,nWidth,nHeight";
            case "5--103": return "第一张图片pfps or nMaxFaceNums is invalid";
            case "5--399": return "第一张图片invalid license";
            case "5--301": return "第一张图片pBuf,ptfp,pFeature is NULL";
            case "5--302": return "第一张图片nChannelID is invalid or SDK is not initialized";
            case "5--599": return "第二张图片invalid license";
            case "5--501": return "第二张图片nChannelID is invalid or SDK is not initialize";
            case "5--502": return "第二张图片image data is invalid,please check function parameter:pImage,bpp,nWidth,nHeight";
            case "5--503": return "第二张图片pfps or nMaxFaceNums is invalid";
            case "5--799": return "第二张图片invalid license";
            case "5--701": return "第二张图片pBuf,ptfp,pFeature is NULL";
            case "5--702": return "第二张图片nChannelID is invalid or SDK is not initialized";
            case "6-701": return "MySQL初始化错误";
            case "6-702": return "MySQL连接错误";
            case "6-711": return "MySQL查询错误";
            case "6-712": return "MySQL获取结果错误";
            case "6-713": return "MySQL行数获取错误";
            case "6-714": return "MySQL获取行记录错误";
            case "6-715": return "MySQL行数太大";
            case "6-721": return "MySQL查询错误";
            case "6-722": return "MySQL获取结果错误";
            case "6-723": return "字段长度错误";
            case "6-729": return "获取数据不符合行数";
            case "6-731": return "MySQL插入错误";
            case "6-732": return "插入行数错误";
            case "6-741": return "MySQL删除错误";
            case "6-742": return "删除行数错误(已经删除)";
            case "6-751": return "MySQL修改错误";
            case "6-752": return "修改行数错误(图片名称,业务id等不允许修改;创建时间,自增id修改无效!)";
            case "6-761": return "MySQL查询错误";
            case "6-762": return "MySQL查询行数错误";
            default:return "success";
        }
    }
}
