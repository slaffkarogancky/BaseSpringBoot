package kharkov.kp.gic.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kharkov.kp.gic.domain.Userok;
import kharkov.kp.gic.repository.UserokRepository;

@RestController
@RequestMapping("/personnel/api/v1") 
public class UserController {

	private UserokRepository userokRepository;	
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserokRepository userokRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userokRepository = userokRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Userok user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userokRepository.save(user);
    }
}

/*
Используется JWT (Json web token) https://en.wikipedia.org/wiki/JSON_Web_Token

1) Чтобы зарегить нового пользователя: 
отправьте POST на http://localhost:2017/personnel/api/v1/sign-up c заголовком Content-Type: application/json и телом
{
  "userName" : "Ваш Логин",
  "password" : "Ваш Пароль",
  "isOperator" : true,
  "isAdmin" : true
}

2) Чтобы получить временный токен:
 отправьте POST на http://localhost:2017/login c заголовком Content-Type: application/json и телом
{
  "userName" : "Ваш Логин",
  "password" : "Ваш Пароль"
}
Придет ответ, с заголовком authorization: Bearer ДЛИННЮЧАЯ_СТРОКА_ОН_ЖЕ_ВАШ_ТОКЕН - токен запомнить!!!


3) Чтобы выполнить любой запрос в RestApi, к запросу добавьте заголовок
Authorization: Bearer ДЛИННЮЧАЯ_СТРОКА_ОН_ЖЕ_ВАШ_ТОКЕН



*/
