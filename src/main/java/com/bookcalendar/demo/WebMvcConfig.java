package com.bookcalendar.demo;

import com.bookcalendar.demo.interceptor.LoginAfterInterceptor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginAfterInterceptor())
                .addPathPatterns("/members/login")
                .addPathPatterns("/members/join");

    }
}
