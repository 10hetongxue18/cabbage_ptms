package cn.xiaobaicai.cabbage_ptms_backend.model.dto.AdminRequest;

import lombok.Data;

import java.io.Serializable;


/**
 * 搜索班级信息请求
 * @author hetongxue
 */
@Data
public class ClassSearchRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 院系
     */
    private String faculty;

    /**
     * 专业
     */
    private String major;

    /**
     * 年级
     */
    private String grade;

    /**
     * 班级
     */
    private String classes;


}
