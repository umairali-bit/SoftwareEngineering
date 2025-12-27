package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.enums.Permissions;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.enums.Role;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.dialect.aggregate.DB2AggregateSupport;
import org.hibernate.grammars.hql.HqlParser;
import org.modelmapper.internal.bytebuddy.build.Plugin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.FrameworkServlet;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

//    if permissions permission in database are needed
//    @ElementCollection(fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//    private Set<Permissions> permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        works if permissions are needed instanceof DB but since we hardcoded permissions with roles we don't need this
//        Set<SimpleGrantedAuthority> authorities = roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                .collect(Collectors.toSet());
//
//        permissions.forEach(
//                permission -> authorities.add(new SimpleGrantedAuthority(permission.name())));


        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.forEach(role -> {
           Set<SimpleGrantedAuthority> roleAuthorities = PermissionMapping.getAuthoritiesForRole(role);
           authorities.addAll(roleAuthorities);
           authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        });




        return authorities;
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    @NonNull
    public String getUsername() {
        return this.email;
    }
}
