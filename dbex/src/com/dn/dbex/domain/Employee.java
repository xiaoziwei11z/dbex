package com.dn.dbex.domain;

public class Employee {
	private String empno;
	private String firstname;
	private String age;
	private String lastname;
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	@Override
	public String toString() {
		return "Employee [empno=" + empno + ", firstname=" + firstname + ", age=" + age + ", lastname=" + lastname
				+ "]";
	}
	
}
