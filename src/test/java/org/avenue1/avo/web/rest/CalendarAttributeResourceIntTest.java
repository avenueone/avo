package org.avenue1.avo.web.rest;

import org.avenue1.avo.AvoApp;

import org.avenue1.avo.domain.CalendarAttribute;
import org.avenue1.avo.repository.CalendarAttributeRepository;
import org.avenue1.avo.service.CalendarAttributeService;
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
 * Test class for the CalendarAttributeResource REST controller.
 *
 * @see CalendarAttributeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvoApp.class)
public class CalendarAttributeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private CalendarAttributeRepository calendarAttributeRepository;

    @Autowired
    private CalendarAttributeService calendarAttributeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCalendarAttributeMockMvc;

    private CalendarAttribute calendarAttribute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalendarAttributeResource calendarAttributeResource = new CalendarAttributeResource(calendarAttributeService);
        this.restCalendarAttributeMockMvc = MockMvcBuilders.standaloneSetup(calendarAttributeResource)
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
    public static CalendarAttribute createEntity() {
        CalendarAttribute calendarAttribute = new CalendarAttribute()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE);
        return calendarAttribute;
    }

    @Before
    public void initTest() {
        calendarAttributeRepository.deleteAll();
        calendarAttribute = createEntity();
    }

    @Test
    public void createCalendarAttribute() throws Exception {
        int databaseSizeBeforeCreate = calendarAttributeRepository.findAll().size();

        // Create the CalendarAttribute
        restCalendarAttributeMockMvc.perform(post("/api/calendar-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarAttribute)))
            .andExpect(status().isCreated());

        // Validate the CalendarAttribute in the database
        List<CalendarAttribute> calendarAttributeList = calendarAttributeRepository.findAll();
        assertThat(calendarAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        CalendarAttribute testCalendarAttribute = calendarAttributeList.get(calendarAttributeList.size() - 1);
        assertThat(testCalendarAttribute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCalendarAttribute.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCalendarAttribute.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createCalendarAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calendarAttributeRepository.findAll().size();

        // Create the CalendarAttribute with an existing ID
        calendarAttribute.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarAttributeMockMvc.perform(post("/api/calendar-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the CalendarAttribute in the database
        List<CalendarAttribute> calendarAttributeList = calendarAttributeRepository.findAll();
        assertThat(calendarAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCalendarAttributes() throws Exception {
        // Initialize the database
        calendarAttributeRepository.save(calendarAttribute);

        // Get all the calendarAttributeList
        restCalendarAttributeMockMvc.perform(get("/api/calendar-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarAttribute.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    public void getCalendarAttribute() throws Exception {
        // Initialize the database
        calendarAttributeRepository.save(calendarAttribute);

        // Get the calendarAttribute
        restCalendarAttributeMockMvc.perform(get("/api/calendar-attributes/{id}", calendarAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calendarAttribute.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingCalendarAttribute() throws Exception {
        // Get the calendarAttribute
        restCalendarAttributeMockMvc.perform(get("/api/calendar-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCalendarAttribute() throws Exception {
        // Initialize the database
        calendarAttributeService.save(calendarAttribute);

        int databaseSizeBeforeUpdate = calendarAttributeRepository.findAll().size();

        // Update the calendarAttribute
        CalendarAttribute updatedCalendarAttribute = calendarAttributeRepository.findById(calendarAttribute.getId()).get();
        updatedCalendarAttribute
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE);

        restCalendarAttributeMockMvc.perform(put("/api/calendar-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalendarAttribute)))
            .andExpect(status().isOk());

        // Validate the CalendarAttribute in the database
        List<CalendarAttribute> calendarAttributeList = calendarAttributeRepository.findAll();
        assertThat(calendarAttributeList).hasSize(databaseSizeBeforeUpdate);
        CalendarAttribute testCalendarAttribute = calendarAttributeList.get(calendarAttributeList.size() - 1);
        assertThat(testCalendarAttribute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCalendarAttribute.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCalendarAttribute.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingCalendarAttribute() throws Exception {
        int databaseSizeBeforeUpdate = calendarAttributeRepository.findAll().size();

        // Create the CalendarAttribute

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarAttributeMockMvc.perform(put("/api/calendar-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the CalendarAttribute in the database
        List<CalendarAttribute> calendarAttributeList = calendarAttributeRepository.findAll();
        assertThat(calendarAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCalendarAttribute() throws Exception {
        // Initialize the database
        calendarAttributeService.save(calendarAttribute);

        int databaseSizeBeforeDelete = calendarAttributeRepository.findAll().size();

        // Get the calendarAttribute
        restCalendarAttributeMockMvc.perform(delete("/api/calendar-attributes/{id}", calendarAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CalendarAttribute> calendarAttributeList = calendarAttributeRepository.findAll();
        assertThat(calendarAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalendarAttribute.class);
        CalendarAttribute calendarAttribute1 = new CalendarAttribute();
        calendarAttribute1.setId("id1");
        CalendarAttribute calendarAttribute2 = new CalendarAttribute();
        calendarAttribute2.setId(calendarAttribute1.getId());
        assertThat(calendarAttribute1).isEqualTo(calendarAttribute2);
        calendarAttribute2.setId("id2");
        assertThat(calendarAttribute1).isNotEqualTo(calendarAttribute2);
        calendarAttribute1.setId(null);
        assertThat(calendarAttribute1).isNotEqualTo(calendarAttribute2);
    }
}
