package io.avand.service;

import io.avand.service.dto.CloudflareRequestDTO;

public interface CloudflareService {
    Boolean createDNSRecord(CloudflareRequestDTO requestDTO);
}
