package cn.xiaobaicai.cabbage_ptms_backend.model.dto.StudentRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 添加学生信息请求
 *
 * @author hetongxue
 */
@Data
public class StudentAddRequest implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 出生年月
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date dateBirth;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 学号
     */
    private String stuId;

}
