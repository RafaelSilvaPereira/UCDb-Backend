package com.ufcg.cc.psoft.ucdb;



import com.ufcg.cc.psoft.ucdb.accessFilter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class UcdbApplication {

    public static void main(String[] args) { SpringApplication.run(UcdbApplication.class, args); }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Bean
    public FilterRegistrationBean filterJwt() {
        FilterRegistrationBean filterRb = new FilterRegistrationBean();
        filterRb.addUrlPatterns(
                "/v1/subjects/id/*",
                "/v1/subjects/search/*",
                "/v1/subjects/like",
                "/v1/subjects/dislike",
                "/v1/subjects/unlike",
                "/v1/subjects/undislike",
                "/v1/comment/create",
                "/v1/comment/reply",
                "/v1/comment/*");
        filterRb.setFilter(new TokenFilter());

        return filterRb;
    }

}
