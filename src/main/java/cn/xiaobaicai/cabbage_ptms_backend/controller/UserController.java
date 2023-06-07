package cn.xiaobaicai.cabbage_ptms_backend.controller;

import cn.xiaobaicai.cabbage_ptms_backend.common.BaseResponse;
import cn.xiaobaicai.cabbage_ptms_backend.common.ResultUtils;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbNews;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbUser;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.UserRequest.UserLoginRequest;
import cn.xiaobaicai.cabbage_ptms_backend.service.TbNewsService;
import cn.xiaobaicai.cabbage_ptms_backend.service.TbStuInfoService;
import cn.xiaobaicai.cabbage_ptms_backend.service.TbUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static cn.xiaobaicai.cabbage_ptms_backend.common.ErrorCode.NOT_LOGIN_ERROR;

/**
 * @author hetongxue
 * @date 2022/10/14 - 20:21
 */
//@CrossOrigin(value = {"http://127.0.0.1:5173/"})
@Slf4j
@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private TbUserService tbUserService;
    @Resource
    private TbNewsService tbNewsService;
    @Resource
    private TbStuInfoService tbStuInfoService;

    /**
     * 用户登录
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<TbUser> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response){
        if(userLoginRequest==null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        TbUser tbUser = tbUserService.userLogin(userAccount, userPassword, request, response);
        return ResultUtils.success(tbUser);
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request){
        if(request.getSession()==null){
            return ResultUtils.error(NOT_LOGIN_ERROR);
        }
        boolean result = tbUserService.userLogout(request);
        return ResultUtils.success(result);
    }


    /**
     * 获取新闻接口
     * @return
     */
    @GetMapping("/news")
    public BaseResponse<List<TbNews>> getNews(){
        List<TbNews> tbNews = tbNewsService.allNews();
        return ResultUtils.success(tbNews);
    }

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    @GetMapping("/personalInfo")
    public BaseResponse<TbUser> getLoginUser(HttpServletRequest request){
        if(request==null){
            return null;
        }
        TbUser loginUser = tbUserService.getLoginUser(request);
        return ResultUtils.success(loginUser);
    }




}
