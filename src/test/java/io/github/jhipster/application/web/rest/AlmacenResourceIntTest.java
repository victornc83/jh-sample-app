package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhSampleApp;

import io.github.jhipster.application.domain.Almacen;
import io.github.jhipster.application.repository.AlmacenRepository;
import io.github.jhipster.application.service.AlmacenService;
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
 * Test class for the AlmacenResource REST controller.
 *
 * @see AlmacenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhSampleApp.class)
public class AlmacenResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private AlmacenRepository almacenRepository;

    @Autowired
    private AlmacenService almacenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlmacenMockMvc;

    private Almacen almacen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlmacenResource almacenResource = new AlmacenResource(almacenService);
        this.restAlmacenMockMvc = MockMvcBuilders.standaloneSetup(almacenResource)
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
    public static Almacen createEntity(EntityManager em) {
        Almacen almacen = new Almacen()
            .nombre(DEFAULT_NOMBRE);
        return almacen;
    }

    @Before
    public void initTest() {
        almacen = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlmacen() throws Exception {
        int databaseSizeBeforeCreate = almacenRepository.findAll().size();

        // Create the Almacen
        restAlmacenMockMvc.perform(post("/api/almacens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(almacen)))
            .andExpect(status().isCreated());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeCreate + 1);
        Almacen testAlmacen = almacenList.get(almacenList.size() - 1);
        assertThat(testAlmacen.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createAlmacenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = almacenRepository.findAll().size();

        // Create the Almacen with an existing ID
        almacen.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlmacenMockMvc.perform(post("/api/almacens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(almacen)))
            .andExpect(status().isBadRequest());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = almacenRepository.findAll().size();
        // set the field null
        almacen.setNombre(null);

        // Create the Almacen, which fails.

        restAlmacenMockMvc.perform(post("/api/almacens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(almacen)))
            .andExpect(status().isBadRequest());

        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlmacens() throws Exception {
        // Initialize the database
        almacenRepository.saveAndFlush(almacen);

        // Get all the almacenList
        restAlmacenMockMvc.perform(get("/api/almacens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(almacen.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getAlmacen() throws Exception {
        // Initialize the database
        almacenRepository.saveAndFlush(almacen);

        // Get the almacen
        restAlmacenMockMvc.perform(get("/api/almacens/{id}", almacen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(almacen.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlmacen() throws Exception {
        // Get the almacen
        restAlmacenMockMvc.perform(get("/api/almacens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlmacen() throws Exception {
        // Initialize the database
        almacenService.save(almacen);

        int databaseSizeBeforeUpdate = almacenRepository.findAll().size();

        // Update the almacen
        Almacen updatedAlmacen = almacenRepository.findById(almacen.getId()).get();
        // Disconnect from session so that the updates on updatedAlmacen are not directly saved in db
        em.detach(updatedAlmacen);
        updatedAlmacen
            .nombre(UPDATED_NOMBRE);

        restAlmacenMockMvc.perform(put("/api/almacens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlmacen)))
            .andExpect(status().isOk());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeUpdate);
        Almacen testAlmacen = almacenList.get(almacenList.size() - 1);
        assertThat(testAlmacen.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingAlmacen() throws Exception {
        int databaseSizeBeforeUpdate = almacenRepository.findAll().size();

        // Create the Almacen

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlmacenMockMvc.perform(put("/api/almacens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(almacen)))
            .andExpect(status().isBadRequest());

        // Validate the Almacen in the database
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlmacen() throws Exception {
        // Initialize the database
        almacenService.save(almacen);

        int databaseSizeBeforeDelete = almacenRepository.findAll().size();

        // Get the almacen
        restAlmacenMockMvc.perform(delete("/api/almacens/{id}", almacen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Almacen> almacenList = almacenRepository.findAll();
        assertThat(almacenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Almacen.class);
        Almacen almacen1 = new Almacen();
        almacen1.setId(1L);
        Almacen almacen2 = new Almacen();
        almacen2.setId(almacen1.getId());
        assertThat(almacen1).isEqualTo(almacen2);
        almacen2.setId(2L);
        assertThat(almacen1).isNotEqualTo(almacen2);
        almacen1.setId(null);
        assertThat(almacen1).isNotEqualTo(almacen2);
    }
}
