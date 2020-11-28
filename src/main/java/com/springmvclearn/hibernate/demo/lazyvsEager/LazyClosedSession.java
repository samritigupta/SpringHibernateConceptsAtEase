package com.springmvclearn.hibernate.demo.lazyvsEager;

import com.springmvclearn.hibernate.demo.lazyvsEager.entity.Course;
import com.springmvclearn.hibernate.demo.lazyvsEager.entity.Instructor;
import com.springmvclearn.hibernate.demo.lazyvsEager.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class LazyClosedSession {

    public static void main(String[] args) {

        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        // create session
        Session session = factory.getCurrentSession();

        try {

            // start a transaction
            session.beginTransaction();

            // get the instructor from db
            int theId = 1;
            Instructor tempInstructor = session.get(Instructor.class, theId);

            System.out.println("Instructor: " + tempInstructor);

            // called get method lazy loading when session is open so can use later when session is closed.
            System.out.println("Courses: " + tempInstructor.getCourses());

            // commit transaction
            session.getTransaction().commit();

            // close the session
            session.close();

            System.out.println("\nThe session is now closed!\n");

            // option 1: call getter method while session is open in lazy loading e.g. above 36line
            // we have already called the get method.

            // get courses for the instructor
            System.out.println("Courses: " + tempInstructor.getCourses());

            System.out.println("Done!");
        }
        finally {

            // add clean up code
            session.close();

            factory.close();
        }
    }
}
