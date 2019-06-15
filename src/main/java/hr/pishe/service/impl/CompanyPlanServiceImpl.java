package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.*;
import hr.pishe.domain.enumeration.InvoiceStatus;
import hr.pishe.repository.jpa.CompanyPlanRepository;
import hr.pishe.repository.jpa.CompanyRepository;
import hr.pishe.repository.jpa.InvoiceRepository;
import hr.pishe.repository.jpa.PlanRepository;
import hr.pishe.service.CompanyPlanService;
import hr.pishe.service.dto.CompanyPlanDTO;
import hr.pishe.service.mapper.CompanyPlanMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CompanyPlanServiceImpl implements CompanyPlanService {

    private final Logger log = LoggerFactory.getLogger(CompanyPlanServiceImpl.class);
    private final CompanyPlanRepository companyPlanRepository;
    private final CompanyPlanMapper companyPlanMapper;
    private final PlanRepository planRepository;
    private final InvoiceRepository invoiceRepository;
    private final CompanyRepository companyRepository;

    public CompanyPlanServiceImpl(CompanyPlanRepository companyPlanRepository,
                                  CompanyPlanMapper companyPlanMapper,
                                  PlanRepository planRepository,
                                  InvoiceRepository invoiceRepository,
                                  CompanyRepository companyRepository) {
        this.companyPlanRepository = companyPlanRepository;
        this.companyPlanMapper = companyPlanMapper;
        this.planRepository = planRepository;
        this.invoiceRepository = invoiceRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyPlanDTO save(CompanyPlanDTO planDTO) {
        log.debug("Request to save userPlan : {}", planDTO);
        CompanyPlanEntity companyPlanEntity = companyPlanMapper.toEntity(planDTO);
        companyPlanEntity = companyPlanRepository.save(companyPlanEntity);
        return companyPlanMapper.toDto(companyPlanEntity);
    }

    @Override
    public CompanyPlanDTO save(Long planId, Long invoiceId, Long companyId) throws NotFoundException {
        log.debug("Request to save userPlan by planId and invoiceId : {}, {}", planId, invoiceId);
        PlanEntity planEntity = planRepository.findOne(planId);
        if (planEntity != null) {
            Optional<CompanyEntity> companyEntityOptional = companyRepository.findById(companyId);
            if (companyEntityOptional.isPresent()) {
                Optional<CompanyPlanEntity> userPlanEntityOptional = companyPlanRepository.findByCompany_Id(companyEntityOptional.get().getId());
                CompanyPlanEntity companyPlanEntity;
                InvoiceEntity oldInvoice = null;
                if (userPlanEntityOptional.isPresent()) {
                    companyPlanEntity = userPlanEntityOptional.get();
                    oldInvoice = companyPlanEntity.getInvoice();
                } else {
                    companyPlanEntity = new CompanyPlanEntity();
                }
                companyPlanEntity.setLength(planEntity.getLength());
                companyPlanEntity.setExtraLength(planEntity.getExtraLength());
                companyPlanEntity.setCompany(companyEntityOptional.get());

                InvoiceEntity invoiceEntity = invoiceRepository.findOne(invoiceId);
                if (invoiceEntity != null) {
                    companyPlanEntity.setInvoice(invoiceEntity);
                } else {
                    throw new NotFoundException("Invoice Not Available");
                }


                Set<CompanyPlanConfigEntity> userPlanConfigEntities = companyPlanEntity.getPlanConfig();
                userPlanConfigEntities.clear();
                for (PlanConfigEntity planConfigEntity : planEntity.getPlanConfig()) {
                    CompanyPlanConfigEntity companyPlanConfigEntity = new CompanyPlanConfigEntity();
                    companyPlanConfigEntity.setType(planConfigEntity.getType());
                    companyPlanConfigEntity.setValue(planConfigEntity.getValue());
                    companyPlanConfigEntity.setPlan(companyPlanEntity);
                    userPlanConfigEntities.add(companyPlanConfigEntity);
                }
                companyPlanEntity.setPlanConfig(userPlanConfigEntities);

                companyPlanEntity = companyPlanRepository.save(companyPlanEntity);

                if (oldInvoice != null && oldInvoice.getStatus() == InvoiceStatus.INITIALIZED)
                    invoiceRepository.delete(oldInvoice);

                return companyPlanMapper.toDto(companyPlanEntity);
            } else {
                throw new NotFoundException("User Not Available");
            }
        } else {
            throw new NotFoundException("Plan Not Available");
        }
    }

    @Override
    public Optional<CompanyPlanDTO> findById(Long planId) {
        log.debug("Request to find userPlan by id : {}", planId);
        return companyPlanRepository
            .findById(planId)
            .map(companyPlanMapper::toDto);
    }

    @Override
    public Optional<CompanyPlanDTO> findByCompanyId(Long companyId) {
        log.debug("Request to find userPlan by userId : {}", companyId);
        return companyPlanRepository
            .findByCompany_Id(companyId)
            .map(companyPlanMapper::toDto);
    }

    @Override
    public Optional<CompanyPlanDTO> findByInvoiceId(Long invoiceId) {
        log.debug("Request to find userPlan by invoiceId : {}", invoiceId);
        return companyPlanRepository
            .findByInvoice_Id(invoiceId)
            .map(companyPlanMapper::toDto);
    }

    @Override
    public void delete(Long planId) {
        log.debug("Request to delete userPlan by id : {}", planId);
        companyPlanRepository.delete(planId);
    }
}
