package com.dn.dbex.test;

import java.sql.SQLException;

import com.dn.dbex.dao.EmployeeDao;
import com.dn.dbex.domain.Employee;

public class Test {
	@org.junit.Test
	public void fun1() {
		EmployeeDao employeeDao = new EmployeeDao();
		
		Employee employee = new Employee();
		employee.setEmpno("aaaaaaaaaa");
		employee.setFirstname("asffffaaaadddddddddda");
		employee.setAge("");
		employee.setLastname("");
		
		try {
			employeeDao.add(employee);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
		}
	}
}
