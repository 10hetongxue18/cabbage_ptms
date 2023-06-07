package cn.xiaobaicai.cabbage_ptms_backend.model.dto.EntTeacherRequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 企业老师信息更新请求
 *
 * @author hetongxue
 */
@Data
public class EntTeacherUpdateRequest implements Serializable {

    /**
     * id
     */
    private Integer id;

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
     * 企业Id
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
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;



}
