
package com.examples.empapp;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.examples.empapp.DOA.EmployeeDAOImpl;
import com.examples.empapp.DOA.EmployeeDao;
import com.examples.empapp.exception.EmployeeException;
import com.examples.empapp.model.Employee;
import com.examples.empapp.service.EmployeeService;
import com.examples.empapp.service.EmployeeServiceColImpl;

public class EmployeeManagementApp {

	private static Scanner in;
	private static EmployeeService empService;
	private static 	EmployeeDao empDao;

	public static void main(String[] args) {

		in = new Scanner(System.in);
		empService = new EmployeeServiceColImpl();
		empDao = new EmployeeDAOImpl() ; 
		ExecutorService executor = Executors.newCachedThreadPool();

		System.out.print("Welcome to Employee Management App!");

		while (true) {

			System.out.println("\n");
			System.out.println("1. Add Employee");
			System.out.println("2. View Employee");
			System.out.println("3. Update Employee");
			System.out.println("4. Delete Employee");
			System.out.println("5. View All Employees");
			System.out.println("6. Print Statistics");
			System.out.println("7. Import");
			System.out.println("8. Export");
			System.out.println("9. Exit");

			System.out.print("Enter the option: ");
			int option = 0;
			// Get option from user
			try {
				option = Integer.parseInt(in.next());
			} catch (NumberFormatException e) {
				System.out.println("Invalid option. Please enter valid option.");
				continue;
			}
			int empId;
			try {
				switch (option) {
				case 1: // add employee
					addEmployee();
					System.out.println("Employee has been added successfully!");
					break;
				case 2: // view single employee 
					System.out.print("Please enter employee id: ");
					empId = in.nextInt();
					Employee emp = null;
					// using Dao class 
					emp = empDao.viewEmp(empId);
					
//					try {
//						emp = empService.get(empId);
//					} catch (EmployeeException e) {
//						System.out.println(e.getMessage());
//						break;
//					}
					
					printHeader();
					printDetail(emp);
					break;
				case 3: // update 
					System.out.print("Please enter employee id: ");
					empId = in.nextInt();
					Employee empForUpdate;
					empForUpdate = empDao.viewEmp(empId);
					captureEmpDetail(empForUpdate);
					empDao.update(empForUpdate);
					System.out.println("Employee has been updated successfully!");
					break;
				case 4: // delete 
					System.out.print("Please enter employee id: ");
					empId = in.nextInt();
					empDao.delete(empId);
					System.out.println("Employee has been deleted successfully!");
					break;
				case 5: // view all employee 
					List<Employee> employees = empDao.getAllemp();
					printHeader();
					for (Employee employee : employees) {
						printDetail(employee);
					}
					break;
					
				case 6:
					empDao.statistics();
					
					break;
				case 7:
					Callable<Boolean> importThread = new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							System.out.println(Thread.currentThread() + " Waiting for 5s to start import process.");
//							Thread.sleep(5000);
							empService.bulkImport();							
							return true;
						}
					};
					
					Future<Boolean> importFuture = executor.submit(importThread);
					System.out.println(Thread.currentThread().getName() + " Import process triggered");				
					
					break;
				case 8:
					
					Callable<Boolean> exportThread = new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							System.out.println(Thread.currentThread() + " Waiting for 5s to start export process.");
							Thread.sleep(5000);
							empService.bulkExport();							
							return true;
						}
					};
					
					Future<Boolean> exportFuture = executor.submit(exportThread);
					System.out.println(Thread.currentThread().getName() + " Export process triggered");						
					
					
					break;					
				case 9:
					System.out.println("Thank you!!!");
					executor.shutdown();
					in.close();
					System.exit(0);
					break;

				default:
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter valid input.");
			}
		}

	}

	private static void printStatistics() {

		System.out.println("No of employees older than thirty years: "
				+ empService.getEmployeeCountAgeGreaterThan(30));
		System.out.println("List employee IDs older than thirty years: " + empService.getEmployeeIdsAgeGreaterThan(30));
		System.out.println("Employee count by Department: " + empService.getEmployeeCountByDepartment());
		System.out.println("Employee count by Department ordered: " + empService.getEmployeeCountByDepartmentOdered());
	}

	private static void printHeader() {
		System.out.format("\n%5s %15s %5s %15s %15s %15s", "EmpID", "Name", "Age", "Designation", "Department",
				"Country");
	}

	private static void printDetail(Employee emp) {
		if (emp == null) {
			return;
		}

		System.out.format("\n%5d %15s %5d %15s %15s %15s", emp.getEmpId(), emp.getName(), emp.getAge(),
				emp.getDesignation(), emp.getDepartment(), emp.getCountry());
	}

	private static void addEmployee() throws NumberFormatException {
		Employee employee = new Employee();

		captureEmpDetail(employee);  // it will set the data in java 
		empDao.insert(employee);  // it will call the method in EmployeeDAOimpl that sets the data into SQL
//		empService.create(employee);
	}

	private static void captureEmpDetail(Employee employee) throws NumberFormatException {
		System.out.print("Enter employee Name: ");
		employee.setName(in.next());
		
		try {
			boolean val = true;
			do {
				System.out.print("Enter employee Age: ");
				String errorMsg = "Invalid Age. Age should be between 18 to 60.";
				employee.setAge(Integer.parseInt(in.next()));
				val = empService.validate(employee, errorMsg, e -> e.getAge() >= 18 && e.getAge() <= 60, m -> {
					System.out.println(m);
					return false;
				});
			} while (!val);
		} catch (NumberFormatException e) {
			throw e;
		}

		System.out.print("Enter employee Designation: ");
		employee.setDesignation(in.next());

		System.out.print("Enter employee Department: ");
		employee.setDepartment(in.next());

		System.out.print("Enter employee Country: ");
		employee.setCountry(in.next());
	}

}
