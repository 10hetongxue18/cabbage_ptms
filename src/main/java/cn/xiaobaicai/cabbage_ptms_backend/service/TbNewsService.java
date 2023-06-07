package cn.xiaobaicai.cabbage_ptms_backend.service;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbNews;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 新闻
 * @author hetongxue
 */
public interface TbNewsService extends IService<TbNews> {

    /**
     * 获取所有新闻
     * @return
     */
    List<TbNews> allNews();

    /**
     * 模糊搜索实习新闻
     * @param postName
     * @param message
     * @return
     */
    List<TbNews> getSearchNewsInfo(String postName, String message);
}
