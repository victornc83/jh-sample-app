package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhSampleApp;

import io.github.jhipster.application.domain.Cuenta;
import io.github.jhipster.application.repository.CuentaRepository;
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

/**
 * Test class for the CuentaResource REST controller.
 *
 * @see CuentaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhSampleApp.class)
public class CuentaResourceIntTest {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_DESCUENTO = 1F;
    private static final Float UPDATED_DESCUENTO = 2F;

    private static final Float DEFAULT_GASTOS = 1F;
    private static final Float UPDATED_GASTOS = 2F;

    private static final Float DEFAULT_TOTAL = 1F;
    private static final Float UPDATED_TOTAL = 2F;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCuentaMockMvc;

    private Cuenta cuenta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CuentaResource cuentaResource = new CuentaResource(cuentaRepository);
        this.restCuentaMockMvc = MockMvcBuilders.standaloneSetup(cuentaResource)
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
    public static Cuenta createEntity(EntityManager em) {
        Cuenta cuenta = new Cuenta()
            .fecha(DEFAULT_FECHA)
            .descuento(DEFAULT_DESCUENTO)
            .gastos(DEFAULT_GASTOS)
            .total(DEFAULT_TOTAL);
        return cuenta;
    }

    @Before
    public void initTest() {
        cuenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createCuenta() throws Exception {
        int databaseSizeBeforeCreate = cuentaRepository.findAll().size();

        // Create the Cuenta
        restCuentaMockMvc.perform(post("/api/cuentas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuenta)))
            .andExpect(status().isCreated());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeCreate + 1);
        Cuenta testCuenta = cuentaList.get(cuentaList.size() - 1);
        assertThat(testCuenta.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCuenta.getDescuento()).isEqualTo(DEFAULT_DESCUENTO);
        assertThat(testCuenta.getGastos()).isEqualTo(DEFAULT_GASTOS);
        assertThat(testCuenta.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createCuentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cuentaRepository.findAll().size();

        // Create the Cuenta with an existing ID
        cuenta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuentaMockMvc.perform(post("/api/cuentas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuenta)))
            .andExpect(status().isBadRequest());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCuentas() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get all the cuentaList
        restCuentaMockMvc.perform(get("/api/cuentas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].gastos").value(hasItem(DEFAULT_GASTOS.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCuenta() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        // Get the cuenta
        restCuentaMockMvc.perform(get("/api/cuentas/{id}", cuenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cuenta.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.descuento").value(DEFAULT_DESCUENTO.doubleValue()))
            .andExpect(jsonPath("$.gastos").value(DEFAULT_GASTOS.doubleValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCuenta() throws Exception {
        // Get the cuenta
        restCuentaMockMvc.perform(get("/api/cuentas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCuenta() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        int databaseSizeBeforeUpdate = cuentaRepository.findAll().size();

        // Update the cuenta
        Cuenta updatedCuenta = cuentaRepository.findById(cuenta.getId()).get();
        // Disconnect from session so that the updates on updatedCuenta are not directly saved in db
        em.detach(updatedCuenta);
        updatedCuenta
            .fecha(UPDATED_FECHA)
            .descuento(UPDATED_DESCUENTO)
            .gastos(UPDATED_GASTOS)
            .total(UPDATED_TOTAL);

        restCuentaMockMvc.perform(put("/api/cuentas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCuenta)))
            .andExpect(status().isOk());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeUpdate);
        Cuenta testCuenta = cuentaList.get(cuentaList.size() - 1);
        assertThat(testCuenta.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCuenta.getDescuento()).isEqualTo(UPDATED_DESCUENTO);
        assertThat(testCuenta.getGastos()).isEqualTo(UPDATED_GASTOS);
        assertThat(testCuenta.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingCuenta() throws Exception {
        int databaseSizeBeforeUpdate = cuentaRepository.findAll().size();

        // Create the Cuenta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuentaMockMvc.perform(put("/api/cuentas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuenta)))
            .andExpect(status().isBadRequest());

        // Validate the Cuenta in the database
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCuenta() throws Exception {
        // Initialize the database
        cuentaRepository.saveAndFlush(cuenta);

        int databaseSizeBeforeDelete = cuentaRepository.findAll().size();

        // Get the cuenta
        restCuentaMockMvc.perform(delete("/api/cuentas/{id}", cuenta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cuenta> cuentaList = cuentaRepository.findAll();
        assertThat(cuentaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cuenta.class);
        Cuenta cuenta1 = new Cuenta();
        cuenta1.setId(1L);
        Cuenta cuenta2 = new Cuenta();
        cuenta2.setId(cuenta1.getId());
        assertThat(cuenta1).isEqualTo(cuenta2);
        cuenta2.setId(2L);
        assertThat(cuenta1).isNotEqualTo(cuenta2);
        cuenta1.setId(null);
        assertThat(cuenta1).isNotEqualTo(cuenta2);
    }
}
