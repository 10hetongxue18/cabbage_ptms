package cn.xiaobaicai.cabbage_ptms_backend.controller;

import cn.xiaobaicai.cabbage_ptms_backend.common.BaseResponse;
import cn.xiaobaicai.cabbage_ptms_backend.common.ErrorCode;
import cn.xiaobaicai.cabbage_ptms_backend.common.ResultUtils;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.ApplicationRequest.UpdateApplicationRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.EntTeacherRequest.EntTeacherUpdateRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.FileRequest.FileUpdateRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.InternshipRequest.InterDocUpdateRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.ReportRequest.GenerateReportRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.SchTeacherRequest.SchTeaSubmitTotalScore;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.SchTeacherRequest.SchTeacherUpdateRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.*;
import cn.xiaobaicai.cabbage_ptms_backend.model.vo.AllPostInfoVo;
import cn.xiaobaicai.cabbage_ptms_backend.model.vo.InterReportVo;
import cn.xiaobaicai.cabbage_ptms_backend.model.vo.PostApplicationInfoVo;
import cn.xiaobaicai.cabbage_ptms_backend.model.vo.StuTeacherDocInfoVo;
import cn.xiaobaicai.cabbage_ptms_backend.service.*;
import cn.xiaobaicai.cabbage_ptms_backend.util.DateTransLocalDate;
import cn.xiaobaicai.cabbage_ptms_backend.util.LoginUtil;
import cn.xiaobaicai.cabbage_ptms_backend.util.ZipUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static cn.xiaobaicai.cabbage_ptms_backend.common.ErrorCode.OPERATION_ERROR;

/**
 * @author hetongxue
 */

