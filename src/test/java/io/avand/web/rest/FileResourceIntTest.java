package io.avand.web.rest;

import io.avand.VoldemortApp;

import io.avand.domain.FileEntity;
import io.avand.domain.CandidateEntity;
import io.avand.repository.FileRepository;
import io.avand.service.FileService;
import io.avand.service.StorageService;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.FileDTO;
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
 * Test class for the FileEntityResource REST controller.
 *
 * @see FileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoldemortApp.class)
public class FileResourceIntTest {

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILETYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILETYPE = "BBBBBBBBBB";

    @Autowired
    private FileService fileService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFileEntityMockMvc;

    private FileDTO fileEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileResource fileResource = new FileResource(storageService, fileService);
        this.restFileEntityMockMvc = MockMvcBuilders.standaloneSetup(fileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileDTO createEntity(EntityManager em) {
        FileDTO fileEntity = new FileDTO();
        fileEntity.setFilename(DEFAULT_FILENAME);
        fileEntity.setFileType(DEFAULT_FILETYPE);
        // Add required entity
        CandidateDTO candidate = CandidateResourceIntTest.createEntity(em);
        em.persist(candidate);
        em.flush();
//        fileEntity.setCandidate(candidate);
        return fileEntity;
    }

    @Before
    public void initTest() {
        fileEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileEntity() throws Exception {
        int databaseSizeBeforeCreate = fileService.findAll().size();

        // Create the FileEntity
        restFileEntityMockMvc.perform(post("/api/file")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileEntity)))
            .andExpect(status().isCreated());

        // Validate the FileEntity in the database
        List<FileDTO> fileEntityList = fileService.findAll();
        assertThat(fileEntityList).hasSize(databaseSizeBeforeCreate + 1);
        FileDTO testFileEntity = fileEntityList.get(fileEntityList.size() - 1);
        assertThat(testFileEntity.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testFileEntity.getFileType()).isEqualTo(DEFAULT_FILETYPE);
    }

    @Test
    @Transactional
    public void createFileEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileService.findAll().size();

        // Create the FileEntity with an existing ID
        fileEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileEntityMockMvc.perform(post("/api/file")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileEntity)))
            .andExpect(status().isBadRequest());

        // Validate the FileEntity in the database
        List<FileDTO> fileEntityList = fileService.findAll();
        assertThat(fileEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFileEntities() throws Exception {
        // Initialize the database
        fileService.save(fileEntity);

        // Get all the fileEntityList
        restFileEntityMockMvc.perform(get("/api/file?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME.toString())))
            .andExpect(jsonPath("$.[*].filetype").value(hasItem(DEFAULT_FILETYPE.toString())));
    }

    @Test
    @Transactional
    public void getFileEntity() throws Exception {
        // Initialize the database
        fileService.save(fileEntity);

        // Get the fileEntity
        restFileEntityMockMvc.perform(get("/api/file/{id}", fileEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileEntity.getId().intValue()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME.toString()))
            .andExpect(jsonPath("$.filetype").value(DEFAULT_FILETYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFileEntity() throws Exception {
        // Get the fileEntity
        restFileEntityMockMvc.perform(get("/api/file/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileEntity() throws Exception {
        // Initialize the database
        fileService.save(fileEntity);
        int databaseSizeBeforeUpdate = fileService.findAll().size();

        // Update the fileEntity
        FileDTO updatedFileEntity = fileService.findById(fileEntity.getId());
        // Disconnect from session so that the updates on updatedFileEntity are not directly saved in db
        em.detach(updatedFileEntity);
        updatedFileEntity.setFilename(UPDATED_FILENAME);
        updatedFileEntity.setFileType(UPDATED_FILETYPE);

        restFileEntityMockMvc.perform(put("/api/file")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileEntity)))
            .andExpect(status().isOk());

        // Validate the FileEntity in the database
        List<FileDTO> fileEntityList = fileService.findAll();
        assertThat(fileEntityList).hasSize(databaseSizeBeforeUpdate);
        FileDTO testFileEntity = fileEntityList.get(fileEntityList.size() - 1);
        assertThat(testFileEntity.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testFileEntity.getFileType()).isEqualTo(UPDATED_FILETYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingFileEntity() throws Exception {
        int databaseSizeBeforeUpdate = fileService.findAll().size();

        // Create the FileEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFileEntityMockMvc.perform(put("/api/file")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileEntity)))
            .andExpect(status().isCreated());

        // Validate the FileEntity in the database
        List<FileDTO> fileEntityList = fileService.findAll();
        assertThat(fileEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFileEntity() throws Exception {
        // Initialize the database
        fileService.save(fileEntity);
        int databaseSizeBeforeDelete = fileService.findAll().size();

        // Get the fileEntity
        restFileEntityMockMvc.perform(delete("/api/file/{id}", fileEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FileDTO> fileEntityList = fileService.findAll();
        assertThat(fileEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileEntity.class);
        FileEntity fileEntity1 = new FileEntity();
        fileEntity1.setId(1L);
        FileEntity fileEntity2 = new FileEntity();
        fileEntity2.setId(fileEntity1.getId());
        assertThat(fileEntity1).isEqualTo(fileEntity2);
        fileEntity2.setId(2L);
        assertThat(fileEntity1).isNotEqualTo(fileEntity2);
        fileEntity1.setId(null);
        assertThat(fileEntity1).isNotEqualTo(fileEntity2);
    }
}
