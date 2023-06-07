package cn.xiaobaicai.cabbage_ptms_backend.mapper;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbStuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生基本信息
 * @author hetongxue
 * @Entity cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbStuInfo
 */
public interface TbStuInfoMapper extends BaseMapper<TbStuInfo> {

    /**
     * 根据条件模糊搜索学生信息
     * @param name
     * @param gender
     * @param stuId
     * @return
     */
    List<TbStuInfo> getSearchStuInfo(String name, Byte gender, String stuId);

    /**
     * 根据条件模糊搜索班级信息
     * @param faculty
     * @param major
     * @param grade
     * @param classes
     * @return
     */
    List<TbStuInfo> getSearchClassInfo(String faculty, String major, String grade, String classes);
}




