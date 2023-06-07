package cn.xiaobaicai.cabbage_ptms_backend.model.dto.SchTeacherRequest;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hetongxue
 * @date 2023/2/15 - 11:58
 */
@Data
public class SchTeaSubmitTotalScore implements Serializable {

    /**
     * 学生学号
     */
    private String stuId;

    /**
     * 实习记录
     */
    private Integer record;

    /**
     * 实习报告
     */
    private Integer report;

    /**
     * 综合评价
     */
    private Integer evaluate;

    /**
     * 实习综合成绩
     */
    private String resultNum;

}
