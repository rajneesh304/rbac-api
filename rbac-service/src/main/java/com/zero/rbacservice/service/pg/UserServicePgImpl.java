package com.zero.rbacservice.service.pg;

import com.zero.rbacservice.constants.IntegrityErrorUtil;
import com.zero.rbacservice.exceptions.AuthenticationFailedException;
import com.zero.rbacservice.exceptions.NotFoundException;
import com.zero.rbacservice.model.dtos.BridgeUtil;
import com.zero.rbacservice.model.dtos.UserDto;
import com.zero.rbacservice.model.dtos.UserGroupDto;
import com.zero.rbacservice.model.entities.User;
import com.zero.rbacservice.model.entities.UserGroup;
import com.zero.rbacservice.model.enums.Status;
import com.zero.rbacservice.model.projections.UserId;
import com.zero.rbacservice.model.rest.request.AddUser;
import com.zero.rbacservice.model.rest.request.AddUserGroup;
import com.zero.rbacservice.model.rest.response.ListResponse;
import com.zero.rbacservice.repositories.UserGroupRepository;
import com.zero.rbacservice.repositories.UserRepository;
import com.zero.rbacservice.service.UserService;
import com.zero.rbacservice.util.EncryptionUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServicePgImpl implements UserService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    @Override
    public UserDto addUser(AddUser user) {
        try {
            return userRepository.save(user.toDataModelObject()).toDto();
        } catch (DataIntegrityViolationException ex) {
            throw IntegrityErrorUtil.formatIntegrityExceptions(ex);
        }
    }

    @Override
    @Transactional
    public void disableUser(UUID id) {
        UserId user = userRepository.findByIdAndStatus(id, Status.ACTIVE, UserId.class).orElseThrow(
                () -> new NotFoundException(String.format("User with id %s not found or already disabled", id))
        );
        userRepository.disableUser(user.getId());
    }

    @Override
    @Transactional
    public void enableUser(UUID id) {
        UserId user = userRepository.findByIdAndStatus(id, Status.INACTIVE, UserId.class).orElseThrow(
                () -> new NotFoundException(String.format("User with id %s not found or already enabled", id))
        );
        userRepository.enableUser(user.getId());
    }

    @Override
    public ListResponse<UserDto> listAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return BridgeUtil.buildPaginatedResponse(users);
    }

    @Override
    public ListResponse<UserDto> getPaginated(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return BridgeUtil.buildPaginatedResponse(page);
    }

    @Override
    public UserDto findUserById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException(String.format("No user found for id %s", id))
                )
                .toDto();
    }

    @Override
    public UserDto findUserByUsername(String name) {
        return userRepository
                .findByUsername(name)
                .orElseThrow(
                        () -> new NotFoundException(String.format("User name %s does not exist", name))
                )
                .toDto();
    }

    @Override
    public UserDto authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("No user found for the username: %s", username)
        );
        if(EncryptionUtil.verifyPassword(password, user.getPassword())){
            return user.toDto();
        } else {
            throw new AuthenticationFailedException("Invalid Username/password");
        }
    }

    @Override
    public UserGroupDto addUserGroup(AddUserGroup userGroup) {
        try {
            return userGroupRepository.save(userGroup.toDataModelObject()).toDto();
        } catch (DataIntegrityViolationException ex) {
            throw IntegrityErrorUtil.formatIntegrityExceptions(ex);
        }
    }

    @Override
    public UserGroupDto getUserGroupById(UUID id) {
        return userGroupRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No user group found for ID %s", id)
        ).toDto();
    }

    @Override
    public ListResponse<UserGroupDto> getUserGroupsPaginated(Pageable pageable) {
        Page<UserGroup> groups = userGroupRepository.findAll(pageable);
        return BridgeUtil.buildPaginatedResponse(groups);
    }

    @Override
    public ListResponse<UserDto> listUsersInGroup(UUID id) {
        List<User> users = userRepository.findAllByUserGroups_Id(id);
        return BridgeUtil.buildResponse(users);
    }
}
