package cn.xiaobaicai.cabbage_ptms_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbEntTea;
import cn.xiaobaicai.cabbage_ptms_backend.service.TbEntTeaService;
import cn.xiaobaicai.cabbage_ptms_backend.mapper.TbEntTeaMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 企业指导老师信息
 * @author hetongxue
 */
@Service
public class TbEntTeaServiceImpl extends ServiceImpl<TbEntTeaMapper, TbEntTea>
    implements TbEntTeaService{

    @Resource
    private TbEntTeaMapper tbEntTeaMapper;

    /**
     * 模糊搜索企业老师信息
     * @param name
     * @param gender
     * @param entLoginId
     * @param entName
     * @return
     */
    @Override
    public List<TbEntTea> getSearchEntTeacherInfo(String name, Byte gender, String entLoginId, String entName) {
        List<TbEntTea> searchEntTeacherInfo = tbEntTeaMapper.getSearchEntTeacherInfo(name, gender, entLoginId, entName);
        if(searchEntTeacherInfo==null){
            return null;
        }
        return searchEntTeacherInfo;
    }
}




