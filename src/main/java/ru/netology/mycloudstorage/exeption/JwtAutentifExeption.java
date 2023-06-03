package ru.netology.mycloudstorage.exeption;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAutentifExeption  extends AuthenticationException {
private HttpStatus httpStatus;

public JwtAutentifExeption(String msg, HttpStatus httpStatus){
    super(msg);
    this.httpStatus = httpStatus;
}

    public JwtAutentifExeption(String msg) {
        super(msg);
    }
}
