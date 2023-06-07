package cn.xiaobaicai.cabbage_ptms_backend.model.dto.FileRequest;

import lombok.Data;

/**
 * 更改文件状态请求
 * @author hetongxue
 */
@Data
public class FileUpdateRequest {

    /**
     * id
     */
    private Integer id;

    /**
     * 是否可见（0-不可见 1-可见）
     */
    private Byte isVisible;

}
