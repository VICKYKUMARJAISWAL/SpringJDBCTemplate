package com.sathya.test;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sathya.springjdbc.dao.EmployeeDao;
import com.sathya.springjdbc.model.Employee;

public class SpringTest {

	public static void main(String[] args) {
		//Get the Spring Context
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("Spring.xml");
		
		//Get the EmployeeDAO Bean
		EmployeeDao employeeDAO = (EmployeeDao) ctx.getBean("employeeDAO",EmployeeDao.class);
		
		//Run some tests for JDBC CRUD operations
		Employee emp = new Employee();
		int rand = new Random().nextInt(1000);
		emp.setEmpId(rand);
		emp.setName("Pankaj");
		emp.setDept("Java Developer");
		emp.setSal(78000);
		emp.setHiredate(new java.sql.Date(new Date().getTime()));
		
		//Insert/Create
		employeeDAO.save(emp);
		
		//Read/Fetch
		Employee emp1 = employeeDAO.getById(rand);
		System.out.println("Employee Retrieved::"+emp1);
		
		//Update
		emp.setDept("CEO");
		employeeDAO.update(emp);
		
		//Get All
		List<Employee> empList = employeeDAO.getAll();
		System.out.println(empList);
		
		//Delete
		employeeDAO.deleteById(rand);
		
		//Close Spring Context
		ctx.close();
		
		System.out.println("DONE");
	}

}
