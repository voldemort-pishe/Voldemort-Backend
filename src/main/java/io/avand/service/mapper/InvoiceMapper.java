package io.avand.service.mapper;

import io.avand.domain.entity.jpa.InvoiceEntity;
import io.avand.service.dto.InvoiceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {PaymentTransactionMapper.class})
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, InvoiceEntity> {
}
