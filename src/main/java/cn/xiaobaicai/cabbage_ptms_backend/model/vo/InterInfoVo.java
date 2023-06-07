package cn.xiaobaicai.cabbage_ptms_backend.model.vo;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbInterInfo;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbStuInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 分页查询学生实习信息请求
 * @author hetongxue
 */
@Data
public class InterInfoVo implements Serializable {

    /**
     * 查询的总数
     */
    private Long total;

    /**
     * 查询的实习数据
     */
    private List<TbInterInfo> tbInterInfos;

    /**
     * 查询的学生数据
     */
    private List<TbStuInfo> tbStuInfos ;

}
