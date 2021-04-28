package com.test.testExe.configs;

import com.test.testExe.security.jwt.AuthEntrypointJwt;
import com.test.testExe.security.jwt.AuthTokenFilter;
import com.test.testExe.security.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    private final SecurityHelper securityHelper;
    private final AuthEntrypointJwt unauthenticatedHandler;
    private final AuthTokenFilter authTokenFilter;

    @Autowired
    public SecurityConfig(SecurityHelper securityHelper, AuthEntrypointJwt unauthenticatedHandler, AuthTokenFilter authTokenFilter) {
        this.securityHelper = securityHelper;
        this.unauthenticatedHandler = unauthenticatedHandler;
        this.authTokenFilter = authTokenFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(unauthenticatedHandler);
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().permitAll();
//        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(securityHelper);
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
