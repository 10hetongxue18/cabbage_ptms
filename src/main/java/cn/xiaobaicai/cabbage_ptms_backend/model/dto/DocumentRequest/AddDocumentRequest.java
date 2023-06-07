package cn.xiaobaicai.cabbage_ptms_backend.model.dto.DocumentRequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 提交文档请求
 * @author hetongxue
 */
@Data
public class AddDocumentRequest implements Serializable {

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档内容
     */
    private String content;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 文档状态
     */
    private Integer state;


}
