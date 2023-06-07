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
 * 实习基地表
 * @author hetongxue
 * @TableName tb_area
 */
@TableName(value ="tb_area")
@Data
public class TbArea implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 实习基地展示图片
     */
    @TableField(value = "picture")
    private String picture;

    /**
     * 实习基地地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 实习基地联系人
     */
    @TableField(value = "userId")
    private Long userId;

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