@Api(tags = "老师模块")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    /**
     * 自定义属性（实习报告存储路径）
     */
    @Value("${report.filePath}")
    private String reportPath;

    @Resource
    private TbSchTeaService tbSchTeaService;
    @Resource
    private TbDocumentService tbDocumentService;
    @Resource
    private TbStuInfoService tbStuInfoService;
    @Resource
    private TbInterDocService tbInterDocService;
    @Resource
    private TbEntTeaService tbEntTeaService;
    @Resource
    private TbApplicationService tbApplicationService;
    @Resource
    private TbNewsService tbNewsService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private TbInterInfoService tbInterInfoService;
    @Resource
    private TbInterReportService tbInterReportService;

    /**
     * 获取当前登录校内老师信息
     *
     * @param request
     * @return
     */
    @GetMapping("/schTeacherInfo")
    public BaseResponse<TbSchTea> getSchTeacherInfo(HttpServletRequest request) {
        if (request == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        TbSchTea schTeacherInfo = tbSchTeaService.getSchTeacherInfo(request);
        if (schTeacherInfo == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(schTeacherInfo);
    }

    /**
     * 更新校内老师信息
     *
     * @param schTeacherUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/updateSchTeacherInfo")
    public BaseResponse<Boolean> updateSchTeacherInfo(@RequestBody SchTeacherUpdateRequest schTeacherUpdateRequest, HttpServletRequest request) {
        //判断登录
        boolean loginState = LoginUtil.isLogin(request);
        if (!loginState) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        if (schTeacherUpdateRequest == null || schTeacherUpdateRequest.getId() == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        TbSchTea tbSchTea = new TbSchTea();
        BeanUtils.copyProperties(schTeacherUpdateRequest, tbSchTea);
        boolean result = tbSchTeaService.updateById(tbSchTea);
        return ResultUtils.success(result);
    }

    /**
     * 查询本人上传文件
     *
     * @param request
     * @return
     */
    @GetMapping("/uploadFileInfo")
    public BaseResponse<List<TbDocument>> getUploadFile(HttpServletRequest request) {
        /*获取当前老师信息*/
        String workId = (String) request.getSession().getAttribute("userAccount");
        QueryWrapper<TbDocument> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uploader", workId).orderByDesc("createTime");
        /*根据工号查询文件上传者*/
        List<TbDocument> documentList = tbDocumentService.list(queryWrapper);
        List<TbDocument> tbDocumentList = new ArrayList<>();
        for (TbDocument document : documentList) {
            TbDocument tBDocument = new TbDocument();
            BeanUtils.copyProperties(document, tBDocument);
            tbDocumentList.add(tBDocument);
        }
        return ResultUtils.success(tbDocumentList);
    }

    /**
     * 设置文件可视状态
     *
     * @param fileUpdateRequest
     * @return
     */
    @PostMapping("/updateFileState")
    public BaseResponse<Integer> updateFileState(@RequestBody FileUpdateRequest fileUpdateRequest) {
        if (fileUpdateRequest.getId() == null || fileUpdateRequest.getIsVisible() == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        TbDocument tbDocument = new TbDocument();
        BeanUtils.copyProperties(fileUpdateRequest, tbDocument);
        boolean result = tbDocumentService.updateById(tbDocument);
        if (!result) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(tbDocument.getId());
    }

    /**
     * 获取学生文档信息
     *
     * @param request
     * @return
     * @cabbage
     */
    @GetMapping("/getStuDocument")
    public BaseResponse<List<StuTeacherDocInfoVo>> getStuDocument(@RequestParam(value = "docState", required = false) Integer docState, HttpServletRequest request) {
        Integer state = 1;
        if (docState != null) {
            state = docState;
        }
        /*获取当前老师工号*/
        String workId = (String) request.getSession().getAttribute("userAccount");
        /*查询当前老师信息*/
        QueryWrapper<TbSchTea> queryTeacher = new QueryWrapper<>();
        queryTeacher.eq("workId", workId);
        TbSchTea schTea = tbSchTeaService.getOne(queryTeacher);
        /*获取当前老师id*/
        Integer id = schTea.getId();
        /*根据id查询相关学生信息*/
        QueryWrapper<TbStuInfo> queryStudent = new QueryWrapper<>();
        queryStudent.eq("schTeaId", id);
        List<TbStuInfo> stuInfoList = tbStuInfoService.list(queryStudent);
        /*存储当前跟随老师实习的学生提交的实习文档的集合*/
        List<StuTeacherDocInfoVo> interDocList = new ArrayList<>();
        for (TbStuInfo stuInfo : stuInfoList) {
            /*获取学生的学号*/
            String stuId = stuInfo.getStuId();
            /*根据学号查询该学生提交的文档信息*/
            QueryWrapper<TbInterDoc> queryInterDoc = new QueryWrapper<>();
            queryInterDoc.eq("stuId", stuId).eq("state", state);
            List<TbInterDoc> docList = tbInterDocService.list(queryInterDoc);
            /*遍历返回的集合，将查询到的学生的实习文档放入封装类*/
            for (TbInterDoc oneDoc : docList) {
                /*返回的封装类,存储学生信息和实习文档信息*/
                StuTeacherDocInfoVo stuTeacherDocInfoVo = new StuTeacherDocInfoVo();
                BeanUtils.copyProperties(stuInfo, stuTeacherDocInfoVo);
                BeanUtils.copyProperties(oneDoc, stuTeacherDocInfoVo);
                /*将封装类存入集合*/
                interDocList.add(stuTeacherDocInfoVo);
            }
        }
        return ResultUtils.success(interDocList);
    }

    @PostMapping("/updateStudentDocInfo")
    public BaseResponse<Long> updateStudentDocInfo(@RequestBody InterDocUpdateRequest interDocUpdateRequest) {
        if (interDocUpdateRequest == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        TbInterDoc tbInterDoc = new TbInterDoc();
        tbInterDoc.setState(2);
        BeanUtils.copyProperties(interDocUpdateRequest, tbInterDoc);
        boolean result = tbInterDocService.updateById(tbInterDoc);
        if (!result) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(tbInterDoc.getId());
    }

    /**
     * 获取当前登录企业老师信息
     *
     * @param request
     * @return
     */
    @GetMapping("entTeacherInfo")
    public BaseResponse<TbEntTea> getEntTeacherInfo(HttpServletRequest request) {
        String entLoginId = (String) request.getSession().getAttribute("userAccount");
        QueryWrapper<TbEntTea> queryEntTea = new QueryWrapper<>();
        queryEntTea.eq("entLoginId", entLoginId);
        TbEntTea entTeacherInfo = tbEntTeaService.getOne(queryEntTea);
        if (entTeacherInfo == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(entTeacherInfo);
    }

    /**
     * 更新企业老师信息
     *
     * @param entTeacherUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/updateEntTeacherInfo")
    public BaseResponse<Boolean> updateEntTeacherInfo(@RequestBody EntTeacherUpdateRequest entTeacherUpdateRequest, HttpServletRequest request) {
        //判断登录
        boolean loginState = LoginUtil.isLogin(request);
        if (!loginState) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        if (entTeacherUpdateRequest == null || entTeacherUpdateRequest.getId() == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        TbEntTea tbEntTea = new TbEntTea();
        BeanUtils.copyProperties(entTeacherUpdateRequest, tbEntTea);
        boolean result = tbEntTeaService.updateById(tbEntTea);
        if (!result) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(result);
    }


    /**
     * 企业老师查询实习岗位申请情况
     *
     * @param request
     * @return
     */
    @GetMapping("/getPostApplication")
    public BaseResponse<List<PostApplicationInfoVo>> getPostApplication(@RequestParam("state") Byte state, HttpServletRequest request) {
        String entLoginId = (String) request.getSession().getAttribute("userAccount");
        /*根据当前登录账号查询企业老师信息*/
        QueryWrapper<TbEntTea> queryEntTea = new QueryWrapper<>();
        queryEntTea.eq("entLoginId", entLoginId);
        TbEntTea tbEntTea = tbEntTeaService.getOne(queryEntTea);
        /*取出当前数据的id*/
        Integer entId = tbEntTea.getId();
        /*根据企业老师的id，查询实习申请表(只查询实习状态为已申请或已通过)*/
        QueryWrapper<TbApplication> queryApplication = new QueryWrapper<>();
        queryApplication.eq("state", state);
        queryApplication.eq("entId", entId);
        List<TbApplication> applicationList = tbApplicationService.list(queryApplication);
        /*返回封装类集合*/
        List<PostApplicationInfoVo> infoVoList = new ArrayList<>();
        /*遍历实习信息*/
        for (TbApplication tbApplication : applicationList) {
            /*分别获取stuId、newsId、areaId，根据这些id去查询其它表的数据*/
            String stuId = tbApplication.getStuId();
            Long newsId = tbApplication.getNewsId();
            Integer areaId = tbApplication.getAreaId();
            /*根据stuId，查询相关的学生信息*/
            QueryWrapper<TbStuInfo> queryStuInfo = new QueryWrapper<>();
            queryStuInfo.eq("stuId", stuId);
            TbStuInfo tbStuInfo = tbStuInfoService.getOne(queryStuInfo);
            /*根据newsId，查询相关的新闻信息*/
            QueryWrapper<TbNews> queryNews = new QueryWrapper<>();
            queryNews.eq("id", newsId);
            TbNews tbNews = tbNewsService.getOne(queryNews);
            /*根据areaId，查询相关的实习基地信息*/
            QueryWrapper<TbArea> queryArea = new QueryWrapper<>();
            queryArea.eq("id", areaId);
            TbArea tbArea = tbAreaService.getOne(queryArea);
            /*返回封装类*/
            PostApplicationInfoVo postApplicationInfoVo = new PostApplicationInfoVo();
            /*封装返回数据*/
            postApplicationInfoVo.setId(tbApplication.getId());
            postApplicationInfoVo.setName(tbStuInfo.getName());
            postApplicationInfoVo.setStuId(stuId);
            postApplicationInfoVo.setGender(tbStuInfo.getGender());
            postApplicationInfoVo.setFaculty(tbStuInfo.getFaculty());
            postApplicationInfoVo.setMajor(tbStuInfo.getMajor());
            postApplicationInfoVo.setClasses(tbStuInfo.getClasses());
            postApplicationInfoVo.setPostName(tbNews.getPostName());
            postApplicationInfoVo.setMessage(tbNews.getMessage());
            postApplicationInfoVo.setPicture(tbArea.getPicture());
            postApplicationInfoVo.setAddress(tbArea.getAddress());
            postApplicationInfoVo.setIntroduction(tbApplication.getIntroduction());
            postApplicationInfoVo.setState(tbApplication.getState());
            infoVoList.add(postApplicationInfoVo);
        }
        return ResultUtils.success(infoVoList);
    }

    /**
     * 企业老师同意申请实习接口（同意实习后，将企业老师id存入学生信息，同时将该企业的实习信息自动存入学生实习信息表中）
     *
     * @param updateApplicationRequest
     * @return
     */
    @PostMapping("/yesApplication")
    public BaseResponse<Integer> yesApplication(@RequestBody UpdateApplicationRequest updateApplicationRequest) {
        if (updateApplicationRequest == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        Integer applicationId = updateApplicationRequest.getApplicationId();
        TbApplication tbApplication = new TbApplication();
        tbApplication.setId(applicationId);
        tbApplication.setState((byte) 3);
        /*修改该实习申请的状态*/
        boolean result = tbApplicationService.updateById(tbApplication);
        if (!result) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        /*查询修改后该实习申请*/
        TbApplication application = tbApplicationService.getById(applicationId);
        /*获取企业老师id*/
        Integer entId = application.getEntId();
        /*获取学生学号*/
        String stuId = updateApplicationRequest.getStuId();
        /*根据学号查询学生信息表*/
        QueryWrapper<TbStuInfo> queryStuInfo = new QueryWrapper<>();
        queryStuInfo.eq("stuId", stuId);
        TbStuInfo stuInfo = tbStuInfoService.getOne(queryStuInfo);
        /*取出该学生的id*/
        Long stuInfoId = stuInfo.getId();
        /*同时更新学生表*/
        TbStuInfo tbStuInfo = new TbStuInfo();
        tbStuInfo.setId(stuInfoId);
        tbStuInfo.setStuId(stuId);
        tbStuInfo.setEntTeaId(entId);
        boolean updateSchInfo = tbStuInfoService.updateById(tbStuInfo);
        if (!updateSchInfo) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        /*企业老师同意实习后，将该学生申请的实习信息存入学生的实习信息表中*/
        /*取出实习申请表中的新闻id、基地id*/
        Long newsId = application.getNewsId();
        Integer areaId = application.getAreaId();
        /*查询企业老师信息*/
        QueryWrapper<TbEntTea> queryEntTea = new QueryWrapper<>();
        queryEntTea.eq("id", entId);
        TbEntTea entTea = tbEntTeaService.getOne(queryEntTea);
        /*查询该条实习新闻信息*/
        QueryWrapper<TbNews> queryNews = new QueryWrapper<>();
        queryNews.eq("id", newsId);
        TbNews news = tbNewsService.getOne(queryNews);
        /*查询该实习基地信息*/
        QueryWrapper<TbArea> queryArea = new QueryWrapper<>();
        queryArea.eq("id", areaId);
        TbArea tbArea = tbAreaService.getOne(queryArea);
        /*创建实习信息对象*/
        TbInterInfo tbInterInfo = new TbInterInfo();
        tbInterInfo.setStuId(stuId);
        tbInterInfo.setAddress(tbArea.getAddress());
        tbInterInfo.setEntName(entTea.getEntName());
        tbInterInfo.setLeader(entTea.getName());
        tbInterInfo.setPost(news.getPostName());
        tbInterInfo.setState(1);
        /*插入实习信息表数据*/
        boolean addInterInfo = tbInterInfoService.save(tbInterInfo);
        if (!addInterInfo) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(applicationId);
    }

    /**
     * 企业老师拒绝申请实习接口（拒绝实习申请后将该条实习信息状态设置为2）
     *
     * @param updateApplicationRequest
     * @return
     */
    @PostMapping("/noApplication")
    public BaseResponse<Integer> noApplication(@RequestBody UpdateApplicationRequest updateApplicationRequest) {
        if (updateApplicationRequest == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        Integer applicationId = updateApplicationRequest.getApplicationId();
        TbApplication tbApplication = new TbApplication();
        tbApplication.setId(applicationId);
        tbApplication.setState((byte) 2);
        /*修改该实习申请的状态（将状态修改为2）*/
        boolean result = tbApplicationService.updateById(tbApplication);
        if (!result) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(applicationId);
    }

    /**
     * AllPostInfoVo
     * 获取当前登录企业老师发布的所有实习岗位信息
     */
    @GetMapping("/getAllPostInfo")
    public BaseResponse<List<AllPostInfoVo>> getAllPostInfo(HttpServletRequest request) {
        String entLoginId = (String) request.getSession().getAttribute("userAccount");
        /*查询当前登录的企业老师信息*/
        QueryWrapper<TbEntTea> queryEntTea = new QueryWrapper<>();
        queryEntTea.eq("entLoginId", entLoginId);
        TbEntTea tbEntTea = tbEntTeaService.getOne(queryEntTea);
        /*获取企业老师的id*/
        Integer entTeaId = tbEntTea.getId();
        /*根据企业老师id查询实习基地表*/
        QueryWrapper<TbArea> queryArea = new QueryWrapper<>();
        queryArea.eq("userId", entTeaId);
        TbArea tbArea = tbAreaService.getOne(queryArea);
        /*取出实习基地表的id*/
        Integer areaId = tbArea.getId();
        QueryWrapper<TbNews> queryNews = new QueryWrapper<>();
        queryNews.eq("areaId", areaId);
        List<AllPostInfoVo> infoVoList = new ArrayList<>();
        List<TbNews> newsList = tbNewsService.list(queryNews);
        for (TbNews tbNew : newsList) {
            AllPostInfoVo allPostInfoVo = new AllPostInfoVo();
            allPostInfoVo.setPicture(tbArea.getPicture());
            allPostInfoVo.setPostName(tbNew.getPostName());
            allPostInfoVo.setMessage(tbNew.getMessage());
            allPostInfoVo.setAddress(tbArea.getAddress());
            allPostInfoVo.setCreateTime(tbNew.getCreateTime());
            infoVoList.add(allPostInfoVo);
        }
        return ResultUtils.success(infoVoList);
    }

    /**
     * 获取当前老师下学生的实习报告数据
     *
     * @param request
     * @return
     */
    @GetMapping("/getInternshipReport")
    public BaseResponse<List<InterReportVo>> getInternshipReport(@RequestParam(value = "state", required = false) Integer state, HttpServletRequest request) {
        Integer rState = 0;
        if (state != null) {
            rState = state;
        }
        String workId = (String) request.getSession().getAttribute("userAccount");
        /*查询当前老师信息*/
        QueryWrapper<TbSchTea> querySchTea = new QueryWrapper<>();
        querySchTea.eq("workId", workId);
        TbSchTea tbSchTea = tbSchTeaService.getOne(querySchTea);
        /*获取当前老师的id,根据id查找该老师下面的学生信息*/
        Integer id = tbSchTea.getId();
        QueryWrapper<TbStuInfo> queryStuInfo = new QueryWrapper<>();
        queryStuInfo.eq("schTeaId", id);
        List<TbStuInfo> stuInfoList = tbStuInfoService.list(queryStuInfo);
        /*获取学生的学号，根据学号查询学生对应的报告信息*/
        List<InterReportVo> reportVoList = new ArrayList<>();
        for (TbStuInfo tbStuInfo : stuInfoList) {
            String stuId = tbStuInfo.getStuId();
            QueryWrapper<TbDocument> queryDoc = new QueryWrapper<>();
            queryDoc.eq("uploader", stuId);
            TbDocument tbDocument = tbDocumentService.getOne(queryDoc);
            if (tbDocument != null) {
                /*取出文档的id，查询报告内容*/
                Integer documentId = tbDocument.getId();
                QueryWrapper<TbInterReport> queryReport = new QueryWrapper<>();
                queryReport.eq("docId", documentId)
                        .eq("state", rState);
                TbInterReport tbInterReport = tbInterReportService.getOne(queryReport);
                InterReportVo interReportVo = new InterReportVo();
                if (tbInterReport != null) {
                    if (tbInterReport.getDocId().equals(tbDocument.getId())) {
                        interReportVo.setName(tbStuInfo.getName());
                        interReportVo.setReportName(tbDocument.getFileName());
                        interReportVo.setCreateTime(tbDocument.getCreateTime());
                        reportVoList.add(interReportVo);
                    }
                }
            } else {
                return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
            }
        }
        return ResultUtils.success(reportVoList);
    }

    /**
     * 获取实习总结内容
     *
     * @param reportName
     * @return
     */
    @GetMapping("/getSummary")
    public BaseResponse<String> getSummary(@RequestParam("reportName") String reportName) {
        QueryWrapper<TbDocument> queryDocument = new QueryWrapper<>();
        queryDocument.eq("fileName", reportName);
        TbDocument tbDocument = tbDocumentService.getOne(queryDocument);
        Integer docId = tbDocument.getId();
        QueryWrapper<TbInterReport> queryReport = new QueryWrapper<>();
        queryReport.eq("docId", docId);
        TbInterReport tbInterReport = tbInterReportService.getOne(queryReport);
        String summary = tbInterReport.getThreeTextArea();
        if (summary == null) {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(summary);
    }

    /**
     * 根据实习报告名称查询学生的相关信息
     *
     * @param reportName
     * @return
     */
    @GetMapping("/getStuInfo")
    public BaseResponse<TbStuInfo> getStuInfo(@RequestParam("reportName") String reportName) {
        QueryWrapper<TbDocument> queryDoc = new QueryWrapper<>();
        queryDoc.eq("fileName", reportName);
        TbDocument tbDocument = tbDocumentService.getOne(queryDoc);
        String stuId = tbDocument.getUploader();
        QueryWrapper<TbStuInfo> queryStuInfo = new QueryWrapper<>();
        queryStuInfo.eq("stuId", stuId);
        TbStuInfo stuInfo = tbStuInfoService.getOne(queryStuInfo);
        return ResultUtils.success(stuInfo);
    }

    /**
     * 提交实习鉴定意见与总成绩
     *
     * @param schTeaSubmitTotalScore
     * @return
     */
    @PostMapping("/addTotalScore")
    public BaseResponse<Boolean> addTotalScore(@RequestBody SchTeaSubmitTotalScore schTeaSubmitTotalScore) throws IOException, TemplateException {
        if (schTeaSubmitTotalScore == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String stuId = schTeaSubmitTotalScore.getStuId();
        Integer record = schTeaSubmitTotalScore.getRecord();
        Integer report = schTeaSubmitTotalScore.getReport();
        Integer evaluate = schTeaSubmitTotalScore.getEvaluate();
        String resultNum = schTeaSubmitTotalScore.getResultNum();

        /*实习报告生成代码*/

        //region
        /*查询当前学生提交的实习文档数量（文档数量未满15篇则不予生成报告，反之则生成报告）*/
        QueryWrapper<TbInterDoc> queryInterDoc = new QueryWrapper<>();
        queryInterDoc.eq("stuId", stuId).eq("state", 2);
        List<TbInterDoc> interDocList = tbInterDocService.list(queryInterDoc);
        if (interDocList.size() != 15) {
            return ResultUtils.error(OPERATION_ERROR, "已审核文档数量未满15篇！");
        }
        /*查询学生基本信息*/
        QueryWrapper<TbStuInfo> queryStuInfo = new QueryWrapper<>();
        queryStuInfo.eq("stuId", stuId);
        TbStuInfo tbStuInfo = tbStuInfoService.getOne(queryStuInfo);
        /*获取学生的校内指导老师id，查询校内指导老师相关信息*/
        Integer schTeaId = tbStuInfo.getSchTeaId();
        QueryWrapper<TbSchTea> querySchTea = new QueryWrapper<>();
        querySchTea.eq("id", schTeaId);
        TbSchTea tbSchTea = tbSchTeaService.getOne(querySchTea);
        /*根据学号查询学生学生的实习信息*/
        QueryWrapper<TbInterInfo> queryInterInfo = new QueryWrapper<>();
        queryInterInfo.eq("stuId", stuId);
        List<TbInterInfo> interInfoList = tbInterInfoService.list(queryInterInfo);
        String entName = null;
        String post = null;
        String entTeaName = null;
        Date createTime = new Date();
        Date updateTime = new Date();
        for (TbInterInfo tbInterInfo : interInfoList) {
            entName = tbInterInfo.getEntName();
            post = tbInterInfo.getPost();
            entTeaName = tbInterInfo.getLeader();
            createTime = tbInterInfo.getCreateTime();
            updateTime = tbInterInfo.getUpdateTime();
        }
        /*Date转换LocalDateTime*/
        LocalDateTime createLocalTime = DateTransLocalDate.transLocalDateTime(createTime);
        LocalDateTime updateLocalTime = DateTransLocalDate.transLocalDateTime(updateTime);
        /** 初始化配置文件 **/
        Configuration configuration = new Configuration();
        /** 设置编码 **/
        configuration.setDefaultEncoding("utf-8");
        /** 实习报告xml源文件存放路径**/
        String fileDirectory = reportPath;
        /** 加载文件 **/
        configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
        /** 加载模板 **/
        Template template = configuration.getTemplate("document.xml", "UTF-8");
        /** 准备数据 **/
        Map<String, Object> dataMap = new HashMap<>();
        /*获取当前时间*/
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDateTime ldt = LocalDateTime.now();
        String currentTime = dtf.format(ldt);
        /*格式化日期*/
        String newCreateLocalTime = dtf.format(createLocalTime);
        String newUpdateLocalTime = dtf.format(updateLocalTime);
        /*计算天数差*/
        long totalDays = ChronoUnit.DAYS.between(createLocalTime, updateLocalTime);
        /*查询学生填写的实习报告信息*/
        QueryWrapper<TbDocument> queryDocument = new QueryWrapper<>();
        queryDocument.eq("uploader", stuId);
        TbDocument tbDocument = tbDocumentService.getOne(queryDocument);
        Integer documentId = tbDocument.getId();
        QueryWrapper<TbInterReport> queryReportInfo = new QueryWrapper<>();
        queryReportInfo.eq("docId", documentId);
        TbInterReport tbInterReport = tbInterReportService.getOne(queryReportInfo);
        /*修改报告的状态和给报告打分*/
        TbInterReport interReport = new TbInterReport();
        interReport.setId(tbInterReport.getId());
        interReport.setState(1);
        interReport.setResult(resultNum);
        boolean updateReportResult = tbInterReportService.updateById(interReport);
        if (!updateReportResult) {
            return ResultUtils.error(OPERATION_ERROR);
        }
        String oneTextArea = tbInterReport.getOneTextArea();
        String twoTextArea = tbInterReport.getTwoTextArea();
        String threeTextArea = tbInterReport.getThreeTextArea();
        /*put数据*/
        dataMap.put("faculty", tbStuInfo.getFaculty());
        dataMap.put("major", tbStuInfo.getMajor());
        dataMap.put("classes", tbStuInfo.getClasses());
        dataMap.put("stuName", tbStuInfo.getName());
        dataMap.put("stuId", stuId);
        dataMap.put("schTeaName", tbSchTea.getName());
        dataMap.put("entName", entName);
        dataMap.put("currentTime", currentTime);
        dataMap.put("post", post);
        dataMap.put("createTime", newCreateLocalTime);
        dataMap.put("updateTime", newUpdateLocalTime);
        dataMap.put("totalDays", totalDays);
        dataMap.put("entTeaName", entTeaName);
        dataMap.put("oneTextArea", oneTextArea);
        dataMap.put("twoTextArea", twoTextArea);
        dataMap.put("threeTextArea", threeTextArea);
        dataMap.put("interDocList", interDocList);
        dataMap.put("resultNum", resultNum);
        switch (record) {
            case 1:
                dataMap.put("recordA", "√");
                break;
            case 2:
                dataMap.put("recordB", "√");
                break;
            case 3:
                dataMap.put("recordC", "√");
                break;
            case 4:
                dataMap.put("recordD", "√");
                break;
            case 5:
                dataMap.put("recordE", "√");
                break;
            default:
                break;
        }
        switch (report) {
            case 1:
                dataMap.put("reportA", "√");
                break;
            case 2:
                dataMap.put("reportB", "√");
                break;
            case 3:
                dataMap.put("reportC", "√");
                break;
            case 4:
                dataMap.put("reportD", "√");
                break;
            case 5:
                dataMap.put("reportE", "√");
                break;
            default:
                break;
        }
        switch (evaluate) {
            case 1:
                dataMap.put("evaluateA", "√");
                break;
            case 2:
                dataMap.put("evaluateB", "√");
                break;
            case 3:
                dataMap.put("evaluateC", "√");
                break;
            case 4:
                dataMap.put("evaluateD", "√");
                break;
            case 5:
                dataMap.put("evaluateE", "√");
                break;
            default:
                break;
        }
        System.out.println("文档列表=====》" + interDocList);
        /** 指定输出填充后的xml文件的路径 **/
        String outFilePath = reportPath + "documentData\\FillData.xml";
        File docFile = new File(outFilePath);
        FileOutputStream fos = new FileOutputStream(docFile);
        OutputStreamWriter oWriter = new OutputStreamWriter(fos);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos), 10240);
        template.process(dataMap, out);
        if (out != null) {
            out.close();
        }
        ZipInputStream zipInputStream = ZipUtils.wrapZipInputStream(new FileInputStream(new File(reportPath + "reportModel.zip")));
        ZipOutputStream zipOutputStream = ZipUtils.wrapZipOutputStream(new FileOutputStream(new File(reportPath + "InternshipReport\\" + tbStuInfo.getClasses() + tbStuInfo.getName() + ".docx")));
        String itemName = "word/document.xml";
        ZipUtils.replaceItem(zipInputStream, zipOutputStream, itemName, new FileInputStream(new File(reportPath + "documentData\\FillData.xml")));
        System.out.println("generateReport success");
        //endregion

        return ResultUtils.success(true);
    }


}
