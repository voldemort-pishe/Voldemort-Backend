package io.avand.service.impl;

import io.avand.domain.entity.jpa.CompanyEntity;
import io.avand.domain.entity.jpa.FileEntity;
import io.avand.repository.jpa.CompanyRepository;
import io.avand.repository.jpa.FileRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.CompanyService;
import io.avand.service.dto.CompanyDTO;
import io.avand.service.mapper.CompanyMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final FileRepository fileRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              CompanyMapper companyMapper,
                              UserRepository userRepository,
                              SecurityUtils securityUtils,
                              FileRepository fileRepository) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
        this.fileRepository = fileRepository;
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) throws NotFoundException {
        log.debug("Request to save company : {}", companyDTO);
        Long userId = securityUtils.getCurrentUserId();
        CompanyEntity companyEntity = companyMapper.toEntity(companyDTO);
        companyEntity.setUser(userRepository.findOne(userId));
        FileEntity fileEntity = fileRepository.getOne(companyDTO.getFileId());
        if (fileEntity != null) {
            companyEntity.setFile(fileEntity);
            companyEntity = companyRepository.save(companyEntity);
            return companyMapper.toDto(companyEntity);
        } else {
            throw new NotFoundException("File Not Found By Id");
        }
    }

    @Override
    public CompanyDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find company by id : {}", id);
        CompanyEntity companyEntity = companyRepository.findOne(id);
        if (companyEntity != null) {
            if (companyEntity.getUser().getId().equals(securityUtils.getCurrentUserId())) {
                return companyMapper.toDto(companyEntity);
            } else {
                throw new SecurityException("You Don't have access to find this company");
            }
        } else {
            throw new NotFoundException("Company Not Found By Id");
        }
    }


    @Override
    public CompanyDTO findBySubDomain(String subDomain) throws NotFoundException {
        log.debug("Request to find company by subDomain : {}", subDomain);
        CompanyEntity companyEntity = companyRepository.findBySubDomain(subDomain);
        if (companyEntity != null) {
            return companyMapper.toDto(companyEntity);
        } else {
            throw new NotFoundException("Company Not Available");
        }
    }

    @Override
    public Page<CompanyDTO> findAll(Pageable pageable) throws NotFoundException {
        log.debug("Request to find all company");
        return companyRepository.findAllByUser_Id(securityUtils.getCurrentUserId(), pageable)
            .map(companyMapper::toDto);
    }

    @Override
    public List<CompanyDTO> findAll() throws NotFoundException {
        log.debug("Request to find all company");
        return companyRepository.findAllByUser_Id(securityUtils.getCurrentUserId())
            .stream()
            .map(companyMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Long userId = securityUtils.getCurrentUserId();
        CompanyEntity companyEntity = companyRepository.findOne(id);
        if (companyEntity != null) {
            if (companyEntity.getUser().getId().equals(userId)) {
                companyRepository.delete(companyEntity);
            } else {
                throw new SecurityException("You Don't have access to delete this company");
            }
        } else {
            throw new NotFoundException("Company Not Found By Id");
        }
    }
}
