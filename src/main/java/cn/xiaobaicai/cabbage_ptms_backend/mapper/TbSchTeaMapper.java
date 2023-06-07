package cn.xiaobaicai.cabbage_ptms_backend.mapper;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbSchTea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 校内指导老师信息
 * @author hetongxue
 * @Entity cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbSchTea
 */
public interface TbSchTeaMapper extends BaseMapper<TbSchTea> {

    /**
     * 模糊搜索老师信息
     * @param name
     * @param gender
     * @param workId
     * @param faculty
     * @return
     */
    List<TbSchTea> getSearchSchTeacherInfo(String name, Byte gender, String workId, String faculty);
}




