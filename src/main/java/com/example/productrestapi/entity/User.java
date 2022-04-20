package com.example.productrestapi.entity;

import com.example.productrestapi.entity.enums.Permission;
import com.example.productrestapi.entity.enums.Provider;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Builder.Default
    @Column(nullable = false)
    private boolean accountNonExpired = true, accountNonLocked = true, credentialsNonExpired = true, enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        for (Permission permissionEnum : role.getPermission()) {
            authorities.add(new SimpleGrantedAuthority(permissionEnum.name()));
        }
        return authorities;
    }

}