package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhSampleApp;

import io.github.jhipster.application.domain.Familia;
import io.github.jhipster.application.repository.FamiliaRepository;
import io.github.jhipster.application.service.FamiliaService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

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


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FamiliaResource REST controller.
 *
 * @see FamiliaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhSampleApp.class)
public class FamiliaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private FamiliaRepository familiaRepository;

    @Autowired
    private FamiliaService familiaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFamiliaMockMvc;

    private Familia familia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamiliaResource familiaResource = new FamiliaResource(familiaService);
        this.restFamiliaMockMvc = MockMvcBuilders.standaloneSetup(familiaResource)
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
    public static Familia createEntity(EntityManager em) {
        Familia familia = new Familia()
            .nombre(DEFAULT_NOMBRE);
        return familia;
    }

    @Before
    public void initTest() {
        familia = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilia() throws Exception {
        int databaseSizeBeforeCreate = familiaRepository.findAll().size();

        // Create the Familia
        restFamiliaMockMvc.perform(post("/api/familias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familia)))
            .andExpect(status().isCreated());

        // Validate the Familia in the database
        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeCreate + 1);
        Familia testFamilia = familiaList.get(familiaList.size() - 1);
        assertThat(testFamilia.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createFamiliaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familiaRepository.findAll().size();

        // Create the Familia with an existing ID
        familia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamiliaMockMvc.perform(post("/api/familias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familia)))
            .andExpect(status().isBadRequest());

        // Validate the Familia in the database
        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = familiaRepository.findAll().size();
        // set the field null
        familia.setNombre(null);

        // Create the Familia, which fails.

        restFamiliaMockMvc.perform(post("/api/familias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familia)))
            .andExpect(status().isBadRequest());

        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFamilias() throws Exception {
        // Initialize the database
        familiaRepository.saveAndFlush(familia);

        // Get all the familiaList
        restFamiliaMockMvc.perform(get("/api/familias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getFamilia() throws Exception {
        // Initialize the database
        familiaRepository.saveAndFlush(familia);

        // Get the familia
        restFamiliaMockMvc.perform(get("/api/familias/{id}", familia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(familia.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFamilia() throws Exception {
        // Get the familia
        restFamiliaMockMvc.perform(get("/api/familias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilia() throws Exception {
        // Initialize the database
        familiaService.save(familia);

        int databaseSizeBeforeUpdate = familiaRepository.findAll().size();

        // Update the familia
        Familia updatedFamilia = familiaRepository.findById(familia.getId()).get();
        // Disconnect from session so that the updates on updatedFamilia are not directly saved in db
        em.detach(updatedFamilia);
        updatedFamilia
            .nombre(UPDATED_NOMBRE);

        restFamiliaMockMvc.perform(put("/api/familias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFamilia)))
            .andExpect(status().isOk());

        // Validate the Familia in the database
        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeUpdate);
        Familia testFamilia = familiaList.get(familiaList.size() - 1);
        assertThat(testFamilia.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilia() throws Exception {
        int databaseSizeBeforeUpdate = familiaRepository.findAll().size();

        // Create the Familia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamiliaMockMvc.perform(put("/api/familias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familia)))
            .andExpect(status().isBadRequest());

        // Validate the Familia in the database
        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFamilia() throws Exception {
        // Initialize the database
        familiaService.save(familia);

        int databaseSizeBeforeDelete = familiaRepository.findAll().size();

        // Get the familia
        restFamiliaMockMvc.perform(delete("/api/familias/{id}", familia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Familia> familiaList = familiaRepository.findAll();
        assertThat(familiaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Familia.class);
        Familia familia1 = new Familia();
        familia1.setId(1L);
        Familia familia2 = new Familia();
        familia2.setId(familia1.getId());
        assertThat(familia1).isEqualTo(familia2);
        familia2.setId(2L);
        assertThat(familia1).isNotEqualTo(familia2);
        familia1.setId(null);
        assertThat(familia1).isNotEqualTo(familia2);
    }
}
