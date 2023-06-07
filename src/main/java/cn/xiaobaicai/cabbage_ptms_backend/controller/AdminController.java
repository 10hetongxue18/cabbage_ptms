package cn.xiaobaicai.cabbage_ptms_backend.controller;

import cn.xiaobaicai.cabbage_ptms_backend.common.BaseResponse;
import cn.xiaobaicai.cabbage_ptms_backend.common.PageRequest;
import cn.xiaobaicai.cabbage_ptms_backend.common.ResultUtils;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.AdminRequest.ClassSearchRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.DeleteRequest.IntegerDeleteRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.DeleteRequest.LongDeleteRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.EntTeacherRequest.EntTeacherAddRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.EntTeacherRequest.EntTeacherSearchRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.EntTeacherRequest.EntTeacherUpdateRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.NewsRequest.NewsAddRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.NewsRequest.NewsSearchRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.NewsRequest.NewsUpdateRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.SchTeacherRequest.SchTeacherAddRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.SchTeacherRequest.SchTeacherSearchRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.SchTeacherRequest.SchTeacherUpdateRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.StudentRequest.StudentAddRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.StudentRequest.StudentSearchRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.StudentRequest.StudentUpdateRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.InternshipRequest.InterSearchRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.*;
import cn.xiaobaicai.cabbage_ptms_backend.model.vo.*;
import cn.xiaobaicai.cabbage_ptms_backend.service.*;
import cn.xiaobaicai.cabbage_ptms_backend.util.LoginUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;


/**
 * admin控制器
 * @author hetongxue
 */
