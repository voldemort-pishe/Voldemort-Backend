package io.avand.service.mapper;

import io.avand.domain.InvoiceEntity;
import io.avand.service.dto.InvoiceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PaymentTransactionMapper.class, UserMapper.class})
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, InvoiceEntity> {
}
