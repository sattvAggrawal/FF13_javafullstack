package com.example.empapp.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.empapp.model.Employee;

 public  final class ConnectionHelperHibernate {

		private static  SessionFactory factory = null; 
		private ConnectionHelperHibernate() {};
		
		public static SessionFactory getSession() {
			if(factory == null) {
				Configuration config = new Configuration() ;
				config.addAnnotatedClass(Employee.class);
				
				// STEP 2: Create SessionFactory
				factory = config.buildSessionFactory();
				System.out.println("Connected to databased - " + factory);
				return factory ;
				}
			
			return factory ;
		}
		
	}

