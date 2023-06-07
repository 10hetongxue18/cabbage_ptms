package cn.xiaobaicai.cabbage_ptms_backend.mapper;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbNews;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 新闻
 * @author hetongxue
 * @Entity cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbNews
 */
public interface TbNewsMapper extends BaseMapper<TbNews> {

    /**
     * 模糊搜索实习新闻
     * @param postName
     * @param message
     * @return
     */
    List<TbNews> getSearchNewsInfo(String postName, String message);
}




