package cn.xiaobaicai.cabbage_ptms_backend.model.dto.StudentRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生信息更新请求
 *
 * @author hetongxue
 */
@Data
public class StudentUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 出生年月
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date dateBirth;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 学校
     */
    private String school;

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

}
