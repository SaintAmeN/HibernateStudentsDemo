package com.java17.students;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class Main {
    public static void main(String[] args) {
        Student st = new Student(null, "Marian", "Kowalski", "123");

        StudentDao studentDao = new StudentDao();
        studentDao.saveStudentIntoDatabase(st);
        System.out.println(studentDao.getAllStudentsFromDatabase());

        Scanner scanner = new Scanner(System.in);
        String tekst;
        do{
            if(tekst.equals("dodaj")){
                Student student = new Student(scanner.next(), scanner.next(), scanner.);
                studentDao.saveStudentIntoDatabase();
            }else if(tekst.equals("listuj")){

            }
        }while (!tekst.equals("exit"));
        // jeśli wpiszę 'dodaj'
        // aplikacja ma poprosić nas o imie, nazwisko i indeks
        // następnie aplikacja ma dodać studenta o podanym imieniu, nazwisku i indeksie do bazy

        // jeśli wpiszę 'listuj'
        // aplikacja ma wypisać wszystki studentów linia pod linią

        // jeśli wpiszę 'exit'
        // aplikacja ma się zakończyć (opuścić pętlę)

    }
}
