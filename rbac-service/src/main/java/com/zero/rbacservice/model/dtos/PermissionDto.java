package com.zero.rbacservice.model.dtos;

import com.zero.rbacservice.model.enums.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private UUID id;
    private String name;
    private PermissionType accessType;
    private String description;
}
