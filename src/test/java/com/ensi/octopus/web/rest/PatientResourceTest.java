package com.ensi.octopus.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ensi.octopus.Application;
import com.ensi.octopus.domain.Patient;
import com.ensi.octopus.repository.PatientRepository;


/**
 * Test class for the PatientResource REST controller.
 *
 * @see PatientResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
public class PatientResourceTest {
	
    private static final Long DEFAULT_ID = 1l;

    private static final String DEFAULT_NAME = "Toto";

    private static final String UPD_NAME = "Tata";

    private static final Integer DEFAULT_AGE = 25;

    private static final Integer UPD_AGE = 52;

    @Inject
    private PatientRepository patientRepository;

    private MockMvc restPatientMockMvc;
    
    private Patient patient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PatientResource patientResource = new PatientResource();
        ReflectionTestUtils.setField(patientResource, "patientRepository", patientRepository);

        this.restPatientMockMvc = MockMvcBuilders.standaloneSetup(patientResource).build();

        patient = new Patient();
        patient.setCin(DEFAULT_ID);
    	patient.setName(DEFAULT_NAME);
    }

    @Test
    public void testCRUDPatient() throws Exception {

    	// Create Patient
    	restPatientMockMvc.perform(post("/app/rest/patients")
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(patient)))
                .andExpect(status().isOk());

    	// Read Patient
    	restPatientMockMvc.perform(get("/app/rest/patients/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cin").value(DEFAULT_ID))
    			.andExpect(jsonPath("$.name").value(DEFAULT_NAME))
    			.andExpect(jsonPath("$.age").value(DEFAULT_AGE));

    	// Update Patient
    	patient.setName(UPD_NAME);
  
    	restPatientMockMvc.perform(post("/app/rest/patients")
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(patient)))
                .andExpect(status().isOk());

    	// Read updated Patient
    	restPatientMockMvc.perform(get("/app/rest/patients/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cin").value(DEFAULT_ID))
    			.andExpect(jsonPath("$.name").value(UPD_NAME))
    			.andExpect(jsonPath("$.age").value(UPD_AGE));

    	// Delete Patient
    	restPatientMockMvc.perform(delete("/app/rest/patients/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

    	// Read nonexisting Patient
    	restPatientMockMvc.perform(get("/app/rest/patients/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

    }
}
