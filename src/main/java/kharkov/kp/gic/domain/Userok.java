package kharkov.kp.gic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gr_user")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Userok {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generate_user_id")	
	@SequenceGenerator(name="generate_user_id", sequenceName = "GR_GEN_USER_ID", allocationSize=1)
	private Integer id;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="user_password")
	private String password;
	
	@Column(name="is_operator")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean isOperator;
	
	@Column(name="is_admin")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean isAdmin;
}
