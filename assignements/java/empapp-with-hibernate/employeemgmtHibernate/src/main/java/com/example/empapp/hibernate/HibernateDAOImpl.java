package com.example.empapp.hibernate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.example.empapp.model.Employee;
import com.example.empapp.utils.ConnectionHelperHibernate;


public class HibernateDAOImpl implements HibernateDAO {
	static SessionFactory factory = ConnectionHelperHibernate.getSession();

	public boolean insertHi(Employee emp) {
		Transaction tx = null;
		Integer id = -1;
		Session session = null;
		
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			
			// insert data into DB by supplying persistance object 
			id = (Integer) session.save(emp);
			
			System.out.println("\nEmployee inserted successfully wiht ID " + id);
			
			tx.commit();
		}catch(HibernateException he) {
			tx.rollback();
			he.printStackTrace();
		}finally {
			session.close();
		}
		return false;
	}
	public Employee viewEmpHi(int Id) {
		Transaction tx = null;
		Session session = null;
		
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			Employee emp = session.get(Employee.class, Id);
			tx.commit();
			return emp;
		}catch(HibernateException he) {
			tx.rollback();
			he.printStackTrace();
		}finally {
			session.close();
		}
		return null;
	}
	
	public boolean updateHi(Employee employee) {
	 Transaction tx = null;
	 Session session = null;
	 try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.update(employee);

			tx.commit();
			return true;
		} catch (HibernateException he) {
			tx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}
	
	public boolean deleteHi(int Id) {
		Transaction tnx = null;
		Session session = null;

		try {
			session = factory.openSession();
			tnx = session.beginTransaction();
			Employee emp = viewEmpHi(Id);
			session.delete(emp);

			tnx.commit();
			return true;
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}

		return false;
	}
	
	public ArrayList<Employee> getAllEmpHi() {
		// TODO Auto-generated method stub
		Transaction tnx = null;
		Session session = null;

		try {
			session = factory.openSession();
			tnx = session.beginTransaction();

			List<Employee> empall = (ArrayList<Employee>) session.createQuery("From Employee").list();

			tnx.commit();
			return (ArrayList<Employee>) empall;
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return null;

	}

	
	private ArrayList<Employee> getEmployeeCountAgeGreaterThan() {
		ArrayList<Employee> list = new ArrayList<>();

		Transaction tnx = null;
		Session session = null;

		try {
			session = factory.openSession();
			tnx = session.beginTransaction();
			list = (ArrayList<Employee>) session
					.createNativeQuery("SELECT * FROM Employee Where age > 25", Employee.class).list();

			tnx.commit();
			return list;
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return null;

	}
	
	private HashMap<Object, Object> getEmployeeCountByDepartment() {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		Transaction tnx = null;
		Session session = null;
		try {
			session = factory.openSession();
			tnx = session.beginTransaction();
			String hql = "SELECT department, COUNT(*) from Employee GROUP BY department";
			ArrayList<?> list = (ArrayList<?>)session.createQuery(hql).list();
			for (int i = 0; i < list.size(); i++) {
				Object[] row = (Object[]) list.get(i);
				map.put(row[0], row[1]);
			}
			return map;
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	
	private List<Integer> getEmployeeIdsAgeGreaterThan() {
		List<Integer> list = new ArrayList<>();

		Transaction tnx = null;
		Session session = null;
		try {
			session = factory.openSession();
			tnx = session.beginTransaction();
			ArrayList<Employee> empList = getEmployeeCountAgeGreaterThan();
			list = empList.stream().map(emp -> emp.getId()).toList();

			tnx.commit();
			return list;
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}
	
	private Map<Object, Object> getEmployeeCountByDepartmentOdered() {

		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		Transaction tnx = null;
		Session session = null;
		try {
			session = factory.openSession();
			tnx = session.beginTransaction();
			String hql = "SELECT department, COUNT(*) from Employee GROUP BY department ORDER BY department";
			List<?> list = session.createQuery(hql).list();
			for (int i = 0; i < list.size(); i++) {
				Object[] row = (Object[]) list.get(i);
				map.put(row[0], row[1]);
			}
			return map;
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		} finally {
			session.close();
		}

		return null;

	}


	public void statisticsHi() {
		// TODO Auto-generated method stub
		System.out.println("No of employees older than thirty years: " + getEmployeeCountAgeGreaterThan().size());
		System.out.println("List employee IDs older than thirty years: " + getEmployeeIdsAgeGreaterThan());
		System.out.println("Employee count by Department: " + getEmployeeCountByDepartment());
		System.out.println("Employee count by Department ordered: " + getEmployeeCountByDepartmentOdered());

	}
	
	
	
	

	}
	
	


