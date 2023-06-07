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
 * 实习报告表
 * @author hetongxue
 * @TableName tb_inter_report
 */
@TableName(value ="tb_inter_report")
@Data
public class TbInterReport implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 实习文档id
     */
    @TableField(value = "docId")
    private Integer docId;

    /**
     * 企业老师签名区
     */
    @TableField(value = "entTeaName")
    private String entTeaName;

    /**
     * 校内老师签名区
     */
    @TableField(value = "stuTeaName")
    private String stuTeaName;

    /**
     * 报告状态 (0-未通过 1-已通过)
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 实习报告成绩
     */
    @TableField(value = "result")
    private String result;

    /**
     * 实习单位基本情况
     */
    @TableField(value = "oneTextArea")
    private String oneTextArea;

    /**
     * 实习岗位与实习任务
     */
    @TableField(value = "twoTextArea")
    private String twoTextArea;

    /**
     * 实习体会（总结）
     */
    @TableField(value = "threeTextArea")
    private String threeTextArea;

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