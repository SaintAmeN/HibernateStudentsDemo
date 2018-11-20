package com.java17.students;

import java.util.ArrayList;
import java.util.List;
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
                student.setIndeks(indeks);
                student.setOceny(ocenaList);

                studentDao.saveStudentWithGradesIntoDb(student);
            } else if (tekst.equals("listuj")) {
                System.out.println(studentDao.getAllStudentsFromDatabase());
            }
        } while (!tekst.equals("exit"));

        HibernateUtil.getSessionFactory().close(); // < - instrukcja zamykająca połączenie z bazą.

        // jeśli wpiszę 'dodaj'
        // aplikacja ma poprosić nas o imie, nazwisko i indeks
        // następnie aplikacja ma dodać studenta o podanym imieniu, nazwisku i indeksie do bazy

        // jeśli wpiszę 'listuj'
        // aplikacja ma wypisać wszystki studentów linia pod linią

        // jeśli wpiszę 'exit'
        // aplikacja ma się zakończyć (opuścić pętlę)

    }
}
