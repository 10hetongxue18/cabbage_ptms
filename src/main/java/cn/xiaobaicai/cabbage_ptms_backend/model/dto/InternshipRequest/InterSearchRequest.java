package cn.xiaobaicai.cabbage_ptms_backend.model.dto.InternshipRequest;

import lombok.Data;

import java.io.Serializable;


/**
 * 搜索实习信息请求（tb_user and tb_inter_info）
 * @author hetongxue
 */
@Data
public class InterSearchRequest implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 实习状态
     */
    private String state;

    /**
     * 实习地址
     */
    private String address;

    /**
     * 企业名称
     */
    private String entName;


}
