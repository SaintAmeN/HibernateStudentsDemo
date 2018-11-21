package com.java17.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

// JPA - Java Persistance API

@Entity
@Data
@ToString(exclude = {"student"})
@AllArgsConstructor
@NoArgsConstructor
public class Ocena extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Integer ocena;

    @Enumerated(value = EnumType.STRING)
    private Przedmiot przedmiot;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
}
