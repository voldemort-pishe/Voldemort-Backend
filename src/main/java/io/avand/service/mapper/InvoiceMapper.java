package io.avand.service.mapper;

import io.avand.domain.InvoiceEntity;
import io.avand.domain.UserEntity;
import io.avand.service.dto.InvoiceDTO;
import io.avand.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InvoiceMapper implements EntityMapper<InvoiceDTO, InvoiceEntity> {

    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public InvoiceEntity toEntity(InvoiceDTO dto) {
        if (dto == null) {
            return null;
        }

        InvoiceEntity invoiceEntity = new InvoiceEntity();

        invoiceEntity.setCreatedBy(dto.getCreatedBy());
        invoiceEntity.setCreatedDate(dto.getCreatedDate());
        invoiceEntity.setLastModifiedBy(dto.getLastModifiedBy());
        invoiceEntity.setLastModifiedDate(dto.getLastModifiedDate());
        invoiceEntity.setId(dto.getId());
        invoiceEntity.setPaymentType(dto.getPaymentType());
        invoiceEntity.setPaymentDate(dto.getPaymentDate());
        invoiceEntity.setAmount(dto.getAmount());
        invoiceEntity.setStatus(dto.getStatus());
        invoiceEntity.setPaymentTransactions(paymentTransactionMapper.toEntity(dto.getPaymentTransactions(), invoiceEntity));
        invoiceEntity.setUser(userMapper.toEntity(dto.getUser()));

        return invoiceEntity;
    }

    protected InvoiceEntity toEntity(InvoiceDTO dto, UserEntity parent) {
        if (dto == null) {
            return null;
        }

        InvoiceEntity invoiceEntity = new InvoiceEntity();

        invoiceEntity.setCreatedBy(dto.getCreatedBy());
        invoiceEntity.setCreatedDate(dto.getCreatedDate());
        invoiceEntity.setLastModifiedBy(dto.getLastModifiedBy());
        invoiceEntity.setLastModifiedDate(dto.getLastModifiedDate());
        invoiceEntity.setId(dto.getId());
        invoiceEntity.setPaymentType(dto.getPaymentType());
        invoiceEntity.setPaymentDate(dto.getPaymentDate());
        invoiceEntity.setAmount(dto.getAmount());
        invoiceEntity.setStatus(dto.getStatus());
        invoiceEntity.setPaymentTransactions(paymentTransactionMapper.toEntity(dto.getPaymentTransactions(), invoiceEntity));
        invoiceEntity.setUser(parent);

        return invoiceEntity;
    }

    @Override
    public InvoiceDTO toDto(InvoiceEntity entity) {
        if (entity == null) {
            return null;
        }

        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setCreatedBy(entity.getCreatedBy());
        invoiceDTO.setCreatedDate(entity.getCreatedDate());
        invoiceDTO.setLastModifiedBy(entity.getLastModifiedBy());
        invoiceDTO.setLastModifiedDate(entity.getLastModifiedDate());
        invoiceDTO.setId(entity.getId());
        invoiceDTO.setPaymentType(entity.getPaymentType());
        invoiceDTO.setPaymentDate(entity.getPaymentDate());
        invoiceDTO.setAmount(entity.getAmount());
        invoiceDTO.setStatus(entity.getStatus());
        invoiceDTO.setPaymentTransactions(paymentTransactionMapper.toDto(entity.getPaymentTransactions(), invoiceDTO));
        invoiceDTO.setUser(userMapper.toDto(entity.getUser()));

        return invoiceDTO;
    }

    protected InvoiceDTO toDto(InvoiceEntity entity, UserDTO parent) {
        if (entity == null) {
            return null;
        }

        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setCreatedBy(entity.getCreatedBy());
        invoiceDTO.setCreatedDate(entity.getCreatedDate());
        invoiceDTO.setLastModifiedBy(entity.getLastModifiedBy());
        invoiceDTO.setLastModifiedDate(entity.getLastModifiedDate());
        invoiceDTO.setId(entity.getId());
        invoiceDTO.setPaymentType(entity.getPaymentType());
        invoiceDTO.setPaymentDate(entity.getPaymentDate());
        invoiceDTO.setAmount(entity.getAmount());
        invoiceDTO.setStatus(entity.getStatus());
        invoiceDTO.setPaymentTransactions(paymentTransactionMapper.toDto(entity.getPaymentTransactions(), invoiceDTO));
        invoiceDTO.setUser(parent);

        return invoiceDTO;
    }

    @Override
    public List<InvoiceEntity> toEntity(List<InvoiceDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<InvoiceEntity> list = new ArrayList<InvoiceEntity>(dtoList.size());
        for (InvoiceDTO invoiceDTO : dtoList) {
            list.add(toEntity(invoiceDTO));
        }

        return list;
    }

    protected List<InvoiceEntity> toEntity(List<InvoiceDTO> dtoList, UserEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<InvoiceEntity> list = new ArrayList<InvoiceEntity>(dtoList.size());
        for (InvoiceDTO invoiceDTO : dtoList) {
            list.add(toEntity(invoiceDTO, parent));
        }

        return list;
    }

    @Override
    public List<InvoiceDTO> toDto(List<InvoiceEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<InvoiceDTO> list = new ArrayList<InvoiceDTO>(entityList.size());
        for (InvoiceEntity invoiceEntity : entityList) {
            list.add(toDto(invoiceEntity));
        }

        return list;
    }

    protected List<InvoiceDTO> toDto(List<InvoiceEntity> entityList, UserDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<InvoiceDTO> list = new ArrayList<InvoiceDTO>(entityList.size());
        for (InvoiceEntity invoiceEntity : entityList) {
            list.add(toDto(invoiceEntity, parent));
        }

        return list;
    }

    @Override
    public Set<InvoiceEntity> toEntity(Set<InvoiceDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<InvoiceEntity> set = new HashSet<InvoiceEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (InvoiceDTO invoiceDTO : dtoList) {
            set.add(toEntity(invoiceDTO));
        }

        return set;
    }

    protected Set<InvoiceEntity> toEntity(Set<InvoiceDTO> dtoList, UserEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<InvoiceEntity> set = new HashSet<InvoiceEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (InvoiceDTO invoiceDTO : dtoList) {
            set.add(toEntity(invoiceDTO, parent));
        }

        return set;
    }

    @Override
    public Set<InvoiceDTO> toDto(Set<InvoiceEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<InvoiceDTO> set = new HashSet<InvoiceDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (InvoiceEntity invoiceEntity : entityList) {
            set.add(toDto(invoiceEntity));
        }

        return set;
    }

    protected Set<InvoiceDTO> toDto(Set<InvoiceEntity> entityList, UserDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<InvoiceDTO> set = new HashSet<InvoiceDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (InvoiceEntity invoiceEntity : entityList) {
            set.add(toDto(invoiceEntity, parent));
        }

        return set;
    }

}
