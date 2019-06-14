package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.*;
import hr.pishe.repository.jpa.CompanyContactRepository;
import hr.pishe.repository.jpa.CompanyRepository;
import hr.pishe.repository.jpa.FileRepository;
import hr.pishe.repository.jpa.UserRepository;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.*;
import hr.pishe.service.dto.*;
import hr.pishe.service.mapper.CompanyContactMapper;
import hr.pishe.service.mapper.CompanyMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final FileRepository fileRepository;
    private final CompanyMemberService companyMemberService;
    private final CompanyContactRepository companyContactRepository;
    private final PlanService planService;
    private final InvoiceService invoiceService;
    private final CompanyPlanService companyPlanService;
    private final SubscriptionService subscriptionService;
    private final CloudflareService cloudflareService;
    private final CompanyContactMapper companyContactMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              CompanyMapper companyMapper,
                              UserRepository userRepository,
                              SecurityUtils securityUtils,
                              FileRepository fileRepository,
                              CompanyMemberService companyMemberService,
                              CompanyContactRepository companyContactRepository,
                              PlanService planService,
                              InvoiceService invoiceService,
                              CompanyPlanService companyPlanService,
                              SubscriptionService subscriptionService,
                              CloudflareService cloudflareService, CompanyContactMapper companyContactMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
        this.fileRepository = fileRepository;
        this.companyMemberService = companyMemberService;
        this.companyContactRepository = companyContactRepository;
        this.planService = planService;
        this.invoiceService = invoiceService;
        this.companyPlanService = companyPlanService;
        this.subscriptionService = subscriptionService;
        this.cloudflareService = cloudflareService;
        this.companyContactMapper = companyContactMapper;
    }

    @Override
    @Transactional
    public CompanyDTO save(CompanyDTO companyDTO) throws NotFoundException {
        log.debug("Request to save company : {}", companyDTO);
        Long userId = securityUtils.getCurrentUserId();
        UserEntity userEntity = userRepository.findOne(userId);
        CompanyEntity companyEntity = companyMapper.toEntity(companyDTO);
        companyEntity.setUser(userEntity);
        FileEntity fileEntity = fileRepository.getOne(companyDTO.getFileId());
        if (fileEntity != null) {
            CompanyMemberDTO companyMemberDTO = companyMemberService.findByUserId(userId);
            if (companyMemberDTO == null) {
                CompanyContactEntity companyContactEntity = companyEntity.getContact();
                companyContactEntity = companyContactRepository.save(companyContactEntity);

                companyEntity.setFile(fileEntity);
                companyEntity.setContact(companyContactEntity);
                companyEntity = companyRepository.save(companyEntity);

                companyMemberDTO = new CompanyMemberDTO();
                companyMemberDTO.setCompanyId(companyEntity.getId());
                companyMemberDTO.setUserEmail(userEntity.getEmail());
                companyMemberDTO.setDepartment("منابع انسانی");
                companyMemberDTO.setPosition("رییس شرکت");
                companyMemberService.save(companyMemberDTO);

                this.addSubscription(companyEntity);

                CloudflareRequestDTO requestDTO = new CloudflareRequestDTO();
                requestDTO.setType("CNAME");
                requestDTO.setName(companyDTO.getSubDomain() + ".avand.hr");
                requestDTO.setContent("avand.hr");
                requestDTO.setProxied(false);
                cloudflareService.createDNSRecord(requestDTO);

                return companyMapper.toDto(companyEntity);
            } else {
                throw new NotFoundException("شما عضو شرکت دیگری هستید");
            }
        } else {
            throw new NotFoundException("File Not Found By Id");
        }
    }

    @Override
    public CompanyDTO update(CompanyDTO companyDTO) throws NotFoundException{
        log.debug("Request to update company : {}", companyDTO);
        Long companyId = securityUtils.getCurrentCompanyId();

        CompanyEntity foundCompany = companyRepository.findById(companyId)
            .orElseThrow(() -> new NotFoundException("Company Not Found By Id"));

        CompanyEntity updateCompany = companyMapper.toEntity(companyDTO);
        foundCompany.setNameEn(updateCompany.getNameEn());
        foundCompany.setNameFa(updateCompany.getNameFa());
        foundCompany.setDescriptionEn(updateCompany.getDescriptionEn());
        foundCompany.setDescriptionFa(updateCompany.getDescriptionFa());
        foundCompany.setLanguage(updateCompany.getLanguage());
        foundCompany.setSubDomain(updateCompany.getSubDomain());

        foundCompany.getContact().setAddress(updateCompany.getContact().getAddress());
        foundCompany.getContact().setLatitude(updateCompany.getContact().getLatitude());
        foundCompany.getContact().setLongitude(updateCompany.getContact().getLongitude());
        foundCompany.getContact().setPhone(updateCompany.getContact().getPhone());
        foundCompany.getContact().setEmail(updateCompany.getContact().getEmail());

        CompanyEntity updatedCompany = companyRepository.save(foundCompany);

        return companyMapper.toDto(updatedCompany);
    }

    @Override
    public CompanyDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find company by id : {}", id);
        Optional<CompanyEntity> companyEntity = companyRepository.findById(id);
        if (companyEntity.isPresent()) {
            return companyMapper.toDto(companyEntity.get());
        } else {
            throw new NotFoundException("Company Not Found By Id");
        }
    }

    @Override
    public CompanyDTO findBySubDomain(String subDomain) throws NotFoundException {
        log.debug("Request to find company by subDomain : {}", subDomain);
        CompanyEntity companyEntity = companyRepository.findBySubDomain(subDomain);
        if (companyEntity != null) {
            return companyMapper.toDto(companyEntity);
        } else {
            throw new NotFoundException("Company Not Available");
        }
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        CompanyEntity companyEntity = companyRepository.findOne(id);
        if (companyEntity != null) {
            companyRepository.delete(companyEntity);
        } else {
            throw new NotFoundException("Company Not Found By Id");
        }
    }

    private void addSubscription(CompanyEntity companyEntity) throws NotFoundException {
        Optional<PlanDTO> planDTO = planService.findFreePlan();
        if (planDTO.isPresent()) {
            InvoiceDTO invoiceDTO = invoiceService.saveByPlanId(planDTO.get().getId(), companyEntity.getId());

            CompanyPlanDTO companyPlanDTO = companyPlanService.save(planDTO.get().getId(), invoiceDTO.getId(), companyEntity.getId());

            subscriptionService.save(companyPlanDTO.getId(), companyEntity.getId());
        }
    }
}
