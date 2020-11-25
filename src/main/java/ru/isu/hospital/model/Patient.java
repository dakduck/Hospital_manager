package ru.isu.hospital.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Класс, реализующий Пациента - вся информация о нем
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // *** Состояние здоровья (здоров или болен) ***
    @NotBlank(message = "Заполните поле")
    private String state;
    @NotBlank(message = "Имя не может быть пустым")
    private String firstName;
    @NotBlank(message = "Имя не может быть пустым")
    private String lastName;
    @NotBlank(message = "Выберите пол!")
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bd;
    private Integer age;

    // *** Список посещений ***
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "patients_visits",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "visit_id")
    )
    private Set<Visit> visits;

    public void add(Visit v){this.visits.add(v);}

}
