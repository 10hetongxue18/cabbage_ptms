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
 * 学生实习信息表
 * @author hetongxue
 * @TableName tb_inter_info
 */
@TableName(value ="tb_inter_info")
@Data
public class TbInterInfo implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 实习地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 实习企业名称
     */
    @TableField(value = "entName")
    private String entName;

    /**
     * 负责人
     */
    @TableField(value = "leader")
    private String leader;

    /**
     * 实习岗位
     */
    @TableField(value = "post")
    private String post;

    /**
     * 学号
     */
    @TableField(value = "stuId")
    private String stuId;

    /**
     * 实习状态 (0-未实习 1-实习中)
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 开始时间
     */
    @TableField(value = "createTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    /**
     * 结束时间
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