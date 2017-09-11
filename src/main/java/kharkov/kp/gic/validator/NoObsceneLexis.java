package kharkov.kp.gic.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { NoObsceneLexisAnnotationValidator.class })
public @interface NoObsceneLexis {

	String message() default "String contains obscene lexis";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


/*
Можно использовать встроенные валидаторы:

ставится над строкой
@Email, @NotBlank, @NotEmpty, @Pattern, @Size(min=, max=), @Length(min=, max=), @Range(min=, max=)

ставится над датой
@Future, @FutureOpPresent, @Past, @PastOrPresent, @Range(min=, max=)


ставится над числом
@Min, @Max, @Positive, @PositiveOrZero 

ставится над коллекцией
@NotEmpty, @Size(min=, max=)

ставится везде
@NotNull, @Null

*/