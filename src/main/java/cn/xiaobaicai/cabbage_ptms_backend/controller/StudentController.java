package cn.xiaobaicai.cabbage_ptms_backend.controller;

import cn.xiaobaicai.cabbage_ptms_backend.common.BaseResponse;
import cn.xiaobaicai.cabbage_ptms_backend.common.ErrorCode;
import cn.xiaobaicai.cabbage_ptms_backend.common.PageRequest;
import cn.xiaobaicai.cabbage_ptms_backend.common.ResultUtils;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.DocumentRequest.AddDocumentRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.InternshipRequest.InternshipUpdateRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.ReportRequest.GenerateReportRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.StudentRequest.StudentChooseTeaRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.dto.StudentRequest.StudentUpdateRequest;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.*;
import cn.xiaobaicai.cabbage_ptms_backend.model.vo.FileDetailInfoVo;
import cn.xiaobaicai.cabbage_ptms_backend.model.vo.ListNewsInfoVo;
import cn.xiaobaicai.cabbage_ptms_backend.model.vo.NewsDetailInfoVo;
import cn.xiaobaicai.cabbage_ptms_backend.service.*;
import cn.xiaobaicai.cabbage_ptms_backend.util.DateTransLocalDate;
import cn.xiaobaicai.cabbage_ptms_backend.util.ZipUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.org.apache.xpath.internal.operations.Variable;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static cn.xiaobaicai.cabbage_ptms_backend.common.ErrorCode.OPERATION_ERROR;

/**
 * @author hetongxue
 */
@Slf4j
@Api(tags = "学生模块")
@RestController
@RequestMapping("/student")
public class StudentController {


    /**
     * 自定义属性（实习报告存储路径）
     */
    @Value("${report.filePath}")
    private String reportPath;

    //region (CRUD)
    @Resource
    private TbStuInfoService tbStuInfoService;
    @Resource
    private TbInterInfoService tbInterInfoService;
    @Resource
    private TbNewsService tbNewsService;
    @Resource
    private TbInterDocService tbInterDocService;
    @Resource
    private TbDocumentService tbDocumentService;
    @Resource
    private TbSchTeaService tbSchTeaService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private TbEntTeaService tbEntTeaService;
    @Resource
    private TbApplicationService tbApplicationService;
    @Resource
    private TbInterReportService tbInterReportService;


