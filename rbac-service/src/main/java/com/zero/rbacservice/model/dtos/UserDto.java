package com.zero.rbacservice.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(name = "User")
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String username;
    private UUID id;
    private String name;
    private String email;
    private String phone;
}
