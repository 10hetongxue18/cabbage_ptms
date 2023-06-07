package cn.xiaobaicai.cabbage_ptms_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 * @author hetongxue
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域配置，并让 authorization 可在响应头中出现
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5173/")
                .allowCredentials(true)
                .allowedMethods("GET","POST","DELETE","PUT")
                .exposedHeaders("authorization")
                .maxAge(3600);
    }
}
