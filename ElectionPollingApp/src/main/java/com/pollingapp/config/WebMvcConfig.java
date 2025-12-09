package com.pollingapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // serve uploaded images from filesystem path (adjust to your folder)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // convenience: map simple paths to templates
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home").setViewName("home");
    }
}
