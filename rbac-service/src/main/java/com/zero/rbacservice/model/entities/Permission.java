package com.zero.rbacservice.model.entities;

import com.zero.rbacservice.model.dtos.DtoBridge;
import com.zero.rbacservice.model.enums.PermissionType;
import com.zero.rbacservice.model.dtos.PermissionDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Permission implements DtoBridge<PermissionDto> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "access_type")
    private PermissionType accessType;

    @Column(name = "description")
    private String description;

    @Override
    public PermissionDto toDto(){
        return new PermissionDto(
                this.id,
                this.name,
                this.accessType,
                this.description
        );
    }

}
