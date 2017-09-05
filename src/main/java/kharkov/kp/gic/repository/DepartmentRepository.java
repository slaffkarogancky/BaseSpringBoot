package kharkov.kp.gic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kharkov.kp.gic.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

	@Query("SELECT d FROM Department d WHERE LOWER(d.departmentName) = LOWER(:departmentName)")
	Department getDepartmentByName(@Param("departmentName") String name);
	
	@Modifying
	@Query(value = "delete from gr_department where department_id = :id", nativeQuery = true)
	void deleteDepartmentById(@Param("id") int id);
}
