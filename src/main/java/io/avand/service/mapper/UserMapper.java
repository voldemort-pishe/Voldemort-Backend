package io.avand.service.mapper;

import io.avand.domain.UserEntity;
import io.avand.service.dto.UserDTO;
import org.checkerframework.checker.units.qual.A;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserMapper implements EntityMapper<UserDTO, UserEntity> {

    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private TalentPoolMapper talentPoolMapper;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public UserEntity toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setCreatedBy(dto.getCreatedBy());
        userEntity.setCreatedDate(dto.getCreatedDate());
        userEntity.setLastModifiedBy(dto.getLastModifiedBy());
        userEntity.setLastModifiedDate(dto.getLastModifiedDate());
        userEntity.setId(dto.getId());
        userEntity.setLogin(dto.getLogin());
        userEntity.setPasswordHash(dto.getPasswordHash());
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setLastName(dto.getLastName());
        userEntity.setEmail(dto.getEmail());
        userEntity.setActivated(dto.getActivated());
        userEntity.setActivationKey(dto.getActivationKey());
        userEntity.setResetKey(dto.getResetKey());
        userEntity.setResetDate(dto.getResetDate());
        userEntity.setUserAuthorities(userAuthorityMapper.toEntity(dto.getUserAuthorities(), userEntity));
        userEntity.setCompanies(companyMapper.toEntity(dto.getCompanies(), userEntity));
        userEntity.setTalentPools(talentPoolMapper.toEntity(dto.getTalentPools(), userEntity));
        userEntity.setInvoices(invoiceMapper.toEntity(dto.getInvoices(), userEntity));

        return userEntity;
    }

    @Override
    public UserDTO toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setCreatedBy(entity.getCreatedBy());
        userDTO.setCreatedDate(entity.getCreatedDate());
        userDTO.setLastModifiedBy(entity.getLastModifiedBy());
        userDTO.setLastModifiedDate(entity.getLastModifiedDate());
        userDTO.setId(entity.getId());
        userDTO.setLogin(entity.getLogin());
        userDTO.setPasswordHash(entity.getPasswordHash());
        userDTO.setFirstName(entity.getFirstName());
        userDTO.setLastName(entity.getLastName());
        userDTO.setEmail(entity.getEmail());
        userDTO.setActivated(entity.isActivated());
        userDTO.setActivationKey(entity.getActivationKey());
        userDTO.setResetKey(entity.getResetKey());
        userDTO.setResetDate(entity.getResetDate());
        userDTO.setUserAuthorities(userAuthorityMapper.toDto(entity.getUserAuthorities(), userDTO));
        userDTO.setCompanies(companyMapper.toDto(entity.getCompanies(), userDTO));
        userDTO.setTalentPools(talentPoolMapper.toDto(entity.getTalentPools(), userDTO));
        userDTO.setInvoices(invoiceMapper.toDto(entity.getInvoices(), userDTO));

        return userDTO;
    }

    @Override
    public List<UserEntity> toEntity(List<UserDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<UserEntity> list = new ArrayList<UserEntity>(dtoList.size());
        for (UserDTO userDTO : dtoList) {
            list.add(toEntity(userDTO));
        }

        return list;
    }

    @Override
    public List<UserDTO> toDto(List<UserEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>(entityList.size());
        for (UserEntity userEntity : entityList) {
            list.add(toDto(userEntity));
        }

        return list;
    }

    @Override
    public Set<UserEntity> toEntity(Set<UserDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<UserEntity> set = new HashSet<UserEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (UserDTO userDTO : dtoList) {
            set.add(toEntity(userDTO));
        }

        return set;
    }

    @Override
    public Set<UserDTO> toDto(Set<UserEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<UserDTO> set = new HashSet<UserDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (UserEntity userEntity : entityList) {
            set.add(toDto(userEntity));
        }

        return set;
    }
}
