package com.ensi.octopus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ensi.octopus.domain.Patient;

/**
 * Spring Data JPA repository for the Patient entity.
 */
public interface PatientRepository extends MongoRepository<Patient, Long> {

}
