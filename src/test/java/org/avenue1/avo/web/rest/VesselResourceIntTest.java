package org.avenue1.avo.web.rest;

import org.avenue1.avo.AvoApp;

import org.avenue1.avo.domain.Vessel;
import org.avenue1.avo.repository.VesselRepository;
import org.avenue1.avo.service.VesselService;
import org.avenue1.avo.web.rest.errors.ExceptionTranslator;

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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static org.avenue1.avo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VesselResource REST controller.
 *
 * @see VesselResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvoApp.class)
public class VesselResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private VesselRepository vesselRepository;

    @Mock
    private VesselRepository vesselRepositoryMock;

    @Mock
    private VesselService vesselServiceMock;

    @Autowired
    private VesselService vesselService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVesselMockMvc;

    private Vessel vessel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VesselResource vesselResource = new VesselResource(vesselService);
        this.restVesselMockMvc = MockMvcBuilders.standaloneSetup(vesselResource)
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
    public static Vessel createEntity() {
        Vessel vessel = new Vessel()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .description(DEFAULT_DESCRIPTION);
        return vessel;
    }

    @Before
    public void initTest() {
        vesselRepository.deleteAll();
        vessel = createEntity();
    }

    @Test
    public void createVessel() throws Exception {
        int databaseSizeBeforeCreate = vesselRepository.findAll().size();

        // Create the Vessel
        restVesselMockMvc.perform(post("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vessel)))
            .andExpect(status().isCreated());

        // Validate the Vessel in the database
        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeCreate + 1);
        Vessel testVessel = vesselList.get(vesselList.size() - 1);
        assertThat(testVessel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVessel.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testVessel.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testVessel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createVesselWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vesselRepository.findAll().size();

        // Create the Vessel with an existing ID
        vessel.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restVesselMockMvc.perform(post("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vessel)))
            .andExpect(status().isBadRequest());

        // Validate the Vessel in the database
        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vesselRepository.findAll().size();
        // set the field null
        vessel.setName(null);

        // Create the Vessel, which fails.

        restVesselMockMvc.perform(post("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vessel)))
            .andExpect(status().isBadRequest());

        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vesselRepository.findAll().size();
        // set the field null
        vessel.setStartDate(null);

        // Create the Vessel, which fails.

        restVesselMockMvc.perform(post("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vessel)))
            .andExpect(status().isBadRequest());

        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vesselRepository.findAll().size();
        // set the field null
        vessel.setEndDate(null);

        // Create the Vessel, which fails.

        restVesselMockMvc.perform(post("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vessel)))
            .andExpect(status().isBadRequest());

        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVessels() throws Exception {
        // Initialize the database
        vesselRepository.save(vessel);

        // Get all the vesselList
        restVesselMockMvc.perform(get("/api/vessels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vessel.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllVesselsWithEagerRelationshipsIsEnabled() throws Exception {
        VesselResource vesselResource = new VesselResource(vesselServiceMock);
        when(vesselServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restVesselMockMvc = MockMvcBuilders.standaloneSetup(vesselResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVesselMockMvc.perform(get("/api/vessels?eagerload=true"))
        .andExpect(status().isOk());

        verify(vesselServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllVesselsWithEagerRelationshipsIsNotEnabled() throws Exception {
        VesselResource vesselResource = new VesselResource(vesselServiceMock);
            when(vesselServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restVesselMockMvc = MockMvcBuilders.standaloneSetup(vesselResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVesselMockMvc.perform(get("/api/vessels?eagerload=true"))
        .andExpect(status().isOk());

            verify(vesselServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getVessel() throws Exception {
        // Initialize the database
        vesselRepository.save(vessel);

        // Get the vessel
        restVesselMockMvc.perform(get("/api/vessels/{id}", vessel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vessel.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingVessel() throws Exception {
        // Get the vessel
        restVesselMockMvc.perform(get("/api/vessels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVessel() throws Exception {
        // Initialize the database
        vesselService.save(vessel);

        int databaseSizeBeforeUpdate = vesselRepository.findAll().size();

        // Update the vessel
        Vessel updatedVessel = vesselRepository.findById(vessel.getId()).get();
        updatedVessel
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION);

        restVesselMockMvc.perform(put("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVessel)))
            .andExpect(status().isOk());

        // Validate the Vessel in the database
        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeUpdate);
        Vessel testVessel = vesselList.get(vesselList.size() - 1);
        assertThat(testVessel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVessel.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testVessel.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testVessel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingVessel() throws Exception {
        int databaseSizeBeforeUpdate = vesselRepository.findAll().size();

        // Create the Vessel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVesselMockMvc.perform(put("/api/vessels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vessel)))
            .andExpect(status().isBadRequest());

        // Validate the Vessel in the database
        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteVessel() throws Exception {
        // Initialize the database
        vesselService.save(vessel);

        int databaseSizeBeforeDelete = vesselRepository.findAll().size();

        // Get the vessel
        restVesselMockMvc.perform(delete("/api/vessels/{id}", vessel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vessel> vesselList = vesselRepository.findAll();
        assertThat(vesselList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vessel.class);
        Vessel vessel1 = new Vessel();
        vessel1.setId("id1");
        Vessel vessel2 = new Vessel();
        vessel2.setId(vessel1.getId());
        assertThat(vessel1).isEqualTo(vessel2);
        vessel2.setId("id2");
        assertThat(vessel1).isNotEqualTo(vessel2);
        vessel1.setId(null);
        assertThat(vessel1).isNotEqualTo(vessel2);
    }
}
