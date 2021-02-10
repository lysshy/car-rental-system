package com.also.car.rental.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("CustomUserDetailsService")
    private UserDetailsService customUserDetailsService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置UserDetailsService
        auth.userDetailsService(this.customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // 装载BCrypt密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //关闭框架登录，自定义登录行为
        httpSecurity
                .formLogin().disable()
                .httpBasic().disable();

        httpSecurity.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html");

        httpSecurity.sessionManagement() // 添加 Session管理器
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authorizeRequests() // 授权配置
                .antMatchers("/auth/**",
                        "/login.html", "/code/image", "/code/sms", "/session/invalid").permitAll()// 无需认证的请求路径
//                .anyRequest()  // 所有请求
//                .authenticated() // 都需要认证
//                .anyRequest()
//                .authenticated()
//                .anyRequest().anonymous()
//                .access("@authorityService.checkPermission(request, authentication)")
                .and()
                .csrf().disable();


        //设置异常处理类
        httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }

    //用于影响全局安全性(配置资源，设置调试模式，通过实现自定义防火墙定义拒绝请求)的配置设置。
    //一般用于配置全局的某些通用事物，例如静态资源等
    @Override
    public void configure(WebSecurity web) {
        //放开swagger的限制
        web.ignoring().antMatchers("/v2/api-docs",
                "/swagger-resources/**", "/webjars/**",
                "/swagger-ui.html", "/login.html", "/css/**",
                "/authentication/require",
                "/code/image", "/code/sms", "/session/invalid", "api/**"
        );

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
