package com.java17.students;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

// data access object
public class StudentDao {

    public boolean saveStudentWithGradesIntoDb(Student student) {
        // pobieramy session factory (fabryka połączenia z bazą)
        SessionFactory sesssionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sesssionFactory.openSession()) {
            // otwieram transakcję
            transaction = session.beginTransaction();

            for (Ocena oc : student.getOceny()) {
                session.save(oc);
            }

            session.save(student); // dokonujemy zapisu na bazie

            // zamykam transakcję i zatwierdzam zmiany
            transaction.commit();
        } catch (SessionException se) {
            // w razie błędu przywróć stan sprzed transakcji
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
        return true;
    }


    public boolean saveIntoDb(BaseEntity entity) {
        // pobieramy session factory (fabryka połączenia z bazą)
        SessionFactory sesssionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sesssionFactory.openSession()) {
            // otwieram transakcję
            transaction = session.beginTransaction();

            session.save(entity); // dokonujemy zapisu na bazie

            // zamykam transakcję i zatwierdzam zmiany
            transaction.commit();
        } catch (SessionException se) {
            // w razie błędu przywróć stan sprzed transakcji
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
        return true;
    }

    public List<Student> getAllStudentsFromDatabase() {
        SessionFactory sesssionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sesssionFactory.openSession()) {

            // stwórz zapytanie
            Query<Student> query = session.createQuery("from Student st ", Student.class);

            // wywołaj zapytanie
            List<Student> students = query.list();

//            System.out.println(students);

            // zwróć wynik
            return students;
        } catch (SessionException se) {
            // jeśli coś pójdzie nietak - wypiszmy komunikat loggerem:
            // todo: logger
            System.err.println("Nie udało się pobrać z bazy!");
        }

        // jeśli nie uda się znaleźć zwracamy pustą listę.
        return new ArrayList<>();
    }
}
