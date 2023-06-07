package cn.xiaobaicai.cabbage_ptms_backend.model.dto.NewsRequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 添加实习新闻信息请求
 *
 * @author hetongxue
 */
@Data
public class NewsAddRequest implements Serializable {

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 实习信息
     */
    private String message;

    /**
     * 实习基地id
     */
    private Integer areaId;


}
