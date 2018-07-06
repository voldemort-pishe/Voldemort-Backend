package io.avand.service.impl;

import io.avand.domain.CompanyEntity;
import io.avand.domain.UserEntity;
import io.avand.repository.CompanyRepository;
import io.avand.repository.UserRepository;
import io.avand.service.CompanyService;
import io.avand.service.dto.CompanyDTO;
import io.avand.service.mapper.CompanyMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserRepository userRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              CompanyMapper companyMapper,
                              UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.userRepository = userRepository;
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) throws NotFoundException {
        log.debug("Request to save company : {}", companyDTO);
        UserEntity user = userRepository.findOne(companyDTO.getUserId());
        if (user == null) {
            throw new NotFoundException("User Not Available");
        } else {
            CompanyEntity companyEntity = companyMapper.toEntity(companyDTO);
            companyEntity.setUser(userRepository.findOne(companyDTO.getUserId()));
            companyEntity = companyRepository.save(companyEntity);
            return companyMapper.toDto(companyEntity);
        }
    }

    @Override
    public CompanyDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find company by id : {}", id);
        CompanyEntity companyEntity = companyRepository.findOne(id);
        if (companyEntity != null) {
            return companyMapper.toDto(companyEntity);
        } else {
            throw new NotFoundException("Company Not Found By Id");
        }
    }

    @Override
    public List<CompanyDTO> findAll() {
        log.debug("Request to find all company");
        return companyRepository.findAll()
            .stream()
            .map(companyMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        companyRepository.delete(id);
    }
}
