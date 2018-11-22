package com.java17.students;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
//        Student st = new Student(null, "Marian", "Kowalski", "123");

        StudentDao studentDao = new StudentDao();
//        studentDao.saveIntoDb(st);
//        System.out.println(studentDao.getAllStudentsFromDatabase());

        Scanner scanner = new Scanner(System.in);
        String tekst;
        do {
            // nextLine + NIC!
            // next + nextInt + nextDouble + next......
            tekst = scanner.next();
            if (tekst.equals("student")) {
                callStudent(scanner, studentDao);
            } else if (tekst.equals("teacher")) {
                callTeacher(scanner, studentDao);
            }
        } while (!tekst.equals("exit"));

        HibernateUtil.getSessionFactory().close(); // < - instrukcja zamykająca połączenie z bazą.


    }

    private static void callTeacher(Scanner scanner, StudentDao studentDao) {
        String tekst = scanner.next();
        if (tekst.equals("dodaj")) {
            String name = scanner.next();
            String surname = scanner.next();


            studentDao.saveIntoDb(new Teacher(null, name, surname, null, null));
        } else if (tekst.equals("get_students")) {
            System.out.println("Podaj id nauczyciela:");
            Long id_nauczyciela = scanner.nextLong();

            List<Student> students = studentDao.getAllStudents_fromTeacher(id_nauczyciela);
            System.out.println("Jego studenci:");
            students.forEach(System.out::println);
        } else if (tekst.equals("powiaz")) {
            System.out.println("Podaj id studenta:");
            Long idStudent = scanner.nextLong();

            System.out.println("Podaj id nauczyciela:");
            Long idTeacher = scanner.nextLong();

            studentDao.addTeacherToStudent(idTeacher, idStudent);
        }
    }

    private static void callStudent(Scanner scanner, StudentDao studentDao) {
        String tekst = scanner.next();
        if (tekst.equals("dodaj")) {
            String imie = scanner.next();
            String nazwisko = scanner.next();
            String indeks = scanner.next();

            // oceny
            System.out.println("Podaj ilość ocen:");
            int iloscOcen = scanner.nextInt();
            List<Ocena> ocenaList = new ArrayList<>();
            Student student = new Student();
            for (int i = 0; i < iloscOcen; i++) {
                System.out.println("Podaj nazwę przedmiotu:");
                // tutaj (w linii niżej) dochodzi do parsowania String -> Przedmiot (enum)
                Przedmiot przedmiot = Przedmiot.valueOf(scanner.next().toUpperCase());

                System.out.println("Podaj ocenę:");
                int ocena = scanner.nextInt();

                ocenaList.add(new Ocena(null, ocena, przedmiot, student));
            }

            student.setImie(imie);
            student.setNazwisko(nazwisko);
//                student.setIndeks(indeks);
            student.setOceny(ocenaList);

            studentDao.saveStudentWithGradesIntoDb(student);
        } else if (tekst.equals("listuj")) {
            System.out.println(studentDao.getAllStudentsFromDatabase());
        } else if (tekst.equals("pobierz")) {
            System.out.println("Podaj identyfikator studenta:");

            Long idStudenta = scanner.nextLong();

//                studentDao.getById(idStudenta).ifPresent(System.out::println);
            Optional<Student> studentOptional = studentDao.getById(idStudenta);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                System.out.println(student);
            }
        } else if (tekst.equals("znajdz")) {
            System.out.println("Podaj ilosc studentów:");
            int ilosc = scanner.nextInt();

            List<Long> ids = new ArrayList<>();
            for (int i = 0; i < ilosc; i++) {
                ids.add(scanner.nextLong());
            }

            System.out.println(studentDao.getById(ids)); // wykorzystujemy przeciążoną metodę
        } else if (tekst.equals("usun")) {
            System.out.println("Podaj id:");
            long id = scanner.nextLong();

            System.out.println(studentDao.removeById(id));
        }
    }
}
