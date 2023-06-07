package cn.xiaobaicai.cabbage_ptms_backend.model.dto.DeleteRequest;


import lombok.Data;

import java.io.Serializable;

/**
 * 删除信息请求
 *
 * @author hetongxue
 */
@Data
public class IntegerDeleteRequest implements Serializable {

    /**
     * id
     */
    private Integer id;

}
