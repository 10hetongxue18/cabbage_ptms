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
 * 实习申请表
 * @author hetongxue
 * @TableName tb_application
 */
@TableName(value ="tb_application")
@Data
public class TbApplication implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学号
     */
    @TableField(value = "stuId")
    private String stuId;

    /**
     * 新闻id
     */
    @TableField(value = "newsId")
    private Long newsId;

    /**
     * 基地id
     */
    @TableField(value = "areaId")
    private Integer areaId;

    /**
     * 企业联系人Id
     */
    @TableField(value = "entId")
    private Integer entId;

    /**
     * 自我简介
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * (0-未申请 1-已申请 2-未通过 3-已通过)
     */
    @TableField(value = "state")
    private Byte state;

    /**
     * 申请时间
     */
    @TableField(value = "createTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}