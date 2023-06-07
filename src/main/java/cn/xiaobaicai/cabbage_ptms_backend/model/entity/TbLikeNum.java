package cn.xiaobaicai.cabbage_ptms_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 留言点赞记录表
 * @TableName tb_like_num
 */
@TableName(value ="tb_like_num")
@Data
public class TbLikeNum implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 留言id
     */
    @TableField(value = "guestId")
    private Long guestId;

    /**
     * 创建用户id
     */
    @TableField(value = "userId")
    private Long userId;

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