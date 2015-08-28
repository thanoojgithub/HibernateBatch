package com.hibernatebatch.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BATCHEMPLOYEE", catalog = "mydb")
public class Employee implements java.io.Serializable {

	private static final long serialVersionUID = 5946056479604771664L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EID", unique = true, nullable = false)
	private Integer eId;

	@Column(name = "ENAME", unique = true, nullable = false)
	private String eName;

	public Employee() {
	}

	public Employee(String eName) {
		super();
		this.eName = eName;
	}

	public Integer geteId() {
		return eId;
	}

	public void seteId(Integer eId) {
		this.eId = eId;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

}