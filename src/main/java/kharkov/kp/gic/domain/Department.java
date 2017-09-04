package kharkov.kp.gic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "gr_department")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Department {

	@Id
	@Column(name="department_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generate_department_id")	
	@SequenceGenerator(name="generate_department_id", sequenceName = "GR_GEN_DEPARTMENT_ID", allocationSize=1)
	private Integer id;
	
	@Column(name="department_name")
	private String departmentName;

}