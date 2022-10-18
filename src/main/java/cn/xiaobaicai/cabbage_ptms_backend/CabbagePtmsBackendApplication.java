package cn.xiaobaicai.cabbage_ptms_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * @author cabbage
 */
@SpringBootApplication
public class CabbagePtmsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CabbagePtmsBackendApplication.class, args);
        succeed();
    }

    public static void succeed(){
        System.out.println("CabbagePTMSBackend Succeed......");
    }



}
