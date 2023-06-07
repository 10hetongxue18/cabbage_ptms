package cn.xiaobaicai.cabbage_ptms_backend.controller;

import cn.xiaobaicai.cabbage_ptms_backend.common.BaseResponse;
import cn.xiaobaicai.cabbage_ptms_backend.common.ResultUtils;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.FileEntity;
import cn.xiaobaicai.cabbage_ptms_backend.model.entity.TbDocument;
import cn.xiaobaicai.cabbage_ptms_backend.service.TbDocumentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件控制器（上传、下载）
 * @author hetongxue
 */
@Api(tags = "文件模块")
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 文件上传/下载路径（可配置参数）
     */
    @Value("${download.filePath}")
    private String filePath;
    @Value("${report.filePath}")
    private String reportPath;

    @Resource
    private TbDocumentService tbDocumentService;


    /**
     * 获取文件列表
     * @return
     */
    @GetMapping("fileName")
    public BaseResponse<List> getFileName(){
        File file=new File(filePath);
        File[] fileArray=file.listFiles();
        List<FileEntity> fileEntityList =new ArrayList<>();
        for (File files : fileArray) {
            System.out.println("文件夹内文件:"+files.getName());
            FileEntity fileEntity =new FileEntity();
            fileEntity.setFileName(files.getName());
            fileEntityList.add(fileEntity);
        }
        return ResultUtils.success(fileEntityList);
    }


    /**
     * 文件下载
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/downloadFile")
    public ResponseEntity<InputStreamResource> downloadFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("本次下载的文件是:"+filePath + fileName);
        
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

        InputStreamResource i = new InputStreamResource(new FileInputStream(filePath + fileName));
        return ResponseEntity
                .ok()
                //.contentLength(pdfFile.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"))
                .body(i);
    }

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/uploadFile", consumes = "multipart/form-data")
    public BaseResponse<Integer> upload(@RequestPart MultipartFile file, HttpServletRequest request) throws IOException {
        /*获取当前登录用户*/
        String workId= (String) request.getSession().getAttribute("userAccount");
        System.out.println(request);
        /*取文件名*/
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        /*创造新文件*/
        File newFile = new File(filePath+fileName);
        /*将文件信息存入数据库*/
        TbDocument tbDocument=new TbDocument();
        tbDocument.setFileUrl(newFile.getAbsolutePath());
        tbDocument.setFileName(fileName);
        tbDocument.setUploader(workId);
        boolean result = tbDocumentService.save(tbDocument);
        if (!result){
            return null;
        }
        /*复制源文件的内容到新文件*/
        Files.copy(file.getInputStream(), newFile.toPath());
        System.out.println("Upload file success : " + newFile.getAbsolutePath());
        return ResultUtils.success(tbDocument.getId());
    }

    /**
     * 报告下载
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/downloadReport")
    public ResponseEntity<InputStreamResource> downloadReport(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
//        System.out.println("本次下载的文件是:"+filePath + fileName);
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
        InputStreamResource i = new InputStreamResource(new FileInputStream(reportPath +"/InternshipReport/"+ fileName));
        return ResponseEntity
                .ok()
                //.contentLength(pdfFile.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"))
                .body(i);
    }



}
