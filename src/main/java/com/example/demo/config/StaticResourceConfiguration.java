package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class StaticResourceConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/templates/**",
                "/js/templates/**",
                "/components/**",
                "/js/components/**",
                "/img/**",
                "/css/**",
                "/fonts/**",
                "/js/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/templates/",
                        "classpath:/static/templates/",
                        "classpath:/static/components/",
                        "classpath:/static/components/",
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/fonts/",
                        "classpath:/static/js/");
    }
}
