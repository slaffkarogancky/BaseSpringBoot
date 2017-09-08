package kharkov.kp.gic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BasicPersonnelApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicPersonnelApplication.class, args);
	}
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

//переписать ExceptionHandler 
//переписать на DTO, добавить свой валидатор, RestAssured #6  https://www.toptal.com/spring/top-10-most-common-spring-framework-mistakes
//swqagger