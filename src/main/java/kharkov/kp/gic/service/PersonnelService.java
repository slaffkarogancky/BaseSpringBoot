package kharkov.kp.gic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kharkov.kp.gic.domain.Department;
import kharkov.kp.gic.repository.DepartmentRepository;

@Service
public class PersonnelService {

	@Autowired
	private DepartmentRepository _departmentRepository;

	@Transactional(readOnly = true)
	public List<Department> getAllDepartments(int page, int size) {
		if (size == 0) {
			return _departmentRepository.findAll(new Sort(Sort.Direction.ASC, "departmentName"));
		} else {
			return _departmentRepository.findAll(new PageRequest(page, size, Direction.ASC, "departmentName")).getContent();
		}
	}

	@Transactional(readOnly = true)
	public Department getDepartmentById(int id) {
		return _departmentRepository.findOne(id);
	}
	
	@Transactional(readOnly = true)
	public Department getDepartmentByName(String name) {
		return _departmentRepository.getDepartmentByName(name);
	}

	@Transactional
	public Integer createDepartment(Department department) {
		Department created = _departmentRepository.saveAndFlush(department);
		return created.getId();
	}

	@Transactional
	public void updateDepartment(Department department) {
		_departmentRepository.save(department);
	}	

	@Transactional
	public void deleteDepartment(int id) {
		_departmentRepository.deleteDepartmentById(id);
	}
}
