package cn.xiaobaicai.cabbage_ptms_backend.model.dto.SchTeacherRequest;

import lombok.Data;

import java.io.Serializable;


/**
 * 搜索校内老师信息请求
 * @author hetongxue
 */
@Data
public class SchTeacherSearchRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 工号
     */
    private String workId;

    /**
     * 院系
     */
    private String faculty;


}
