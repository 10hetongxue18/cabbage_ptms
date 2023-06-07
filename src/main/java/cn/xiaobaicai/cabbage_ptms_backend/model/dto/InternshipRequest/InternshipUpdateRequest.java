package cn.xiaobaicai.cabbage_ptms_backend.model.dto.InternshipRequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 学生实习信息更新请求
 *
 * @author hetongxue
 */
@Data
public class InternshipUpdateRequest implements Serializable {

    /**
     * 实习地址
     */
    private String address;

    /**
     * 实习企业名称
     */
    private String entName;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 实习岗位
     */
    private String post;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 实习状态
     */
    private Integer state;


}
