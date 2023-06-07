package cn.xiaobaicai.cabbage_ptms_backend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 实习报告封装类
 * @author hetongxue
 * @date 2023/2/12 - 16:58
 */
@Data
public class InterReportVo implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 报告名
     */
    private String reportName;

    /**
     * 上传时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

}
