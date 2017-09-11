package kharkov.kp.gic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kharkov.kp.gic.domain.Department;
import kharkov.kp.gic.domain.DepartmentScan;
import kharkov.kp.gic.exception.EntityWasNotFoundedException;
import kharkov.kp.gic.repository.DepartmentRepository;
import kharkov.kp.gic.repository.DepartmentScanRepository;
import kharkov.kp.gic.utils.Utils;

@Service
public class PersonnelService {

	@Autowired
	private DepartmentRepository _departmentRepository;
	
	@Autowired
	private DepartmentScanRepository _departmentScanRepository;

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
		Department department = _departmentRepository.findOne(id);
		if (department != null) {
			@SuppressWarnings("unused")
			int count = department.getScans().size(); // fetch all scans
		}
		return department;
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
	
	@Transactional
	public int saveDepartmentScan(int departmentId, String fileName, byte[] content) {
		Department department = _departmentRepository.findOne(departmentId);
		if (department == null) {
			throw new EntityWasNotFoundedException("Department with id " + departmentId + " not found");
		}
		String fileExt = Utils.getFileExtension(fileName);	
		DepartmentScan scan = DepartmentScan.builder()
											.department(department)
											.scanName(fileName)
											.scanExt(fileExt)
											.content(content)
											.build();
		DepartmentScan created = _departmentScanRepository.saveAndFlush(scan);
		return created.getId();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unused")
	public DepartmentScan getDepartmentScan(int id) {
		DepartmentScan scan = _departmentScanRepository.findOne(id);
		if (scan != null)
		{
			Department d = scan.getDepartment();
			int contentLength = scan.getContent().length;
		}
		return scan;
	}
	
	
}
