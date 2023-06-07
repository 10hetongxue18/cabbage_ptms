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
 * 日常文档（实习计划表）
 * @author hetongxue
 * @TableName tb_document
 */
@TableName(value ="tb_document")
@Data
public class TbDocument implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件路径
     */
    @TableField(value = "fileUrl")
    private String fileUrl;

    /**
     * 文件名
     */
    @TableField(value = "fileName")
    private String fileName;

    /**
     * 上传者
     */
    @TableField(value = "uploader")
    private String uploader;

    /**
     * 下载次数
     */
    @TableField(value = "downloadNum")
    private Integer downloadNum;

    /**
     * 是否可见
     */
    @TableField(value = "isVisible")
    private Byte isVisible;

    /**
     * 上传时间
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