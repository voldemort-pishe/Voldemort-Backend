package io.avand.service;

import io.avand.service.dto.CityDTO;
import io.avand.service.dto.ProvinceDTO;

import java.util.List;

public interface ProvinceService {

    List<ProvinceDTO> findAll();

    ProvinceDTO findByName(String name);
}
