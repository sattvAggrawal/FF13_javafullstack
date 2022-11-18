package com.examples.empapp.DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.examples.empapp.model.Employee;

public class EmployeeDAOImpl implements EmployeeDao {
	static Connection con = ConnectionHelper.createConnection();
	
	@Override
	public boolean insert(Employee emp) {
		
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO empdata (name, age, designation, department, country) VALUES(?,?,?,?,?)");
			
			ps.setString(1, emp.getName());
			ps.setInt(2, emp.getAge());
			ps.setString(3, emp.getDesignation());
			ps.setString(4, emp.getDepartment());
			ps.setString(5, emp.getCountry());
			
			try {
				int i = ps.executeUpdate();
				System.out.println("Employee inserted successfully");
				ps.close();
			}catch(Exception e) {
				System.out.println("Error while inserting");
				e.printStackTrace();
			}
			
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override 
	public boolean update(Employee emp) {
		try {
			Statement st = con.createStatement();
			String query = "UPDATE empdata SET name = '" + emp.getName() + "', age = '" + emp.getAge() + "', department = '" + emp.getDepartment() + "', designation = '" + emp.getDesignation() + "', country = '" + emp.getCountry() + "' WHERE ID = " + emp.getEmpId() + ";";
			st.executeUpdate(query);
			st.close();
		}catch(SQLException e ) {
			System.out.println(e);
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(int empId) {
			String q = "DELETE FROM empdata WHERE ID = "+ empId +";" ;
			
			try {
				Statement st = con.createStatement();
				st.execute(q);
				return true ;
				
			}catch (SQLException e) {
				e.printStackTrace();
				
			}
		return false ;
	}

	

	
	@Override
	public Employee viewEmp(int empId) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM empdata WHERE ID = ? ;");
			ps.setInt(1, empId);
			ResultSet rs = ps.executeQuery();
			Employee emp  = null;
			if(rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String desgi = rs.getString("designation");
				String depart = rs.getString("department");
				String country = rs.getString("country");
				emp = new Employee(id, name, age, desgi, depart, country);
			}
			rs.close();
			ps.close();
			
			return emp;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Employee> getAllemp() {
		// TODO Auto-generated method stub
		List<Employee> employees = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			String quer = "SELECT * FROM empdata";
			ResultSet rs = st.executeQuery(quer);
			while(rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String desgi = rs.getString("designation");
				String depart = rs.getString("department");
				String country = rs.getString("country");
				Employee emp = new Employee(id, name, age, desgi, depart, country);
				employees.add(emp);
			}
			rs.close();
			st.close();
			return(ArrayList<Employee>) employees;
		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;
	}
	private int getEmployeeCountAgeGreaterThan(){
		String q = "SELECT COUNT(*) as count  from empdata where age >= 30;";
		int count = 0 ; 
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			if(rs.next()) {
				count = rs.getInt("count");
				return count ;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		}
	
	
	private List<Integer> getEmployeeIdsAgeGreaterThan()  {
		List<Integer> list = new ArrayList<>();
		String q = "SELECT ID from empdata where age >= 30;";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(q);
			while(rs.next()) {
				int id = rs.getInt("ID");
				list.add(id);
				
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
	
	
	private Map<String, Integer> getEmployeeCountByDepartment(){
		Map<String,Integer> map = new HashMap<String, Integer>();
		String q = "SELECT department,COUNT(*) as count FROM empdata GROUP BY department;" ;
		try {
		PreparedStatement ps = con.prepareStatement(q);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			String department = rs.getString("department");
			int count = rs.getInt("Count");
			map.put(department, count);
		}
		rs.close();
		}catch(Exception e) {
		e.printStackTrace();
		}
		return map;
	}
	
	private Map<String, Integer> getEmployeeCountByDepartmentOdered(){
		Map<String,Integer> map = new LinkedHashMap<String, Integer>();
		String q = "SELECT department,COUNT(*) as count FROM empdata GROUP BY department ORDER BY department;" ;
		try {
		PreparedStatement ps = con.prepareStatement(q);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			String department = rs.getString("department");
			int count = rs.getInt("Count");
			map.put(department, count);
		}
		rs.close();
		}catch(Exception e) {
		e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public void statistics() {
		// TODO Auto-generated method stub
		System.out.println("No of employees older than thirty years: "+ getEmployeeCountAgeGreaterThan());
		System.out.println("List employee IDs older than thirty years: " + getEmployeeIdsAgeGreaterThan());
		System.out.println("Employee count by Department: " + getEmployeeCountByDepartment());
		System.out.println("Employee count by Department ordered: " + getEmployeeCountByDepartmentOdered());
	}

	@Override
	public void export() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}
}
