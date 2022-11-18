package com.example.empapp.hibernate;

import java.util.ArrayList;

import com.example.empapp.model.Employee;


//dao is an interface that performs  CRUD operation on the table of a database
//generally for 1 table 1 dao is made

public interface HibernateDAO {
	
public boolean insertHi(Employee employee);

public boolean updateHi(Employee employee);

public boolean deleteHi(int empId);

public Employee viewEmpHi(int empId);

public ArrayList<Employee> getAllEmpHi();

public void statisticsHi();




	
}
