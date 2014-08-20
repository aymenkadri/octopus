package com.ensi.octopus.repository;

import com.ensi.octopus.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Patient entity.
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
