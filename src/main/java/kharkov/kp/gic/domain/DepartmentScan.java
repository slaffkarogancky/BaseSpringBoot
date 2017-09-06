package kharkov.kp.gic.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gr_department_scan")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DepartmentScan {

	@Id
	@Column(name="scan_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generate_department_scan_id")	
	@SequenceGenerator(name="generate_department_scan_id", sequenceName = "GR_GEN_SCAN_ID", allocationSize=1)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="department_id")
	@JsonIgnore
	private Department department;  
	
	@Column(name="scan_name")
	private String scanName;
	
	@Column(name="scan_ext")
	private String scanExt;
	
	@Column(name="scan_blob")
	@Lob
	@Basic(fetch=FetchType.LAZY)	
	private byte[] content;
	
	// чтобы избежать рекурсии при конвертации в json
	@JsonIgnore
	public Department getDepartment() {
		return this.department;
	}
	
	// чтобы избежать отображения содержимого файла при конвертации в json
	@JsonIgnore
	public byte[] getContent() {
		return this.content;
	}
}



