package hr.pishe.service.mapper;

import hr.pishe.domain.entity.jpa.InvoiceEntity;
import hr.pishe.service.dto.InvoiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {InvoiceItemMapper.class})
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, InvoiceEntity> {

    @Override
    @Mapping(source = "company.id",target = "companyId")
    InvoiceDTO toDto(InvoiceEntity entity);
}
