package hr.pishe.service.impl;

import hr.pishe.domain.entity.file.ProvinceEntity;
import hr.pishe.repository.file.ProvinceRepository;
import hr.pishe.service.ProvinceService;
import hr.pishe.service.dto.ProvinceDTO;
import hr.pishe.service.mapper.ProvinceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    private final Logger log = LoggerFactory.getLogger(ProvinceServiceImpl.class);
    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;

    public ProvinceServiceImpl(ProvinceRepository provinceRepository,
                               ProvinceMapper provinceMapper) {
        this.provinceRepository = provinceRepository;
        this.provinceMapper = provinceMapper;
    }

    @Override
    public List<ProvinceDTO> findAll() {
        log.debug("Request to find all province");
        return provinceRepository.findAll()
            .stream()
            .map(provinceMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public ProvinceDTO findByName(String name) {
        log.debug("Request to find province by name : {}", name);
        ProvinceEntity provinceEntity = provinceRepository.findByName(name);
        return provinceMapper.toDto(provinceEntity);
    }
}
