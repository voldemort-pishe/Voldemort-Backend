package hr.pishe.service;

import hr.pishe.service.dto.ProvinceDTO;

import java.util.List;

public interface ProvinceService {

    List<ProvinceDTO> findAll();

    ProvinceDTO findByName(String name);
}
