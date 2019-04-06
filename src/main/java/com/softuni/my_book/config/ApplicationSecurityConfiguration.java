package com.softuni.my_book.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
//                .csrfTokenRepository(csrfTokenRepository())
//                .and()
                .authorizeRequests()
                .antMatchers("/", "/login", "/register").anonymous()
                .antMatchers("/js/**", "/css/**", "/fonts/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//                .antMatchers("/viruses/add", "/viruses/delete", "/viruses/edit").hasAnyAuthority("ROLE_MODERATOR", "ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/profile/create")
                .and()
                .logout().logoutSuccessUrl("/login?logout").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized");

    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository =
                new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}
