package com.java17.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// JPA - Java Persistance API

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ocena extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Integer ocena;

    private Przedmiot przedmiot;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
}
