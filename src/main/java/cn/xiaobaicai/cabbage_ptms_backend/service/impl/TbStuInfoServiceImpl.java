package cn.xiaobaicai.cabbage_ptms_backend.service.impl;

import cn.xiaobaicai.cabbage_ptms_backend.mapper.TbUserMapper;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbUser;
import cn.xiaobaicai.cabbage_ptms_backend.util.LoginUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbStuInfo;
import cn.xiaobaicai.cabbage_ptms_backend.service.TbStuInfoService;
import cn.xiaobaicai.cabbage_ptms_backend.mapper.TbStuInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static cn.xiaobaicai.cabbage_ptms_backend.contant.UserContant.USER_LOGIN_STATE;

/**
 * 学生基本信息
 * @author hetongxue
 */
@Service
public class TbStuInfoServiceImpl extends ServiceImpl<TbStuInfoMapper, TbStuInfo>
    implements TbStuInfoService{

    @Resource
    private TbStuInfoMapper tbStuInfoMapper;


    /**
     *获取当前登录学生信息
     * @param request
     * @return
     */
    @Override
    public TbStuInfo getStudentInfo(HttpServletRequest request) {
        //判断是否已登录
        boolean loginState = LoginUtil.isLogin(request);
        if(!loginState){
            return null;
        }
        //查询数据库
        Object userAccount = request.getSession().getAttribute("userAccount");
        String account=(String) userAccount;
        QueryWrapper<TbStuInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stuId",account);
        TbStuInfo tbStuInfo = tbStuInfoMapper.selectOne(queryWrapper);
        if(tbStuInfo==null){
            return null;
        }
        return tbStuInfo;
    }

    /**
     * 根据条件模糊搜索学生信息
     * @param name
     * @param gender
     * @param stuId
     * @return
     */
    @Override
    public List<TbStuInfo> getSearchStuInfo(String name, Byte gender, String stuId) {
        List<TbStuInfo> searchStuInfo = tbStuInfoMapper.getSearchStuInfo(name, gender, stuId);
        if(searchStuInfo==null){
            return null;
        }
        return searchStuInfo;
    }

    /**
     * 根据条件模糊搜索班级信息
     * @param faculty
     * @param major
     * @param grade
     * @param classes
     * @return
     */
    @Override
    public List<TbStuInfo> getSearchClassInfo(String faculty, String major, String grade, String classes) {
        List<TbStuInfo> searchClassInfo = tbStuInfoMapper.getSearchClassInfo(faculty, major, grade, classes);
        if(searchClassInfo==null){
            return null;
        }
        return searchClassInfo;
    }


}




