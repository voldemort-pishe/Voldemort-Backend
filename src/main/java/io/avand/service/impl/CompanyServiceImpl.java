package io.avand.service.impl;

import io.avand.domain.entity.jpa.CompanyContactEntity;
import io.avand.domain.entity.jpa.CompanyEntity;
import io.avand.domain.entity.jpa.FileEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.repository.jpa.CompanyContactRepository;
import io.avand.repository.jpa.CompanyRepository;
import io.avand.repository.jpa.FileRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.CompanyMemberService;
import io.avand.service.CompanyService;
import io.avand.service.dto.CompanyDTO;
import io.avand.service.dto.CompanyMemberDTO;
import io.avand.service.mapper.CompanyMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final FileRepository fileRepository;
    private final CompanyMemberService companyMemberService;
    private final CompanyContactRepository companyContactRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              CompanyMapper companyMapper,
                              UserRepository userRepository,
                              SecurityUtils securityUtils,
                              FileRepository fileRepository,
                              CompanyMemberService companyMemberService,
                              CompanyContactRepository companyContactRepository) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
        this.fileRepository = fileRepository;
        this.companyMemberService = companyMemberService;
        this.companyContactRepository = companyContactRepository;
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) throws NotFoundException {
        log.debug("Request to save company : {}", companyDTO);
        Long userId = securityUtils.getCurrentUserId();
        UserEntity userEntity = userRepository.findOne(userId);
        CompanyEntity companyEntity = companyMapper.toEntity(companyDTO);
        companyEntity.setUser(userEntity);
        FileEntity fileEntity = fileRepository.getOne(companyDTO.getFileId());
        if (fileEntity != null) {

            CompanyContactEntity companyContactEntity = companyEntity.getContact();
            companyContactEntity = companyContactRepository.save(companyContactEntity);

            companyEntity.setFile(fileEntity);
            companyEntity.setContact(companyContactEntity);
            companyEntity = companyRepository.save(companyEntity);

            if (companyDTO.getId() == null) {
                CompanyMemberDTO companyMemberDTO = new CompanyMemberDTO();
                companyMemberDTO.setCompanyId(companyEntity.getId());
                companyMemberDTO.setUserEmail(userEntity.getEmail());
                companyMemberService.save(companyMemberDTO);
            }

            return companyMapper.toDto(companyEntity);
        } else {
            throw new NotFoundException("File Not Found By Id");
        }
    }

    @Override
    public CompanyDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find company by id : {}", id);
        Optional<CompanyEntity> companyEntity = companyRepository.findById(id);
        if (companyEntity.isPresent()) {
            return companyMapper.toDto(companyEntity.get());
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
    public void delete(Long id) throws NotFoundException {
        CompanyEntity companyEntity = companyRepository.findOne(id);
        if (companyEntity != null) {
            companyRepository.delete(companyEntity);
        } else {
            throw new NotFoundException("Company Not Found By Id");
        }
    }
}
