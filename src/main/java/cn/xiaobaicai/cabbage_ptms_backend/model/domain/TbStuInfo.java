package cn.xiaobaicai.cabbage_ptms_backend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生信息表
 * @TableName tb_stu_info
 */
@TableName(value ="tb_stu_info")
@Data
public class TbStuInfo implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 性别（0-男 1-女）
     */
    @TableField(value = "gender")
    private Byte gender;

    /**
     * 年龄
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 出生年月
     */
    @TableField(value = "dateBirth")
    private Date dateBirth;

    /**
     * 联系电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 学号（学生的登录账号）
     */
    @TableField(value = "stuId")
    private String stuId;

    /**
     * 学校
     */
    @TableField(value = "school")
    private String school;

    /**
     * 院系
     */
    @TableField(value = "faculty")
    private String faculty;

    /**
     * 专业
     */
    @TableField(value = "major")
    private String major;

    /**
     * 年级
     */
    @TableField(value = "grade")
    private String grade;

    /**
     * 班级
     */
    @TableField(value = "classes")
    private String classes;

    /**
     * 校内指导老师id
     */
    @TableField(value = "schTeaId")
    private Integer schTeaId;

    /**
     * 企业指导老师id
     */
    @TableField(value = "entTeaId")
    private Integer entTeaId;

    /**
     * 用户角色 0-学生 1-老师 2-管理员
     */
    @TableField(value = "userRole")
    private Integer userRole;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private Date updateTime;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}