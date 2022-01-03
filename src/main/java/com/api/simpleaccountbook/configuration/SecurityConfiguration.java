package com.api.simpleaccountbook.configuration;

import com.api.simpleaccountbook.jwt.JwtAuthenticationFilter;
import com.api.simpleaccountbook.jwt.JwtAuthorizationFilter;
import com.api.simpleaccountbook.member.repository.MemberRepository;
import com.api.simpleaccountbook.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Bean
    PasswordEncoder getPasswordEncoder() { return new BCryptPasswordEncoder(); }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();

        // 동일 도메인 iframe 접근 허용
        http.headers().frameOptions().sameOrigin();

        // 기본 session 설정 해제
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.memberRepository));
        http.authorizeRequests()
                    .antMatchers(
                            "/",
                            "/api/member/**"
                    ).permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers(
                            "/api/account/**"
                    ).hasAuthority("ROLE_USER")
                    .anyRequest()
                    .authenticated();

        // form login 설정 해제
        http.formLogin()
                .disable()
                .headers()
                .frameOptions()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(getPasswordEncoder());
    }
}
