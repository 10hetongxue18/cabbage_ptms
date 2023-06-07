package cn.xiaobaicai.cabbage_ptms_backend.model.vo;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbStuInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询学生信息请求
 * @author hetongxue
 */
@Data
public class StudentInfoVo implements Serializable {

    /**
     * 查询的总数
     */
    private Long total;

    /**
     * 查询的数据
     */
    private List<TbStuInfo> tbStuInfo;

}
