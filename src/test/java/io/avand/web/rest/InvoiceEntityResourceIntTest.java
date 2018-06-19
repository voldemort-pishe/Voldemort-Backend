package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.InvoiceEntity;
import io.avand.repository.InvoiceEntityRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.avand.web.rest.TestUtil.sameInstant;
import static io.avand.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.avand.domain.enumeration.SubscribeState;
import io.avand.domain.enumeration.PaymentType;
import io.avand.domain.enumeration.InvoiceStatus;
/**
 * Test class for the InvoiceEntityResource REST controller.
 *
 * @see InvoiceEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class InvoiceEntityResourceIntTest {

    private static final SubscribeState DEFAULT_SUBSCRIPTION = SubscribeState.MONTHLY;
    private static final SubscribeState UPDATED_SUBSCRIPTION = SubscribeState.ANNUALLY;

    private static final PaymentType DEFAULT_PAYMENT_TYPE = PaymentType.PASARGARD;
    private static final PaymentType UPDATED_PAYMENT_TYPE = PaymentType.SAMAN;

    private static final ZonedDateTime DEFAULT_PAYMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PAYMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final Integer DEFAULT_AMOUNT_WITH_TAX = 1;
    private static final Integer UPDATED_AMOUNT_WITH_TAX = 2;

    private static final InvoiceStatus DEFAULT_STATUS = InvoiceStatus.SUCCESS;
    private static final InvoiceStatus UPDATED_STATUS = InvoiceStatus.FAILED;

    @Autowired
    private InvoiceEntityRepository invoiceEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvoiceEntityMockMvc;

    private InvoiceEntity invoiceEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceEntityResource invoiceEntityResource = new InvoiceEntityResource(invoiceEntityRepository);
        this.restInvoiceEntityMockMvc = MockMvcBuilders.standaloneSetup(invoiceEntityResource)
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
    public static InvoiceEntity createEntity(EntityManager em) {
        InvoiceEntity invoiceEntity = new InvoiceEntity()
            .subscription(DEFAULT_SUBSCRIPTION)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .amount(DEFAULT_AMOUNT)
            .amountWithTax(DEFAULT_AMOUNT_WITH_TAX)
            .status(DEFAULT_STATUS);
        return invoiceEntity;
    }

    @Before
    public void initTest() {
        invoiceEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceEntity() throws Exception {
        int databaseSizeBeforeCreate = invoiceEntityRepository.findAll().size();

        // Create the InvoiceEntity
        restInvoiceEntityMockMvc.perform(post("/api/invoice-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceEntity)))
            .andExpect(status().isCreated());

        // Validate the InvoiceEntity in the database
        List<InvoiceEntity> invoiceEntityList = invoiceEntityRepository.findAll();
        assertThat(invoiceEntityList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceEntity testInvoiceEntity = invoiceEntityList.get(invoiceEntityList.size() - 1);
        assertThat(testInvoiceEntity.getSubscription()).isEqualTo(DEFAULT_SUBSCRIPTION);
        assertThat(testInvoiceEntity.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testInvoiceEntity.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testInvoiceEntity.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInvoiceEntity.getAmountWithTax()).isEqualTo(DEFAULT_AMOUNT_WITH_TAX);
        assertThat(testInvoiceEntity.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInvoiceEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceEntityRepository.findAll().size();

        // Create the InvoiceEntity with an existing ID
        invoiceEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceEntityMockMvc.perform(post("/api/invoice-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceEntity)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceEntity in the database
        List<InvoiceEntity> invoiceEntityList = invoiceEntityRepository.findAll();
        assertThat(invoiceEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInvoiceEntities() throws Exception {
        // Initialize the database
        invoiceEntityRepository.saveAndFlush(invoiceEntity);

        // Get all the invoiceEntityList
        restInvoiceEntityMockMvc.perform(get("/api/invoice-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].subscription").value(hasItem(DEFAULT_SUBSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(sameInstant(DEFAULT_PAYMENT_DATE))))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].amountWithTax").value(hasItem(DEFAULT_AMOUNT_WITH_TAX)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getInvoiceEntity() throws Exception {
        // Initialize the database
        invoiceEntityRepository.saveAndFlush(invoiceEntity);

        // Get the invoiceEntity
        restInvoiceEntityMockMvc.perform(get("/api/invoice-entities/{id}", invoiceEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceEntity.getId().intValue()))
            .andExpect(jsonPath("$.subscription").value(DEFAULT_SUBSCRIPTION.toString()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.paymentDate").value(sameInstant(DEFAULT_PAYMENT_DATE)))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.amountWithTax").value(DEFAULT_AMOUNT_WITH_TAX))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceEntity() throws Exception {
        // Get the invoiceEntity
        restInvoiceEntityMockMvc.perform(get("/api/invoice-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceEntity() throws Exception {
        // Initialize the database
        invoiceEntityRepository.saveAndFlush(invoiceEntity);
        int databaseSizeBeforeUpdate = invoiceEntityRepository.findAll().size();

        // Update the invoiceEntity
        InvoiceEntity updatedInvoiceEntity = invoiceEntityRepository.findOne(invoiceEntity.getId());
        // Disconnect from session so that the updates on updatedInvoiceEntity are not directly saved in db
        em.detach(updatedInvoiceEntity);
        updatedInvoiceEntity
            .subscription(UPDATED_SUBSCRIPTION)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .amount(UPDATED_AMOUNT)
            .amountWithTax(UPDATED_AMOUNT_WITH_TAX)
            .status(UPDATED_STATUS);

        restInvoiceEntityMockMvc.perform(put("/api/invoice-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoiceEntity)))
            .andExpect(status().isOk());

        // Validate the InvoiceEntity in the database
        List<InvoiceEntity> invoiceEntityList = invoiceEntityRepository.findAll();
        assertThat(invoiceEntityList).hasSize(databaseSizeBeforeUpdate);
        InvoiceEntity testInvoiceEntity = invoiceEntityList.get(invoiceEntityList.size() - 1);
        assertThat(testInvoiceEntity.getSubscription()).isEqualTo(UPDATED_SUBSCRIPTION);
        assertThat(testInvoiceEntity.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testInvoiceEntity.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testInvoiceEntity.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInvoiceEntity.getAmountWithTax()).isEqualTo(UPDATED_AMOUNT_WITH_TAX);
        assertThat(testInvoiceEntity.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceEntity() throws Exception {
        int databaseSizeBeforeUpdate = invoiceEntityRepository.findAll().size();

        // Create the InvoiceEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInvoiceEntityMockMvc.perform(put("/api/invoice-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceEntity)))
            .andExpect(status().isCreated());

        // Validate the InvoiceEntity in the database
        List<InvoiceEntity> invoiceEntityList = invoiceEntityRepository.findAll();
        assertThat(invoiceEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInvoiceEntity() throws Exception {
        // Initialize the database
        invoiceEntityRepository.saveAndFlush(invoiceEntity);
        int databaseSizeBeforeDelete = invoiceEntityRepository.findAll().size();

        // Get the invoiceEntity
        restInvoiceEntityMockMvc.perform(delete("/api/invoice-entities/{id}", invoiceEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceEntity> invoiceEntityList = invoiceEntityRepository.findAll();
        assertThat(invoiceEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceEntity.class);
        InvoiceEntity invoiceEntity1 = new InvoiceEntity();
        invoiceEntity1.setId(1L);
        InvoiceEntity invoiceEntity2 = new InvoiceEntity();
        invoiceEntity2.setId(invoiceEntity1.getId());
        assertThat(invoiceEntity1).isEqualTo(invoiceEntity2);
        invoiceEntity2.setId(2L);
        assertThat(invoiceEntity1).isNotEqualTo(invoiceEntity2);
        invoiceEntity1.setId(null);
        assertThat(invoiceEntity1).isNotEqualTo(invoiceEntity2);
    }
}
