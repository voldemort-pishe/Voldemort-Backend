package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.CandidateEntity;
import hr.pishe.domain.entity.jpa.CandidateEvaluationCriteriaEntity;
import hr.pishe.domain.entity.jpa.EvaluationCriteriaEntity;
import hr.pishe.repository.jpa.CandidateEvaluationCriteriaRepository;
import hr.pishe.repository.jpa.CandidateRepository;
import hr.pishe.repository.jpa.EvaluationCriteriaRepository;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.CandidateEvaluationCriteriaService;
import hr.pishe.service.dto.CandidateEvaluationCriteriaDTO;
import hr.pishe.service.mapper.CandidateEvaluationCriteriaMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateEvaluationCriteriaServiceImpl implements CandidateEvaluationCriteriaService {

    private final Logger log = LoggerFactory.getLogger(CandidateEvaluationCriteriaServiceImpl.class);
    private final CandidateEvaluationCriteriaRepository candidateEvaluationCriteriaRepository;
    private final CandidateEvaluationCriteriaMapper candidateEvaluationCriteriaMapper;
    private final CandidateRepository candidateRepository;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final SecurityUtils securityUtils;

    public CandidateEvaluationCriteriaServiceImpl(CandidateEvaluationCriteriaRepository candidateEvaluationCriteriaRepository,
                                                  CandidateEvaluationCriteriaMapper candidateEvaluationCriteriaMapper,
                                                  CandidateRepository candidateRepository,
                                                  EvaluationCriteriaRepository evaluationCriteriaRepository,
                                                  SecurityUtils securityUtils) {
        this.candidateEvaluationCriteriaRepository = candidateEvaluationCriteriaRepository;
        this.candidateEvaluationCriteriaMapper = candidateEvaluationCriteriaMapper;
        this.candidateRepository = candidateRepository;
        this.evaluationCriteriaRepository = evaluationCriteriaRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public CandidateEvaluationCriteriaDTO save(CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO) throws NotFoundException {
        log.debug("Request to save candidate evaluation criteria : {}", candidateEvaluationCriteriaDTO);
        if (candidateEvaluationCriteriaDTO.getId() != null) {
            CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity = candidateEvaluationCriteriaRepository.findOne(candidateEvaluationCriteriaDTO.getId());
            if (candidateEvaluationCriteriaEntity != null) {
                candidateEvaluationCriteriaEntity.setUserComment(candidateEvaluationCriteriaDTO.getUserComment());
                candidateEvaluationCriteriaEntity = candidateEvaluationCriteriaRepository.save(candidateEvaluationCriteriaEntity);
                return candidateEvaluationCriteriaMapper.toDto(candidateEvaluationCriteriaEntity);
            } else {
                throw new NotFoundException("Candidate Evaluation Criteria Not Found");
            }
        } else {
            if (!candidateEvaluationCriteriaRepository.existsByUserIdAndCandidate_Id(securityUtils.getCurrentUserId(), candidateEvaluationCriteriaDTO.getCandidateId())) {
                CandidateEntity candidateEntity = candidateRepository.findOne(candidateEvaluationCriteriaDTO.getCandidateId());
                if (candidateEntity != null) {

                    EvaluationCriteriaEntity evaluationCriteriaEntity =
                        evaluationCriteriaRepository.findOne(candidateEvaluationCriteriaDTO.getEvaluationCriteriaId());

                    if (evaluationCriteriaEntity != null) {

                        CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity =
                            candidateEvaluationCriteriaMapper.toEntity(candidateEvaluationCriteriaDTO);
                        candidateEvaluationCriteriaEntity.setCandidate(candidateEntity);
                        candidateEvaluationCriteriaEntity.setUserId(securityUtils.getCurrentUserId());
                        candidateEvaluationCriteriaEntity.setEvaluationCriteriaId(evaluationCriteriaEntity.getId());
                        candidateEvaluationCriteriaEntity =
                            candidateEvaluationCriteriaRepository.save(candidateEvaluationCriteriaEntity);
                        return candidateEvaluationCriteriaMapper.toDto(candidateEvaluationCriteriaEntity);

                    } else {
                        throw new NotFoundException("Evaluation Criteria Not Found");
                    }
                } else {
                    throw new NotFoundException("Candidate Not Found");
                }
            } else {
                throw new NotFoundException("Criteria Available From You For This Candidate");
            }
        }
    }

    @Override
    public Optional<CandidateEvaluationCriteriaDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find candidate evaluation criteria by id : {}", id);
        return candidateEvaluationCriteriaRepository
            .findByIdAndCandidate_Job_Company_Id(id, securityUtils.getCurrentCompanyId())
            .map(candidateEvaluationCriteriaMapper::toDto);
    }

    @Override
    public Page<CandidateEvaluationCriteriaDTO> findAllByCandidateId(Long candidateId, Pageable pageable) throws NotFoundException {
        log.debug("Request to find all candidate evaluation criteria by candidateId");
        return candidateEvaluationCriteriaRepository
            .findAllByCandidate_IdAndCandidate_Job_Company_Id(candidateId, securityUtils.getCurrentCompanyId(), pageable)
            .map(candidateEvaluationCriteriaMapper::toDto);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete candidate evaluation criteria : {}", id);
        CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity =
            candidateEvaluationCriteriaRepository.findOne(id);
        if (candidateEvaluationCriteriaEntity != null) {
            candidateEvaluationCriteriaRepository.delete(candidateEvaluationCriteriaEntity);
        } else {
            throw new NotFoundException("Candidate Evaluation Criteria Not Found");
        }
    }
}
