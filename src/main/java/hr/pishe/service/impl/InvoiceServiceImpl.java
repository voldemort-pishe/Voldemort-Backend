package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.CompanyEntity;
import hr.pishe.domain.entity.jpa.InvoiceEntity;
import hr.pishe.domain.entity.jpa.InvoiceItemEntity;
import hr.pishe.domain.enumeration.InvoiceStatus;
import hr.pishe.domain.enumeration.PlanType;
import hr.pishe.repository.jpa.CompanyRepository;
import hr.pishe.repository.jpa.InvoiceRepository;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.InvoiceService;
import hr.pishe.service.PlanService;
import hr.pishe.service.dto.InvoiceDTO;
import hr.pishe.service.dto.PlanDTO;
import hr.pishe.service.mapper.InvoiceMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository invoiceRepository;

    private final PlanService planService;

    private final InvoiceMapper invoiceMapper;

    private final CompanyRepository companyRepository;

    private final SecurityUtils securityUtils;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              PlanService planService,
                              InvoiceMapper invoiceMapper,
                              CompanyRepository companyRepository,
                              SecurityUtils securityUtils) {
        this.invoiceRepository = invoiceRepository;
        this.planService = planService;
        this.invoiceMapper = invoiceMapper;
        this.companyRepository = companyRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public InvoiceDTO save(InvoiceDTO invoiceDTO) throws NotFoundException {
        logger.debug("Request to save invoice : {}", invoiceDTO);
        InvoiceEntity invoiceEntity = invoiceMapper.toEntity(invoiceDTO);
        Optional<CompanyEntity> companyEntityOptional = companyRepository.findById(invoiceDTO.getCompanyId());
        if (companyEntityOptional.isPresent()) {
            invoiceEntity.setCompany(companyEntityOptional.get());
            for (InvoiceItemEntity invoiceItemEntity : invoiceEntity.getInvoiceItem()) {
                invoiceItemEntity.setInvoice(invoiceEntity);
            }
            invoiceEntity = invoiceRepository.save(invoiceEntity);
            return invoiceMapper.toDto(invoiceEntity);
        } else {
            throw new NotFoundException("User Not Available");
        }
    }

    @Override
    public InvoiceDTO saveByPlanId(Long planId, Long companyId) throws NotFoundException {
        logger.debug("Request to save invoice by planId : {}", planId);
        Optional<PlanDTO> planDTOOptional = planService.findOneById(planId);
        if (planDTOOptional.isPresent()) {
            InvoiceEntity invoiceEntity = new InvoiceEntity();
            invoiceEntity.setStatus(planDTOOptional.get().getType() == PlanType.FREE ? InvoiceStatus.SUCCESS : InvoiceStatus.INITIALIZED);

            InvoiceItemEntity invoiceItemEntity = new InvoiceItemEntity();
            invoiceItemEntity.setCount(1L);
            invoiceItemEntity.setInvoice(invoiceEntity);
            invoiceItemEntity.setPrice(planDTOOptional.get().getAmount());
            invoiceItemEntity.setTotal(planDTOOptional.get().getAmount());
            invoiceItemEntity.setTitle(String.format("خرید اشتراک %s", planDTOOptional.get().getTitle()));

            Set<InvoiceItemEntity> invoiceItemEntities = new HashSet<>();
            invoiceItemEntities.add(invoiceItemEntity);
            invoiceEntity.setInvoiceItem(invoiceItemEntities);
            invoiceEntity.setAmount(planDTOOptional.get().getAmount());
            invoiceEntity.setDiscount(0L);
            invoiceEntity.setTax((planDTOOptional.get().getAmount() * 9) / 100);
            invoiceEntity.setTotal(invoiceEntity.getAmount() + invoiceEntity.getTax() - invoiceEntity.getDiscount());

            Optional<CompanyEntity> companyEntityOptional = companyRepository.findById(companyId);
            if (companyEntityOptional.isPresent()) {
                invoiceEntity.setCompany(companyEntityOptional.get());
                invoiceEntity = invoiceRepository.save(invoiceEntity);
                return invoiceMapper.toDto(invoiceEntity);
            } else {
                throw new NotFoundException("User Not Available");
            }

        } else {
            throw new NotFoundException("Plan Not Available");
        }
    }

    @Override
    public void delete(Long id) {
        logger.debug("Request for service to delete a invoice : {}", id);
        invoiceRepository.delete(id);
    }

    @Override
    public Optional<InvoiceDTO> findOneById(Long id) throws NotFoundException {
        logger.debug("Request to get a invoice with an id : {}", id);
        return invoiceRepository.
            findByIdAndCompany_Id(id, securityUtils.getCurrentCompanyId())
            .map(invoiceMapper::toDto);
    }

    @Override
    public Optional<InvoiceDTO> findOneByTrackingCode(String trackingCode) {
        logger.debug("Request to invoice service to find a invoice by reference id : {}", trackingCode);
        return invoiceRepository
            .findByTrackingCode(trackingCode)
            .map(invoiceMapper::toDto);
    }

    @Override
    public Page<InvoiceDTO> findAll(Pageable pageable) throws NotFoundException {
        logger.debug("Request to get all invoices");
        return invoiceRepository
            .findAllByCompany_Id(securityUtils.getCurrentCompanyId(), pageable)
            .map(invoiceMapper::toDto);
    }
}
