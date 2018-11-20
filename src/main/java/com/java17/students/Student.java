package com.java17.students;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity // <- hibernate
@Data // <-lombok
@AllArgsConstructor()
@NoArgsConstructor
public class Student extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // identity - pobiera id, następnie przypisuje wartość do obiektu i zapisuje obiekt
    // sequence - zapisuje obiekt, pobiera go z powrotem i sprawdza id
    private Long id;

    @Column(name = "name")
    private String imie;

    @Column(name = "surname")
    private String nazwisko;

    @Column(name = "indeksik", unique = true)
    private String indeks;

    // one (this class object) to many (some class Objects below)
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    private List<Ocena> oceny;
}
