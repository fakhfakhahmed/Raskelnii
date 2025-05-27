package tn.ecnam.resources.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;



@Configuration
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {




    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/favicon.ico",
                "/webjars/**",
                "/swagger-ui-custom.html",
                "/swagger-ui/**");

    }

    }


