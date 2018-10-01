package ar.edu.um.turnos.web.rest;

import ar.edu.um.turnos.UmturnosApp;

import ar.edu.um.turnos.domain.TurnType;
import ar.edu.um.turnos.repository.TurnTypeRepository;
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
 * Test class for the TurnTypeResource REST controller.
 *
 * @see TurnTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UmturnosApp.class)
public class TurnTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TurnTypeRepository turnTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTurnTypeMockMvc;

    private TurnType turnType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TurnTypeResource turnTypeResource = new TurnTypeResource(turnTypeRepository);
        this.restTurnTypeMockMvc = MockMvcBuilders.standaloneSetup(turnTypeResource)
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
    public static TurnType createEntity(EntityManager em) {
        TurnType turnType = new TurnType()
            .name(DEFAULT_NAME);
        return turnType;
    }

    @Before
    public void initTest() {
        turnType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTurnType() throws Exception {
        int databaseSizeBeforeCreate = turnTypeRepository.findAll().size();

        // Create the TurnType
        restTurnTypeMockMvc.perform(post("/api/turn-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnType)))
            .andExpect(status().isCreated());

        // Validate the TurnType in the database
        List<TurnType> turnTypeList = turnTypeRepository.findAll();
        assertThat(turnTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TurnType testTurnType = turnTypeList.get(turnTypeList.size() - 1);
        assertThat(testTurnType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTurnTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = turnTypeRepository.findAll().size();

        // Create the TurnType with an existing ID
        turnType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTurnTypeMockMvc.perform(post("/api/turn-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnType)))
            .andExpect(status().isBadRequest());

        // Validate the TurnType in the database
        List<TurnType> turnTypeList = turnTypeRepository.findAll();
        assertThat(turnTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = turnTypeRepository.findAll().size();
        // set the field null
        turnType.setName(null);

        // Create the TurnType, which fails.

        restTurnTypeMockMvc.perform(post("/api/turn-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnType)))
            .andExpect(status().isBadRequest());

        List<TurnType> turnTypeList = turnTypeRepository.findAll();
        assertThat(turnTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTurnTypes() throws Exception {
        // Initialize the database
        turnTypeRepository.saveAndFlush(turnType);

        // Get all the turnTypeList
        restTurnTypeMockMvc.perform(get("/api/turn-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(turnType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getTurnType() throws Exception {
        // Initialize the database
        turnTypeRepository.saveAndFlush(turnType);

        // Get the turnType
        restTurnTypeMockMvc.perform(get("/api/turn-types/{id}", turnType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(turnType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTurnType() throws Exception {
        // Get the turnType
        restTurnTypeMockMvc.perform(get("/api/turn-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTurnType() throws Exception {
        // Initialize the database
        turnTypeRepository.saveAndFlush(turnType);

        int databaseSizeBeforeUpdate = turnTypeRepository.findAll().size();

        // Update the turnType
        TurnType updatedTurnType = turnTypeRepository.findById(turnType.getId()).get();
        // Disconnect from session so that the updates on updatedTurnType are not directly saved in db
        em.detach(updatedTurnType);
        updatedTurnType
            .name(UPDATED_NAME);

        restTurnTypeMockMvc.perform(put("/api/turn-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTurnType)))
            .andExpect(status().isOk());

        // Validate the TurnType in the database
        List<TurnType> turnTypeList = turnTypeRepository.findAll();
        assertThat(turnTypeList).hasSize(databaseSizeBeforeUpdate);
        TurnType testTurnType = turnTypeList.get(turnTypeList.size() - 1);
        assertThat(testTurnType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTurnType() throws Exception {
        int databaseSizeBeforeUpdate = turnTypeRepository.findAll().size();

        // Create the TurnType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTurnTypeMockMvc.perform(put("/api/turn-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(turnType)))
            .andExpect(status().isBadRequest());

        // Validate the TurnType in the database
        List<TurnType> turnTypeList = turnTypeRepository.findAll();
        assertThat(turnTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTurnType() throws Exception {
        // Initialize the database
        turnTypeRepository.saveAndFlush(turnType);

        int databaseSizeBeforeDelete = turnTypeRepository.findAll().size();

        // Get the turnType
        restTurnTypeMockMvc.perform(delete("/api/turn-types/{id}", turnType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TurnType> turnTypeList = turnTypeRepository.findAll();
        assertThat(turnTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TurnType.class);
        TurnType turnType1 = new TurnType();
        turnType1.setId(1L);
        TurnType turnType2 = new TurnType();
        turnType2.setId(turnType1.getId());
        assertThat(turnType1).isEqualTo(turnType2);
        turnType2.setId(2L);
        assertThat(turnType1).isNotEqualTo(turnType2);
        turnType1.setId(null);
        assertThat(turnType1).isNotEqualTo(turnType2);
    }
}
