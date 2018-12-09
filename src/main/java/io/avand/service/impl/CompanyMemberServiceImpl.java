package io.avand.service.impl;

import io.avand.domain.entity.jpa.CompanyEntity;
import io.avand.domain.entity.jpa.CompanyMemberEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.repository.jpa.CompanyMemberRepository;
import io.avand.repository.jpa.CompanyRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.CompanyMemberService;
import io.avand.service.MailService;
import io.avand.service.dto.CompanyMemberDTO;
import io.avand.service.mapper.CompanyMemberMapper;
import io.avand.service.util.RandomUtil;
import io.avand.web.rest.vm.CompanyMemberFilterVM;
import io.avand.web.specification.CompanyMemberSpecification;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyMemberServiceImpl implements CompanyMemberService {

    private final Logger log = LoggerFactory.getLogger(CompanyMemberServiceImpl.class);
    private final CompanyMemberRepository companyMemberRepository;
    private final CompanyMemberMapper companyMemberMapper;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final CompanyRepository companyRepository;
    private final SecurityUtils securityUtils;
    private final CompanyMemberSpecification companyMemberSpecification;

    public CompanyMemberServiceImpl(CompanyMemberRepository companyMemberRepository,
                                    CompanyMemberMapper companyMemberMapper,
                                    UserRepository userRepository,
                                    MailService mailService,
                                    CompanyRepository companyRepository,
                                    SecurityUtils securityUtils,
                                    CompanyMemberSpecification companyMemberSpecification) {
        this.companyMemberRepository = companyMemberRepository;
        this.companyMemberMapper = companyMemberMapper;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.companyRepository = companyRepository;
        this.securityUtils = securityUtils;
        this.companyMemberSpecification = companyMemberSpecification;
    }

    @Override
    public CompanyMemberDTO save(CompanyMemberDTO companyMemberDTO) throws NotFoundException {
        log.debug("Request to save company member : {}", companyMemberDTO);
        Optional<CompanyEntity> companyEntity = companyRepository.findById(companyMemberDTO.getCompanyId());
        if (companyEntity.isPresent()) {
            Optional<UserEntity> userEntityOp = userRepository.findByLogin(companyMemberDTO.getUserEmail());
            UserEntity userEntity;
            boolean register;
            if (!userEntityOp.isPresent()) {
                UserEntity user = new UserEntity();
                user.setLogin(companyMemberDTO.getUserEmail());
                user.setEmail(companyMemberDTO.getUserEmail());
                user.setInvitationKey(RandomUtil.generateInvitationKey());
                userEntity = userRepository.save(user);
                register = true;
            } else {
                CompanyMemberEntity companyMemberEntity = companyMemberRepository.findByUser_Id(userEntityOp.get().getId());
                if (companyMemberEntity == null) {
                    userEntity = userEntityOp.get();
                    register = false;
                } else {
                    throw new NotFoundException("User Is Available in another company");
                }
            }

            CompanyMemberEntity companyMemberEntity = companyMemberMapper.toEntity(companyMemberDTO);
            companyMemberEntity.setUser(userEntity);
            companyMemberEntity.setCompany(companyEntity.get());

            companyMemberEntity = companyMemberRepository.save(companyMemberEntity);

            if (register) {
                mailService.sendInvitationMemberEmailWithRegister(companyMemberEntity);
            } else {
                mailService.sendInvitationMemberEmail(companyMemberEntity);
            }

            return companyMemberMapper.toDto(companyMemberEntity);
        } else {
            throw new NotFoundException("Company Not Available");
        }
    }

    @Override
    public List<CompanyMemberDTO> saveAll(List<CompanyMemberDTO> memberDTOS) throws NotFoundException {
        log.debug("Request to save company member : {}");
        CompanyEntity companyEntity = companyRepository.findOne(securityUtils.getCurrentCompanyId());
        if (companyEntity != null) {
            List<CompanyMemberEntity> companyMemberEntities = new ArrayList<>();
            for (CompanyMemberDTO memberDTO : memberDTOS) {
                Optional<UserEntity> userEntityOp = userRepository.findByLogin(memberDTO.getUserEmail());
                UserEntity userEntity;
                boolean register;
                if (!userEntityOp.isPresent()) {
                    UserEntity user = new UserEntity();
                    user.setLogin(memberDTO.getUserEmail());
                    user.setEmail(memberDTO.getUserEmail());
                    user.setInvitationKey(RandomUtil.generateInvitationKey());
                    user.setActivated(false);
                    userEntity = userRepository.save(user);
                    register = true;
                } else {
                    userEntity = userEntityOp.get();
                    register = false;
                }

                CompanyMemberEntity companyMemberEntity = new CompanyMemberEntity();
                companyMemberEntity.setUser(userEntity);
                companyMemberEntity.setCompany(companyEntity);
                companyMemberEntity.setPosition(memberDTO.getPosition());
                companyMemberEntity.setDepartment(memberDTO.getDepartment());

                companyMemberEntity = companyMemberRepository.save(companyMemberEntity);

                if (register) {
                    mailService.sendInvitationMemberEmailWithRegister(companyMemberEntity);
                } else {
                    mailService.sendInvitationMemberEmail(companyMemberEntity);
                }

                companyMemberEntities.add(companyMemberEntity);
            }
            return companyMemberMapper.toDto(companyMemberEntities);
        } else {
            throw new NotFoundException("Company Not Available");
        }
    }

    @Override
    public CompanyMemberDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find company member by id");
        CompanyMemberEntity companyMemberEntity = companyMemberRepository
            .findByIdAndCompany_Id(id, securityUtils.getCurrentCompanyId());
        if (companyMemberEntity != null) {
            return companyMemberMapper.toDto(companyMemberEntity);
        } else {
            throw new NotFoundException("Company Member not Available");
        }
    }

    @Override
    public CompanyMemberDTO findByUserId(Long userId) throws NotFoundException {
        log.debug("Request to find companyMember by userId : {}", userId);
        CompanyMemberEntity companyMemberEntity = companyMemberRepository.findByUser_Id(userId);
        if (companyMemberEntity != null) {
            return companyMemberMapper.toDto(companyMemberEntity);
        } else {
            throw new NotFoundException("Company Member Not Found");
        }
    }

    @Override
    public Optional<CompanyMemberDTO> findByLogin(String login) {
        log.debug("Request to find companyMember by login : {}", login);
        return companyMemberRepository
            .findByUser_Login(login)
            .map(companyMemberMapper::toDto);
    }

    @Override
    public Page<CompanyMemberDTO> findAllByFilter(CompanyMemberFilterVM filterVM, Pageable pageable)
        throws NotFoundException {
        log.debug("Request to find all company member");
        if (filterVM == null)
            filterVM = new CompanyMemberFilterVM();
        filterVM.setCompany(securityUtils.getCurrentCompanyId());
        return companyMemberRepository
            .findAll(companyMemberSpecification.getFilter(filterVM), pageable)
            .map(companyMemberMapper::toDto);
    }

    @Override
    public Page<CompanyMemberDTO> findAllActiveMember(Pageable pageable) throws NotFoundException {
        log.debug("Request to find all active company member");
        return companyMemberRepository
            .findAllByUser_ActivatedAndCompany_Id(true, securityUtils.getCurrentCompanyId(), pageable)
            .map(companyMemberMapper::toDto);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete company member by id : {}", id);
        CompanyMemberEntity companyMemberEntity = companyMemberRepository.findOne(id);
        if (companyMemberEntity != null) {
            companyMemberRepository.delete(companyMemberEntity);
        } else {
            throw new NotFoundException("Company Member Not Available");
        }
    }
}
