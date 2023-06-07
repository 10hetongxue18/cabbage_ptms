package cn.xiaobaicai.cabbage_ptms_backend.controller;

import cn.xiaobaicai.cabbage_ptms_backend.util.ZipUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testController {


//        @Test
//        public void generateRessultFile() {
//            try{
//                //1.通过传入的实体组装Map数据
//                Map<String, Object> map = new HashMap<>();
//                map.put("faculty","信息工程学院");
//                map.put("major","软件技术");
//                map.put("classes","班级");
//                map.put("name","姓名");
//                map.put("stuId","202004260047");
//                map.put("schTeacher","刘老师");
//                map.put("entName","上海天命有限公司");
//                map.put("createTime", System.currentTimeMillis());
//
//                //2.生成到的根目录地址
//                ClassPathResource resource = new ClassPathResource("templates/");
//                //3.文件地址
//                String filePath = resource.getPath() + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSS") + "-" + "实习实训报告书"+".docx";
//                //4.文件名称
//                String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
//                System.out.println("filePath:"+filePath);
//                System.out.println("fileName:"+fileName);
//                //5.文件地址是否存在，不存在新建目录
//                DocUtils.isExist(filePath);
//                //6.传入对应的文件地址、Map组装数据、xml模板地址
//                DocUtils.saveWord(filePath, map, "templates/文档模板.xml");
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }

    @Test
    public void ttt() throws Exception {
        /** 初始化配置文件 **/
        Configuration configuration = new Configuration();
        /** 设置编码 **/
        /** 我的xml文件是放在D盘的**/
        String fileDirectory = "D:\\freemarker";
        /** 加载文件 **/
        configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
        /** 加载模板 **/
        Template template = configuration.getTemplate("document.xml");

        /** 准备数据，这里是我自己的业务。就是把变量赋值上${xxxx}去啦 **/
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("faculty","信息工程学院");
        dataMap.put("major","软件技术");
        dataMap.put("classes","班级");
        dataMap.put("name","姓名");
        dataMap.put("stuId","202004260047");
        dataMap.put("schTeacher","刘老师");
        dataMap.put("entName","上海天命有限公司");
        dataMap.put("createTime", System.currentTimeMillis());
        dataMap.put("stuName","冯荷");
        dataMap.put("faculty","信息工程学院");
        dataMap.put("classes","计软20-2");
        dataMap.put("entName","腾讯计算机有限公司");
        dataMap.put("post","Java工程师");
        dataMap.put("createTime",System.currentTimeMillis());
        dataMap.put("updateTime",System.currentTimeMillis());
        dataMap.put("entTeaName","王德发");
        dataMap.put("schTeaName","牛德华");



        /** 指定输出word文件的路径 **/
        String outFilePath = "D:\\freemarker\\documentData\\FillData.xml";
        File docFile = new File(outFilePath);
        FileOutputStream fos = new FileOutputStream(docFile);
        OutputStreamWriter oWriter = new OutputStreamWriter(fos);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos),10240);
        template.process(dataMap,out);

        if(out != null){
            out.close();
        }
        ZipInputStream zipInputStream = ZipUtils.wrapZipInputStream(new FileInputStream(new File("D:\\freemarker\\reportModel.zip")));
        ZipOutputStream zipOutputStream = ZipUtils.wrapZipOutputStream(new FileOutputStream(new File("D:\\freemarker\\InternshipReport\\计软20-2-07冯荷.docx")));
        String itemname = "word/document.xml";
        ZipUtils.replaceItem(zipInputStream, zipOutputStream, itemname, new FileInputStream(new File("D:\\freemarker\\documentData\\FillData.xml")));
        System.out.println("success");
    }






}
