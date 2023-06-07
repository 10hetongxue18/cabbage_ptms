package cn.xiaobaicai.cabbage_ptms_backend.model.dto.EntTeacherRequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 添加企业老师信息请求
 *
 * @author hetongxue
 */
@Data
public class EntTeacherAddRequest implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 企业工号
     */
    private String entLoginId;

    /**
     * 企业id
     */
    private Integer entId;

    /**
     * 企业名称
     */
    private String entName;

    /**
     * 部门名称
     */
    private String department;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String phone;


}
