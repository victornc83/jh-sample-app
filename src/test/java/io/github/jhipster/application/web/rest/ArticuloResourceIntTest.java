package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhSampleApp;

import io.github.jhipster.application.domain.Articulo;
import io.github.jhipster.application.repository.ArticuloRepository;
import io.github.jhipster.application.service.ArticuloService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArticuloResource REST controller.
 *
 * @see ArticuloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhSampleApp.class)
public class ArticuloResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Float DEFAULT_PRECIO = 1F;
    private static final Float UPDATED_PRECIO = 2F;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Mock
    private ArticuloRepository articuloRepositoryMock;

    @Mock
    private ArticuloService articuloServiceMock;

    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArticuloMockMvc;

    private Articulo articulo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticuloResource articuloResource = new ArticuloResource(articuloService);
        this.restArticuloMockMvc = MockMvcBuilders.standaloneSetup(articuloResource)
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
    public static Articulo createEntity(EntityManager em) {
        Articulo articulo = new Articulo()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .precio(DEFAULT_PRECIO);
        return articulo;
    }

    @Before
    public void initTest() {
        articulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticulo() throws Exception {
        int databaseSizeBeforeCreate = articuloRepository.findAll().size();

        // Create the Articulo
        restArticuloMockMvc.perform(post("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articulo)))
            .andExpect(status().isCreated());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeCreate + 1);
        Articulo testArticulo = articuloList.get(articuloList.size() - 1);
        assertThat(testArticulo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testArticulo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testArticulo.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createArticuloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articuloRepository.findAll().size();

        // Create the Articulo with an existing ID
        articulo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticuloMockMvc.perform(post("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articulo)))
            .andExpect(status().isBadRequest());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = articuloRepository.findAll().size();
        // set the field null
        articulo.setNombre(null);

        // Create the Articulo, which fails.

        restArticuloMockMvc.perform(post("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articulo)))
            .andExpect(status().isBadRequest());

        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = articuloRepository.findAll().size();
        // set the field null
        articulo.setPrecio(null);

        // Create the Articulo, which fails.

        restArticuloMockMvc.perform(post("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articulo)))
            .andExpect(status().isBadRequest());

        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArticulos() throws Exception {
        // Initialize the database
        articuloRepository.saveAndFlush(articulo);

        // Get all the articuloList
        restArticuloMockMvc.perform(get("/api/articulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllArticulosWithEagerRelationshipsIsEnabled() throws Exception {
        ArticuloResource articuloResource = new ArticuloResource(articuloServiceMock);
        when(articuloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restArticuloMockMvc = MockMvcBuilders.standaloneSetup(articuloResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArticuloMockMvc.perform(get("/api/articulos?eagerload=true"))
        .andExpect(status().isOk());

        verify(articuloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllArticulosWithEagerRelationshipsIsNotEnabled() throws Exception {
        ArticuloResource articuloResource = new ArticuloResource(articuloServiceMock);
            when(articuloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restArticuloMockMvc = MockMvcBuilders.standaloneSetup(articuloResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArticuloMockMvc.perform(get("/api/articulos?eagerload=true"))
        .andExpect(status().isOk());

            verify(articuloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getArticulo() throws Exception {
        // Initialize the database
        articuloRepository.saveAndFlush(articulo);

        // Get the articulo
        restArticuloMockMvc.perform(get("/api/articulos/{id}", articulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(articulo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingArticulo() throws Exception {
        // Get the articulo
        restArticuloMockMvc.perform(get("/api/articulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticulo() throws Exception {
        // Initialize the database
        articuloService.save(articulo);

        int databaseSizeBeforeUpdate = articuloRepository.findAll().size();

        // Update the articulo
        Articulo updatedArticulo = articuloRepository.findById(articulo.getId()).get();
        // Disconnect from session so that the updates on updatedArticulo are not directly saved in db
        em.detach(updatedArticulo);
        updatedArticulo
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precio(UPDATED_PRECIO);

        restArticuloMockMvc.perform(put("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticulo)))
            .andExpect(status().isOk());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeUpdate);
        Articulo testArticulo = articuloList.get(articuloList.size() - 1);
        assertThat(testArticulo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testArticulo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testArticulo.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingArticulo() throws Exception {
        int databaseSizeBeforeUpdate = articuloRepository.findAll().size();

        // Create the Articulo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticuloMockMvc.perform(put("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articulo)))
            .andExpect(status().isBadRequest());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArticulo() throws Exception {
        // Initialize the database
        articuloService.save(articulo);

        int databaseSizeBeforeDelete = articuloRepository.findAll().size();

        // Get the articulo
        restArticuloMockMvc.perform(delete("/api/articulos/{id}", articulo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Articulo.class);
        Articulo articulo1 = new Articulo();
        articulo1.setId(1L);
        Articulo articulo2 = new Articulo();
        articulo2.setId(articulo1.getId());
        assertThat(articulo1).isEqualTo(articulo2);
        articulo2.setId(2L);
        assertThat(articulo1).isNotEqualTo(articulo2);
        articulo1.setId(null);
        assertThat(articulo1).isNotEqualTo(articulo2);
    }
}
