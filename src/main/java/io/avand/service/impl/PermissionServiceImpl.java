package io.avand.service.impl;

import io.avand.domain.entity.jpa.PermissionEntity;
import io.avand.domain.enumeration.PermissionAccess;
import io.avand.repository.jpa.PermissionRepository;
import io.avand.service.PermissionService;
import io.avand.service.dto.PermissionDTO;
import io.avand.service.mapper.PermissionMapper;
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
