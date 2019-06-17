package com.ufcg.cc.psoft.ucdb;

import com.ufcg.cc.psoft.ucdb.controller.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UcdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcdbApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterJwt() {
        FilterRegistrationBean filterR = new FilterRegistrationBean();
        filterR.setFilter(new TokenFilter());
        filterR.addUrlPatterns("/v1/users/private");
        return filterR;
    }
}
