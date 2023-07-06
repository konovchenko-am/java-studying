package final_work.controllers;

import final_work.models.User;
import final_work.security.AuthRequest;
import final_work.security.AuthResponse;
import final_work.security.JWTUtil;
import final_work.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST контроллер для аутентификации и регистрации пользователей
 */
@RestController
public class AuthenticationController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtTokenUtil;

    @Autowired
    IUserService userService;

    /**
     * Обработка запроса аутентификации
     * @param authRequest запрос аутентификации
     * @return объект AuthResponse
     */
    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
            System.out.println(authentication);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверные имя пользователя, и/или пароль.", e);
        }
        // при создании токена в него кладется username как Subject и список authorities как кастомный claim
        String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());

        return new AuthResponse(jwt);
    }

    /**
     * Обработка запроса на регистрацию от неавторизованного пользователя
     * @param user объект User
     * @return ResponseEntity c объектом User, полученным по результатам его сохранения в БД
     */
    @PostMapping(value = "/registration")
    public ResponseEntity<?> create(@RequestBody User user){ //user - это объект, который клиент отправил в виде json
        user.setRole("USER"); // принудительно выставляем роль обычного пользователя
        User dbUser;
        try{
            dbUser = userService.create(user);
            return new ResponseEntity<>(dbUser, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(Messages.CREATE_USER_ERROR, HttpStatus.BAD_REQUEST);
        }
    }
}
