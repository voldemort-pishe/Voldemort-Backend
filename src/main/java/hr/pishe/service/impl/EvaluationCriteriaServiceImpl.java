package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.CompanyEntity;
import hr.pishe.domain.entity.jpa.EvaluationCriteriaEntity;
import hr.pishe.repository.jpa.CompanyRepository;
import hr.pishe.repository.jpa.EvaluationCriteriaRepository;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.EvaluationCriteriaService;
import hr.pishe.service.dto.EvaluationCriteriaDTO;
import hr.pishe.service.mapper.EvaluationCriteriaMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EvaluationCriteriaServiceImpl implements EvaluationCriteriaService {

    private final Logger log = LoggerFactory.getLogger(EvaluationCriteriaServiceImpl.class);
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final EvaluationCriteriaMapper evaluationCriteriaMapper;
    private final CompanyRepository companyRepository;
    private final SecurityUtils securityUtils;

    public EvaluationCriteriaServiceImpl(EvaluationCriteriaRepository evaluationCriteriaRepository,
                                         EvaluationCriteriaMapper evaluationCriteriaMapper,
                                         CompanyRepository companyRepository,
                                         SecurityUtils securityUtils) {
        this.evaluationCriteriaRepository = evaluationCriteriaRepository;
        this.evaluationCriteriaMapper = evaluationCriteriaMapper;
        this.companyRepository = companyRepository;
        this.securityUtils = securityUtils;
    }


    @Override
    public EvaluationCriteriaDTO save(EvaluationCriteriaDTO evaluationCriteriaDTO) throws NotFoundException {
        log.debug("Request to save evaluation criteria : {}", evaluationCriteriaDTO);
        Optional<CompanyEntity> companyEntity = companyRepository.findById(evaluationCriteriaDTO.getCompanyId());
        if (companyEntity.isPresent()) {
            EvaluationCriteriaEntity evaluationCriteriaEntity = evaluationCriteriaMapper.toEntity(evaluationCriteriaDTO);
            evaluationCriteriaEntity.setCompany(companyEntity.get());
            evaluationCriteriaEntity = evaluationCriteriaRepository.save(evaluationCriteriaEntity);
            return evaluationCriteriaMapper.toDto(evaluationCriteriaEntity);
        } else {
            throw new NotFoundException("company not Found");
        }
    }

    @Override
    public Optional<EvaluationCriteriaDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find evaluation criteria by id : {}", id);
        return evaluationCriteriaRepository
            .findByIdAndCompany_Id(id, securityUtils.getCurrentCompanyId())
            .map(evaluationCriteriaMapper::toDto);
    }

    @Override
    public Page<EvaluationCriteriaDTO> findAll(Pageable pageable) throws NotFoundException {
        log.debug("Request to find all evaluation criteria");
        return evaluationCriteriaRepository
            .findAllByCompany_Id(securityUtils.getCurrentCompanyId(), pageable)
            .map(evaluationCriteriaMapper::toDto);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete evaluation criteria by id : {}", id);
        EvaluationCriteriaEntity evaluationCriteriaEntity = evaluationCriteriaRepository.findOne(id);
        if (evaluationCriteriaEntity != null) {
            evaluationCriteriaRepository.delete(evaluationCriteriaEntity);
        } else {
            throw new NotFoundException("Evaluation Criteria Not Found");
        }
    }
}
