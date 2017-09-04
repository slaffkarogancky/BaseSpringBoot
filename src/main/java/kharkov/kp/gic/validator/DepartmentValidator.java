package kharkov.kp.gic.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kharkov.kp.gic.domain.Department;
import kharkov.kp.gic.service.PersonnelService;

@Component
public class DepartmentValidator implements Validator {

	@Autowired
	private PersonnelService personnelService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Department.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		String newDepartmentName = ((Department) target).getDepartmentName();		
		if (!StringUtils.hasText(newDepartmentName)) {
			errors.reject("Incorrect parameter", "Department name cannot be empty");
		} else if (personnelService.getDepartmentByName(newDepartmentName) != null) {
			errors.reject("Incorrect parameter", "Department with name '" + newDepartmentName + "' already exists");
		}

	}
}
