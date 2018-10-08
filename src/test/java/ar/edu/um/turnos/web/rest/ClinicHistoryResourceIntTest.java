package ar.edu.um.turnos.web.rest;

import ar.edu.um.turnos.UmturnosApp;

import ar.edu.um.turnos.domain.ClinicHistory;
import ar.edu.um.turnos.domain.Patient;
import ar.edu.um.turnos.repository.ClinicHistoryRepository;
import ar.edu.um.turnos.service.ClinicHistoryService;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static ar.edu.um.turnos.web.rest.TestUtil.sameInstant;
import static ar.edu.um.turnos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClinicHistoryResource REST controller.
 *
 * @see ClinicHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UmturnosApp.class)
public class ClinicHistoryResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_AND_HOUR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_AND_HOUR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ISSUE = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE = "BBBBBBBBBB";

    private static final String DEFAULT_HISTORY = "AAAAAAAAAA";
    private static final String UPDATED_HISTORY = "BBBBBBBBBB";

    @Autowired
    private ClinicHistoryRepository clinicHistoryRepository;
    
    @Autowired
    private ClinicHistoryService clinicHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClinicHistoryMockMvc;

    private ClinicHistory clinicHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClinicHistoryResource clinicHistoryResource = new ClinicHistoryResource(clinicHistoryService);
        this.restClinicHistoryMockMvc = MockMvcBuilders.standaloneSetup(clinicHistoryResource)
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
    public static ClinicHistory createEntity(EntityManager em) {
        ClinicHistory clinicHistory = new ClinicHistory()
            .dateAndHour(DEFAULT_DATE_AND_HOUR)
            .issue(DEFAULT_ISSUE)
            .history(DEFAULT_HISTORY);
        // Add required entity
        Patient patient = PatientResourceIntTest.createEntity(em);
        em.persist(patient);
        em.flush();
        clinicHistory.setPatient(patient);
        return clinicHistory;
    }

    @Before
    public void initTest() {
        clinicHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createClinicHistory() throws Exception {
        int databaseSizeBeforeCreate = clinicHistoryRepository.findAll().size();

        // Create the ClinicHistory
        restClinicHistoryMockMvc.perform(post("/api/clinic-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicHistory)))
            .andExpect(status().isCreated());

        // Validate the ClinicHistory in the database
        List<ClinicHistory> clinicHistoryList = clinicHistoryRepository.findAll();
        assertThat(clinicHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ClinicHistory testClinicHistory = clinicHistoryList.get(clinicHistoryList.size() - 1);
        assertThat(testClinicHistory.getDateAndHour()).isEqualTo(DEFAULT_DATE_AND_HOUR);
        assertThat(testClinicHistory.getIssue()).isEqualTo(DEFAULT_ISSUE);
        assertThat(testClinicHistory.getHistory()).isEqualTo(DEFAULT_HISTORY);
    }

    @Test
    @Transactional
    public void createClinicHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clinicHistoryRepository.findAll().size();

        // Create the ClinicHistory with an existing ID
        clinicHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClinicHistoryMockMvc.perform(post("/api/clinic-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ClinicHistory in the database
        List<ClinicHistory> clinicHistoryList = clinicHistoryRepository.findAll();
        assertThat(clinicHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateAndHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = clinicHistoryRepository.findAll().size();
        // set the field null
        clinicHistory.setDateAndHour(null);

        // Create the ClinicHistory, which fails.

        restClinicHistoryMockMvc.perform(post("/api/clinic-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicHistory)))
            .andExpect(status().isBadRequest());

        List<ClinicHistory> clinicHistoryList = clinicHistoryRepository.findAll();
        assertThat(clinicHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssueIsRequired() throws Exception {
        int databaseSizeBeforeTest = clinicHistoryRepository.findAll().size();
        // set the field null
        clinicHistory.setIssue(null);

        // Create the ClinicHistory, which fails.

        restClinicHistoryMockMvc.perform(post("/api/clinic-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicHistory)))
            .andExpect(status().isBadRequest());

        List<ClinicHistory> clinicHistoryList = clinicHistoryRepository.findAll();
        assertThat(clinicHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHistoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = clinicHistoryRepository.findAll().size();
        // set the field null
        clinicHistory.setHistory(null);

        // Create the ClinicHistory, which fails.

        restClinicHistoryMockMvc.perform(post("/api/clinic-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicHistory)))
            .andExpect(status().isBadRequest());

        List<ClinicHistory> clinicHistoryList = clinicHistoryRepository.findAll();
        assertThat(clinicHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClinicHistories() throws Exception {
        // Initialize the database
        clinicHistoryRepository.saveAndFlush(clinicHistory);

        // Get all the clinicHistoryList
        restClinicHistoryMockMvc.perform(get("/api/clinic-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinicHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAndHour").value(hasItem(sameInstant(DEFAULT_DATE_AND_HOUR))))
            .andExpect(jsonPath("$.[*].issue").value(hasItem(DEFAULT_ISSUE.toString())))
            .andExpect(jsonPath("$.[*].history").value(hasItem(DEFAULT_HISTORY.toString())));
    }
    
    @Test
    @Transactional
    public void getClinicHistory() throws Exception {
        // Initialize the database
        clinicHistoryRepository.saveAndFlush(clinicHistory);

        // Get the clinicHistory
        restClinicHistoryMockMvc.perform(get("/api/clinic-histories/{id}", clinicHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clinicHistory.getId().intValue()))
            .andExpect(jsonPath("$.dateAndHour").value(sameInstant(DEFAULT_DATE_AND_HOUR)))
            .andExpect(jsonPath("$.issue").value(DEFAULT_ISSUE.toString()))
            .andExpect(jsonPath("$.history").value(DEFAULT_HISTORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClinicHistory() throws Exception {
        // Get the clinicHistory
        restClinicHistoryMockMvc.perform(get("/api/clinic-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinicHistory() throws Exception {
        // Initialize the database
        clinicHistoryService.save(clinicHistory);

        int databaseSizeBeforeUpdate = clinicHistoryRepository.findAll().size();

        // Update the clinicHistory
        ClinicHistory updatedClinicHistory = clinicHistoryRepository.findById(clinicHistory.getId()).get();
        // Disconnect from session so that the updates on updatedClinicHistory are not directly saved in db
        em.detach(updatedClinicHistory);
        updatedClinicHistory
            .dateAndHour(UPDATED_DATE_AND_HOUR)
            .issue(UPDATED_ISSUE)
            .history(UPDATED_HISTORY);

        restClinicHistoryMockMvc.perform(put("/api/clinic-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClinicHistory)))
            .andExpect(status().isOk());

        // Validate the ClinicHistory in the database
        List<ClinicHistory> clinicHistoryList = clinicHistoryRepository.findAll();
        assertThat(clinicHistoryList).hasSize(databaseSizeBeforeUpdate);
        ClinicHistory testClinicHistory = clinicHistoryList.get(clinicHistoryList.size() - 1);
        assertThat(testClinicHistory.getDateAndHour()).isEqualTo(UPDATED_DATE_AND_HOUR);
        assertThat(testClinicHistory.getIssue()).isEqualTo(UPDATED_ISSUE);
        assertThat(testClinicHistory.getHistory()).isEqualTo(UPDATED_HISTORY);
    }

    @Test
    @Transactional
    public void updateNonExistingClinicHistory() throws Exception {
        int databaseSizeBeforeUpdate = clinicHistoryRepository.findAll().size();

        // Create the ClinicHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClinicHistoryMockMvc.perform(put("/api/clinic-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clinicHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ClinicHistory in the database
        List<ClinicHistory> clinicHistoryList = clinicHistoryRepository.findAll();
        assertThat(clinicHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClinicHistory() throws Exception {
        // Initialize the database
        clinicHistoryService.save(clinicHistory);

        int databaseSizeBeforeDelete = clinicHistoryRepository.findAll().size();

        // Get the clinicHistory
        restClinicHistoryMockMvc.perform(delete("/api/clinic-histories/{id}", clinicHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClinicHistory> clinicHistoryList = clinicHistoryRepository.findAll();
        assertThat(clinicHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClinicHistory.class);
        ClinicHistory clinicHistory1 = new ClinicHistory();
        clinicHistory1.setId(1L);
        ClinicHistory clinicHistory2 = new ClinicHistory();
        clinicHistory2.setId(clinicHistory1.getId());
        assertThat(clinicHistory1).isEqualTo(clinicHistory2);
        clinicHistory2.setId(2L);
        assertThat(clinicHistory1).isNotEqualTo(clinicHistory2);
        clinicHistory1.setId(null);
        assertThat(clinicHistory1).isNotEqualTo(clinicHistory2);
    }
}