@Slf4j
@Api(tags = "admin模块")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private TbStuInfoService tbStuInfoService;
    @Resource
    private TbUserService tbUserService;
    @Resource
    private TbInterInfoService tbInterInfoService;
    @Resource
    private TbSchTeaService tbSchTeaService;
    @Resource
    private TbEntTeaService tbEntTeaService;
    @Resource
    private TbNewsService tbNewsService;
    @Resource
    private TbInterDocService tbInterDocService;


    /**
     * 分页获取学生基本信息
     * @return
     */
    @GetMapping("/list/studentInfo")
    public BaseResponse<StudentInfoVo> listStuInfoByPage(@ModelAttribute PageRequest pageRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        long current=1;
        long size=12;
        if(pageRequest!=null){
            current=pageRequest.getCurrent();
            size=pageRequest.getPageSize();
        }
        Page<TbStuInfo> newsPage = tbStuInfoService.page(new Page<>(current, size), null);
        //返回分页查询数据
        List<TbStuInfo> records = newsPage.getRecords();
        //返回数据总条数
        long total = newsPage.getTotal();
        StudentInfoVo studentInfoVo=new StudentInfoVo();
        studentInfoVo.setTotal(total);
        studentInfoVo.setTbStuInfo(records);
        return ResultUtils.success(studentInfoVo);
    }

    /**
     * 根据条件模糊搜索学生信息
     * @param studentSearchRequest
     * @param request
     * @return
     */
    @GetMapping("/searchStuInfo")
    public BaseResponse<List<TbStuInfo>> getSearchStuInfo(StudentSearchRequest studentSearchRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        //判空
        if(studentSearchRequest==null){
            return null;
        }
        //取值
        String name=studentSearchRequest.getName();
        Byte gender = studentSearchRequest.getGender();
        String stuId=studentSearchRequest.getStuId();
        List<TbStuInfo> searchInfo = tbStuInfoService.getSearchStuInfo(name, gender, stuId);
        if(searchInfo==null){
            return null;
        }
        return ResultUtils.success(searchInfo);
    }

    /**
     * 添加学生信息(同时将学号给注册为user表的账号)
     * @param studentAddRequest
     * @param request
     * @return
     */
    @PostMapping("/addStudentInfo")
    public BaseResponse<Long> addStudentInfo(@RequestBody StudentAddRequest studentAddRequest, HttpServletRequest request){
        ////判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        if(studentAddRequest==null){
            return null;
        }
        TbStuInfo tbStuInfo=new TbStuInfo();
        BeanUtils.copyProperties(studentAddRequest,tbStuInfo);
        boolean stuInfoResult = tbStuInfoService.save(tbStuInfo);
        if(!stuInfoResult){
            return null;
        }
        /*获取学生信息中的名字和学号，作为user表的用户名和账号，密码默认为12345678*/
        TbUser tbUser=new TbUser();
        tbUser.setUserName(tbStuInfo.getName());
        tbUser.setUserAccount(tbStuInfo.getStuId());
        tbUser.setUserPassword("12345678");
        tbUser.setPhone(tbStuInfo.getPhone());
        tbUser.setEmail(tbStuInfo.getEmail());
        boolean userInfoResult = tbUserService.save(tbUser);
        if(!userInfoResult){
            return null;
        }
        return ResultUtils.success(tbStuInfo.getId());
    }

    /**
     * 更新学生信息
     * @param studentUpdateRequest
     * @return
     */
    @PostMapping("/updateStudentInfo")
    public BaseResponse<Boolean> updateStudentInfo(@RequestBody StudentUpdateRequest studentUpdateRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        if(studentUpdateRequest==null){
            return null;
        }
        TbStuInfo tbStuInfo=new TbStuInfo();
        BeanUtils.copyProperties(studentUpdateRequest,tbStuInfo);
        boolean result = tbStuInfoService.updateById(tbStuInfo);
        return ResultUtils.success(result);
    }

    /**
     * 删除学生信息
     */
    @PostMapping("/deleteStudentInfo")
    public BaseResponse<Boolean> deleteStudentInfo(@RequestBody LongDeleteRequest longDeleteRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        TbStuInfo tbStuInfo=new TbStuInfo();
        BeanUtils.copyProperties(longDeleteRequest,tbStuInfo);
        boolean result = tbStuInfoService.removeById(tbStuInfo);
        if(!result){
            return null;
        }
        return ResultUtils.success(result);
    }


    /**
     * 根据条件模糊搜索班级信息
     * @param classSearchRequest
     * @param request
     * @return
     */
    @GetMapping("/searchClassInfo")
    public BaseResponse<List<TbStuInfo>> getSearchClassInfo(ClassSearchRequest classSearchRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        //判空
        if(classSearchRequest==null){
            return null;
        }
        //取值
        String faculty=classSearchRequest.getFaculty();
        String major = classSearchRequest.getMajor();
        String grade = classSearchRequest.getGrade();
        String classes = classSearchRequest.getClasses();
        List<TbStuInfo> searchInfo = tbStuInfoService.getSearchClassInfo(faculty,major,grade,classes);
        if(searchInfo==null){
            return null;
        }
        return ResultUtils.success(searchInfo);
    }


    /**
     * 分页获取学生实习信息(stu_info and inter_info)
     * @param pageRequest
     * @param request
     * @return
     */
    @GetMapping("/list/InterInfo")
    public BaseResponse<InterInfoVo> listInterInfoByPage(@ModelAttribute PageRequest pageRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        long current=1;
        long size=12;
        if(pageRequest!=null){
            current=pageRequest.getCurrent();
            size=pageRequest.getPageSize();
        }
        /*InterInfoVo*/
        InterInfoVo interInfoVo=new InterInfoVo();
        /*分页查询学生实习信息*/
        Page<TbInterInfo> newsPage = tbInterInfoService.page(new Page<>(current, size), null);
        //返回分页查询数据
        List<TbInterInfo> records = newsPage.getRecords();
        //返回数据总条数
        long total = newsPage.getTotal();
        //存储学生学号
        List<String> stuIdList=new ArrayList<>();
        //存入学号
        for (TbInterInfo record : records) {
            stuIdList.add(record.getStuId());
        }
        //存储根据学号查询出的学生信息
        List tbStuInfos=new ArrayList<>();
        //取出学生学号
        for (String stuId : stuIdList) {
            QueryWrapper<TbStuInfo> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("stuId",stuId);
            /*根据学号查询学生信息*/
            TbStuInfo stuInfoOne = tbStuInfoService.getOne(queryWrapper);
            /*存入学生信息*/
            tbStuInfos.add(stuInfoOne);
        }
        /*将学生信息存入VO*/
        interInfoVo.setTbStuInfos(tbStuInfos);
        /*将实习信息存入VO*/
        interInfoVo.setTbInterInfos(records);
        /*将数据总条数存入VO*/
        interInfoVo.setTotal(total);
        return ResultUtils.success(interInfoVo);
    }


    /**
     * 模糊搜索学生实习信息
     * TODO
     * @param interSearchRequest
     * @param request
     * @return
     */
    @GetMapping("/searchInterInfo")
    public BaseResponse<InterInfoVo> getSearchInterInfo(InterSearchRequest interSearchRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        //判空
        if(interSearchRequest==null){
            return null;
        }
        //取值
        String name = interSearchRequest.getName();
        Byte gender = interSearchRequest.getGender();
        String stuId = interSearchRequest.getStuId();
        String state = interSearchRequest.getState();
        String address = interSearchRequest.getAddress();
        String entName = interSearchRequest.getEntName();
        List<TbInterInfo> searchInterInfo = tbInterInfoService.getSearchInterInfo(stuId, state, address, entName);
        List<TbStuInfo> searchStuInfo = tbStuInfoService.getSearchStuInfo(name, gender, stuId);
        InterInfoVo interInfoVo=new InterInfoVo();
        interInfoVo.setTbStuInfos(searchStuInfo);
        interInfoVo.setTbInterInfos(searchInterInfo);
        return ResultUtils.success(interInfoVo);
    }




    /**
     * 分页获取老师基本信息
     * @return
     */
    @GetMapping("/list/schTeacherInfo")
    public BaseResponse<SchTeacherInfoVo> listSchTeacherInfoByPage(@ModelAttribute PageRequest pageRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        long current=1;
        long size=12;
        if(pageRequest!=null){
            current=pageRequest.getCurrent();
            size=pageRequest.getPageSize();
        }
        Page<TbSchTea> newsPage = tbSchTeaService.page(new Page<>(current, size), null);
        //返回分页查询数据
        List<TbSchTea> records = newsPage.getRecords();
        //返回数据总条数
        long total = newsPage.getTotal();
        SchTeacherInfoVo schTeacherInfoVo=new SchTeacherInfoVo();
        schTeacherInfoVo.setTotal(total);
        schTeacherInfoVo.setTbSchTeas(records);
        return ResultUtils.success(schTeacherInfoVo);
    }


    /**
     * 根据条件模糊搜索老师信息
     * @param schTeacherSearchRequest
     * @param request
     * @return
     */
    @GetMapping("/searchSchTeacherInfo")
    public BaseResponse<List<TbSchTea>> getSearchSchTeacherInfo(@ModelAttribute SchTeacherSearchRequest schTeacherSearchRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        //判空
        if(schTeacherSearchRequest==null){
            return null;
        }
        //取值
        String name=schTeacherSearchRequest.getName();
        Byte gender = schTeacherSearchRequest.getGender();
        String workId=schTeacherSearchRequest.getWorkId();
        String faculty = schTeacherSearchRequest.getFaculty();
        List<TbSchTea> searchSchTeacherInfo = tbSchTeaService.getSearchSchTeacherInfo(name, gender, workId, faculty);
        if(searchSchTeacherInfo==null){
            return null;
        }
        return ResultUtils.success(searchSchTeacherInfo);
    }



    /**
     * 添加校内老师信息(同时将工号号给注册为user表的账号)
     * @param schTeacherAddRequest
     * @param request
     * @return
     */
    @PostMapping("/addSchTeacherInfo")
    public BaseResponse<Integer> addSchTeacherInfo(@RequestBody SchTeacherAddRequest schTeacherAddRequest, HttpServletRequest request){
        ////判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        if(schTeacherAddRequest==null){
            return null;
        }
        TbSchTea tbSchTea=new TbSchTea();
        BeanUtils.copyProperties(schTeacherAddRequest,tbSchTea);
        boolean schTeaResult = tbSchTeaService.save(tbSchTea);
        if(!schTeaResult){
            return null;
        }
        /*获取老师信息中的名字和学号，作为user表的用户名和账号，密码默认为12345678，将userRole设置为1*/
        TbUser tbUser=new TbUser();
        tbUser.setUserName(tbSchTea.getName());
        tbUser.setUserAccount(tbSchTea.getWorkId());
        tbUser.setUserPassword("12345678");
        tbUser.setUserRole(1);
        tbUser.setPhone(tbSchTea.getPhone());
        tbUser.setEmail(tbSchTea.getEmail());
        boolean userInfoResult = tbUserService.save(tbUser);
        if(!userInfoResult){
            return null;
        }
        return ResultUtils.success(tbSchTea.getId());
    }

    /**
     * 更新老师信息
     * @param schTeacherUpdateRequest
     * @return
     */
    @PostMapping("/updateSchTeacherInfo")
    public BaseResponse<Boolean> updateSchTeacherInfo(@RequestBody SchTeacherUpdateRequest schTeacherUpdateRequest, HttpServletRequest request){
        //判断登录
        boolean loginState = LoginUtil.isLogin(request);
        if(!loginState){
            return null;
        }
        if(schTeacherUpdateRequest==null){
            return null;
        }
        TbSchTea tbSchTea=new TbSchTea();
        BeanUtils.copyProperties(schTeacherUpdateRequest,tbSchTea);
        System.out.println(tbSchTea);
        boolean result = tbSchTeaService.updateById(tbSchTea);
        if(!result){
            return null;
        }
        return ResultUtils.success(result);
    }



    /**
     * 删除老师信息
     */
    @PostMapping("/deleteSchTeacherInfo")
    public BaseResponse<Boolean> deleteSchTeacherInfo(@RequestBody IntegerDeleteRequest integerDeleteRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        TbSchTea tbSchTea=new TbSchTea();
        BeanUtils.copyProperties(integerDeleteRequest,tbSchTea);
        boolean result = tbSchTeaService.removeById(tbSchTea);
        if(!result){
            return null;
        }
        return ResultUtils.success(result);
    }



    /**
     * 分页获取企业老师基本信息
     * @return
     */
    @GetMapping("/list/entTeacherInfo")
    public BaseResponse<EntTeacherInfoVo> listEntTeacherInfoByPage(@ModelAttribute PageRequest pageRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        long current=1;
        long size=12;
        if(pageRequest!=null){
            current=pageRequest.getCurrent();
            size=pageRequest.getPageSize();
        }
        Page<TbEntTea> newsPage = tbEntTeaService.page(new Page<>(current, size), null);
        //返回分页查询数据
        List<TbEntTea> records = newsPage.getRecords();
        //返回数据总条数
        long total = newsPage.getTotal();
        EntTeacherInfoVo entTeacherInfoVo=new EntTeacherInfoVo();
        entTeacherInfoVo.setTotal(total);
        entTeacherInfoVo.setTbEntTeas(records);
        return ResultUtils.success(entTeacherInfoVo);
    }



    /**
     * 根据条件模糊搜索企业老师信息
     * @param entTeacherSearchRequest
     * @param request
     * @return
     */
    @GetMapping("/searchEntTeacherInfo")
    public BaseResponse<List<TbEntTea>> getSearchEntTeacherInfo(@ModelAttribute EntTeacherSearchRequest entTeacherSearchRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        //判空
        if(entTeacherSearchRequest==null){
            return null;
        }
        //取值
        String name=entTeacherSearchRequest.getName();
        Byte gender = entTeacherSearchRequest.getGender();
        String entLoginId = entTeacherSearchRequest.getEntLoginId();
        String entName = entTeacherSearchRequest.getEntName();
        List<TbEntTea> searchEntTeacherInfo = tbEntTeaService.getSearchEntTeacherInfo(name, gender, entLoginId, entName);
        if(searchEntTeacherInfo==null){
            return null;
        }
        return ResultUtils.success(searchEntTeacherInfo);
    }

    /**
     * 添加企业老师信息(同时将企业工号号给注册为user表的账号)
     * @param entTeacherAddRequest
     * @param request
     * @return
     */
    @PostMapping("/addEntTeacherInfo")
    public BaseResponse<Integer> addEntTeacherInfo(@RequestBody EntTeacherAddRequest entTeacherAddRequest, HttpServletRequest request){
        ////判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        if(entTeacherAddRequest==null){
            return null;
        }
        TbEntTea tbEntTea=new TbEntTea();
        BeanUtils.copyProperties(entTeacherAddRequest,tbEntTea);
        boolean entTeaResult = tbEntTeaService.save(tbEntTea);
        if(!entTeaResult){
            return null;
        }
        /*获取企业老师信息中的名字和企业工号，作为user表的用户名和账号，密码默认为12345678 ，将userRole设置为2*/
        TbUser tbUser=new TbUser();
        tbUser.setUserName(tbEntTea.getName());
        tbUser.setUserAccount(tbEntTea.getEntLoginId());
        tbUser.setUserPassword("12345678");
        tbUser.setUserRole(2);
        tbUser.setPhone(tbEntTea.getPhone());
        tbUser.setEmail(tbEntTea.getEmail());
        boolean userInfoResult = tbUserService.save(tbUser);
        if(!userInfoResult){
            return null;
        }
        return ResultUtils.success(tbEntTea.getId());
    }



    /**
     * 更新企业老师信息
     * @param entTeacherUpdateRequest
     * @return
     */
    @PostMapping("/updateEntTeacherInfo")
    public BaseResponse<Boolean> updateEntTeacherInfo(@RequestBody EntTeacherUpdateRequest entTeacherUpdateRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        if(entTeacherUpdateRequest==null){
            return null;
        }
        TbEntTea tbEntTea=new TbEntTea();
        BeanUtils.copyProperties(entTeacherUpdateRequest,tbEntTea);
        boolean result = tbEntTeaService.updateById(tbEntTea);
        if(!result){
            return null;
        }
        return ResultUtils.success(result);
    }

    /**
     * 删除企业老师信息
     */
    @PostMapping("/deleteEntTeacherInfo")
    public BaseResponse<Boolean> deleteEntTeacherInfo(@RequestBody IntegerDeleteRequest integerDeleteRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        TbEntTea tbEntTea=new TbEntTea();
        BeanUtils.copyProperties(integerDeleteRequest,tbEntTea);
        boolean result = tbEntTeaService.removeById(tbEntTea);
        if(!result){
            return null;
        }
        return ResultUtils.success(result);
    }


    /**
     * 分页获取实习新闻信息
     * @return
     */
    @GetMapping("/list/newsInfo")
    public BaseResponse<NewsInfoVo> listNewsInfoByPage(@ModelAttribute PageRequest pageRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        long current=1;
        long size=12;
        if(pageRequest!=null){
            current=pageRequest.getCurrent();
            size=pageRequest.getPageSize();
        }
        Page<TbNews> newsPage = tbNewsService.page(new Page<>(current, size), null);

        //返回分页查询数据
        List<TbNews> records = newsPage.getRecords();
        //返回数据总条数
        long total = newsPage.getTotal();
        NewsInfoVo newsInfoVo=new NewsInfoVo();
        newsInfoVo.setTotal(total);
        newsInfoVo.setTbNews(records);
        return ResultUtils.success(newsInfoVo);
    }


    /**
     * 根据条件模糊搜索实习新闻信息
     * @param newsSearchRequest
     * @param request
     * @return
     */
    @GetMapping("/searchNewsInfo")
    public BaseResponse<List<TbNews>> getSearchNewsInfo(@ModelAttribute NewsSearchRequest newsSearchRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        //判空
        if(newsSearchRequest==null){
            return null;
        }
        //取值
        String postName = newsSearchRequest.getPostName();
        String message = newsSearchRequest.getMessage();
        List<TbNews> searchNewsInfo = tbNewsService.getSearchNewsInfo(postName, message);
        if(searchNewsInfo==null){
            return null;
        }
        return ResultUtils.success(searchNewsInfo);
    }


    /**
     * 添加实习新闻信息
     * @param newsAddRequest
     * @param request
     * @return
     */
    @PostMapping("/addNewsInfo")
    public BaseResponse<Long> addNewsInfo(@RequestBody NewsAddRequest newsAddRequest, HttpServletRequest request){
        ////判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        if(newsAddRequest==null){
            return null;
        }
        TbNews tbNews=new TbNews();
        BeanUtils.copyProperties(newsAddRequest,tbNews);
        boolean newsResult = tbNewsService.save(tbNews);
        if(!newsResult){
            return null;
        }
        return ResultUtils.success(tbNews.getId());
    }



    /**
     * 更新实习新闻信息
     * @param newsUpdateRequest
     * @return
     */
    @PostMapping("/updateNewsInfo")
    public BaseResponse<Boolean> updateNewsInfo(@RequestBody NewsUpdateRequest newsUpdateRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        if(newsUpdateRequest==null){
            return null;
        }
        TbNews tbNews=new TbNews();
        BeanUtils.copyProperties(newsUpdateRequest,tbNews);
        boolean result = tbNewsService.updateById(tbNews);
        if(!result){
            return null;
        }
        return ResultUtils.success(result);
    }

    /**
     * 删除实习新闻信息
     */
    @PostMapping("/deleteNewsInfo")
    public BaseResponse<Boolean> deleteNewsInfo(@RequestBody LongDeleteRequest longDeleteRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        TbNews tbNews=new TbNews();
        BeanUtils.copyProperties(longDeleteRequest,tbNews);
        boolean result = tbNewsService.removeById(tbNews);
        if(!result){
            return null;
        }
        return ResultUtils.success(result);
    }

    /**
     * 分页获取实习文档信息
     * @return
     */
    @GetMapping("/list/InterDocInfo")
    public BaseResponse<List<InterDocVo>> listInterDocInfoByPage(@ModelAttribute PageRequest pageRequest, HttpServletRequest request){
        //判断登录
        boolean login = LoginUtil.isLogin(request);
        if(!login){
            return null;
        }
        long current=1;
        long size=12;
        if(pageRequest!=null){
            current=pageRequest.getCurrent();
            size=pageRequest.getPageSize();
        }
        Page<TbInterDoc> newsPage = tbInterDocService.page(new Page<>(current, size), null);
        List<InterDocVo> interDocVos=new ArrayList<>();
        //返回数据总条数
        long total = newsPage.getTotal();
        //返回分页查询数据
        List<TbInterDoc> records = newsPage.getRecords();
        //遍历分页数据,根据学生id查询提交实习文档的学生
        for (TbInterDoc record : records) {
            String stuId = record.getStuId();
            QueryWrapper<TbStuInfo> queryStuInfo=new QueryWrapper<>();
            queryStuInfo.eq("stuId",stuId);
            TbStuInfo stuInfo = tbStuInfoService.getOne(queryStuInfo);
            if(stuInfo!=null){
                //取出学生姓名
                String stuName = stuInfo.getName();
                //实习文档返回封装类
                InterDocVo interDocVo=new InterDocVo();
                interDocVo.setStuName(stuName);
                interDocVo.setTitle(record.getTitle());
                interDocVo.setContent(record.getContent());
                interDocVo.setState(record.getState());
                interDocVo.setCreateTime(record.getCreateTime());
                interDocVo.setTotal(total);
                interDocVos.add(interDocVo);
            }
        }
        return ResultUtils.success(interDocVos);
    }


}
