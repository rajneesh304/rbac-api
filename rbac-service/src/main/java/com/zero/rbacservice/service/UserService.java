package com.zero.rbacservice.service;

import com.zero.rbacservice.model.dtos.UserDto;
import com.zero.rbacservice.model.dtos.UserGroupDto;
import com.zero.rbacservice.model.rest.request.AddUser;
import com.zero.rbacservice.model.rest.request.AddUserGroup;
import com.zero.rbacservice.model.rest.response.ListResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface to list all functionalities that any user service implementation must provide.
 *
 */
public interface UserService {

    UserDto addUser(AddUser user);

    void disableUser(UUID id);

    void enableUser(UUID id);

    ListResponse<UserDto> listAllUsers(Pageable pageable);

    ListResponse<UserDto> getPaginated(Pageable pageable);

    UserDto findUserById(UUID id);

    UserDto findUserByUsername(String name);

    UserDto authenticate(String username, String password);

    UserGroupDto addUserGroup(AddUserGroup userGroup);

    UserGroupDto getUserGroupById(UUID id);

    ListResponse<UserGroupDto> getUserGroupsPaginated(Pageable pageable);

    ListResponse<UserDto> listUsersInGroup(UUID id);
}
