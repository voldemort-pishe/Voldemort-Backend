package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.CompanyEntity;
import hr.pishe.domain.entity.jpa.CompanyPipelineEntity;
import hr.pishe.repository.jpa.CompanyPipelineRepository;
import hr.pishe.repository.jpa.CompanyRepository;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.CompanyPipelineService;
import hr.pishe.service.dto.CompanyPipelineDTO;
import hr.pishe.service.mapper.CompanyPipelineMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyPipelineServiceImpl implements CompanyPipelineService {

    private final Logger logger = LoggerFactory.getLogger(CompanyPipelineServiceImpl.class);

    private final CompanyPipelineRepository companyPipelineRepository;

    private final CompanyPipelineMapper companyPipelineMapper;

    private final CompanyRepository companyRepository;

    private final SecurityUtils securityUtils;

    public CompanyPipelineServiceImpl(CompanyPipelineRepository companyPipelineRepository,
                                      CompanyPipelineMapper companyPipelineMapper,
                                      CompanyRepository companyRepository,
                                      SecurityUtils securityUtils) {
        this.companyPipelineRepository = companyPipelineRepository;
        this.companyPipelineMapper = companyPipelineMapper;
        this.companyRepository = companyRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public CompanyPipelineDTO save(CompanyPipelineDTO companyPipelineDTO) throws NotFoundException {

        logger.debug("Request to company pipeline service to save a pipeline object : {}", companyPipelineDTO);

        Optional<CompanyEntity> companyEntityOp = companyRepository.findById(securityUtils.getCurrentCompanyId());

        if (companyEntityOp.isPresent()) {
            CompanyEntity companyEntity = companyEntityOp.get();
            CompanyPipelineEntity companyPipelineEntity = companyPipelineMapper.toEntity(companyPipelineDTO);
            companyPipelineEntity.setCompany(companyEntity);
            companyPipelineEntity = companyPipelineRepository.save(companyPipelineEntity);
            return companyPipelineMapper.toDto(companyPipelineEntity);
        } else {
            throw new NotFoundException("Sorry the company you're claiming to own does not exist!");
        }
    }

    @Override
    public Page<CompanyPipelineDTO> findAll(Pageable pageable) throws NotFoundException {
        logger.debug("Request to company pipeline service to get all by user id");
        return companyPipelineRepository
            .findAllByCompany_Id(securityUtils.getCurrentCompanyId(), pageable)
            .map(companyPipelineMapper::toDto);
    }

    @Override
    public CompanyPipelineDTO findOne(Long id) throws NotFoundException {
        logger.debug("Request to company pipeline service to find one : {}", id);

        CompanyPipelineEntity companyPipelineEntity =
            companyPipelineRepository.findByIdAndCompany_Id(id, securityUtils.getCurrentCompanyId());
        if (companyPipelineEntity != null) {
            return companyPipelineMapper.toDto(companyPipelineEntity);
        } else {
            throw new NotFoundException("Pipeline Not Found");
        }
    }

    @Override
    public void delete(Long id) {
        logger.debug("Request to company pipeline service to delete : {}", id);
        CompanyPipelineEntity companyPipelineEntity = companyPipelineRepository.findOne(id);
        companyPipelineRepository.delete(companyPipelineEntity.getId());
    }
}
