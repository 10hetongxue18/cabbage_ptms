package cn.xiaobaicai.cabbage_ptms_backend.mapper;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbEntTea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 企业指导老师信息
 * @author hetongxue
 * @Entity cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbEntTea
 */
public interface TbEntTeaMapper extends BaseMapper<TbEntTea> {

    /**
     * 模糊搜索企业老师信息
     * @param name
     * @param gender
     * @param entLoginId
     * @param entName
     * @return
     */
    List<TbEntTea> getSearchEntTeacherInfo(String name, Byte gender, String entLoginId, String entName);
}




