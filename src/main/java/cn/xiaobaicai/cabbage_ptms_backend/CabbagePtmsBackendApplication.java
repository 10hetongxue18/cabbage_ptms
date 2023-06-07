package cn.xiaobaicai.cabbage_ptms_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


/**
 * @author cabbage
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "cn.xiaobaicai.cabbage_ptms_backend.filter")
@MapperScan("cn.xiaobaicai.cabbage_ptms_backend.mapper")
public class CabbagePtmsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CabbagePtmsBackendApplication.class, args);
        System.out.println("CabbagePTMSBackend Succeed......");
    }



}
