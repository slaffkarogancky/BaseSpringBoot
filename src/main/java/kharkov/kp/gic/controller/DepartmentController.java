package kharkov.kp.gic.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import kharkov.kp.gic.domain.Department;
import kharkov.kp.gic.exception.ResourceWasNotFoundedException;
import kharkov.kp.gic.service.PersonnelService;
import kharkov.kp.gic.validator.DepartmentValidator;

@RestController
@RequestMapping("/personnel/api/v1")
public class DepartmentController {

	@Autowired
	PersonnelService personnelService;

	@Autowired
	DepartmentValidator departmentValidator;

	@InitBinder("department")
	protected void setupBinder(WebDataBinder binder) {
		binder.setValidator(departmentValidator);
	}

	// http://localhost:2017/personnel/api/v1/ping
	@GetMapping(value = "/ping")
	public ResponseEntity<String> ping() {
		return new ResponseEntity<String>("PONG", HttpStatus.OK);
	}

	// http://localhost:2017/personnel/api/v1/departments
	@GetMapping(value = "/departments")
	public ResponseEntity<Iterable<Department>> getAllDepartments(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "0") int size) {
		Iterable<Department> departments = personnelService.getAllDepartments(page, size);
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}

	// http://localhost:2017/personnel/api/v1/departments/1
	@GetMapping(value = "/departments/{id}")
	public ResponseEntity<Department> getDepartment(@PathVariable int id) {		
		Department department = personnelService.getDepartmentById(id);
		if (department == null) {
			throw new ResourceWasNotFoundedException("Department with id " + id + " not found");
		}
		return new ResponseEntity<>(department, HttpStatus.OK);
	}

	@PostMapping(value = "/departments")
	public ResponseEntity<?> createDepartment(@Valid @RequestBody Department department) {
		int createdDepId = personnelService.createDepartment(department);
		HttpHeaders responseHeaders = new HttpHeaders();
		// @formatter:off
		URI newDepartmentUri = ServletUriComponentsBuilder.fromCurrentRequest()
														  .path("/{id}")
														  .buildAndExpand(createdDepId).toUri();
		// @formatter:on
		responseHeaders.setLocation(newDepartmentUri);
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}

	@PutMapping(value = "/departments/{id}")
	public ResponseEntity<?> updateDepartment(@Valid @RequestBody Department department, @PathVariable int id) {
		department.setId(id);
		personnelService.updateDepartment(department);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/departments/{id}")
	public ResponseEntity<?> deleteDepartment(@PathVariable int id) {
		personnelService.deleteDepartment(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
