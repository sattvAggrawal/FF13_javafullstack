package com.example.empapp.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.example.empapp.exception.EmployeeException;
import com.example.empapp.model.Employee;

public interface EmployeeService {

	public boolean create(Employee emp);
	
	public boolean update(Employee emp);
	
	public boolean delete(int empId);

	public Employee get(int empId) throws EmployeeException;

	public List<Employee> getAll();

	public void bulkImport();

	public void bulkExport();
	
	public boolean validate(Employee emp, String msg, Predicate<Employee> condition,
			Function<String, Boolean> operation);

	public long getEmployeeCountAgeGreaterThan(int age);

	public List<Integer> getEmployeeIdsAgeGreaterThan(int age);

	public Map<String, Long> getEmployeeCountByDepartment();

	public Map<String, Long> getEmployeeCountByDepartmentOdered();

}
	

