package io.avand.service.impl;

import io.avand.domain.entity.jpa.CompanyEntity;
import io.avand.domain.entity.jpa.CompanyPipelineEntity;
import io.avand.repository.jpa.CompanyPipelineRepository;
import io.avand.repository.jpa.CompanyRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.CompanyPipelineService;
import io.avand.service.dto.CompanyPipelineDTO;
import io.avand.service.mapper.CompanyPipelineMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

        CompanyEntity companyEntity = companyRepository.findOne(companyPipelineDTO.getCompanyId());

        if (companyEntity != null) {
            if (companyEntity.getUser().getId().equals(securityUtils.getCurrentUserId())) {
                CompanyPipelineEntity companyPipelineEntity = companyPipelineMapper.toEntity(companyPipelineDTO);
                companyPipelineEntity.setCompany(companyEntity);
                companyPipelineEntity = companyPipelineRepository.save(companyPipelineEntity);
                return companyPipelineMapper.toDto(companyPipelineEntity);
            } else {
                throw new SecurityException("Sorry you don't have right permissions to save this!");
            }
        } else {
            throw new NotFoundException("Sorry the company you're claiming to own does not exist!");
        }
    }

    @Override
    public CompanyPipelineDTO update(CompanyPipelineDTO companyPipelineDTO) throws NotFoundException {

        logger.debug("Request to company pipeline service to update a pipeline object : {}", companyPipelineDTO);

        CompanyPipelineEntity companyPipelineEntity = companyPipelineRepository.findOne(companyPipelineDTO.getId());
        if (companyPipelineEntity.getCompany().getUser().getId().equals(securityUtils.getCurrentUserId())) {
            companyPipelineEntity.setTitle(companyPipelineDTO.getTitle());
            companyPipelineEntity.setWeight(companyPipelineDTO.getWeight());
            companyPipelineEntity = companyPipelineRepository.save(companyPipelineEntity);
            return companyPipelineMapper.toDto(companyPipelineEntity);
        } else {
            throw new SecurityException("Sorry you do not have the right permission to update this!");
        }
    }

    @Override
    public List<CompanyPipelineDTO> getAllByCompanyId(Long companyId) throws NotFoundException {
        logger.debug("Request to company pipeline service to get all by user id");

        CompanyEntity companyEntity = companyRepository.findOne(companyId);
        List<CompanyPipelineEntity> companies = companyPipelineRepository.findAllByCompany(companyEntity);

        if (companyEntity.getUser().getId().equals(securityUtils.getCurrentUserId())) {
            return companyPipelineMapper.toDto(companies);
        } else {
            throw new SecurityException("Sorry you do not have the right permission to get this company pipelines!");
        }
    }

    @Override
    public CompanyPipelineDTO findOne(Long id) throws NotFoundException {
        logger.debug("Request to company pipeline service to find one : {}", id);

        CompanyEntity companyEntity = companyRepository.findOne(id);

        if (companyEntity.getUser().getId().equals(securityUtils.getCurrentUserId())) {
            CompanyPipelineEntity companyPipelineEntity = companyPipelineRepository.findOne(id);
            return companyPipelineMapper.toDto(companyPipelineEntity);
        } else {
            throw new SecurityException("Sorry you do not have the right permission to get this!");
        }
    }

    @Override
    public void delete(Long  id) throws NotFoundException {
        logger.debug("Request to company pipeline service to delete : {}", id);

        CompanyPipelineEntity companyPipelineEntity = companyPipelineRepository.findOne(id);
        CompanyEntity companyEntity = companyRepository.findOne(companyPipelineEntity.getCompany().getId());
        if (companyEntity.getUser().getId().equals(securityUtils.getCurrentUserId())) {
            companyPipelineRepository.delete(companyPipelineEntity.getId());
        } else {
            throw new SecurityException("Sorry you do not have the right permission to delete this!");
        }
    }
}
