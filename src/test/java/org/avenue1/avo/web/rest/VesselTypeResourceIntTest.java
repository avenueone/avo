package org.avenue1.avo.web.rest;

import org.avenue1.avo.AvoApp;

import org.avenue1.avo.domain.VesselType;
import org.avenue1.avo.repository.VesselTypeRepository;
import org.avenue1.avo.service.VesselTypeService;
import org.avenue1.avo.web.rest.errors.ExceptionTranslator;

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

import java.util.List;


import static org.avenue1.avo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.avenue1.avo.domain.enumeration.VesselTypeEnum;
/**
 * Test class for the VesselTypeResource REST controller.
 *
 * @see VesselTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvoApp.class)
public class VesselTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final VesselTypeEnum DEFAULT_TYPE = VesselTypeEnum.FLYER;
    private static final VesselTypeEnum UPDATED_TYPE = VesselTypeEnum.WEB;

    private static final Boolean DEFAULT_RECURRING = false;
    private static final Boolean UPDATED_RECURRING = true;

    private static final Integer DEFAULT_DAY_OF_MONTH = 1;
    private static final Integer UPDATED_DAY_OF_MONTH = 2;

    private static final Integer DEFAULT_DAY_OF_WEEK = 1;
    private static final Integer UPDATED_DAY_OF_WEEK = 2;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    @Autowired
    private VesselTypeRepository vesselTypeRepository;

    @Autowired
    private VesselTypeService vesselTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVesselTypeMockMvc;

    private VesselType vesselType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VesselTypeResource vesselTypeResource = new VesselTypeResource(vesselTypeService);
        this.restVesselTypeMockMvc = MockMvcBuilders.standaloneSetup(vesselTypeResource)
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
    public static VesselType createEntity() {
        VesselType vesselType = new VesselType()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .recurring(DEFAULT_RECURRING)
            .dayOfMonth(DEFAULT_DAY_OF_MONTH)
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .month(DEFAULT_MONTH);
        return vesselType;
    }

    @Before
    public void initTest() {
        vesselTypeRepository.deleteAll();
        vesselType = createEntity();
    }

    @Test
    public void createVesselType() throws Exception {
        int databaseSizeBeforeCreate = vesselTypeRepository.findAll().size();

        // Create the VesselType
        restVesselTypeMockMvc.perform(post("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselType)))
            .andExpect(status().isCreated());

        // Validate the VesselType in the database
        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeCreate + 1);
        VesselType testVesselType = vesselTypeList.get(vesselTypeList.size() - 1);
        assertThat(testVesselType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVesselType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testVesselType.isRecurring()).isEqualTo(DEFAULT_RECURRING);
        assertThat(testVesselType.getDayOfMonth()).isEqualTo(DEFAULT_DAY_OF_MONTH);
        assertThat(testVesselType.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testVesselType.getMonth()).isEqualTo(DEFAULT_MONTH);
    }

    @Test
    public void createVesselTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vesselTypeRepository.findAll().size();

        // Create the VesselType with an existing ID
        vesselType.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restVesselTypeMockMvc.perform(post("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselType)))
            .andExpect(status().isBadRequest());

        // Validate the VesselType in the database
        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vesselTypeRepository.findAll().size();
        // set the field null
        vesselType.setName(null);

        // Create the VesselType, which fails.

        restVesselTypeMockMvc.perform(post("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselType)))
            .andExpect(status().isBadRequest());

        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vesselTypeRepository.findAll().size();
        // set the field null
        vesselType.setType(null);

        // Create the VesselType, which fails.

        restVesselTypeMockMvc.perform(post("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselType)))
            .andExpect(status().isBadRequest());

        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVesselTypes() throws Exception {
        // Initialize the database
        vesselTypeRepository.save(vesselType);

        // Get all the vesselTypeList
        restVesselTypeMockMvc.perform(get("/api/vessel-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vesselType.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].recurring").value(hasItem(DEFAULT_RECURRING.booleanValue())))
            .andExpect(jsonPath("$.[*].dayOfMonth").value(hasItem(DEFAULT_DAY_OF_MONTH)))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)));
    }
    
    @Test
    public void getVesselType() throws Exception {
        // Initialize the database
        vesselTypeRepository.save(vesselType);

        // Get the vesselType
        restVesselTypeMockMvc.perform(get("/api/vessel-types/{id}", vesselType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vesselType.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.recurring").value(DEFAULT_RECURRING.booleanValue()))
            .andExpect(jsonPath("$.dayOfMonth").value(DEFAULT_DAY_OF_MONTH))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH));
    }

    @Test
    public void getNonExistingVesselType() throws Exception {
        // Get the vesselType
        restVesselTypeMockMvc.perform(get("/api/vessel-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVesselType() throws Exception {
        // Initialize the database
        vesselTypeService.save(vesselType);

        int databaseSizeBeforeUpdate = vesselTypeRepository.findAll().size();

        // Update the vesselType
        VesselType updatedVesselType = vesselTypeRepository.findById(vesselType.getId()).get();
        updatedVesselType
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .recurring(UPDATED_RECURRING)
            .dayOfMonth(UPDATED_DAY_OF_MONTH)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .month(UPDATED_MONTH);

        restVesselTypeMockMvc.perform(put("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVesselType)))
            .andExpect(status().isOk());

        // Validate the VesselType in the database
        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeUpdate);
        VesselType testVesselType = vesselTypeList.get(vesselTypeList.size() - 1);
        assertThat(testVesselType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVesselType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVesselType.isRecurring()).isEqualTo(UPDATED_RECURRING);
        assertThat(testVesselType.getDayOfMonth()).isEqualTo(UPDATED_DAY_OF_MONTH);
        assertThat(testVesselType.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testVesselType.getMonth()).isEqualTo(UPDATED_MONTH);
    }

    @Test
    public void updateNonExistingVesselType() throws Exception {
        int databaseSizeBeforeUpdate = vesselTypeRepository.findAll().size();

        // Create the VesselType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVesselTypeMockMvc.perform(put("/api/vessel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselType)))
            .andExpect(status().isBadRequest());

        // Validate the VesselType in the database
        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteVesselType() throws Exception {
        // Initialize the database
        vesselTypeService.save(vesselType);

        int databaseSizeBeforeDelete = vesselTypeRepository.findAll().size();

        // Get the vesselType
        restVesselTypeMockMvc.perform(delete("/api/vessel-types/{id}", vesselType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VesselType> vesselTypeList = vesselTypeRepository.findAll();
        assertThat(vesselTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VesselType.class);
        VesselType vesselType1 = new VesselType();
        vesselType1.setId("id1");
        VesselType vesselType2 = new VesselType();
        vesselType2.setId(vesselType1.getId());
        assertThat(vesselType1).isEqualTo(vesselType2);
        vesselType2.setId("id2");
        assertThat(vesselType1).isNotEqualTo(vesselType2);
        vesselType1.setId(null);
        assertThat(vesselType1).isNotEqualTo(vesselType2);
    }
}
