package cn.xiaobaicai.cabbage_ptms_backend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 返回所有实习岗位封装类
 * @author hetongxue
 */
@Data
public class AllPostInfoVo {

    /**
     * 实习基地展示图
     */
    private String picture;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 详细信息
     */
    private String message;

    /**
     * 实习地址
     */
    private String address;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

}
