package cn.xiaobaicai.cabbage_ptms_backend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 分页查询实习文档信息请求
 * @author hetongxue
 */
@Data
public class InterDocVo implements Serializable {

    /**
     * 查询的总数
     */
    private Long total;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档内容
     */
    private String content;

    /**
     * 文档状态
     */
    private Integer state;

    /**
     * 提交文档的学生姓名
     */
    private String stuName;

    /**
     * 文档提交时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

}
