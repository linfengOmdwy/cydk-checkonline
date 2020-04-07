package com.cydk.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //创业者申请页面
        registry.addViewController("/EntrepreneurPage").setViewName("EntrepreneurPage");
        //金融机构审批页面
        registry.addViewController("/bankPage").setViewName("BankPage");
        //区就业审批页面
        registry.addViewController("/districtJiuYePage").setViewName("districtJiuYePage");
    }
}
