package com.mergeco.oiljang.auth.model;

import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DetailsUser implements UserDetails {

    private int userCode;
    private String nickname;
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
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (role != null) {
            role.forEach(userRole -> {
                authorities.add(new SimpleGrantedAuthority(userRole.getRole().toString()));
            });
        }

        return authorities;
    }

    public User getUser() {
        return user;
    }

    public DetailsUser(User user) {
        this.userCode = user.getUserCode();
        this.nickname = user.getNickname();
        this.id = user.getId();
        this.pwd = user.getPwd();
        this.name = user.getName();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.role = new ArrayList<>();
        this.role.add(user.getRole());
        this.user = user;
        this.authorities = getAuthorities();
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
