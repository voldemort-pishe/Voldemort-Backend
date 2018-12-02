package io.avand.service;

import io.avand.domain.enumeration.PermissionAccess;
import io.avand.domain.enumeration.PermissionType;
import io.avand.service.dto.PermissionDTO;

import java.util.List;

public interface PermissionService {
    List<PermissionDTO> findAllByAuthorityName(String authorityName);

    Boolean existByAccessAndTypeAndNames(PermissionAccess access, PermissionType type, List<String> authorityNames);
}
