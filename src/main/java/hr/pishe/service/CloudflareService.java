package hr.pishe.service;

import hr.pishe.service.dto.CloudflareRequestDTO;

public interface CloudflareService {
    void createDNSRecord(CloudflareRequestDTO requestDTO);
}
