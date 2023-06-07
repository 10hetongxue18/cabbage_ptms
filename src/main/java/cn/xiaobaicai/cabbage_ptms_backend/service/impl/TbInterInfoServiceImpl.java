package cn.xiaobaicai.cabbage_ptms_backend.service.impl;


import cn.xiaobaicai.cabbage_ptms_backend.model.vo.InterInfoVo;
import cn.xiaobaicai.cabbage_ptms_backend.util.LoginUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbInterInfo;
import cn.xiaobaicai.cabbage_ptms_backend.service.TbInterInfoService;
import cn.xiaobaicai.cabbage_ptms_backend.mapper.TbInterInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学生实习信息
 * @author hetongxue
 */
@Service
public class TbInterInfoServiceImpl extends ServiceImpl<TbInterInfoMapper, TbInterInfo>
    implements TbInterInfoService{

    @Resource
    private TbInterInfoMapper tbInterInfoMapper;

    /**
     * 获取学生实习信息
     * @param request
     * @return
     */
    @Override
    public TbInterInfo getInternshipInfo(HttpServletRequest request) {
        //判断是否已登录
        boolean loginState = LoginUtil.isLogin(request);
        if(!loginState){
            return null;
        }
        //查询数据库
        Object userAccount = request.getSession().getAttribute("userAccount");
        String account=(String) userAccount;
        QueryWrapper<TbInterInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stuId",account)
                    .orderByDesc("createTime")
                    .last("limit 1");
        TbInterInfo tbInterInfo = tbInterInfoMapper.selectOne(queryWrapper);
        if(tbInterInfo==null){
            return null;
        }
        return tbInterInfo;
    }

    /**
     * 模糊搜索学生实习信息
     * @param stuId
     * @param state
     * @param address
     * @param entName
     * @return
     */
    @Override
    public List<TbInterInfo> getSearchInterInfo(String stuId, String state, String address, String entName) {
        List<TbInterInfo> searchInterInfo = tbInterInfoMapper.getSearchInterInfo(stuId, state, address, entName);
        return searchInterInfo;
    }
}




