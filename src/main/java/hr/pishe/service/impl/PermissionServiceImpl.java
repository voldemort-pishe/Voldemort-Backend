package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.PermissionEntity;
import hr.pishe.domain.enumeration.PermissionAccess;
import hr.pishe.repository.jpa.PermissionRepository;
import hr.pishe.service.PermissionService;
import hr.pishe.service.dto.PermissionDTO;
import hr.pishe.service.mapper.PermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class PermissionServiceImpl implements PermissionService {

    private final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionRepository permissionRepository,
                                 PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public PermissionDTO findByAccess(PermissionAccess access) {
        log.debug("Request to find permissions by access : {}", access);
        PermissionEntity permissionEntity = permissionRepository.findByAccess(access);
        return permissionMapper.toDto(permissionEntity);
    }
}
