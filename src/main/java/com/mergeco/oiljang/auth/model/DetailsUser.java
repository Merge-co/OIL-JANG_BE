package com.mergeco.oiljang.auth.model;

import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DetailsUser implements UserDetails {

    private int userCode;
    private String id;
    private String pwd;
    private String name;
    private String email;
    private String birthDate;
    private String gender;
    private String phone;
    private List<UserRole> role;
    private User user;

    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(role != null) {
            role.forEach(role -> {
                authorities.add(() -> role.getRole().toString());
            });
            return authorities;
        }
        return new ArrayList<>();
    }

    public User getUser() {
        return user;
    }
    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
