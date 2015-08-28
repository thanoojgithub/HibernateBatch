package com.hibernatebatch.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.hibernatebatch.dao.EmployeeDao;

@Component
public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-beans.xml")) {
			EmployeeDao employeeDao = (EmployeeDao)ctx.getBean("employeeDao");
			employeeDao.deleteAll();
			employeeDao.batchSave();
			employeeDao.batchUpdate();
			
		}
	}

	

}
