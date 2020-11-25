package ru.isu.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.hospital.model.Doctor;
import ru.isu.hospital.model.Patient;
import ru.isu.hospital.model.Visit;

import java.util.List;

/**
 * Репозиторий для обращения к таблице Посещений
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("select p from Patient as p where p.id=:patientId")
    Visit findById(@Param("") Long id);

}
