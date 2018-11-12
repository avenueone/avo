package org.avenue1.avo.web.rest;

import org.avenue1.avo.AvoApp;

import org.avenue1.avo.domain.VesselAttribute;
import org.avenue1.avo.repository.VesselAttributeRepository;
import org.avenue1.avo.service.VesselAttributeService;
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

/**
 * Test class for the VesselAttributeResource REST controller.
 *
 * @see VesselAttributeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvoApp.class)
public class VesselAttributeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private VesselAttributeRepository vesselAttributeRepository;

    @Autowired
    private VesselAttributeService vesselAttributeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVesselAttributeMockMvc;

    private VesselAttribute vesselAttribute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VesselAttributeResource vesselAttributeResource = new VesselAttributeResource(vesselAttributeService);
        this.restVesselAttributeMockMvc = MockMvcBuilders.standaloneSetup(vesselAttributeResource)
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
    public static VesselAttribute createEntity() {
        VesselAttribute vesselAttribute = new VesselAttribute()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE);
        return vesselAttribute;
    }

    @Before
    public void initTest() {
        vesselAttributeRepository.deleteAll();
        vesselAttribute = createEntity();
    }

    @Test
    public void createVesselAttribute() throws Exception {
        int databaseSizeBeforeCreate = vesselAttributeRepository.findAll().size();

        // Create the VesselAttribute
        restVesselAttributeMockMvc.perform(post("/api/vessel-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselAttribute)))
            .andExpect(status().isCreated());

        // Validate the VesselAttribute in the database
        List<VesselAttribute> vesselAttributeList = vesselAttributeRepository.findAll();
        assertThat(vesselAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        VesselAttribute testVesselAttribute = vesselAttributeList.get(vesselAttributeList.size() - 1);
        assertThat(testVesselAttribute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVesselAttribute.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testVesselAttribute.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createVesselAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vesselAttributeRepository.findAll().size();

        // Create the VesselAttribute with an existing ID
        vesselAttribute.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restVesselAttributeMockMvc.perform(post("/api/vessel-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the VesselAttribute in the database
        List<VesselAttribute> vesselAttributeList = vesselAttributeRepository.findAll();
        assertThat(vesselAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllVesselAttributes() throws Exception {
        // Initialize the database
        vesselAttributeRepository.save(vesselAttribute);

        // Get all the vesselAttributeList
        restVesselAttributeMockMvc.perform(get("/api/vessel-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vesselAttribute.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    public void getVesselAttribute() throws Exception {
        // Initialize the database
        vesselAttributeRepository.save(vesselAttribute);

        // Get the vesselAttribute
        restVesselAttributeMockMvc.perform(get("/api/vessel-attributes/{id}", vesselAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vesselAttribute.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingVesselAttribute() throws Exception {
        // Get the vesselAttribute
        restVesselAttributeMockMvc.perform(get("/api/vessel-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVesselAttribute() throws Exception {
        // Initialize the database
        vesselAttributeService.save(vesselAttribute);

        int databaseSizeBeforeUpdate = vesselAttributeRepository.findAll().size();

        // Update the vesselAttribute
        VesselAttribute updatedVesselAttribute = vesselAttributeRepository.findById(vesselAttribute.getId()).get();
        updatedVesselAttribute
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE);

        restVesselAttributeMockMvc.perform(put("/api/vessel-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVesselAttribute)))
            .andExpect(status().isOk());

        // Validate the VesselAttribute in the database
        List<VesselAttribute> vesselAttributeList = vesselAttributeRepository.findAll();
        assertThat(vesselAttributeList).hasSize(databaseSizeBeforeUpdate);
        VesselAttribute testVesselAttribute = vesselAttributeList.get(vesselAttributeList.size() - 1);
        assertThat(testVesselAttribute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVesselAttribute.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVesselAttribute.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingVesselAttribute() throws Exception {
        int databaseSizeBeforeUpdate = vesselAttributeRepository.findAll().size();

        // Create the VesselAttribute

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVesselAttributeMockMvc.perform(put("/api/vessel-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vesselAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the VesselAttribute in the database
        List<VesselAttribute> vesselAttributeList = vesselAttributeRepository.findAll();
        assertThat(vesselAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteVesselAttribute() throws Exception {
        // Initialize the database
        vesselAttributeService.save(vesselAttribute);

        int databaseSizeBeforeDelete = vesselAttributeRepository.findAll().size();

        // Get the vesselAttribute
        restVesselAttributeMockMvc.perform(delete("/api/vessel-attributes/{id}", vesselAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VesselAttribute> vesselAttributeList = vesselAttributeRepository.findAll();
        assertThat(vesselAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VesselAttribute.class);
        VesselAttribute vesselAttribute1 = new VesselAttribute();
        vesselAttribute1.setId("id1");
        VesselAttribute vesselAttribute2 = new VesselAttribute();
        vesselAttribute2.setId(vesselAttribute1.getId());
        assertThat(vesselAttribute1).isEqualTo(vesselAttribute2);
        vesselAttribute2.setId("id2");
        assertThat(vesselAttribute1).isNotEqualTo(vesselAttribute2);
        vesselAttribute1.setId(null);
        assertThat(vesselAttribute1).isNotEqualTo(vesselAttribute2);
    }
}
