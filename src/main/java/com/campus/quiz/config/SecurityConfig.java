package com.campus.quiz.config;

import com.campus.quiz.repo.UserAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.campus.quiz.repo.UserAccountRepository;

@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean UserDetailsService userDetailsService(UserAccountRepository users){return username->users.findByUsername(username)
            .map(u->org.springframework.security.core.userdetails.User.withUsername(u.getUsername()).password(u.getPassword()).roles(u.getRole()).build())
            .orElseThrow(()->new org.springframework.security.core.userdetails.UsernameNotFoundException(username));}
    @Bean AuthenticationProvider authenticationProvider(UserDetailsService details,PasswordEncoder encoder){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();provider.setUserDetailsService(details);provider.setPasswordEncoder(encoder);return provider;
    }
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http,UserAccountRepository users)throws Exception{
        return http.authorizeHttpRequests(a->a.requestMatchers("/","/login","/register","/css/**","/error").permitAll()
                .requestMatchers("/teacher/**").hasRole("TEACHER").requestMatchers("/student/**").hasRole("STUDENT").anyRequest().authenticated())
            .formLogin(f->f.loginPage("/login").successHandler((request,response,authentication)->{
                users.findByUsername(authentication.getName()).ifPresent(user->{user.setLastLoginAt(java.time.LocalDateTime.now());users.save(user);});
                boolean teacher=authentication.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_TEACHER"));
                response.sendRedirect(teacher?"/teacher":"/student");
            }).permitAll()).logout(l->l.logoutSuccessUrl("/login?logout")).build();
    }
}
