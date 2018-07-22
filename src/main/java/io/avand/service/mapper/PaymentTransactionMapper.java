package io.avand.service.mapper;

import io.avand.domain.entity.jpa.PaymentTransactionEntity;
import io.avand.service.dto.PaymentTransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {})
public interface PaymentTransactionMapper extends EntityMapper<PaymentTransactionDTO, PaymentTransactionEntity> {

    @Override
    @Mapping(source = "invoice.id",target = "invoiceId")
    PaymentTransactionDTO toDto(PaymentTransactionEntity entity);
}
