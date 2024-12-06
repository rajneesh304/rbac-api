package com.zero.rbacservice.service.pg;

import com.zero.rbacservice.constants.IntegrityErrorUtil;
import com.zero.rbacservice.exceptions.NotFoundException;
import com.zero.rbacservice.model.dtos.BridgeUtil;
import com.zero.rbacservice.model.dtos.RoleDto;
import com.zero.rbacservice.model.dtos.RoleGroupDto;
import com.zero.rbacservice.model.entities.Role;
import com.zero.rbacservice.model.entities.RoleGroup;
import com.zero.rbacservice.model.rest.request.AddRole;
import com.zero.rbacservice.model.rest.request.AddRoleGroup;
import com.zero.rbacservice.model.rest.response.ListResponse;
import com.zero.rbacservice.repositories.RoleGroupRepository;
import com.zero.rbacservice.repositories.RoleRepository;
import com.zero.rbacservice.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleServicePgImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleGroupRepository roleGroupRepository;

    @Override
    public ListResponse<RoleDto> getAllRoles(Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(pageable);
        return BridgeUtil.buildPaginatedResponse(roles);
    }

    @Override
    public ListResponse<RoleGroupDto> getRoleAllGroupsPaginated(Pageable pageable) {
        Page<RoleGroup> groups = roleGroupRepository.findAll(pageable);
        return BridgeUtil.buildPaginatedResponse(groups);
    }

    @Override
    public RoleDto getRoleById(UUID id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Role with id %s not found",id)
        ).toDto();
    }

    @Override
    public ListResponse<RoleDto> searchRoleByName(String name, Pageable pageable) {
        Page<Role> roles = roleRepository.findByNameContaining(name, pageable);
        return BridgeUtil.buildPaginatedResponse(roles);
    }

    @Override
    public RoleDto createRole(AddRole role) {
        try {
            return roleRepository.save(role.toDataModelObject()).toDto();
        } catch (DataIntegrityViolationException ex) {
            throw IntegrityErrorUtil.formatIntegrityExceptions(ex);
        }
    }

    @Override
    public RoleGroupDto addRoleGroup(AddRoleGroup roleGroup) {
        try{
            return roleGroupRepository.save(roleGroup.toDataModelObject()).toDto();
        } catch (DataIntegrityViolationException ex) {
            throw IntegrityErrorUtil.formatIntegrityExceptions(ex);
        }
    }

    @Override
    public RoleGroupDto getRoleGroupById(UUID id) {
        return roleGroupRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No role group found for id: %s", id)
        ).toDto();
    }

    @Override
    public ListResponse<RoleDto> getRolesInRoleGroup(UUID id) {
        List<Role> roles = roleRepository.findAllByRoleGroups_Id(id);
        return BridgeUtil.buildResponse(roles);

    }
}
