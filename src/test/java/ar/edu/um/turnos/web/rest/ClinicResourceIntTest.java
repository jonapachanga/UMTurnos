package ar.edu.um.turnos.web.rest;

import ar.edu.um.turnos.UmturnosApp;

import ar.edu.um.turnos.domain.Clinic;
import ar.edu.um.turnos.repository.ClinicRepository;
import ar.edu.um.turnos.service.ClinicService;
import ar.edu.um.turnos.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static ar.edu.um.turnos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClinicResource REST controller.
 *
 * @see ClinicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UmturnosApp.class)
public class ClinicResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private ClinicRepository clinicRepository;
    
    @Autowired
    private ClinicService clinicService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClinicMockMvc;

    private Clinic clinic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClinicResource clinicResource = new ClinicResource(clinicService);
        this.restClinicMockMvc = MockMvcBuilders.standaloneSetup(clinicResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clinic createEntity(EntityManager em) {
        Clinic clinic = new Clinic()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .website(DEFAULT_WEBSITE)
            .note(DEFAULT_NOTE);
        return clinic;
    }

    @Before
    public void initTest() {
        clinic = createEntity(em);
    }

    @Test
    @Transactional
    public void createClinic() throws Exception {
        int databaseSizeBeforeCreate = clinicRepository.findAll().size();

        // Create the Clinic
        restClinicMockMvc.perform(post("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isCreated());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeCreate + 1);
        Clinic testClinic = clinicList.get(clinicList.size() - 1);
        assertThat(testClinic.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClinic.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClinic.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClinic.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testClinic.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testClinic.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createClinicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clinicRepository.findAll().size();

        // Create the Clinic with an existing ID
        clinic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClinicMockMvc.perform(post("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isBadRequest());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clinicRepository.findAll().size();
        // set the field null
        clinic.setName(null);

        // Create the Clinic, which fails.

        restClinicMockMvc.perform(post("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isBadRequest());

        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clinicRepository.findAll().size();
        // set the field null
        clinic.setEmail(null);

        // Create the Clinic, which fails.

        restClinicMockMvc.perform(post("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isBadRequest());

        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClinics() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        // Get all the clinicList
        restClinicMockMvc.perform(get("/api/clinics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinic.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        // Get the clinic
        restClinicMockMvc.perform(get("/api/clinics/{id}", clinic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clinic.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClinic() throws Exception {
        // Get the clinic
        restClinicMockMvc.perform(get("/api/clinics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinic() throws Exception {
        // Initialize the database
        clinicService.save(clinic);

        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();

        // Update the clinic
        Clinic updatedClinic = clinicRepository.findById(clinic.getId()).get();
        // Disconnect from session so that the updates on updatedClinic are not directly saved in db
        em.detach(updatedClinic);
        updatedClinic
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .website(UPDATED_WEBSITE)
            .note(UPDATED_NOTE);

        restClinicMockMvc.perform(put("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClinic)))
            .andExpect(status().isOk());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
        Clinic testClinic = clinicList.get(clinicList.size() - 1);
        assertThat(testClinic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClinic.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClinic.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClinic.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClinic.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testClinic.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingClinic() throws Exception {
        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();

        // Create the Clinic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClinicMockMvc.perform(put("/api/clinics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isBadRequest());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClinic() throws Exception {
        // Initialize the database
        clinicService.save(clinic);

        int databaseSizeBeforeDelete = clinicRepository.findAll().size();

        // Get the clinic
        restClinicMockMvc.perform(delete("/api/clinics/{id}", clinic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clinic.class);
        Clinic clinic1 = new Clinic();
        clinic1.setId(1L);
        Clinic clinic2 = new Clinic();
        clinic2.setId(clinic1.getId());
        assertThat(clinic1).isEqualTo(clinic2);
        clinic2.setId(2L);
        assertThat(clinic1).isNotEqualTo(clinic2);
        clinic1.setId(null);
        assertThat(clinic1).isNotEqualTo(clinic2);
    }
}
