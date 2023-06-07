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
 * 实习文档表
 * @author hetongxue
 * @TableName tb_inter_doc
 */
@TableName(value ="tb_inter_doc")
@Data
public class TbInterDoc implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文档标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 文档内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 学号
     */
    @TableField(value = "stuId")
    private String stuId;

    /**
     * 文档状态 (0-未提交 1-已提交)
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 提交时间
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