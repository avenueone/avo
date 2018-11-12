package org.avenue1.avo.web.rest;

import org.avenue1.avo.AvoApp;

import org.avenue1.avo.domain.CampaignAttribute;
import org.avenue1.avo.repository.CampaignAttributeRepository;
import org.avenue1.avo.service.CampaignAttributeService;
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
 * Test class for the CampaignAttributeResource REST controller.
 *
 * @see CampaignAttributeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvoApp.class)
public class CampaignAttributeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private CampaignAttributeRepository campaignAttributeRepository;

    @Autowired
    private CampaignAttributeService campaignAttributeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCampaignAttributeMockMvc;

    private CampaignAttribute campaignAttribute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CampaignAttributeResource campaignAttributeResource = new CampaignAttributeResource(campaignAttributeService);
        this.restCampaignAttributeMockMvc = MockMvcBuilders.standaloneSetup(campaignAttributeResource)
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
    public static CampaignAttribute createEntity() {
        CampaignAttribute campaignAttribute = new CampaignAttribute()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE);
        return campaignAttribute;
    }

    @Before
    public void initTest() {
        campaignAttributeRepository.deleteAll();
        campaignAttribute = createEntity();
    }

    @Test
    public void createCampaignAttribute() throws Exception {
        int databaseSizeBeforeCreate = campaignAttributeRepository.findAll().size();

        // Create the CampaignAttribute
        restCampaignAttributeMockMvc.perform(post("/api/campaign-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignAttribute)))
            .andExpect(status().isCreated());

        // Validate the CampaignAttribute in the database
        List<CampaignAttribute> campaignAttributeList = campaignAttributeRepository.findAll();
        assertThat(campaignAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        CampaignAttribute testCampaignAttribute = campaignAttributeList.get(campaignAttributeList.size() - 1);
        assertThat(testCampaignAttribute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCampaignAttribute.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCampaignAttribute.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createCampaignAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campaignAttributeRepository.findAll().size();

        // Create the CampaignAttribute with an existing ID
        campaignAttribute.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampaignAttributeMockMvc.perform(post("/api/campaign-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the CampaignAttribute in the database
        List<CampaignAttribute> campaignAttributeList = campaignAttributeRepository.findAll();
        assertThat(campaignAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCampaignAttributes() throws Exception {
        // Initialize the database
        campaignAttributeRepository.save(campaignAttribute);

        // Get all the campaignAttributeList
        restCampaignAttributeMockMvc.perform(get("/api/campaign-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campaignAttribute.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    public void getCampaignAttribute() throws Exception {
        // Initialize the database
        campaignAttributeRepository.save(campaignAttribute);

        // Get the campaignAttribute
        restCampaignAttributeMockMvc.perform(get("/api/campaign-attributes/{id}", campaignAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campaignAttribute.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingCampaignAttribute() throws Exception {
        // Get the campaignAttribute
        restCampaignAttributeMockMvc.perform(get("/api/campaign-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCampaignAttribute() throws Exception {
        // Initialize the database
        campaignAttributeService.save(campaignAttribute);

        int databaseSizeBeforeUpdate = campaignAttributeRepository.findAll().size();

        // Update the campaignAttribute
        CampaignAttribute updatedCampaignAttribute = campaignAttributeRepository.findById(campaignAttribute.getId()).get();
        updatedCampaignAttribute
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE);

        restCampaignAttributeMockMvc.perform(put("/api/campaign-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCampaignAttribute)))
            .andExpect(status().isOk());

        // Validate the CampaignAttribute in the database
        List<CampaignAttribute> campaignAttributeList = campaignAttributeRepository.findAll();
        assertThat(campaignAttributeList).hasSize(databaseSizeBeforeUpdate);
        CampaignAttribute testCampaignAttribute = campaignAttributeList.get(campaignAttributeList.size() - 1);
        assertThat(testCampaignAttribute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCampaignAttribute.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCampaignAttribute.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingCampaignAttribute() throws Exception {
        int databaseSizeBeforeUpdate = campaignAttributeRepository.findAll().size();

        // Create the CampaignAttribute

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampaignAttributeMockMvc.perform(put("/api/campaign-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the CampaignAttribute in the database
        List<CampaignAttribute> campaignAttributeList = campaignAttributeRepository.findAll();
        assertThat(campaignAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCampaignAttribute() throws Exception {
        // Initialize the database
        campaignAttributeService.save(campaignAttribute);

        int databaseSizeBeforeDelete = campaignAttributeRepository.findAll().size();

        // Get the campaignAttribute
        restCampaignAttributeMockMvc.perform(delete("/api/campaign-attributes/{id}", campaignAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CampaignAttribute> campaignAttributeList = campaignAttributeRepository.findAll();
        assertThat(campaignAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignAttribute.class);
        CampaignAttribute campaignAttribute1 = new CampaignAttribute();
        campaignAttribute1.setId("id1");
        CampaignAttribute campaignAttribute2 = new CampaignAttribute();
        campaignAttribute2.setId(campaignAttribute1.getId());
        assertThat(campaignAttribute1).isEqualTo(campaignAttribute2);
        campaignAttribute2.setId("id2");
        assertThat(campaignAttribute1).isNotEqualTo(campaignAttribute2);
        campaignAttribute1.setId(null);
        assertThat(campaignAttribute1).isNotEqualTo(campaignAttribute2);
    }
}
