package cn.xiaobaicai.cabbage_ptms_backend.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录工具类（判断是否已登录）
 * @author hetongxue
 */
public class LoginUtil {

    /**
     * 是否登录
     * @param request
     * @return
     */
    public static boolean isLogin(HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        String userName = (String) request.getSession().getAttribute("userName");
        String userAccount= (String) request.getSession().getAttribute("userAccount");
        if(id==null&& StringUtils.isAnyBlank(userName,userAccount)){
            return false;
        }
        return true;
    }
}
