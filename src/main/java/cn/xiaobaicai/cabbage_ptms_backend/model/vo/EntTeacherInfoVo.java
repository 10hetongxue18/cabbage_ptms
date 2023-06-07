package cn.xiaobaicai.cabbage_ptms_backend.model.vo;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbEntTea;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbSchTea;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询企业老师信息请求
 * @author hetongxue
 */
@Data
public class EntTeacherInfoVo implements Serializable {

    /**
     * 查询的总数
     */
    private Long total;

    /**
     * 查询的数据
     */
    private List<TbEntTea> tbEntTeas;

}
