package cn.xiaobaicai.cabbage_ptms_backend.util;


import freemarker.core.XMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DocUtils {
 

    public static void saveWord(String filePath, Map<String, Object> dataMap, String templatePath) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(DocUtils.class, "/");
        configuration.setOutputFormat(XMLOutputFormat.INSTANCE);
        Template template = configuration.getTemplate(templatePath,"utf-8");
//        File outFile=new File(filePath);
//
//        if (!outFile.getParentFile().exists()) {
//            outFile.getParentFile().mkdirs();// 新建文件夹
//        }
//        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));
//        try {
//            template.process(dataMap, out);
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }
        InputStreamSource streamSource = createWord(template, dataMap);
//        System.out.println("streamSource:"+streamSource);
        InputStream inputStream = streamSource.getInputStream();
        File file=new File(filePath);
        FileOutputStream outputStream = new FileOutputStream(filePath);
        byte[] bytes = new byte[1024];
        while ((inputStream.read(bytes)) != -1) {
            outputStream.write(bytes);// 写入数据
        }
        inputStream.close();
        outputStream.close();
    }

    public static InputStreamSource createWord(Template template, Map<String, Object> dataMap) {
        StringWriter out = null;
        Writer writer = null;
        try {
            out = new StringWriter();
            writer = new BufferedWriter(out, 1024);
            template.process(dataMap, writer);
            return new ByteArrayResource(out.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void isExist(String path) {
        File dest = new File(path);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();// 新建文件夹
        }
    }


 
}