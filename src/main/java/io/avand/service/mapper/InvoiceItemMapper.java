package io.avand.service.mapper;

import io.avand.domain.entity.jpa.InvoiceItemEntity;
import io.avand.service.dto.InvoiceItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceItemMapper extends EntityMapper<InvoiceItemDTO, InvoiceItemEntity> {

    @Override
    @Mapping(source = "invoice.id", target = "invoiceId")
    InvoiceItemDTO toDto(InvoiceItemEntity entity);
}
