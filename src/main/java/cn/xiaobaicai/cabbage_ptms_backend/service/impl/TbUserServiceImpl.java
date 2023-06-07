package cn.xiaobaicai.cabbage_ptms_backend.service.impl;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbInterInfo;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbStuInfo;
import cn.xiaobaicai.cabbage_ptms_backend.util.JwtUtil;
import cn.xiaobaicai.cabbage_ptms_backend.util.LoginUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbUser;
import cn.xiaobaicai.cabbage_ptms_backend.service.TbUserService;
import cn.xiaobaicai.cabbage_ptms_backend.mapper.TbUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static cn.xiaobaicai.cabbage_ptms_backend.contant.UserContant.USER_LOGIN_STATE;

/**
 * 用户
 * @author hetongxue
 */
@Slf4j
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser>
    implements TbUserService{

    /**
     * 正则表达式 验证账号为数字
     */
    String Verify="[0-9]+";

    @Resource
    private TbUserMapper tbUserMapper;

    //region 增删改查(收纳)

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    @Override
    public TbUser userLogin(String userAccount, String userPassword, HttpServletRequest request, HttpServletResponse response) {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        if(userAccount.length()<12){
            return null;
        }
        if(userPassword.length()<8){
            return null;
        }
        // 账号只能是数字
        if(!userAccount.matches(Verify)){
            return null;
        }
        //2.查询用户是否存在
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount).eq("userPassword",userPassword);
        TbUser tbUser = tbUserMapper.selectOne(queryWrapper);
        if (tbUser==null){
            return null;
        }
        //3.用户脱敏
        TbUser safeUser = getSafeUser(tbUser);

        //4.生成token，存入响应头
        log.info("登录成功！生成token！");
        String token = JwtUtil.createToken(tbUser);
        response.setHeader("authorization",token);
        //返回脱敏用户信息
        return safeUser;
    }


    /**
     *获取当前登录用户
     * @param request
     * @return
     */
    @Override
    public TbUser getLoginUser(HttpServletRequest request) {
        //判断是否登录
        boolean loginState = LoginUtil.isLogin(request);
        if(!loginState){
            return null;
        }
        //查询数据库
        Integer id = (Integer) request.getSession().getAttribute("id");
        TbUser tbUser= this.getById(id);
        if(tbUser==null){
            return null;
        }
        return tbUser;
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        //判断是否登录
        boolean loginState = LoginUtil.isLogin(request);
        if(!loginState){
            return false;
        }
        //移除登录态
        request.getSession().removeAttribute("id");
        request.getSession().removeAttribute("userName");
        request.getSession().removeAttribute("userAccount");
        return true;
    }

    //endregion

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public TbUser getSafeUser(TbUser originUser) {
        if(originUser==null){
            return null;
        }
        TbUser safetyUser=new TbUser();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUpdateTime(originUser.getUpdateTime());
        return safetyUser;
    }

}




