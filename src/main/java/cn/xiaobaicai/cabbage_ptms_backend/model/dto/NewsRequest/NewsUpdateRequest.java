package cn.xiaobaicai.cabbage_ptms_backend.model.dto.NewsRequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 实习新闻信息更新请求
 *
 * @author hetongxue
 */
@Data
public class NewsUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 实习详情
     */
    private String message;



}
