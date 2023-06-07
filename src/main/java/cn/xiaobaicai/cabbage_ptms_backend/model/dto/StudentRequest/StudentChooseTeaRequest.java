package cn.xiaobaicai.cabbage_ptms_backend.model.dto.StudentRequest;

import lombok.Data;

import java.io.Serializable;


/**
 * 学生选择实习老师请求
 * @author hetongxue
 */
@Data
public class StudentChooseTeaRequest implements Serializable {

    /**
     * 老师id
     */
    private Integer teacherId;

}
