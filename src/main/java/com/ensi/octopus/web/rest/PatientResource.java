package com.ensi.octopus.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ensi.octopus.domain.Patient;
import com.ensi.octopus.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Patient.
 */
@RestController
@RequestMapping("/app")
public class PatientResource {

    private final Logger log = LoggerFactory.getLogger(PatientResource.class);

    @Inject
    private PatientRepository patientRepository;

    /**
     * POST  /rest/patients -> Create a new patient.
     */
    @RequestMapping(value = "/rest/patients",
            method = RequestMethod.POST,
            produces = "application/json")
    @Timed
    public void create(@RequestBody Patient patient) {
        log.debug("REST request to save Patient : {}", patient);
        patientRepository.save(patient);
    }

    /**
     * GET  /rest/patients -> get all the patients.
     */
    @RequestMapping(value = "/rest/patients",
            method = RequestMethod.GET,
            produces = "application/json")
    @Timed
    public List<Patient> getAll() {
        log.debug("REST request to get all Patients");
        return patientRepository.findAll();
    }

    /**
     * GET  /rest/patients/:id -> get the "id" patient.
     */
    @RequestMapping(value = "/rest/patients/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    @Timed
    public Patient get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Patient : {}", id);
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return patient;
    }

    /**
     * DELETE  /rest/patients/:id -> delete the "id" patient.
     */
    @RequestMapping(value = "/rest/patients/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @Timed
    public void delete(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to delete Patient : {}", id);
        patientRepository.delete(id);
    }
}
