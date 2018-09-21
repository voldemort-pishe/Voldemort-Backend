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
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyMemberServiceImpl implements CompanyMemberService {

    private final Logger log = LoggerFactory.getLogger(CompanyMemberServiceImpl.class);
    private final CompanyMemberRepository companyMemberRepository;
    private final CompanyMemberMapper companyMemberMapper;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final CompanyRepository companyRepository;
    private final SecurityUtils securityUtils;

    public CompanyMemberServiceImpl(CompanyMemberRepository companyMemberRepository,
                                    CompanyMemberMapper companyMemberMapper,
                                    UserRepository userRepository,
                                    MailService mailService,
                                    CompanyRepository companyRepository,
                                    SecurityUtils securityUtils) {
        this.companyMemberRepository = companyMemberRepository;
        this.companyMemberMapper = companyMemberMapper;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.companyRepository = companyRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public List<CompanyMemberDTO> save(List<String> emails, Long companyId) throws NotFoundException {
        log.debug("Request to save company member : {}", companyId);
        CompanyEntity companyEntity = companyRepository.findOne(companyId);
        if (companyEntity != null) {

            if (companyEntity.getUser().getId().equals(securityUtils.getCurrentUserId())) {
                List<CompanyMemberEntity> companyMemberEntities = new ArrayList<>();
                for (String userEmail : emails) {
                    Optional<UserEntity> userEntityOp = userRepository.findByLogin(userEmail);
                    UserEntity userEntity;
                    if (!userEntityOp.isPresent()) {
                        UserEntity user = new UserEntity();
                        user.setLogin(userEmail);
                        user.setEmail(userEmail);
                        user.setInvitationKey(RandomUtil.generateInvitationKey());
                        userEntity = userRepository.save(user);

                        mailService.sendInviationMemberEmailWithRegister(userEntity);
                    } else {
                        userEntity = userEntityOp.get();
                        mailService.sendInviationMemberEmail(userEntity);
                    }

                    CompanyMemberEntity companyMemberEntity = new CompanyMemberEntity();
                    companyMemberEntity.setUser(userEntity);
                    companyMemberEntity.setCompany(companyEntity);

                    companyMemberEntity = companyMemberRepository.save(companyMemberEntity);
                    companyMemberEntities.add(companyMemberEntity);
                }
                return companyMemberMapper.toDto(companyMemberEntities);
            } else {
                throw new SecurityException("You Don't Have Access To Add Member to this Company");
            }
        } else {
            throw new NotFoundException("Company Not Available");
        }
    }

    @Override
    public CompanyMemberDTO findById(Long id) throws NotFoundException {
        log.debug("Request to find company member by id");
        CompanyMemberEntity companyMemberEntity = companyMemberRepository.findOne(id);
        if (companyMemberEntity != null) {
            if (companyMemberEntity.getCompany().getUser().getId().equals(securityUtils.getCurrentUserId())) {
                return companyMemberMapper.toDto(companyMemberEntity);
            } else {
                throw new SecurityException("You Don't Have Access to find this company Member");
            }
        } else {
            throw new NotFoundException("Company Member not Available");
        }
    }

    @Override
    public Page<CompanyMemberDTO> findAll(Long companyId, Pageable pageable) throws NotFoundException {
        log.debug("Request to find all company member by id : {}", companyId);
        return companyMemberRepository
            .findAllByCompany_User_IdAndCompany_Id(securityUtils.getCurrentUserId(), companyId, pageable)
            .map(companyMemberMapper::toDto);
    }

    @Override
    public Page<CompanyMemberDTO> findAllActiveMember(Long companyId, Pageable pageable) throws NotFoundException {
        log.debug("Request to find all active company member by id : {}", companyId);
        return companyMemberRepository
            .findAllByCompany_User_IdAndUser_ActivatedAndCompany_Id(securityUtils.getCurrentUserId(), true, companyId, pageable)
            .map(companyMemberMapper::toDto);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.debug("Request to delete company member by id : {}", id);
        CompanyMemberEntity companyMemberEntity = companyMemberRepository.findOne(id);
        if (companyMemberEntity != null) {
            if (companyMemberEntity.getCompany().getUser().getId().equals(securityUtils.getCurrentUserId())) {
                companyMemberRepository.delete(companyMemberEntity);
            } else {
                throw new SecurityException("You Don't Have Access To Delete this company Member");
            }
        } else {
            throw new NotFoundException("Company Member Not Available");
        }
    }
}
