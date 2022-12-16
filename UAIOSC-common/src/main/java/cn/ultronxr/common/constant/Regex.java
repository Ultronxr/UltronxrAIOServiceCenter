package cn.ultronxr.common.constant;

/**
 * @author Ultronxr
 * @date 2022/12/15 22:26
 * @description 常量 - 正则表达式
 */
public class Regex {

    /** 内网IPv4地址 */
    public static final String IPV4_INTRANET = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";

    /** 内网网址（不带协议标识符） */
    public static final String URL_INTRANET = "^localhost.*";

    /** 日期（yyyy-MM-dd 或 yyyy-M-d） */
    public static final String DATE = "\\d{4}(-)(1[0-2]|0?\\d)\\1([0-2]\\d|\\d|30|31)";

    /** 24小时制时间（HH:mm:ss） */
    public static final String TIME_24 = "(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d";

    /** 12小时制时间（HH:mm:ss） */
    public static final String TIME_12 = "(?:1[0-2]|0?[1-9]):[0-5]\\d:[0-5]\\d";

    /**
     * 整数区间表达式，包含正负无穷
     * 待匹配的字符串需要去掉所有空格；区间左右两边的数字正负与大小需要额外代码判断，这里的正则没有约束
     *   可以匹配如下字符串：
     *     [11,22]  (-11,+22]  (-11,-22)  [22,-11]  (-11,-22)  [-11,+∞)  (-∞,+∞)
     *   不能匹配如下字符串（正负无穷和区间开闭括号不规范）：
     *     [-∞,+∞]  (∞,∞)  (-∞,∞)
     */
    public static final String MATH_INTERVAL = "([\\[(]([-+]?\\d+)|\\((-∞)),(([-+]?\\d+)[])]|(\\+∞)\\))";

    /**
     * 以所有 不被包围在引号内的空格 分割字符串<br/>
     *      例：aa bb cc 'dd ee' ff gg "hh ii"<br/>
     * 匹配结果：["aa","bb","cc","'dd ee'",ff,gg,"\"hh ii\""]
     */
    public static final String SPLIT_BY_SPACES_NOT_IN_QUOTES = "[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";

}
