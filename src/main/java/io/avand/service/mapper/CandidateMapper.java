package io.avand.service.mapper;

import io.avand.domain.CandidateEntity;
import io.avand.domain.JobEntity;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.JobDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CandidateMapper implements EntityMapper<CandidateDTO, CandidateEntity> {

    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CandidateEvaluationCriteriaMapper candidateEvaluationCriteriaMapper;
    @Autowired
    private CandidateScheduleMapper candidateScheduleMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private JobMapper jobMapper;

    @Override
    public CandidateEntity toEntity(CandidateDTO dto) {
        if (dto == null) {
            return null;
        }

        CandidateEntity candidateEntity = new CandidateEntity();

        candidateEntity.setId(dto.getId());
        candidateEntity.setFirstName(dto.getFirstName());
        candidateEntity.setLastName(dto.getLastName());
        candidateEntity.setState(dto.getState());
        candidateEntity.setCellphone(dto.getCellphone());
        candidateEntity.setEmail(dto.getEmail());
        candidateEntity.setCandidatePipeline(dto.getCandidatePipeline());
        candidateEntity.setFeedbacks(feedbackMapper.toEntity(dto.getFeedbacks(), candidateEntity));
        candidateEntity.setComments(commentMapper.toEntity(dto.getComments(), candidateEntity));
        candidateEntity.setCandidateEvaluationCriteria(candidateEvaluationCriteriaMapper.toEntity(dto.getCandidateEvaluationCriteria(), candidateEntity));
        candidateEntity.setCandidateSchedules(candidateScheduleMapper.toEntity(dto.getCandidateSchedule(), candidateEntity));
        candidateEntity.setFile(fileMapper.toEntity(dto.getFile(), candidateEntity));
        candidateEntity.setJob(jobMapper.toEntity(dto.getJob()));

        return candidateEntity;
    }

    protected CandidateEntity toEntity(CandidateDTO dto, JobEntity parent) {
        if (dto == null) {
            return null;
        }

        CandidateEntity candidateEntity = new CandidateEntity();

        candidateEntity.setId(dto.getId());
        candidateEntity.setFirstName(dto.getFirstName());
        candidateEntity.setLastName(dto.getLastName());
        candidateEntity.setState(dto.getState());
        candidateEntity.setCellphone(dto.getCellphone());
        candidateEntity.setEmail(dto.getEmail());
        candidateEntity.setCandidatePipeline(dto.getCandidatePipeline());
        candidateEntity.setFeedbacks(feedbackMapper.toEntity(dto.getFeedbacks(), candidateEntity));
        candidateEntity.setComments(commentMapper.toEntity(dto.getComments(), candidateEntity));
        candidateEntity.setCandidateEvaluationCriteria(candidateEvaluationCriteriaMapper.toEntity(dto.getCandidateEvaluationCriteria(), candidateEntity));
        candidateEntity.setCandidateSchedules(candidateScheduleMapper.toEntity(dto.getCandidateSchedule(), candidateEntity));
        candidateEntity.setFile(fileMapper.toEntity(dto.getFile(), candidateEntity));
        candidateEntity.setJob(parent);

        return candidateEntity;
    }

    @Override
    public CandidateDTO toDto(CandidateEntity entity) {
        if (entity == null) {
            return null;
        }

        CandidateDTO candidateDTO = new CandidateDTO();

        candidateDTO.setId(entity.getId());
        candidateDTO.setFirstName(entity.getFirstName());
        candidateDTO.setLastName(entity.getLastName());
        candidateDTO.setState(entity.getState());
        candidateDTO.setCellphone(entity.getCellphone());
        candidateDTO.setEmail(entity.getEmail());
        candidateDTO.setCandidatePipeline(entity.getCandidatePipeline());
        candidateDTO.setFeedbacks(feedbackMapper.toDto(entity.getFeedbacks(), candidateDTO));
        candidateDTO.setComments(commentMapper.toDto(entity.getComments(), candidateDTO));
        candidateDTO.setCandidateEvaluationCriteria(candidateEvaluationCriteriaMapper.toDto(entity.getCandidateEvaluationCriteria(), candidateDTO));
        candidateDTO.setCandidateSchedule(candidateScheduleMapper.toDto(entity.getCandidateSchedules(), candidateDTO));
        candidateDTO.setFile(fileMapper.toDto(entity.getFile(), candidateDTO));
        candidateDTO.setJob(jobMapper.toDto(entity.getJob()));

        return candidateDTO;
    }

    protected CandidateDTO toDto(CandidateEntity entity, JobDTO parent) {
        if (entity == null) {
            return null;
        }

        CandidateDTO candidateDTO = new CandidateDTO();

        candidateDTO.setId(entity.getId());
        candidateDTO.setFirstName(entity.getFirstName());
        candidateDTO.setLastName(entity.getLastName());
        candidateDTO.setState(entity.getState());
        candidateDTO.setCellphone(entity.getCellphone());
        candidateDTO.setEmail(entity.getEmail());
        candidateDTO.setCandidatePipeline(entity.getCandidatePipeline());
        candidateDTO.setFeedbacks(feedbackMapper.toDto(entity.getFeedbacks(), candidateDTO));
        candidateDTO.setComments(commentMapper.toDto(entity.getComments(), candidateDTO));
        candidateDTO.setCandidateEvaluationCriteria(candidateEvaluationCriteriaMapper.toDto(entity.getCandidateEvaluationCriteria(), candidateDTO));
        candidateDTO.setCandidateSchedule(candidateScheduleMapper.toDto(entity.getCandidateSchedules(), candidateDTO));
        candidateDTO.setFile(fileMapper.toDto(entity.getFile(), candidateDTO));
        candidateDTO.setJob(parent);

        return candidateDTO;
    }

    @Override
    public List<CandidateEntity> toEntity(List<CandidateDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<CandidateEntity> list = new ArrayList<CandidateEntity>(dtoList.size());
        for (CandidateDTO candidateDTO : dtoList) {
            list.add(toEntity(candidateDTO));
        }

        return list;
    }

    protected List<CandidateEntity> toEntity(List<CandidateDTO> dtoList, JobEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<CandidateEntity> list = new ArrayList<CandidateEntity>(dtoList.size());
        for (CandidateDTO candidateDTO : dtoList) {
            list.add(toEntity(candidateDTO, parent));
        }

        return list;
    }

    @Override
    public List<CandidateDTO> toDto(List<CandidateEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<CandidateDTO> list = new ArrayList<CandidateDTO>(entityList.size());
        for (CandidateEntity candidateEntity : entityList) {
            list.add(toDto(candidateEntity));
        }

        return list;
    }

    protected List<CandidateDTO> toDto(List<CandidateEntity> entityList, JobDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<CandidateDTO> list = new ArrayList<CandidateDTO>(entityList.size());
        for (CandidateEntity candidateEntity : entityList) {
            list.add(toDto(candidateEntity, parent));
        }

        return list;
    }

    @Override
    public Set<CandidateEntity> toEntity(Set<CandidateDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<CandidateEntity> set = new HashSet<CandidateEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CandidateDTO candidateDTO : dtoList) {
            set.add(toEntity(candidateDTO));
        }

        return set;
    }

    protected Set<CandidateEntity> toEntity(Set<CandidateDTO> dtoList, JobEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<CandidateEntity> set = new HashSet<CandidateEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (CandidateDTO candidateDTO : dtoList) {
            set.add(toEntity(candidateDTO, parent));
        }

        return set;
    }

    @Override
    public Set<CandidateDTO> toDto(Set<CandidateEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<CandidateDTO> set = new HashSet<CandidateDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CandidateEntity candidateEntity : entityList) {
            set.add(toDto(candidateEntity));
        }

        return set;
    }

    protected Set<CandidateDTO> toDto(Set<CandidateEntity> entityList, JobDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<CandidateDTO> set = new HashSet<CandidateDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (CandidateEntity candidateEntity : entityList) {
            set.add(toDto(candidateEntity, parent));
        }

        return set;
    }

}
