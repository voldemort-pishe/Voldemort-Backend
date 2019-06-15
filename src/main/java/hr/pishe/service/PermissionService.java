package hr.pishe.service;

import hr.pishe.domain.enumeration.PermissionAccess;
import hr.pishe.service.dto.PermissionDTO;

public interface PermissionService {
    PermissionDTO findByAccess(PermissionAccess access);

}
