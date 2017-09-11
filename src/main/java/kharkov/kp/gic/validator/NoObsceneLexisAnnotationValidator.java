package kharkov.kp.gic.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class NoObsceneLexisAnnotationValidator implements ConstraintValidator<NoObsceneLexis, String> {

	private String[] obscene = new String[] {"nigger", "faggot", "fuck", "cunt"};
	
	@Override
	public void initialize(NoObsceneLexis constraintAnnotation) {
		
	}	

	@Override
	public boolean isValid(String s, ConstraintValidatorContext context) {
		if (!StringUtils.hasText(s)) {
			return true;
		}
		String l = s.toLowerCase();
		for(String obs : obscene) {
			if (l.contains(obs)) {
				return false;
			}
		}
		return true;
	}

}
