package com.java17.students;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public Optional<Student> getById(Long id) {
        SessionFactory sesssionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sesssionFactory.openSession()) {

            Query<Student> query = session.createQuery("from Student where id = :id", Student.class);
            query.setParameter("id", id);

            return Optional.ofNullable(query.getSingleResult());
        } catch (PersistenceException se) {
            System.err.println("Nie udało się pobrać z bazy!");
        } // UWAGA: https://docs.oracle.com/javaee/6/api/javax/persistence/Query.html#getSingleResult()


        return Optional.empty();
    }

    public List<Student> getById(List<Long> ids) {
        SessionFactory sesssionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sesssionFactory.openSession()) {

            Query<Student> query = session.createQuery("from Student where id IN :ids", Student.class);
            query.setParameterList("ids", ids);

            return query.list();
        } catch (PersistenceException se) {
            System.err.println("Nie udało się pobrać z bazy!");
        } // UWAGA: https://docs.oracle.com/javaee/6/api/javax/persistence/Query.html#getSingleResult()

        return new ArrayList<>();
    }

    // 1. stwórz metodę 'removeById' która jako parametr przyjmuje Long 'id' studenta i zwraca boolean (wynik powodzenia operacji)
    // 2. w metodzie zaimplementuj usuwanie z session, posłuż się metodą ('delete')
    //      UWAGA!!! zwróć uwagę że delete przyjmuje jako parametr Object!
    //      - oznacza to, że najpierw musimy znaleźć ten obiekt ( pobrać go z bazy) a dopiero potem przekazać do usunięcia.
    // 3. zwróć wynik
    // 4. przetestuj działanie aplikacji.

    public boolean removeById(Long id) {
        SessionFactory sesssionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;

        Optional<Student> studentOptional = getById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            try (Session session = sesssionFactory.openSession()) {
                transaction = session.beginTransaction();

//                for (Ocena ocena : student.getOceny()) {
//                    // czyścimy relację zanim usuniemy studenta.
//                    session.delete(ocena);
//                }

                session.delete(student);
                // logika usuwania

                transaction.commit();
                return true;
            } catch (SessionException se) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return false;
    }

    public boolean addTeacherToStudent(Long teacherId, Long studentId) {
        // 1 pobierz teachera o podanym id
        // 2 pobierz studenta o podanym id
        // 3 do listy nauczycieli w studencie dodać teachera
        // 4 do listy studentów w teacherze dodać studenta
        // zapisać obie encje (student i teacher)
        // UWAGA! PAMIĘTAJ BY ZAPISAĆ STUDENTA I TEACHERA W JEDNEJ SESJI/TRANZAKCJI
    }

    public List<Student> getAllStudents_fromTeacher(Long teacherId) {
        SessionFactory sesssionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sesssionFactory.openSession()) {

            Query<Teacher> query = session.createQuery("from Teacher where id = :zmienna", Teacher.class);
            query.setParameter("zmienna", teacherId);

            Teacher teacher = query.getSingleResult();

            // UWAGA! pobieram studentów przed zakończeniem sesji (sesja kończy się wraz z blokiem try)
            return teacher.getStudents();
        } catch (PersistenceException se) {
            System.err.println("Nie udało się pobrać z bazy!");
        } // UWAGA: https://docs.oracle.com/javaee/6/api/javax/persistence/Query.html#getSingleResult()

        /// logika logika logika

        // coś pobiera z bazy nauczyciela o podanym id
        // po pobraniu nauczyciela używamy gettera i pobieramy pole studentów
        // (WEWNĄTRZ TRY/CATCH, KIEDY SESJA JEST JESZCZE OTWARTA)

        // zwracamy wynik, a jeśli wystąpi błąd - pustą listę:
        return new ArrayList<>();
    }
}
