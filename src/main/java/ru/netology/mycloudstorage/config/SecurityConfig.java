package ru.netology.mycloudstorage.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.netology.mycloudstorage.sequrity.JwtConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// дает возможность воспользоваться @PreAuthorize

public class SecurityConfig extends WebSecurityConfigurerAdapter {


  private final JwtConfigurer jwtConfigurer;

    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    //мэмори сторадж хранит наших пользователей
    //гранд ассорити хранит данные о правах пользователей
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//создаем сессию
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()  //доступ для всех
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()//все должны быть аутонтифицированы
                .and()
                .apply(jwtConfigurer);//буду аутнтифицировать пользователей не основе кофигурации в этом классе


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()throws Exception{
        return super.authenticationManagerBean();
    }
   //для кодирования пароля
    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(4);
    }

}
