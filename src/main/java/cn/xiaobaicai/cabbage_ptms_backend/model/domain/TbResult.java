package cn.xiaobaicai.cabbage_ptms_backend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实习成绩与评价表
 * @TableName tb_result
 */
@TableName(value ="tb_result")
@Data
public class TbResult implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 报告id
     */
    @TableField(value = "reportId")
    private Long reportId;

    /**
     * 实习成绩
     */
    @TableField(value = "score")
    private String score;

    /**
     * 评价
     */
    @TableField(value = "appraise")
    private String appraise;

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