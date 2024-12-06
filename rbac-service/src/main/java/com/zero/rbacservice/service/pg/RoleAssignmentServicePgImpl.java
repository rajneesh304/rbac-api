package com.zero.rbacservice.service.pg;

import com.zero.rbacservice.exceptions.BadRequestException;
import com.zero.rbacservice.exceptions.NotFoundException;
import com.zero.rbacservice.model.dtos.RoleDto;
import com.zero.rbacservice.model.entities.Permission;
import com.zero.rbacservice.model.entities.Role;
import com.zero.rbacservice.model.entities.RoleGroup;
import com.zero.rbacservice.model.entities.User;
import com.zero.rbacservice.model.entities.UserGroup;
import com.zero.rbacservice.repositories.PermissionRepository;
import com.zero.rbacservice.repositories.RoleGroupRepository;
import com.zero.rbacservice.repositories.RoleRepository;
import com.zero.rbacservice.repositories.UserGroupRepository;
import com.zero.rbacservice.repositories.UserRepository;
import com.zero.rbacservice.service.RoleAssignmentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RoleAssignmentServicePgImpl implements RoleAssignmentService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final UserGroupRepository userGroupRepository;
    private final RoleGroupRepository roleGroupRepository;

    @Override
    public void assignRoleToUser(UUID roleId, UUID userId) {
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new NotFoundException("Role with id %s not found",roleId)
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id %s not found",userId)
        );
        if (!user.hasRole(role)) {
            user.addRole(role);
            userRepository.save(user);
        }
    }

    @Override
    public void addPermissionToRole(UUID roleId, UUID permissionId) {
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new NotFoundException("Role with id %s not found",roleId)
        );
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(
                () -> new NotFoundException("User with id %s not found",permissionId)
        );
        if(role.getRolePermissions().stream().anyMatch(p -> p.getId().equals(permission.getId()))) {
            throw new BadRequestException("Permission already added");
        }
        role.getRolePermissions().add(permission);
        roleRepository.save(role).toDto();
    }

    @Override
    public void assignUserGroupToUser(UUID userId, UUID userGroupId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id %s not found",userId)
        );

        UserGroup group = userGroupRepository.findById(userGroupId).orElseThrow(
                () -> new NotFoundException("User Group with id %s not found",userGroupId)
        );

        if (user.getUserGroups().stream().anyMatch(g -> g.getId().equals(group.getId()))) {
            throw new BadRequestException("User is already a part of the user group.");
        }
        group.getUsers().add(user);
        userGroupRepository.save(group);
    }

    @Override
    public void assignRoleToUserGroup(UUID roleId, UUID userGroupId) {
        UserGroup group = userGroupRepository.findById(userGroupId).orElseThrow(
                () -> new NotFoundException("User Group with id %s not found",userGroupId)
        );

        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new NotFoundException("Role with id %s not found",roleId)
        );

        if (group.getRolesInGroup().stream().anyMatch(r -> r.getId().equals(role.getId()))) {
            throw new BadRequestException("Role is already assigned to the user group.");
        }
        group.getRolesInGroup().add(role);
        userGroupRepository.save(group);
    }

    @Override
    public void assignRoleGroupToUser(UUID userId, UUID roleGroupId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id %s not found",userId)
        );

        RoleGroup group = roleGroupRepository.findById(roleGroupId).orElseThrow(
                () -> new NotFoundException("Role Group with id %s not found",roleGroupId)
        );

        if (user.getRoleGroups().stream().anyMatch(g -> g.getId().equals(group.getId()))) {
            throw new BadRequestException("User has already been assigned the role group.");
        }
        user.getRoleGroups().add(group);
        userRepository.save(user);
    }

    @Override
    public void addRoleToRoleGroup(UUID roleId, UUID roleGroupId) {
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new NotFoundException("Role with id %s not found",roleId)
        );

        RoleGroup group = roleGroupRepository.findById(roleGroupId).orElseThrow(
                () -> new NotFoundException("Role Group with id %s not found",roleGroupId)
        );

        if (role.getRoleGroups().stream().anyMatch(g -> g.getId().equals(group.getId()))) {
            throw new BadRequestException("Role has already been added the role group.");
        }

        group.getRoles().add(role);
        roleGroupRepository.save(group);

    }

    @Override
    public void addUserToUserGroup(UUID userId, UUID userGroupId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id %s not found",userId)
        );

        UserGroup group = userGroupRepository.findById(userGroupId).orElseThrow(
                () -> new NotFoundException("User Group with id %s not found",userId)
        );

        if (user.getUserGroups().stream().anyMatch(g-> g.getId().equals(group.getId()))) {
            throw new BadRequestException("User has already been added the user group.");
        }
        user.getUserGroups().add(group);
        userRepository.save(user);
    }
}
