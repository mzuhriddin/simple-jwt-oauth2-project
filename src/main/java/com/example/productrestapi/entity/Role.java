package com.example.productrestapi.entity;

import com.example.productrestapi.entity.enums.Permission;
import com.example.productrestapi.entity.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Permission> permission;

    public Role(RoleEnum roleName, List<Permission> permission) {
        this.roleName = roleName;
        this.permission = permission;
    }
}
