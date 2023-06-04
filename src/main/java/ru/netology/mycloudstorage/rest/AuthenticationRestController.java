package ru.netology.mycloudstorage.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import ru.netology.mycloudstorage.modele.AuthToken;
import ru.netology.mycloudstorage.modele.MyToken;
import ru.netology.mycloudstorage.modele.User;
import ru.netology.mycloudstorage.repositopy.TokenRepositiry;
import ru.netology.mycloudstorage.repositopy.UserRepository;
import ru.netology.mycloudstorage.sequrity.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping("")
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private TokenRepositiry tokenRepositiry;


    public AuthenticationRestController(AuthenticationManager authenticationManager,
                                        UserRepository userRepository,
                                        JwtTokenProvider jwtTokenProvider,
                                        TokenRepositiry tokenRepositiry
                                           ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenRepositiry = tokenRepositiry;
                   }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {

        try {
            String login = authenticationRequestDTO.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getLogin(), authenticationRequestDTO.getPassword()));
            //если не найдешь пользователя по логину и паролю дай мне исключение
            User user = userRepository.findByLogin(authenticationRequestDTO.getLogin()).orElseThrow(() -> new UsernameNotFoundException("пользователь не найден"));
        //если нашел создай токен
            String token = jwtTokenProvider.createToken(authenticationRequestDTO.getLogin(), user.getRole().name());
            System.out.println(token);
            AuthToken authToken = new AuthToken(token);
            //сщхраняем токен в бд
            tokenRepositiry.save(MyToken.builder().token(token).build());
            Map<Object, Object> response = new HashMap<>();
            response.put("auth-token", authToken.getAuthToken());
            return ResponseEntity.ok(response);

        }catch (AuthenticationException ex){
            return new ResponseEntity<>("не валидный логин или пароль", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response, HttpServletRequest request) {

    SecurityContextLogoutHandler securityCotextLogoutHandler = new SecurityContextLogoutHandler();
    securityCotextLogoutHandler.logout(request, response, null);
    //ДАЛИТЬ ТОКЕН ИЗ БД добавить метод
        return ResponseEntity.ok("Success logout");
    }

    @GetMapping("/list")
    public void getAllFileNames(@RequestParam("auth-token") String token,
                                             @RequestParam("limit") Integer limit) {
        System.out.println("rrrrr" + token);
        //return service.getAllFileNames(token, limit);
    }

}

