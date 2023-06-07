package cn.xiaobaicai.cabbage_ptms_backend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 老师带领的实习学生提交的文档信息
 * @author hetongxue
 */
@Data
public class StuTeacherDocInfoVo implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别（0-男 1-女）
     */
    private Byte gender;

    /**
     * 学号（学生的登录账号）
     */
    private String stuId;

    /**
     * 院系
     */
    private String faculty;

    /**
     * 专业
     */
    private String major;

    /**
     * 年级
     */
    private String grade;

    /**
     * 班级
     */
    private String classes;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档内容
     */
    private String content;

    /**
     * 文档状态 (0-未提交 1-已提交)
     */
    private Integer state;

    /**
     * 提交时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

}
