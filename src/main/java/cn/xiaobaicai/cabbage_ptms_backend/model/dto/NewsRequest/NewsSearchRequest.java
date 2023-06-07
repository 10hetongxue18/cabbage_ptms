package cn.xiaobaicai.cabbage_ptms_backend.model.dto.NewsRequest;

import lombok.Data;

import java.io.Serializable;


/**
 * 搜索实习新闻信息请求
 * @author hetongxue
 */
@Data
public class NewsSearchRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 实习信息
     */
    private String message;

}
