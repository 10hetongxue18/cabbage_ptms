package cn.xiaobaicai.cabbage_ptms_backend.model.dto.ApplicationRequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改实习申请状态请求
 * @author hetongxue
 */
@Data
public class UpdateApplicationRequest implements Serializable {

    /**
     * 实习申请表id
     */
    private Integer applicationId;

    /**
     * 学生学号
     */
    private String stuId;

}
