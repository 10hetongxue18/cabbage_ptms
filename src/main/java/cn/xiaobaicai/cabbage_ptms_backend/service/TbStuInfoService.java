package cn.xiaobaicai.cabbage_ptms_backend.service;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbStuInfo;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学生基本信息
 * @author hetongxue
 */
public interface TbStuInfoService extends IService<TbStuInfo> {

    /**
     * 获取当前登录学生信息
     * @param request
     * @return
     */
    TbStuInfo getStudentInfo(HttpServletRequest request);

    /**
     * 根据条件模糊搜索学生信息
     * @param name
     * @param gender
     * @param stuId
     * @return
     */
    List<TbStuInfo> getSearchStuInfo(String name,Byte gender,String stuId);

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
