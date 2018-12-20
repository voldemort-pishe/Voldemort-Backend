package io.avand.service;

import io.avand.service.dto.GoogleSearchItemDTO;

import java.util.List;
import java.util.Map;

public interface GoogleService {
    Map<String, List<GoogleSearchItemDTO>> search(String query);
}
