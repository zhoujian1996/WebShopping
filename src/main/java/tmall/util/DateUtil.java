package tmall.util;

/**
 * @author:zhoujian
 * @date:2019/10/9 0009 16:
 * DateUtil这个日期
 * 用于java.util.Date类与java.sql.Timestamp类的相互转换
 */
public class DateUtil {

    public static java.sql.Timestamp d2t(java.util.Date d) {
        if (null == d)
            return null;
        return new java.sql.Timestamp(d.getTime());
    }

    public static java.util.Date t2d(java.sql.Timestamp t) {
        if (null == t)
            return null;
        return new java.util.Date(t.getTime());
    }
}
