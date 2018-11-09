package io.avand.service;

import io.avand.service.dto.SmsSendRequestDTO;

public interface SmsService {

    Boolean send(SmsSendRequestDTO requestDTO);

}
