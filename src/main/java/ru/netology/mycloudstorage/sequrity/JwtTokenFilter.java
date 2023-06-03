package ru.netology.mycloudstorage.sequrity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.netology.mycloudstorage.exeption.JwtAutentifExeption;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        System.out.println(" doFilter tok " + token);
        try {
            //если токен есть и он валидный то аутентифицируемся

            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                System.out.println(" doFilter au " + authentication);
                //если аутентификация есть то секюрити контекст холдер возьми эту идентификацию и положи в себя
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (JwtAutentifExeption e){//если будет исключение,очисти контекст и выкинь ошибку
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            throw new JwtAutentifExeption("токен истек или не валидный");
        }
        //если ни каких проблем нет передай дальше реквэст и респонс
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
