package com.hibernatebatch.dao;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.SessionStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hibernatebatch.pojo.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {
	
	static Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void batchSave() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (int i = 0; i < 100; i++) {
			Employee employee = new Employee("hanuma");
			session.save(employee);
			SessionStatistics statistics = session.getStatistics();

			if (i % 50 == 0) {
				// Same as the JDBC batch size
				logger.info("batchSave - before flush and clear :: " + statistics.getEntityCount());
				// flush a batch of inserts and release memory:
				session.flush();
				session.clear();
				logger.info("batchSave - after flush and clear :: " + statistics.getEntityCount());
			}
		}
		tx.commit();
		session.close();
	}

	@Override
	public void batchUpdate() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ScrollableResults employees = session.createQuery("SELECT E FROM Employee E").scroll(ScrollMode.FORWARD_ONLY);
		int count = 0;
		SessionStatistics statistics = session.getStatistics();
		while (employees.next()) {
			Employee e = (Employee) employees.get(0);
			e.seteName("ramBhakth");
			session.update(e);
			if (count++ % 50 == 0) {
				// Same as the JDBC batch size
				// flush a batch of inserts and release memory:
				logger.info("batchUpdate - before flush and clear :: " + statistics.getEntityCount());
				session.flush();
				session.clear();
				logger.info("batchUpdate - after flush and clear :: " + statistics.getEntityCount());
			}
		}
		tx.commit();
		session.close();
	}

	@Override
	public void deleteAll() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query createQuery = session.createQuery("DELETE FROM Employee E");
		if (createQuery.executeUpdate() > 0) {
			SQLQuery createSQLQuery = session
					.createSQLQuery("ALTER TABLE MYDB.BATCHEMPLOYEE ALTER COLUMN EID RESTART WITH 1");
			createSQLQuery.executeUpdate();
		}
		tx.commit();
		session.close();
	}
}
