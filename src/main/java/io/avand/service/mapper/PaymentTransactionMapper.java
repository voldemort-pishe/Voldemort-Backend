package io.avand.service.mapper;

import io.avand.domain.InvoiceEntity;
import io.avand.domain.PaymentTransactionEntity;
import io.avand.service.dto.InvoiceDTO;
import io.avand.service.dto.PaymentTransactionDTO;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PaymentTransactionMapper implements EntityMapper<PaymentTransactionDTO, PaymentTransactionEntity> {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public PaymentTransactionEntity toEntity(PaymentTransactionDTO dto) {
        if (dto == null) {
            return null;
        }

        PaymentTransactionEntity paymentTransactionEntity = new PaymentTransactionEntity();

        paymentTransactionEntity.setId(dto.getId());
        paymentTransactionEntity.setUserId(dto.getUserId());
        paymentTransactionEntity.setRefrenceId(dto.getRefrenceId());
        paymentTransactionEntity.setAmount(dto.getAmount());
        paymentTransactionEntity.setInvoice(invoiceMapper.toEntity(dto.getInvoice()));

        return paymentTransactionEntity;
    }

    protected PaymentTransactionEntity toEntity(PaymentTransactionDTO dto, InvoiceEntity parent) {
        if (dto == null) {
            return null;
        }

        PaymentTransactionEntity paymentTransactionEntity = new PaymentTransactionEntity();

        paymentTransactionEntity.setId(dto.getId());
        paymentTransactionEntity.setUserId(dto.getUserId());
        paymentTransactionEntity.setRefrenceId(dto.getRefrenceId());
        paymentTransactionEntity.setAmount(dto.getAmount());
        paymentTransactionEntity.setInvoice(parent);

        return paymentTransactionEntity;
    }

    @Override
    public PaymentTransactionDTO toDto(PaymentTransactionEntity entity) {
        if (entity == null) {
            return null;
        }

        PaymentTransactionDTO paymentTransactionDTO = new PaymentTransactionDTO();

        paymentTransactionDTO.setId(entity.getId());
        paymentTransactionDTO.setUserId(entity.getUserId());
        paymentTransactionDTO.setRefrenceId(entity.getRefrenceId());
        paymentTransactionDTO.setAmount(entity.getAmount());
        paymentTransactionDTO.setInvoice(invoiceMapper.toDto(entity.getInvoice()));

        return paymentTransactionDTO;
    }

    protected PaymentTransactionDTO toDto(PaymentTransactionEntity entity, InvoiceDTO parent) {
        if (entity == null) {
            return null;
        }

        PaymentTransactionDTO paymentTransactionDTO = new PaymentTransactionDTO();

        paymentTransactionDTO.setId(entity.getId());
        paymentTransactionDTO.setUserId(entity.getUserId());
        paymentTransactionDTO.setRefrenceId(entity.getRefrenceId());
        paymentTransactionDTO.setAmount(entity.getAmount());
        paymentTransactionDTO.setInvoice(parent);

        return paymentTransactionDTO;
    }

    @Override
    public List<PaymentTransactionEntity> toEntity(List<PaymentTransactionDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<PaymentTransactionEntity> list = new ArrayList<PaymentTransactionEntity>(dtoList.size());
        for (PaymentTransactionDTO paymentTransactionDTO : dtoList) {
            list.add(toEntity(paymentTransactionDTO));
        }

        return list;
    }

    protected List<PaymentTransactionEntity> toEntity(List<PaymentTransactionDTO> dtoList, InvoiceEntity parent) {
        if (dtoList == null) {
            return null;
        }

        List<PaymentTransactionEntity> list = new ArrayList<PaymentTransactionEntity>(dtoList.size());
        for (PaymentTransactionDTO paymentTransactionDTO : dtoList) {
            list.add(toEntity(paymentTransactionDTO, parent));
        }

        return list;
    }

    @Override
    public List<PaymentTransactionDTO> toDto(List<PaymentTransactionEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        List<PaymentTransactionDTO> list = new ArrayList<PaymentTransactionDTO>(entityList.size());
        for (PaymentTransactionEntity paymentTransactionEntity : entityList) {
            list.add(toDto(paymentTransactionEntity));
        }

        return list;
    }

    protected List<PaymentTransactionDTO> toDto(List<PaymentTransactionEntity> entityList, InvoiceDTO parent) {
        if (entityList == null) {
            return null;
        }

        List<PaymentTransactionDTO> list = new ArrayList<PaymentTransactionDTO>(entityList.size());
        for (PaymentTransactionEntity paymentTransactionEntity : entityList) {
            list.add(toDto(paymentTransactionEntity, parent));
        }

        return list;
    }

    @Override
    public Set<PaymentTransactionEntity> toEntity(Set<PaymentTransactionDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        Set<PaymentTransactionEntity> set = new HashSet<PaymentTransactionEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (PaymentTransactionDTO paymentTransactionDTO : dtoList) {
            set.add(toEntity(paymentTransactionDTO));
        }

        return set;
    }

    protected Set<PaymentTransactionEntity> toEntity(Set<PaymentTransactionDTO> dtoList, InvoiceEntity parent) {
        if (dtoList == null) {
            return null;
        }

        Set<PaymentTransactionEntity> set = new HashSet<PaymentTransactionEntity>(Math.max((int) (dtoList.size() / .75f) + 1, 16));
        for (PaymentTransactionDTO paymentTransactionDTO : dtoList) {
            set.add(toEntity(paymentTransactionDTO, parent));
        }

        return set;
    }

    @Override
    public Set<PaymentTransactionDTO> toDto(Set<PaymentTransactionEntity> entityList) {
        if (entityList == null) {
            return null;
        }

        Set<PaymentTransactionDTO> set = new HashSet<PaymentTransactionDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (PaymentTransactionEntity paymentTransactionEntity : entityList) {
            set.add(toDto(paymentTransactionEntity));
        }

        return set;
    }

    protected Set<PaymentTransactionDTO> toDto(Set<PaymentTransactionEntity> entityList, InvoiceDTO parent) {
        if (entityList == null) {
            return null;
        }

        Set<PaymentTransactionDTO> set = new HashSet<PaymentTransactionDTO>(Math.max((int) (entityList.size() / .75f) + 1, 16));
        for (PaymentTransactionEntity paymentTransactionEntity : entityList) {
            set.add(toDto(paymentTransactionEntity, parent));
        }

        return set;
    }

}
