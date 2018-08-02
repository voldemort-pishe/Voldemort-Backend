package io.avand.service.impl;

import io.avand.domain.entity.jpa.InvoiceEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.domain.enumeration.InvoiceStatus;
import io.avand.repository.jpa.InvoiceRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.service.InvoiceService;
import io.avand.service.SubscriptionService;
import io.avand.service.dto.InvoiceDTO;
import io.avand.service.dto.SubscriptionDTO;
import io.avand.service.mapper.InvoiceMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    private final SubscriptionService subscriptionService;

    private final UserRepository userRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              InvoiceMapper invoiceMapper,
                              SubscriptionService subscriptionService,
                              UserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.subscriptionService = subscriptionService;
        this.userRepository = userRepository;
    }

    @Override
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        logger.debug("Request for service to save an invoice : {}", invoiceDTO);
        invoiceDTO.setStatus(InvoiceStatus.INITIALIZED);

        InvoiceEntity invoiceEntity = invoiceRepository.save(invoiceMapper.toEntity(invoiceDTO));

        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setPlanTitle("");

        return invoiceMapper.toDto(invoiceEntity);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Request for service to delete a invoice : {}", id);
        invoiceRepository.delete(id);
    }

    @Override
    public Optional<InvoiceDTO> findOneById(Long id) {
        logger.debug("Request to get a invoice with an id : {}", id);

        return invoiceRepository.findById(id).map(invoiceMapper::toDto);
    }

    @Override
    public Optional<InvoiceDTO> findOneByUserId(Long userId) throws NotFoundException {
        logger.debug("Request to invoice service to find a invoice by user id : {}", userId);

        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            Optional<InvoiceEntity> invoiceEntity = invoiceRepository.findTopByUser(userEntity.get());
            return invoiceEntity.map(invoiceMapper::toDto);
        } else {
            throw new NotFoundException("Sorry, who are you?");
        }
    }

    @Override
    public Page<InvoiceDTO> getAll(Pageable pageable) {
        logger.debug("Request to get all invoices");
        return invoiceRepository.findAll(pageable).map(invoiceMapper::toDto);
    }
}
