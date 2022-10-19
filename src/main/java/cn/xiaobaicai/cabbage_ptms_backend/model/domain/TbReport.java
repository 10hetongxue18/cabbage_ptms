package cn.xiaobaicai.cabbage_ptms_backend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实习报告表
 * @TableName tb_report
 */
@TableName(value ="tb_report")
@Data
public class TbReport implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 报告状态（0-待审核 1-未通过 2-通过）
     */
    @TableField(value = "repState")
    private Byte repState;

    /**
     * id
     */
    @TableField(value = "userId")
    private Long userId;

    /**
     * 报告主题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 报告内容
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