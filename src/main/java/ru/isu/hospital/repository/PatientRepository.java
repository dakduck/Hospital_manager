package ru.isu.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.hospital.model.Patient;
import ru.isu.hospital.model.Visit;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(value = "select p from Patient as p ORDER BY p.lastName")
    List<Patient> findAll();

    @Query("select p from Patient as p")
    List<Patient> byLastName(Pageable page);

    @Query("select p from Patient as p")
    List<Patient> byLastName();

    @Query("select p from Patient as p where p.id=:patientId")
    Patient findById(@Param("patientId") Long id);

    @Query("select p from Patient as p where p.id=:patientId")
    Page<Patient> visitsById(@Param("patientId") Long id, Pageable page);

    @Query("select v from Patient as p join p.visits v where p.id=:patientId ORDER BY v.date DESC")
    Page<Visit> findVisits(@Param("patientId") Long id, Pageable page);

}
