package io.avand.service.mapper;

import io.avand.domain.PaymentTransactionEntity;
import io.avand.service.dto.PaymentTransactionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {InvoiceMapper.class})
public interface PaymentTransactionMapper extends EntityMapper<PaymentTransactionDTO, PaymentTransactionEntity> {
}
