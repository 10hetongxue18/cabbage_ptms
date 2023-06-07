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
 * 企业指导老师信息表
 * @author hetongxue
 * @TableName tb_ent_tea
 */
@TableName(value ="tb_ent_tea")
@Data
public class TbEntTea implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 企业id
     */
    @TableField(value = "entId")
    private Integer entId;

    /**
     * 企业名称
     */
    @TableField(value = "entName")
    private String entName;

    /**
     * 企业工号id
     */
    @TableField(value = "entLoginId")
    private String entLoginId;

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
     * 部门id
     */
    @TableField(value = "depId")
    private String depId;

    /**
     * 部门
     */
    @TableField(value = "department")
    private String department;

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