package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.InvoiceItemEntity;
import hr.pishe.service.dto.InvoiceItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceItemMapper extends EntityMapper<InvoiceItemDTO, InvoiceItemEntity> {

    @Override
    @Mapping(source = "invoice.id", target = "invoiceId")
    InvoiceItemDTO toDto(InvoiceItemEntity entity);
}
