package ru.isu.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.hospital.model.Doctor;
import ru.isu.hospital.model.Patient;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByUsername(String username);

    @Query("select distinct d from Doctor as d where not d.role='ROLE_ADMIN' ORDER BY d.lastName")
    List<Doctor> findAllNotAdmin();

    @Query("select p from Doctor as p where p.id=:doctorId")
    Doctor findById(@Param("doctorId") Long id);

    @Query("select p.username from Doctor as p where p.username=:u")
    String findUsername(@Param("u") String u);

}
