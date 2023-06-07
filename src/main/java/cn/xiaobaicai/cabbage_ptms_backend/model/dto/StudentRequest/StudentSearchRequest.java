package cn.xiaobaicai.cabbage_ptms_backend.model.dto.StudentRequest;
import lombok.Data;
import java.io.Serializable;


/**
 * 搜索学生信息请求
 * @author hetongxue
 */
@Data
public class StudentSearchRequest implements Serializable {

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
     * 学号
     */
    private String stuId;


}
