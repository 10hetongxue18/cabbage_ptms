package cn.xiaobaicai.cabbage_ptms_backend.mapper;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbInterInfo;
import cn.xiaobaicai.cabbage_ptms_backend.model.vo.InterInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 学生实习信息
 * @author hetongxue
 * @Entity cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbInterInfo
 */
public interface TbInterInfoMapper extends BaseMapper<TbInterInfo> {

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




