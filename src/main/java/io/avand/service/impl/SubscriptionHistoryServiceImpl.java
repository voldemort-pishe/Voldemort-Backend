package io.avand.service.impl;

import io.avand.domain.entity.jpa.SubscriptionHistoryEntity;
import io.avand.repository.jpa.SubscriptionHistoryRepository;
import io.avand.service.SubscriptionHistoryService;
import io.avand.service.dto.SubscriptionHistoryDTO;
import io.avand.service.mapper.SubscriptionHistoryMapper;
import io.avand.web.rest.errors.ServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionHistoryServiceImpl implements SubscriptionHistoryService {

    private final Logger logger = LoggerFactory.getLogger(SubscriptionHistoryServiceImpl.class);

    private final SubscriptionHistoryRepository subsHistoryRepository;

    private final SubscriptionHistoryMapper subsHistoryMapper;

    public SubscriptionHistoryServiceImpl(SubscriptionHistoryRepository subsHistoryRepository,
                                          SubscriptionHistoryMapper subsHistoryMapper) {
        this.subsHistoryRepository = subsHistoryRepository;
        this.subsHistoryMapper = subsHistoryMapper;
    }

    @Override
    public SubscriptionHistoryDTO save(SubscriptionHistoryDTO subscriptionHistoryDTO) {
        logger.debug("Request for service to save a subscription history : {}", subscriptionHistoryDTO);
        SubscriptionHistoryEntity subscriptionHistoryEntity = subsHistoryMapper.toEntity(subscriptionHistoryDTO);
        subscriptionHistoryEntity = subsHistoryRepository.save(subscriptionHistoryEntity);
        return subsHistoryMapper.toDto(subscriptionHistoryEntity);
    }

    @Override
    public SubscriptionHistoryDTO update(SubscriptionHistoryDTO subscriptionHistoryDTO) {
        logger.debug("Request for service to update a subscription history : {}", subscriptionHistoryDTO);
        SubscriptionHistoryEntity subscriptionHistoryEntity = subsHistoryRepository.findOne(subscriptionHistoryDTO.getId());
        if (subscriptionHistoryEntity != null) {
            subscriptionHistoryEntity.setEndDate(subscriptionHistoryDTO.getEndDate());
            subscriptionHistoryEntity.setStartDate(subscriptionHistoryDTO.getStartDate());
            subscriptionHistoryEntity.setPlanTitle(subscriptionHistoryDTO.getPlanTitle());
            subscriptionHistoryEntity = subsHistoryRepository.save(subscriptionHistoryEntity);
        } else {
            throw new ServerErrorException("Required subscription did not found!");
        }
        return subsHistoryMapper.toDto(subscriptionHistoryEntity);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Request for service to delete a subscription history : {}", id);
        subsHistoryRepository.delete(id);
    }

    @Override
    public Page<SubscriptionHistoryDTO> findAll(Pageable pageable) {
        logger.debug("Request for service to get all subscription history");
        return subsHistoryRepository.findAll(pageable).map(subsHistoryMapper::toDto);
    }
}
