package cn.xiaobaicai.cabbage_ptms_backend.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 实习计划详情信息请求
 * @author hetongxue
 */
@Data
public class FileDetailInfoVo implements Serializable {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 上传时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    /**
     * 老师姓名
     */
    private String name;


}
