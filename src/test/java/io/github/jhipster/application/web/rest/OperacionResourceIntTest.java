package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhSampleApp;

import io.github.jhipster.application.domain.Operacion;
import io.github.jhipster.application.repository.OperacionRepository;
import io.github.jhipster.application.service.OperacionService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.Accion;
/**
 * Test class for the OperacionResource REST controller.
 *
 * @see OperacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhSampleApp.class)
public class OperacionResourceIntTest {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Accion DEFAULT_ACCION = Accion.ENTRADA;
    private static final Accion UPDATED_ACCION = Accion.SALIDA;

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    @Autowired
    private OperacionRepository operacionRepository;

    @Autowired
    private OperacionService operacionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperacionMockMvc;

    private Operacion operacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperacionResource operacionResource = new OperacionResource(operacionService);
        this.restOperacionMockMvc = MockMvcBuilders.standaloneSetup(operacionResource)
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
    public static Operacion createEntity(EntityManager em) {
        Operacion operacion = new Operacion()
            .fecha(DEFAULT_FECHA)
            .accion(DEFAULT_ACCION)
            .cantidad(DEFAULT_CANTIDAD);
        return operacion;
    }

    @Before
    public void initTest() {
        operacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperacion() throws Exception {
        int databaseSizeBeforeCreate = operacionRepository.findAll().size();

        // Create the Operacion
        restOperacionMockMvc.perform(post("/api/operacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operacion)))
            .andExpect(status().isCreated());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeCreate + 1);
        Operacion testOperacion = operacionList.get(operacionList.size() - 1);
        assertThat(testOperacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testOperacion.getAccion()).isEqualTo(DEFAULT_ACCION);
        assertThat(testOperacion.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createOperacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operacionRepository.findAll().size();

        // Create the Operacion with an existing ID
        operacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperacionMockMvc.perform(post("/api/operacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operacion)))
            .andExpect(status().isBadRequest());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOperacions() throws Exception {
        // Initialize the database
        operacionRepository.saveAndFlush(operacion);

        // Get all the operacionList
        restOperacionMockMvc.perform(get("/api/operacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].accion").value(hasItem(DEFAULT_ACCION.toString())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())));
    }
    
    @Test
    @Transactional
    public void getOperacion() throws Exception {
        // Initialize the database
        operacionRepository.saveAndFlush(operacion);

        // Get the operacion
        restOperacionMockMvc.perform(get("/api/operacions/{id}", operacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operacion.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.accion").value(DEFAULT_ACCION.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOperacion() throws Exception {
        // Get the operacion
        restOperacionMockMvc.perform(get("/api/operacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperacion() throws Exception {
        // Initialize the database
        operacionService.save(operacion);

        int databaseSizeBeforeUpdate = operacionRepository.findAll().size();

        // Update the operacion
        Operacion updatedOperacion = operacionRepository.findById(operacion.getId()).get();
        // Disconnect from session so that the updates on updatedOperacion are not directly saved in db
        em.detach(updatedOperacion);
        updatedOperacion
            .fecha(UPDATED_FECHA)
            .accion(UPDATED_ACCION)
            .cantidad(UPDATED_CANTIDAD);

        restOperacionMockMvc.perform(put("/api/operacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperacion)))
            .andExpect(status().isOk());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeUpdate);
        Operacion testOperacion = operacionList.get(operacionList.size() - 1);
        assertThat(testOperacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testOperacion.getAccion()).isEqualTo(UPDATED_ACCION);
        assertThat(testOperacion.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingOperacion() throws Exception {
        int databaseSizeBeforeUpdate = operacionRepository.findAll().size();

        // Create the Operacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperacionMockMvc.perform(put("/api/operacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operacion)))
            .andExpect(status().isBadRequest());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOperacion() throws Exception {
        // Initialize the database
        operacionService.save(operacion);

        int databaseSizeBeforeDelete = operacionRepository.findAll().size();

        // Get the operacion
        restOperacionMockMvc.perform(delete("/api/operacions/{id}", operacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operacion.class);
        Operacion operacion1 = new Operacion();
        operacion1.setId(1L);
        Operacion operacion2 = new Operacion();
        operacion2.setId(operacion1.getId());
        assertThat(operacion1).isEqualTo(operacion2);
        operacion2.setId(2L);
        assertThat(operacion1).isNotEqualTo(operacion2);
        operacion1.setId(null);
        assertThat(operacion1).isNotEqualTo(operacion2);
    }
}
