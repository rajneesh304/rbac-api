package com.zero.rbacservice.service;

import com.zero.rbacservice.model.dtos.RoleDto;
import com.zero.rbacservice.model.dtos.RoleGroupDto;
import com.zero.rbacservice.model.rest.request.AddRole;
import com.zero.rbacservice.model.rest.request.AddRoleGroup;
import com.zero.rbacservice.model.rest.response.ListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    ListResponse<RoleDto> getAllRoles(Pageable pageable);

    ListResponse<RoleGroupDto> getRoleAllGroupsPaginated(Pageable pageable);

    RoleDto getRoleById(UUID id);

    ListResponse<RoleDto> searchRoleByName(String name, Pageable pageable);

    RoleDto createRole(AddRole role);

    RoleGroupDto addRoleGroup(AddRoleGroup roleGroup);

    RoleGroupDto getRoleGroupById(UUID id);

    ListResponse<RoleDto> getRolesInRoleGroup(UUID id);
}
