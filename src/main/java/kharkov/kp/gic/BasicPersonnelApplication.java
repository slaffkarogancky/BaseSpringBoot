package kharkov.kp.gic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BasicPersonnelApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicPersonnelApplication.class, args);
	}
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	// http://localhost:2017/swagger-ui.html
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())				
				.build()
				.apiInfo(metaData());
	}	
	
	private ApiInfo metaData() {
		return new ApiInfoBuilder()
                .title("Rogan gopota Rest API")
                .description("Spring Boot REST API для четких посонов")
                .build();

    }
}

//переписать ExceptionHandler 
//переписать на DTO, добавить свой валидатор, RestAssured #6  https://www.toptal.com/spring/top-10-most-common-spring-framework-mistakes
//swqagger