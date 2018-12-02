package io.avand.service.impl;

import io.avand.domain.enumeration.PermissionAccess;
import io.avand.domain.enumeration.PermissionType;
import io.avand.repository.jpa.PermissionRepository;
import io.avand.service.PermissionService;
import io.avand.service.dto.PermissionDTO;
import io.avand.service.mapper.PermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<PermissionDTO> findAllByAuthorityName(String authorityName) {
        log.debug("Request to findAll permission by authorityName");
        return permissionRepository
            .findAllByAuthority_Name(authorityName)
            .stream()
            .map(permissionMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public Boolean existByAccessAndTypeAndNames(PermissionAccess access, PermissionType type, List<String> authorityNames) {
        log.debug("Request to check exist by access and type and authorityNames");
        return permissionRepository
            .existsByAccessAndTypeAndAuthority_NameIn(access, type, authorityNames);
    }
}
