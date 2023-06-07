package cn.xiaobaicai.cabbage_ptms_backend.service;

import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbInterInfo;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbStuInfo;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户
 * @author hetongxue
 */
public interface TbUserService extends IService<TbUser> {

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @param response
     * @return
     */
    TbUser userLogin(String userAccount, String userPassword, HttpServletRequest request, HttpServletResponse response);

    /**
     *获取当前登录用户
     * @param request
     * @return
     */
    TbUser getLoginUser(HttpServletRequest request);

    /**
     * 用户注销
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    TbUser getSafeUser(TbUser originUser);
}
