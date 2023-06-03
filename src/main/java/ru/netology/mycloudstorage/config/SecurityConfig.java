package ru.netology.mycloudstorage.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.netology.mycloudstorage.modele.Role;

@Configuration
@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)// дает возможность воспользоваться @PreAuthorize

public class SecurityConfig extends WebSecurityConfigurerAdapter {
//мэмори сторадж хранит наших пользователей
    //гранд ассорити хранит данные о правах пользователей
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()  //доступ для всех
                .anyRequest()
                .authenticated()//все должны быть аутонтифицированы
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))// выход по данной ссылке и использовать только метод пост
                .invalidateHttpSession(true)//инвалидировать сессию
                .clearAuthentication(true)// очистить аутентификацию
                .deleteCookies("JSESSIONID")// удалит куки
                .logoutSuccessUrl("/login")//направит на страницу логин
        ;


    }
// Добавили пользователей в систкму
    //
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin")) // закодирует в $2a$04$cHv484BkdxUkZPf20ypWDuU7OJ7PViWobT5ZkNfhJm17XjhCwjG1C
                        .authorities(Role.ADMIN.getAuthorities())
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("user")) // закодирует в $2a$04$i34azfc6tZ2B8X1ZrZ8.peCjNc1.opU0VLu4zf2fdrCqL8IyvYgg.
                        .authorities(Role.USER.getAuthorities())
                        .build()
        );
    }

    //для кодирования пароля
    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(4);
    }
}
