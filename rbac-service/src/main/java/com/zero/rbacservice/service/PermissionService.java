package com.zero.rbacservice.service;

import com.zero.rbacservice.model.enums.PermissionType;
import com.zero.rbacservice.model.dtos.PermissionDto;
import com.zero.rbacservice.model.rest.request.AddPermission;
import com.zero.rbacservice.model.rest.response.ListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    PermissionDto create(AddPermission permission);

    ListResponse<PermissionDto> getAllPermissions(Pageable pageable);

    PermissionDto getPermissionById(UUID id);

    PermissionDto getPermissionByName(String name);

    ListResponse<PermissionDto> getPermissionByAccessTypes(List<PermissionType> permissionTypes, Pageable pageable);
}
