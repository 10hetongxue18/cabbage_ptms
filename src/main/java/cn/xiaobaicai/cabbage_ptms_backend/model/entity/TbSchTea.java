package cn.xiaobaicai.cabbage_ptms_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 校内指导老师信息表
 * @author hetongxue
 * @TableName tb_sch_tea
 */
@TableName(value ="tb_sch_tea")
@Data
public class TbSchTea implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 工号
     */
    @TableField(value = "workId")
    private String workId;

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
     * 系部id
     */
    @TableField(value = "depId")
    private String depId;

    /**
     * 专业id
     */
    @TableField(value = "proId")
    private String proId;

    /**
     * 用户角色（0-学生 1-老师 2-管理员）
     */
    @TableField(value = "userRole")
    private Integer userRole;

    /**
     * 老师类别（0-学校指导老师 1-企业指导老师）
     */
    @TableField(value = "teaRole")
    private Integer teaRole;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}