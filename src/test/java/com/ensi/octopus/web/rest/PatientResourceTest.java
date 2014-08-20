package com.ensi.octopus.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.joda.time.LocalDate;
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
	
    private static final Long DEFAULT_ID = new Long(1L);

    private static final LocalDate DEFAULT_SAMPLE_DATE_ATTR = new LocalDate(0L);

    private static final LocalDate UPD_SAMPLE_DATE_ATTR = new LocalDate();

    private static final String DEFAULT_SAMPLE_TEXT_ATTR = "sampleTextAttribute";

    private static final String UPD_SAMPLE_TEXT_ATTR = "sampleTextAttributeUpt";

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
        patient.setId(DEFAULT_ID);
    	patient.setSampleDateAttribute(DEFAULT_SAMPLE_DATE_ATTR);
    	patient.setSampleTextAttribute(DEFAULT_SAMPLE_TEXT_ATTR);
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
                .andExpect(jsonPath("$.id").value(DEFAULT_ID.intValue()))
    			.andExpect(jsonPath("$.sampleDateAttribute").value(DEFAULT_SAMPLE_DATE_ATTR.toString()))
    			.andExpect(jsonPath("$.sampleTextAttribute").value(DEFAULT_SAMPLE_TEXT_ATTR));

    	// Update Patient
    	patient.setSampleDateAttribute(UPD_SAMPLE_DATE_ATTR);
    	patient.setSampleTextAttribute(UPD_SAMPLE_TEXT_ATTR);
  
    	restPatientMockMvc.perform(post("/app/rest/patients")
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(patient)))
                .andExpect(status().isOk());

    	// Read updated Patient
    	restPatientMockMvc.perform(get("/app/rest/patients/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID.intValue()))
    			.andExpect(jsonPath("$.sampleDateAttribute").value(UPD_SAMPLE_DATE_ATTR.toString()))
    			.andExpect(jsonPath("$.sampleTextAttribute").value(UPD_SAMPLE_TEXT_ATTR));

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
