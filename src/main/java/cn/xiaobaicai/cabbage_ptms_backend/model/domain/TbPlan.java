package cn.xiaobaicai.cabbage_ptms_backend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实习计划表
 * @TableName tb_plan
 */
@TableName(value ="tb_plan")
@Data
public class TbPlan implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建者
     */
    @TableField(value = "creator")
    private String creator;

    /**
     * 用户id
     */
    @TableField(value = "userId")
    private Long userId;

    /**
     * 计划名称
     */
    @TableField(value = "planName")
    private String planName;

    /**
     * 计划详情
     */
    @TableField(value = "content")
    private String content;

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