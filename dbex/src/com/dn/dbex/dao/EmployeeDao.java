package com.dn.dbex.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.dn.dbex.domain.Employee;

import jdbc.JDBCUtils;
import jdbc.TxQueryRunner;

public class EmployeeDao {
	private TxQueryRunner queryRunner = new TxQueryRunner();
	
	public void add(Employee employee) throws SQLException{
		String sql = "INSERT INTO employee(empno,firstname,age,lastname) values(?,?,?,?)";
		
		Object[] params = {employee.getEmpno(),employee.getFirstname(),employee.getAge(),employee.getLastname()};
		
		queryRunner.update(sql, params);
	}
	
	public Vector<String> listTables() throws ClassNotFoundException, SQLException {
		Connection con = JDBCUtils.getConnection();
		
		DatabaseMetaData dmd = con.getMetaData();
		ResultSet tableSet = dmd.getTables(null, null, "%", new String[] {"TABLE"});
		
		Vector<String> datas = new Vector<String>();
		while(tableSet.next()) {
			datas.add(tableSet.getString("TABLE_NAME"));
		}
		
		tableSet.close();
		JDBCUtils.releaseConnection(con);
		return datas;
	}
	
	public Vector<String> getColumnName(String table) throws SQLException, ClassNotFoundException{
		Connection con = JDBCUtils.getConnection();
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
		Vector<String> names = new Vector<String>();
		for(int i=1;i<=rs.getMetaData().getColumnCount();i++) {
			names.add(rs.getMetaData().getColumnName(i));
		}
		
		rs.close();
		stmt.close();
		JDBCUtils.releaseConnection(con);
		return names;
	}
	
	public List<Employee> list(String table) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM " + table;
		return queryRunner.query(sql, new BeanListHandler<Employee>(Employee.class));
	}
	
	public void deleteAll(String table) {
		String sql = "DELETE FROM " + table;
		try {
			queryRunner.update(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
