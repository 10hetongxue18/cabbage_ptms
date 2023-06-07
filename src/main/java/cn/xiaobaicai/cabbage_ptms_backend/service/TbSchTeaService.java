package cn.xiaobaicai.cabbage_ptms_backend.service;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbSchTea;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 校内指导老师信息
 * @author hetongxue
 */
public interface TbSchTeaService extends IService<TbSchTea> {

    /**
     * 模糊搜索老师信息
     * @param name
     * @param gender
     * @param workId
     * @param faculty
     * @return
     */
    List<TbSchTea> getSearchSchTeacherInfo(String name, Byte gender, String workId, String faculty);

    /**
     * 获取当前登录老师信息
     * @param request
     * @return
     */
    TbSchTea getSchTeacherInfo(HttpServletRequest request);
}
