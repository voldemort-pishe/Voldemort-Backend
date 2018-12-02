package io.avand.service;

import io.avand.domain.enumeration.PermissionAccess;
import io.avand.service.dto.PermissionDTO;

import java.util.List;

public interface PermissionService {
    PermissionDTO findByAccess(PermissionAccess access);

}
