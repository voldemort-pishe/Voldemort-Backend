package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.entity.jpa.PaymentTransactionEntity;
import io.avand.repository.jpa.PaymentTransactionRepository;
import io.avand.web.rest.errors.ExceptionTranslator;

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

import static io.avand.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PaymentTransactionEntityResource REST controller.
 *
 * @see PaymentTransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class PaymentTransactionResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_REFRENCE_ID = 1L;
    private static final Long UPDATED_REFRENCE_ID = 2L;

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentTransactionEntityMockMvc;

    private PaymentTransactionEntity paymentTransactionEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentTransactionResource paymentTransactionResource = new PaymentTransactionResource(paymentTransactionRepository);
        this.restPaymentTransactionEntityMockMvc = MockMvcBuilders.standaloneSetup(paymentTransactionResource)
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
    public static PaymentTransactionEntity createEntity(EntityManager em) {
        PaymentTransactionEntity paymentTransactionEntity = new PaymentTransactionEntity()
            .userId(DEFAULT_USER_ID)
            .refrenceId(DEFAULT_REFRENCE_ID)
            .amount(DEFAULT_AMOUNT);
        return paymentTransactionEntity;
    }

    @Before
    public void initTest() {
        paymentTransactionEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentTransactionEntity() throws Exception {
        int databaseSizeBeforeCreate = paymentTransactionRepository.findAll().size();

        // Create the PaymentTransactionEntity
        restPaymentTransactionEntityMockMvc.perform(post("/api/payment-transaction-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionEntity)))
            .andExpect(status().isCreated());

        // Validate the PaymentTransactionEntity in the database
        List<PaymentTransactionEntity> paymentTransactionEntityList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionEntityList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentTransactionEntity testPaymentTransactionEntity = paymentTransactionEntityList.get(paymentTransactionEntityList.size() - 1);
        assertThat(testPaymentTransactionEntity.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPaymentTransactionEntity.getRefrenceId()).isEqualTo(DEFAULT_REFRENCE_ID);
        assertThat(testPaymentTransactionEntity.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createPaymentTransactionEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentTransactionRepository.findAll().size();

        // Create the PaymentTransactionEntity with an existing ID
        paymentTransactionEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTransactionEntityMockMvc.perform(post("/api/payment-transaction-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionEntity)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentTransactionEntity in the database
        List<PaymentTransactionEntity> paymentTransactionEntityList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaymentTransactionEntities() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransactionEntity);

        // Get all the paymentTransactionEntityList
        restPaymentTransactionEntityMockMvc.perform(get("/api/payment-transaction-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentTransactionEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].refrenceId").value(hasItem(DEFAULT_REFRENCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)));
    }

    @Test
    @Transactional
    public void getPaymentTransactionEntity() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransactionEntity);

        // Get the paymentTransactionEntity
        restPaymentTransactionEntityMockMvc.perform(get("/api/payment-transaction-entities/{id}", paymentTransactionEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentTransactionEntity.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.refrenceId").value(DEFAULT_REFRENCE_ID.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentTransactionEntity() throws Exception {
        // Get the paymentTransactionEntity
        restPaymentTransactionEntityMockMvc.perform(get("/api/payment-transaction-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentTransactionEntity() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransactionEntity);
        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();

        // Update the paymentTransactionEntity
        PaymentTransactionEntity updatedPaymentTransactionEntity = paymentTransactionRepository.findOne(paymentTransactionEntity.getId());
        // Disconnect from session so that the updates on updatedPaymentTransactionEntity are not directly saved in db
        em.detach(updatedPaymentTransactionEntity);
        updatedPaymentTransactionEntity
            .userId(UPDATED_USER_ID)
            .refrenceId(UPDATED_REFRENCE_ID)
            .amount(UPDATED_AMOUNT);

        restPaymentTransactionEntityMockMvc.perform(put("/api/payment-transaction-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentTransactionEntity)))
            .andExpect(status().isOk());

        // Validate the PaymentTransactionEntity in the database
        List<PaymentTransactionEntity> paymentTransactionEntityList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionEntityList).hasSize(databaseSizeBeforeUpdate);
        PaymentTransactionEntity testPaymentTransactionEntity = paymentTransactionEntityList.get(paymentTransactionEntityList.size() - 1);
        assertThat(testPaymentTransactionEntity.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPaymentTransactionEntity.getRefrenceId()).isEqualTo(UPDATED_REFRENCE_ID);
        assertThat(testPaymentTransactionEntity.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentTransactionEntity() throws Exception {
        int databaseSizeBeforeUpdate = paymentTransactionRepository.findAll().size();

        // Create the PaymentTransactionEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentTransactionEntityMockMvc.perform(put("/api/payment-transaction-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTransactionEntity)))
            .andExpect(status().isCreated());

        // Validate the PaymentTransactionEntity in the database
        List<PaymentTransactionEntity> paymentTransactionEntityList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaymentTransactionEntity() throws Exception {
        // Initialize the database
        paymentTransactionRepository.saveAndFlush(paymentTransactionEntity);
        int databaseSizeBeforeDelete = paymentTransactionRepository.findAll().size();

        // Get the paymentTransactionEntity
        restPaymentTransactionEntityMockMvc.perform(delete("/api/payment-transaction-entities/{id}", paymentTransactionEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentTransactionEntity> paymentTransactionEntityList = paymentTransactionRepository.findAll();
        assertThat(paymentTransactionEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentTransactionEntity.class);
        PaymentTransactionEntity paymentTransactionEntity1 = new PaymentTransactionEntity();
        paymentTransactionEntity1.setId(1L);
        PaymentTransactionEntity paymentTransactionEntity2 = new PaymentTransactionEntity();
        paymentTransactionEntity2.setId(paymentTransactionEntity1.getId());
        assertThat(paymentTransactionEntity1).isEqualTo(paymentTransactionEntity2);
        paymentTransactionEntity2.setId(2L);
        assertThat(paymentTransactionEntity1).isNotEqualTo(paymentTransactionEntity2);
        paymentTransactionEntity1.setId(null);
        assertThat(paymentTransactionEntity1).isNotEqualTo(paymentTransactionEntity2);
    }
}
