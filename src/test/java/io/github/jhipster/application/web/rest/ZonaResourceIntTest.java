package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhSampleApp;

import io.github.jhipster.application.domain.Zona;
import io.github.jhipster.application.repository.ZonaRepository;
import io.github.jhipster.application.service.ZonaService;
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
 * Test class for the ZonaResource REST controller.
 *
 * @see ZonaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhSampleApp.class)
public class ZonaResourceIntTest {

    private static final String DEFAULT_ZONA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ZONA_NAME = "BBBBBBBBBB";

    @Autowired
    private ZonaRepository zonaRepository;

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restZonaMockMvc;

    private Zona zona;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZonaResource zonaResource = new ZonaResource(zonaService);
        this.restZonaMockMvc = MockMvcBuilders.standaloneSetup(zonaResource)
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
    public static Zona createEntity(EntityManager em) {
        Zona zona = new Zona()
            .zonaName(DEFAULT_ZONA_NAME);
        return zona;
    }

    @Before
    public void initTest() {
        zona = createEntity(em);
    }

    @Test
    @Transactional
    public void createZona() throws Exception {
        int databaseSizeBeforeCreate = zonaRepository.findAll().size();

        // Create the Zona
        restZonaMockMvc.perform(post("/api/zonas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zona)))
            .andExpect(status().isCreated());

        // Validate the Zona in the database
        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeCreate + 1);
        Zona testZona = zonaList.get(zonaList.size() - 1);
        assertThat(testZona.getZonaName()).isEqualTo(DEFAULT_ZONA_NAME);
    }

    @Test
    @Transactional
    public void createZonaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zonaRepository.findAll().size();

        // Create the Zona with an existing ID
        zona.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZonaMockMvc.perform(post("/api/zonas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zona)))
            .andExpect(status().isBadRequest());

        // Validate the Zona in the database
        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkZonaNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = zonaRepository.findAll().size();
        // set the field null
        zona.setZonaName(null);

        // Create the Zona, which fails.

        restZonaMockMvc.perform(post("/api/zonas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zona)))
            .andExpect(status().isBadRequest());

        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZonas() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get all the zonaList
        restZonaMockMvc.perform(get("/api/zonas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zona.getId().intValue())))
            .andExpect(jsonPath("$.[*].zonaName").value(hasItem(DEFAULT_ZONA_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getZona() throws Exception {
        // Initialize the database
        zonaRepository.saveAndFlush(zona);

        // Get the zona
        restZonaMockMvc.perform(get("/api/zonas/{id}", zona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zona.getId().intValue()))
            .andExpect(jsonPath("$.zonaName").value(DEFAULT_ZONA_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingZona() throws Exception {
        // Get the zona
        restZonaMockMvc.perform(get("/api/zonas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZona() throws Exception {
        // Initialize the database
        zonaService.save(zona);

        int databaseSizeBeforeUpdate = zonaRepository.findAll().size();

        // Update the zona
        Zona updatedZona = zonaRepository.findById(zona.getId()).get();
        // Disconnect from session so that the updates on updatedZona are not directly saved in db
        em.detach(updatedZona);
        updatedZona
            .zonaName(UPDATED_ZONA_NAME);

        restZonaMockMvc.perform(put("/api/zonas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedZona)))
            .andExpect(status().isOk());

        // Validate the Zona in the database
        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeUpdate);
        Zona testZona = zonaList.get(zonaList.size() - 1);
        assertThat(testZona.getZonaName()).isEqualTo(UPDATED_ZONA_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingZona() throws Exception {
        int databaseSizeBeforeUpdate = zonaRepository.findAll().size();

        // Create the Zona

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZonaMockMvc.perform(put("/api/zonas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zona)))
            .andExpect(status().isBadRequest());

        // Validate the Zona in the database
        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZona() throws Exception {
        // Initialize the database
        zonaService.save(zona);

        int databaseSizeBeforeDelete = zonaRepository.findAll().size();

        // Get the zona
        restZonaMockMvc.perform(delete("/api/zonas/{id}", zona.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Zona> zonaList = zonaRepository.findAll();
        assertThat(zonaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zona.class);
        Zona zona1 = new Zona();
        zona1.setId(1L);
        Zona zona2 = new Zona();
        zona2.setId(zona1.getId());
        assertThat(zona1).isEqualTo(zona2);
        zona2.setId(2L);
        assertThat(zona1).isNotEqualTo(zona2);
        zona1.setId(null);
        assertThat(zona1).isNotEqualTo(zona2);
    }
}
