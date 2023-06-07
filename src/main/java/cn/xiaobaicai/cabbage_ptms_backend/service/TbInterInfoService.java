package cn.xiaobaicai.cabbage_ptms_backend.service;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbInterInfo;
import cn.xiaobaicai.cabbage_ptms_backend.model.vo.InterInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学生实习信息
 * @author hetongxue
 */
public interface TbInterInfoService extends IService<TbInterInfo> {

    /**
     * 获取学生实习信息
     * @param request
     * @return
     */
    TbInterInfo getInternshipInfo(HttpServletRequest request);

    /**
     * 模糊搜索学生实习信息
     * @param stuId
     * @param state
     * @param address
     * @param entName
     * @return
     */
    List<TbInterInfo> getSearchInterInfo(String stuId, String state, String address, String entName);
}
