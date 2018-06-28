package io.avand.service.mapper;

import io.avand.domain.CandidateEntity;
import io.avand.domain.FeedbackEntity;
import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.FeedbackDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FeedbackMapper implements EntityMapper<FeedbackDTO, FeedbackEntity> {

    @Autowired
    private CandidateMapper candidateMapper;

    @Override
    public FeedbackEntity toEntity(FeedbackDTO dto) {
        if (dto == null) {
            return null;
        }

        FeedbackEntity feedbackEntity = new FeedbackEntity();

        feedbackEntity.setId(dto.getId());
        feedbackEntity.setUserId(dto.getUserId());
        feedbackEntity.setFeedbackText(dto.getFeedbackText());
        feedbackEntity.setRating(dto.getRating());
        feedbackEntity.setCandidate(candidateMapper.toEntity(dto.getCandidate()));

        return feedbackEntity;
    }

    protected FeedbackEntity toEntity(FeedbackDTO dto, CandidateEntity parent) {
        if (dto == null) {
            return null;
        }

        FeedbackEntity feedbackEntity = new FeedbackEntity();

        feedbackEntity.setId(dto.getId());
        feedbackEntity.setUserId(dto.getUserId());
        feedbackEntity.setFeedbackText(dto.getFeedbackText());
        feedbackEntity.setRating(dto.getRating());
        feedbackEntity.setCandidate(parent);

        return feedbackEntity;
    }

    @Override
    public FeedbackDTO toDto(FeedbackEntity entity) {
        if (entity == null) {
            return null;
        }

        FeedbackDTO feedbackDTO = new FeedbackDTO();

        feedbackDTO.setId(entity.getId());
        feedbackDTO.setUserId(entity.getUserId());
        feedbackDTO.setFeedbackText(entity.getFeedbackText());
        feedbackDTO.setRating(entity.getRating());
        feedbackDTO.setCandidate(candidateMapper.toDto(entity.getCandidate()));

        return feedbackDTO;
    }

    protected FeedbackDTO toDto(FeedbackEntity entity, CandidateDTO parent) {
        if (entity == null) {
            return null;
        }

        FeedbackDTO feedbackDTO = new FeedbackDTO();

        feedbackDTO.setId(entity.getId());
        feedbackDTO.setUserId(entity.getUserId());
        feedbackDTO.setFeedbackText(entity.getFeedbackText());
        feedbackDTO.setRating(entity.getRating());
        feedbackDTO.setCandidate(parent);

        return feedbackDTO;
    }

    @Override
    public List<FeedbackEntity> toEntity(List<FeedbackDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<FeedbackEntity> list = new ArrayList<FeedbackEntity>(dtoList.size());
        for (FeedbackDTO feedbackDTO : dtoList) {
            list.add(toEntity(feedbackDTO));
        }

        return list;
    }

    protected List<FeedbackEntity> toEntity(List<FeedbackDTO> dtoList, CandidateEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<FeedbackEntity> list = new ArrayList<FeedbackEntity>(dtoList.size());
        for (FeedbackDTO feedbackDTO : dtoList) {
            list.add(toEntity(feedbackDTO, parent));
        }

        return list;
    }

    @Override
    public List<FeedbackDTO> toDto(List<FeedbackEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<FeedbackDTO> list = new ArrayList<FeedbackDTO>(entityList.size());
        for (FeedbackEntity feedbackEntity : entityList) {
            list.add(toDto(feedbackEntity));
        }

        return list;
    }

    protected List<FeedbackDTO> toDto(List<FeedbackEntity> entityList, CandidateDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<FeedbackDTO> list = new ArrayList<FeedbackDTO>(entityList.size());
        for (FeedbackEntity feedbackEntity : entityList) {
            list.add(toDto(feedbackEntity, parent));
        }

        return list;
    }

    @Override
    public Set<FeedbackEntity> toEntity(Set<FeedbackDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<FeedbackEntity> set = new HashSet<FeedbackEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (FeedbackDTO feedbackDTO : dtoList) {
            set.add(toEntity(feedbackDTO));
        }

        return set;
    }

    protected Set<FeedbackEntity> toEntity(Set<FeedbackDTO> dtoList, CandidateEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<FeedbackEntity> set = new HashSet<FeedbackEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (FeedbackDTO feedbackDTO : dtoList) {
            set.add(toEntity(feedbackDTO, parent));
        }

        return set;
    }

    @Override
    public Set<FeedbackDTO> toDto(Set<FeedbackEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<FeedbackDTO> set = new HashSet<FeedbackDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (FeedbackEntity feedbackEntity : entityList) {
            set.add(toDto(feedbackEntity));
        }

        return set;
    }

    protected Set<FeedbackDTO> toDto(Set<FeedbackEntity> entityList, CandidateDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<FeedbackDTO> set = new HashSet<FeedbackDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (FeedbackEntity feedbackEntity : entityList) {
            set.add(toDto(feedbackEntity, parent));
        }

        return set;
    }

}
