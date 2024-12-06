package com.zero.rbacservice.model.rest.request;

import com.zero.rbacservice.model.entities.Permission;
import com.zero.rbacservice.model.enums.PermissionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddPermission {
    @NotEmpty(message = "Permission name cannot be empty")
    @Size(min = 5, max = 50, message = "Permission name must be between [5-50] characters length")
    private String name;
    private PermissionType accessType;
    @Size(max = 255, message = "Permission name must be less than 255 characters")
    private String description;

    public Permission toDataModelObject(){
        return Permission.builder()
                .name(name)
                .description(description)
                .accessType(accessType)
                .build();
    }

}
