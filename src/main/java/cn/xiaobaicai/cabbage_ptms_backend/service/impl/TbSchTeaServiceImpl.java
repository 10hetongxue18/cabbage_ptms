package cn.xiaobaicai.cabbage_ptms_backend.service.impl;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbStuInfo;
import cn.xiaobaicai.cabbage_ptms_backend.util.LoginUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbSchTea;
import cn.xiaobaicai.cabbage_ptms_backend.service.TbSchTeaService;
import cn.xiaobaicai.cabbage_ptms_backend.mapper.TbSchTeaMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 校内指导老师信息
 * @author hetongxue
 */
@Service
public class TbSchTeaServiceImpl extends ServiceImpl<TbSchTeaMapper, TbSchTea>
    implements TbSchTeaService{

    @Resource
    private TbSchTeaMapper tbSchTeaMapper;


    /**
     * 模糊搜索老师信息
     * @param name
     * @param gender
     * @param workId
     * @param faculty
     * @return
     */
    @Override
    public List<TbSchTea> getSearchSchTeacherInfo(String name, Byte gender, String workId, String faculty) {
        List<TbSchTea> searchStuInfo = tbSchTeaMapper.getSearchSchTeacherInfo(name, gender, workId, faculty);
        if(searchStuInfo==null){
            return null;
        }
        return searchStuInfo;
    }

    /**
     * 获取当前登录老师信息
     * @param request
     * @return
     */
    @Override
    public TbSchTea getSchTeacherInfo(HttpServletRequest request) {
        //判断是否已登录
        boolean loginState = LoginUtil.isLogin(request);
        if(!loginState){
            return null;
        }
        Object userAccount = request.getSession().getAttribute("userAccount");
        String account=(String) userAccount;
        QueryWrapper<TbSchTea> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("workId",account);
        TbSchTea tbSchTea = tbSchTeaMapper.selectOne(queryWrapper);
        if(tbSchTea==null){
            return null;
        }
        return tbSchTea;
    }
}




