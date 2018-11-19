package com.java17.students;

import lombok.*;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // identity - pobiera id, następnie przypisuje wartość do obiektu i zapisuje obiekt
    // sequence - zapisuje obiekt, pobiera go z powrotem i sprawdza id
    private Long id;

    @Column(name = "name")
    private String imie;

    @Column(name = "surname")
    private String nazwisko;

    @Column(name = "indeksik")
    private String indeks;
}
