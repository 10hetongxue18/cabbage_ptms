package cn.xiaobaicai.cabbage_ptms_backend.model.dto.InternshipRequest;


import lombok.Data;


import java.io.Serializable;


/**
 * 实习文档评分请求
 *
 * @author hetongxue
 */
@Data
public class InterDocUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 文档状态
     */
    private String state;

    /**
     * 文档评分
     */
    private String remarks;



}
