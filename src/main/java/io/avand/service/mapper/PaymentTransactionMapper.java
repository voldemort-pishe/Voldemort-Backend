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

@Mapper(componentModel = "spring",uses = {})
public interface PaymentTransactionMapper extends EntityMapper<PaymentTransactionDTO, PaymentTransactionEntity> {
}