    /**
     * 获取当前登录学生信息
     * @param request
     * @return
     */
    @GetMapping("/studentInfo")
    public BaseResponse<TbStuInfo> getStudentInfo(HttpServletRequest request){
        if(request==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        TbStuInfo studentInfo = tbStuInfoService.getStudentInfo(request);
        return ResultUtils.success(studentInfo);
    }

    /**
     * 更新学生信息
     * @param studentUpdateRequest
     * @return
     */
    @PostMapping("/updateStudentInfo")
    public BaseResponse<Boolean> updateStudentInfo(@RequestBody StudentUpdateRequest studentUpdateRequest){
        if(studentUpdateRequest==null||studentUpdateRequest.getId()==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        TbStuInfo tbStuInfo=new TbStuInfo();
        BeanUtils.copyProperties(studentUpdateRequest,tbStuInfo);
        boolean result = tbStuInfoService.updateById(tbStuInfo);
        return ResultUtils.success(result);
    }

    /**
     * 获取学生实习信息
     * @param request
     * @return
     */
    @GetMapping("/internshipInfo")
    public BaseResponse<TbInterInfo> getInternshipInfo(HttpServletRequest request){
        if(request==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        TbInterInfo internshipInfo = tbInterInfoService.getInternshipInfo(request);
        return ResultUtils.success(internshipInfo);
    }

    /**
     * 获取学生全部实习信息
     * @param request
     * @return
     */
    @GetMapping("/allInternshipInfo")
    public BaseResponse<List<TbInterInfo>> getAllInternshipInfo(HttpServletRequest request){
        if(request==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        //查询数据库
        Object userAccount = request.getSession().getAttribute("userAccount");
        String stuId=(String) userAccount;
        QueryWrapper<TbInterInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stuId",stuId).orderByAsc("createTime");
        List<TbInterInfo> allInterInfoList = tbInterInfoService.list(queryWrapper);
        if(allInterInfoList==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        return ResultUtils.success(allInterInfoList);
    }

    /**
     * 更新学生实习信息
     * @param internshipUpdateRequest
     * @return
     */
    @PostMapping("/updateInternshipInfo")
    public BaseResponse<Boolean> updateInternshipInfo(@RequestBody InternshipUpdateRequest internshipUpdateRequest, HttpServletRequest request){
        if(internshipUpdateRequest==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        String stuId= (String) request.getSession().getAttribute("userAccount");
        //查询学生的上一条实习记录
        QueryWrapper<TbInterInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stuId",stuId)
                .orderByDesc("createTime")
                .last("limit 1");
        TbInterInfo oneInterInfo = tbInterInfoService.getOne(queryWrapper);
        //修改上一条学生实习信息(state、updateTime)
        oneInterInfo.setState(0);
        oneInterInfo.setUpdateTime(new Date());
        boolean updateResult = tbInterInfoService.updateById(oneInterInfo);
        //实习信息是否修改成功
        if(!updateResult){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        //添加学生实习信息
        TbInterInfo tbInterInfo=new TbInterInfo();
        BeanUtils.copyProperties(internshipUpdateRequest,tbInterInfo);
        tbInterInfo.setStuId(stuId);
        boolean addResult = tbInterInfoService.save(tbInterInfo);
        return ResultUtils.success(addResult);
    }

    /**
     * 分页获取实习新闻（默认12条）
     * @return
     */
    @GetMapping("/list/news")
    public BaseResponse<List<ListNewsInfoVo>> listNewsByPage(@ModelAttribute PageRequest pageRequest){
        long current=1;
        long size=12;
        if(pageRequest!=null){
            current=pageRequest.getCurrent();
            size=pageRequest.getPageSize();
        }
        Page<TbNews> newsPage = tbNewsService.page(new Page<>(current, size), null);
        List<TbNews> records = newsPage.getRecords();
        List<ListNewsInfoVo> infoVoList=new ArrayList<>();
        for (TbNews news : records) {
            ListNewsInfoVo listNewsInfoVo=new ListNewsInfoVo();
            Integer areaId = news.getAreaId();
            TbArea tbArea = tbAreaService.getById(areaId);
            Long userId = tbArea.getUserId();
            TbEntTea tbEntTea = tbEntTeaService.getById(userId);
            listNewsInfoVo.setId(news.getId());
            listNewsInfoVo.setPostName(news.getPostName());
            listNewsInfoVo.setMessage(news.getMessage());
            listNewsInfoVo.setPicture(tbArea.getPicture());
            listNewsInfoVo.setEntName(tbEntTea.getEntName());
            infoVoList.add(listNewsInfoVo);
        }
        //返回分页查询数据
        return ResultUtils.success(infoVoList);
    }
    //endregion


    /**
     * 提交文档
     * @param addDocumentRequest
     * @param request
     * @return
     */
    @PostMapping("/addSubmitDoc")
    public BaseResponse<Long> AddDocument(@RequestBody AddDocumentRequest addDocumentRequest, HttpServletRequest request){
        String title = addDocumentRequest.getTitle();
        String content = addDocumentRequest.getContent();
        String stuId= (String) request.getSession().getAttribute("userAccount");
        TbInterDoc interDoc=new TbInterDoc();
        interDoc.setTitle(title);
        interDoc.setContent(content);
        interDoc.setStuId(stuId);
        interDoc.setState(1);
        boolean result = tbInterDocService.save(interDoc);
        if(!result){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        return ResultUtils.success(interDoc.getId());
    }

    /**
     * 获取实习文档信息
     * @param request
     * @return
     */
    @GetMapping("/getDocument")
    public BaseResponse<List<TbInterDoc>> getDocument(@RequestParam(value = "state",required = false) Integer state, HttpServletRequest request){
        String stuId= (String) request.getSession().getAttribute("userAccount");
        QueryWrapper<TbInterDoc> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stuId", stuId);
        queryWrapper.eq("state", state);
        List<TbInterDoc> tbInterDocs = tbInterDocService.list(queryWrapper);
        return ResultUtils.success(tbInterDocs);
    }

    /**
     * 更新实习文档信息
     * @param addDocumentRequest
     * @param request
     * @return
     */
    @PostMapping("/updateDocument")
    public BaseResponse<Long> updateDocument(@RequestBody AddDocumentRequest addDocumentRequest, HttpServletRequest request){
        String title = addDocumentRequest.getTitle();
        String content = addDocumentRequest.getContent();
        String stuId= (String) request.getSession().getAttribute("userAccount");

        TbInterDoc interDoc=new TbInterDoc();
        interDoc.setTitle(title);
        interDoc.setContent(content);
        interDoc.setStuId(stuId);
        interDoc.setState(1);
        boolean result = tbInterDocService.updateById(interDoc);
        if(!result){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        return ResultUtils.success(interDoc.getId());
    }

    /**
     * 获取指定老师上传的文件
     * @return
     */
    @GetMapping("fileName")
    public BaseResponse<List<TbDocument>> getFileName(HttpServletRequest request){
        /*获取登录学生学号*/
        String stuId= (String) request.getSession().getAttribute("userAccount");
        /*查询该学生信息*/
        QueryWrapper<TbStuInfo> queryStudent=new QueryWrapper<>();
        queryStudent.eq("stuId", stuId);
        TbStuInfo stuInfo = tbStuInfoService.getOne(queryStudent);
        /*取出该学生的指导老师id*/
        Integer schTeaId = stuInfo.getSchTeaId();
        /*查询该学生对应的指导老师信息*/
        QueryWrapper<TbSchTea> queryTeacher=new QueryWrapper<>();
        queryTeacher.eq("id", schTeaId);
        TbSchTea teacherInfo = tbSchTeaService.getOne(queryTeacher);
        /*取出老师的工号*/
        String workId = teacherInfo.getWorkId();
        /*查询该工号对应的文件上传信息*/
        QueryWrapper<TbDocument> queryDocument=new QueryWrapper<>();
        queryDocument.eq("uploader", workId).eq("isVisible",1);
        List<TbDocument> documentList = tbDocumentService.list(queryDocument);
        if(documentList==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        return ResultUtils.success(documentList);
    }


    /**
     * 实习新闻详情请求
     * @param id
     * @return
     */
    @GetMapping("/getNewsDetail")
    public BaseResponse<NewsDetailInfoVo> getNewsDetail(@RequestParam("id") Long id){
        if(id==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        /*封装返回对象*/
        NewsDetailInfoVo newsDetailInfoVo=new NewsDetailInfoVo();

        /*查询参数id的新闻数据*/
        TbNews news = tbNewsService.getById(id);
        newsDetailInfoVo.setNewsId(news.getId());
        newsDetailInfoVo.setPostName(news.getPostName());
        newsDetailInfoVo.setMessage(news.getMessage());
        /*取出实习基地id*/
        Integer areaId = news.getAreaId();
        /*查询实习基地信息*/
        QueryWrapper<TbArea> queryArea=new QueryWrapper<>();
        queryArea.eq("id", areaId);
        TbArea tbArea = tbAreaService.getOne(queryArea);
        newsDetailInfoVo.setAreaId(tbArea.getId());
        newsDetailInfoVo.setPicture(tbArea.getPicture());
        newsDetailInfoVo.setAddress(tbArea.getAddress());
        /*取出企业老师id*/
        Long EntTeaId = tbArea.getUserId();
        /*根据企业老师id查询对应的企业老师信息*/
        QueryWrapper<TbEntTea> queryEntTea=new QueryWrapper<>();
        queryEntTea.eq("id", EntTeaId);
        TbEntTea tbEntTea = tbEntTeaService.getOne(queryEntTea);
        newsDetailInfoVo.setEntTeaId(tbEntTea.getId());
        newsDetailInfoVo.setEntTeaName(tbEntTea.getName());
        newsDetailInfoVo.setEntName(tbEntTea.getEntName());
        newsDetailInfoVo.setPhone(tbEntTea.getPhone());
        newsDetailInfoVo.setEmail(tbEntTea.getEmail());
        if(newsDetailInfoVo==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        return ResultUtils.success(newsDetailInfoVo);
    }


    /**
     * 获取老师上传文件的详细信息
     * @return
     */
    @GetMapping("getFileDetailInfo")
    public BaseResponse<List<FileDetailInfoVo>> getFileDetailInfo(HttpServletRequest request){
        List<FileDetailInfoVo> fileDetailList = new ArrayList<>();
        /*获取登录学生学号*/
        String stuId= (String) request.getSession().getAttribute("userAccount");
        /*查询该学生信息*/
        QueryWrapper<TbStuInfo> queryStudent=new QueryWrapper<>();
        queryStudent.eq("stuId", stuId);
        TbStuInfo stuInfo = tbStuInfoService.getOne(queryStudent);
        /*取出该学生的指导老师id*/
        Integer schTeaId = stuInfo.getSchTeaId();
        /*查询该学生对应的指导老师信息*/
        QueryWrapper<TbSchTea> queryTeacher=new QueryWrapper<>();
        queryTeacher.eq("id", schTeaId);
        TbSchTea teacherInfo = tbSchTeaService.getOne(queryTeacher);
        /*判断老师信息非空*/
        if(teacherInfo==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        /*获取上传老师的姓名*/
        String schTeaName = teacherInfo.getName();
        /*取出老师的工号*/
        String workId = teacherInfo.getWorkId();
        /*查询该工号对应的文件上传信息*/
        QueryWrapper<TbDocument> queryDocument=new QueryWrapper<>();
        queryDocument.eq("uploader", workId).eq("isVisible",1).orderByDesc("createTime");
        List<TbDocument> documentList = tbDocumentService.list(queryDocument);
        for (TbDocument tbDocument : documentList) {
            /*返回封装对象*/
            FileDetailInfoVo fileDetailInfoVo = new FileDetailInfoVo();
            fileDetailInfoVo.setName(schTeaName);
            BeanUtils.copyProperties(tbDocument, fileDetailInfoVo);
            fileDetailList.add(fileDetailInfoVo);
        }
        if(documentList==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        return ResultUtils.success(fileDetailList);
    }

    /**
     * 学生申请实习操作
     * @param tbApplication
     * @param request
     * @return
     */
    @PostMapping("/addApplication")
    public BaseResponse<Integer> addApplication(@RequestBody TbApplication tbApplication, HttpServletRequest request){
        if(tbApplication==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        String stuId= (String) request.getSession().getAttribute("userAccount");
        TbApplication application=new TbApplication();
        application.setStuId(stuId);
        application.setNewsId(tbApplication.getNewsId());
        application.setAreaId(tbApplication.getAreaId());
        application.setEntId(tbApplication.getEntId());
        application.setIntroduction(tbApplication.getIntroduction());
        application.setState(tbApplication.getState());
        boolean result = tbApplicationService.save(application);
        if(!result){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        return ResultUtils.success(application.getId());
    }

    /**
     * 更多实习
     * @param id
     * @return
     */
    @GetMapping("/getMoreInternships")
    public BaseResponse<List<ListNewsInfoVo>> getMoreInternships(@RequestParam("id") Long id){
        if(id==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        /*查询除去当前id的所有实习新闻信息*/
        QueryWrapper<TbNews> queryNews=new QueryWrapper<>();
        queryNews.notIn("id",id);
        List<TbNews> newsList = tbNewsService.list(queryNews);
        List<ListNewsInfoVo> infoVoList=new ArrayList<>();
        /*遍历这些实习新闻信息，查询对应信息*/
        for (TbNews tbNews : newsList) {
            /*获取基地id,查询对应基地信息*/
            Integer areaId = tbNews.getAreaId();
            TbArea area = tbAreaService.getById(areaId);
            /*获取企业id，查询对应企业信息*/
            Long entId = area.getUserId();
            TbEntTea entTea = tbEntTeaService.getById(entId);
            /*封装返回对象*/
            ListNewsInfoVo listNewsInfoVo=new ListNewsInfoVo();
            listNewsInfoVo.setId(tbNews.getId());
            listNewsInfoVo.setPicture(area.getPicture());
            listNewsInfoVo.setEntName(entTea.getEntName());
            /*写入集合*/
            infoVoList.add(listNewsInfoVo);
        }
        //返回分页查询数据
        return ResultUtils.success(infoVoList);
    }


    /**
     * 自动生成实习报告
     * */
    @PostMapping("generateReport")
    public BaseResponse<Boolean> generateReport(@RequestBody GenerateReportRequest generateReportRequest, HttpServletRequest request) throws Exception{
        String stuId= (String) request.getSession().getAttribute("userAccount");
        /*查询当前学生提交的实习文档数量（文档数量未满15篇则不予生成报告，反之则生成报告）*/
        QueryWrapper<TbInterDoc> queryInterDoc=new QueryWrapper<>();
        queryInterDoc.eq("stuId",stuId).eq("state",2);
        List<TbInterDoc> interDocList = tbInterDocService.list(queryInterDoc);
        if(interDocList.size()!=15){
            return ResultUtils.error(OPERATION_ERROR,"已审核文档数量未满15篇！");
        }
        /*查询学生基本信息*/
        QueryWrapper<TbStuInfo> queryStuInfo=new QueryWrapper<>();
        queryStuInfo.eq("stuId",stuId);
        TbStuInfo tbStuInfo = tbStuInfoService.getOne(queryStuInfo);
        /*获取学生的校内指导老师id，查询校内指导老师相关信息*/
        Integer schTeaId = tbStuInfo.getSchTeaId();
        QueryWrapper<TbSchTea> querySchTea=new QueryWrapper<>();
        querySchTea.eq("id",schTeaId);
        TbSchTea tbSchTea = tbSchTeaService.getOne(querySchTea);
        /*根据学号查询学生学生的实习信息*/
        QueryWrapper<TbInterInfo> queryInterInfo=new QueryWrapper<>();
        queryInterInfo.eq("stuId",stuId);
        List<TbInterInfo> interInfoList = tbInterInfoService.list(queryInterInfo);
        String entName=null;
        String post=null;
        String entTeaName=null;
        Date createTime=new Date();
        Date updateTime=new Date();
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
        Template template = configuration.getTemplate("document.xml","UTF-8");
        /** 准备数据 **/
        Map<String, Object> dataMap = new HashMap<>();
        /*获取当前时间*/
        DateTimeFormatter dtf= DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDateTime ldt=LocalDateTime.now();
        String currentTime = dtf.format(ldt);
        /*格式化日期*/
        String newCreateLocalTime = dtf.format(createLocalTime);
        String newUpdateLocalTime = dtf.format(updateLocalTime);
        /*计算天数差*/
        long totalDays = ChronoUnit.DAYS.between(createLocalTime, updateLocalTime);
        /*学生填写的实习报告信息*/
        String oneTextArea = generateReportRequest.getOneTextArea();
        String twoTextArea = generateReportRequest.getTwoTextArea();
        String threeTextArea = generateReportRequest.getThreeTextArea();
        /*put数据*/
        dataMap.put("faculty",tbStuInfo.getFaculty());
        dataMap.put("major",tbStuInfo.getMajor());
        dataMap.put("classes",tbStuInfo.getClasses());
        dataMap.put("stuName",tbStuInfo.getName());
        dataMap.put("stuId",stuId);
        dataMap.put("schTeaName",tbSchTea.getName());
        dataMap.put("entName",entName);
        dataMap.put("currentTime",currentTime);
        dataMap.put("post",post);
        dataMap.put("createTime",newCreateLocalTime);
        dataMap.put("updateTime",newUpdateLocalTime);
        dataMap.put("totalDays",totalDays);
        dataMap.put("entTeaName",entTeaName);
        dataMap.put("oneTextArea",oneTextArea);
        dataMap.put("twoTextArea",twoTextArea);
        dataMap.put("threeTextArea",threeTextArea);
        dataMap.put("interDocList",interDocList);
        System.out.println("文档列表=====》"+interDocList);
        /** 指定输出填充后的xml文件的路径 **/
        String outFilePath = reportPath+"documentData\\FillData.xml";
        File docFile = new File(outFilePath);
        FileOutputStream fos = new FileOutputStream(docFile);
        OutputStreamWriter oWriter = new OutputStreamWriter(fos);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos),10240);
        template.process(dataMap,out);
        if(out != null){
            out.close();
        }
        ZipInputStream zipInputStream = ZipUtils.wrapZipInputStream(new FileInputStream(new File(reportPath+"reportModel.zip")));
        ZipOutputStream zipOutputStream = ZipUtils.wrapZipOutputStream(new FileOutputStream(new File(reportPath+"InternshipReport\\"+tbStuInfo.getClasses()+tbStuInfo.getName()+".docx")));
        String itemName = "word/document.xml";
        ZipUtils.replaceItem(zipInputStream, zipOutputStream, itemName, new FileInputStream(new File(reportPath+"documentData\\FillData.xml")));
        System.out.println("generateReport success");
        /*将实习报告信息存入数据库，如果数据库已有该条数据，则更新该条数据*/
        QueryWrapper<TbDocument> queryDoc=new QueryWrapper<>();
        queryDoc.eq("fileUrl",reportPath+"InternshipReport\\"+tbStuInfo.getClasses()+tbStuInfo.getName()+".docx").eq("fileName",tbStuInfo.getClasses()+tbStuInfo.getName()+".docx");
        TbDocument document = tbDocumentService.getOne(queryDoc);
        if(document==null){
            /*数据库无该条数据,添加该条数据到数据库*/
            TbDocument tbDocument=new TbDocument();
            tbDocument.setFileUrl(reportPath+"InternshipReport\\"+tbStuInfo.getClasses()+tbStuInfo.getName()+".docx");
            tbDocument.setFileName(tbStuInfo.getClasses()+tbStuInfo.getName()+".docx");
            tbDocument.setUploader(stuId);
            boolean result = tbDocumentService.save(tbDocument);
            if (!result){
                return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);            }
        }else{
            /*数据库有该条数据，修改该条数据*/
            Integer id = document.getId();
            TbDocument tbDocument=new TbDocument();
            tbDocument.setId(id);
            tbDocument.setFileUrl(reportPath+"InternshipReport\\"+tbStuInfo.getClasses()+tbStuInfo.getName()+".docx");
            tbDocument.setFileName(tbStuInfo.getClasses()+tbStuInfo.getName()+".docx");
            tbDocument.setUploader(stuId);
            boolean result = tbDocumentService.updateById(tbDocument);
            if (!result){
                return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);            }
        }
        /*查询刚存进数据库的那一条数据信息*/
        QueryWrapper<TbDocument> queryDocTwo=new QueryWrapper<>();
        queryDocTwo.eq("fileUrl",reportPath+"InternshipReport\\"+tbStuInfo.getClasses()+tbStuInfo.getName()+".docx").eq("fileName",tbStuInfo.getClasses()+tbStuInfo.getName()+".docx");
        TbDocument documentInfo = tbDocumentService.getOne(queryDoc);
        /*将生成的实习报告数据存入tb_inter_report*/
        TbInterReport tbInterReport = new TbInterReport();
        tbInterReport.setDocId(documentInfo.getId());
        tbInterReport.setEntTeaName(entTeaName);
        tbInterReport.setStuTeaName(tbSchTea.getName());
        tbInterReport.setOneTextArea(oneTextArea);
        tbInterReport.setTwoTextArea(twoTextArea);
        tbInterReport.setThreeTextArea(threeTextArea);
        boolean reportResult = tbInterReportService.save(tbInterReport);
        if(!reportResult){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }

        return ResultUtils.success(true);
    }


    /**
     * 查询学生生成的实习报告
     * @param request
     * @return
     */
    @GetMapping("/uploadFileInfo")
    public BaseResponse<TbDocument> getUploadFile(HttpServletRequest request){
        /*获取当前学生信息*/
        String stuId= (String) request.getSession().getAttribute("userAccount");
        QueryWrapper<TbDocument> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uploader", stuId);
        /*根据学号查询报告上传者*/
        TbDocument tbDocument = tbDocumentService.getOne(queryWrapper);
        return ResultUtils.success(tbDocument);
    }

    /**
     * 报告下载
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/downloadFile")
    public ResponseEntity<InputStreamResource> downloadFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("本次下载的文件是:"+"D:\\freemarker\\InternshipReport\\" + fileName);

        /*获取下载文件信息*/
        QueryWrapper<TbDocument> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fileName",fileName);
        TbDocument documentOne = tbDocumentService.getOne(queryWrapper);
        Integer id = documentOne.getId();
        Integer downloadNum = documentOne.getDownloadNum();

        /*增加下载次数，存入数据库*/
        TbDocument tbDocument=new TbDocument();
        tbDocument.setId(id);
        tbDocument.setDownloadNum(++downloadNum);
        boolean result = tbDocumentService.updateById(tbDocument);
        if(!result){
            return null;
        }

        InputStreamResource i = new InputStreamResource(new FileInputStream("D:\\freemarker\\InternshipReport\\" + fileName));
        return ResponseEntity
                .ok()
                //.contentLength(pdfFile.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"))
                .body(i);
    }

    /**
     * 查询学生实习指导老师情况
     * @param request
     * @return
     */
    @GetMapping("/getSchTeaDetails")
    public BaseResponse<TbSchTea> getSchTeaDetails(HttpServletRequest request){
        TbStuInfo studentInfo = tbStuInfoService.getStudentInfo(request);
        //查询当前学生是否有实习指导老师
        Integer schTeaId = studentInfo.getSchTeaId();
        if(schTeaId==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        //查询学生具体的实习指导老师详情
        QueryWrapper querySchTea=new QueryWrapper();
        querySchTea.eq("id",schTeaId);
        TbSchTea tbSchTea = tbSchTeaService.getOne(querySchTea);
        return ResultUtils.success(tbSchTea);
    }

    /***
     * 根据当前学生的院系信息查询该院系对应的老师信息
     * @param request
     * @return
     */
    @GetMapping("/getFacultyInfo")
    public BaseResponse<List<TbSchTea>> getFacultyInfo(HttpServletRequest request){
        TbStuInfo studentInfo = tbStuInfoService.getStudentInfo(request);
        String stuFaculty = studentInfo.getFaculty();
        QueryWrapper<TbSchTea> querySchTea = new QueryWrapper<TbSchTea>();
        querySchTea.eq("faculty",stuFaculty);
        List<TbSchTea> schTeaList = tbSchTeaService.list(querySchTea);
        if(schTeaList==null){
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR);        }
        return ResultUtils.success(schTeaList);
    }

    /**
     * 学生选择实习老师
     * @param studentChooseTeaRequest
     * @param request
     * @return
     */
    @PostMapping("/chooseTeacher")
    public BaseResponse<Boolean> chooseTeacher(@RequestBody StudentChooseTeaRequest studentChooseTeaRequest, HttpServletRequest request){
        if(studentChooseTeaRequest==null){
            ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        Integer teacherId = studentChooseTeaRequest.getTeacherId();
        TbStuInfo studentInfo = tbStuInfoService.getStudentInfo(request);
        studentInfo.setSchTeaId(teacherId);
        boolean result = tbStuInfoService.updateById(studentInfo);
        if(!result){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(true);
    }


}
