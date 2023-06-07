package cn.xiaobaicai.cabbage_ptms_backend.service;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbEntTea;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 企业指导老师信息
 * @author hetongxue
 */
public interface TbEntTeaService extends IService<TbEntTea> {

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
