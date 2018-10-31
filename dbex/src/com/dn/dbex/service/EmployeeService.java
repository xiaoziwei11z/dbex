package com.dn.dbex.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.dn.dbex.dao.EmployeeDao;
import com.dn.dbex.domain.Employee;
import com.dn.utils.CommonUtils;

public class EmployeeService {
	private EmployeeDao employeeDao = new EmployeeDao();
	
	public void sqlInsert(Vector<Vector<String>> lists) {
		employeeDao.deleteAll("employee");
		for (Vector<String> vector : lists) {
			try {
				String empno = vector.get(0);
				String firstname = vector.get(1);
				String age = vector.get(2);
				String lastname = vector.get(3);
				
				if(empno.equals("")) {
					throw new SQLException("not write");
				}
				if(empno.length()>10) {
					throw new SQLException("-1no");
				}
				if(firstname.length()>20) {
					throw new SQLException("-1fn");
				}
				if(age.length()>5) {
					throw new SQLException("-1ag");
				}
				if(lastname.length()>20) {
					throw new SQLException("-1ln");
				}
				if(!age.isEmpty() && (Integer.parseInt(age)<0 || Integer.parseInt(age)>200)) {
					throw new SQLException("agevalue");
				}
				
				Map<String, String> map = new HashMap<String,String>();
				map.put("empno",empno.equals("空")?null:empno);
				map.put("firstname",firstname.equals("空")?null:firstname);
				map.put("age",age.equals("空")?null:age);
				map.put("lastname",lastname.equals("空")?null:lastname);
				Employee employee = CommonUtils.toBean(map, Employee.class);
				employeeDao.add(employee);
			} catch (SQLException e) {
				if(e.getErrorCode() == -407 || e.getMessage().equals("not write")) {
					JOptionPane.showMessageDialog(null, "请填写工号");
				}
				if(e.getErrorCode() == -803) {
					JOptionPane.showMessageDialog(null, "该雇员已存在");
				}
				if(e.getMessage().equals("-1no")) {
					JOptionPane.showMessageDialog(null, "您输入的工号过长");
				}
				if(e.getMessage().equals("-1fn")) {
					JOptionPane.showMessageDialog(null, "您输入的名字过长");
				}
				if(e.getMessage().equals("-1ag")) {
					JOptionPane.showMessageDialog(null, "您输入的年龄过长");
				}
				if(e.getMessage().equals("-1ln")) {
					JOptionPane.showMessageDialog(null, "您输入的姓氏过长");
				}
				if(e.getMessage().equals("agevalue")) {
					JOptionPane.showMessageDialog(null, "年龄必须在0~200之间");
				}
			}
		}
	}

	public Vector<String> listTables() throws ClassNotFoundException, SQLException {
		return employeeDao.listTables();
	}
	
	public Vector<String> getColumnName(String table) throws SQLException, ClassNotFoundException{
		return employeeDao.getColumnName(table);
	}
	
	public Vector<Vector<String>> listDatas(String table) throws ClassNotFoundException, SQLException {
		//将Bean的Vector转化为Vector<String>的Vector返回
		Vector<Employee> employees = new Vector<Employee>(employeeDao.list(table));
		Vector<Vector<String>> lists = new Vector<Vector<String>>();
		for(int i=0;i<employees.size();i++) {
			Vector<String> list = new Vector<String>();
			Employee employee = employees.get(i);
			list.add(employee.getEmpno());
			list.add(employee.getFirstname());
			list.add(employee.getAge());
			list.add(employee.getLastname());
			for(int j=0;j<list.size();j++) {
				if(list.get(j) == null) {
					list.set(j, "空");
				}
			}
			lists.add(list);
		}
		return lists;
	}
}
