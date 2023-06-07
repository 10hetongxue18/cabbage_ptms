package cn.xiaobaicai.cabbage_ptms_backend.util;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Date转LocalDateTime工具类
 * @author hetongxue
 */
@Data
public class DateTransLocalDate {

    public static LocalDateTime transLocalDateTime(Date date){
        /*获取时间实例*/
        Instant instant = date.toInstant();
        /*获取时间地区ID*/
        ZoneId zoneId = ZoneId.systemDefault();
        /*转换为LocalDate*/
        LocalDateTime localDateTime=instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

}
