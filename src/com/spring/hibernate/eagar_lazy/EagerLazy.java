package com.spring.hibernate.eagar_lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class EagerLazy {
	
	public static void main(String[] args) {
		
		SessionFactory sessionFactory=new Configuration().configure("hibernate-setup.xml")
				.addAnnotatedClass(Instructor.class).addAnnotatedClass(InstructorDetails.class)
				.addAnnotatedClass(Course.class).buildSessionFactory();
		
		Session session=sessionFactory.getCurrentSession();
		
		try
		{
			session.beginTransaction();			
			//Instructor inst1=session.get(Instructor.class, 1);
			
			int id=1;
			
			Query<Instructor> query=session.createQuery("select i from Instructor i "
			+"JOIN FETCH i.courses "
			+"where i.id=:id", Instructor.class);
			
			query.setParameter("id",id);			
			Instructor inst1=query.getSingleResult();			
			System.out.println(inst1);
			session.getTransaction().commit();	
			session.close();
			System.out.println(inst1.getCourses());
     		System.out.println("done");
			
		}
		finally
		{
			
			sessionFactory.close();
		}		
	}
}
