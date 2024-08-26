package com.cq.cd;

import com.cq.cd.jwt.JwtAuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MavenProjectApplication extends SpringBootServletInitializer {
    //用于配置一个 JWT 身份验证的过滤器
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MavenProjectApplication.class);
    }
    //将过滤器通过@Bean注入到SpringBoot中
    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        registrationBean.setFilter(filter);
        return registrationBean;
    }
    public static void main(String[] args) {
        SpringApplication.run(MavenProjectApplication.class, args);
    }
}
