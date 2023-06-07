package cn.xiaobaicai.cabbage_ptms_backend.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbNews;
import cn.xiaobaicai.cabbage_ptms_backend.service.TbNewsService;
import cn.xiaobaicai.cabbage_ptms_backend.mapper.TbNewsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 新闻
 * @author hetongxue
 */
@Service
public class TbNewsServiceImpl extends ServiceImpl<TbNewsMapper, TbNews>
    implements TbNewsService{

    @Resource
    private TbNewsMapper tbNewsMapper;

    /**
     * 获取所有新闻
     * @return
     */
    @Override
    public List<TbNews> allNews() {
        List<TbNews> tbNews=tbNewsMapper.selectList(null);
        return tbNews;
    }

    /**
     * 模糊搜索实习新闻
     * @param postName
     * @param message
     * @return
     */
    @Override
    public List<TbNews> getSearchNewsInfo(String postName, String message) {
        List<TbNews> searchNewsInfo = tbNewsMapper.getSearchNewsInfo(postName, message);
        if (searchNewsInfo==null){
            return null;
        }
        return searchNewsInfo;
    }

}




