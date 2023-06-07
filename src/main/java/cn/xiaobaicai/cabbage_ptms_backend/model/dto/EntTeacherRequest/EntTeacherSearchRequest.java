package cn.xiaobaicai.cabbage_ptms_backend.model.dto.EntTeacherRequest;

import lombok.Data;

import java.io.Serializable;


/**
 * 搜索企业老师信息请求
 * @author hetongxue
 */
@Data
public class EntTeacherSearchRequest implements Serializable {

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
     * 企业名称
     */
    private String entName;


}
