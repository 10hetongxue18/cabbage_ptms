package cn.xiaobaicai.cabbage_ptms_backend.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询实习新闻信息
 * @author hetongxue
 */
@Data
public class ListNewsInfoVo implements Serializable {

    /**
     * 新闻id
     */
    private Long id;

    /**
     * 公司图片
     */
    private String picture;

    /**
     * 公司名字
     */
    private String entName;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 详细信息
     */
    private String message;

}
