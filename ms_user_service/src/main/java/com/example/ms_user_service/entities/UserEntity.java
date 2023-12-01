package com.example.ms_user_service.entities;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String email;

    private String address;

    private String phone;
    @Getter
    private String password;
    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(columnDefinition = "float8 default 0 check(balance >= 0)")
    private Double balance;

    public UserEntity(String email, String address, String phone, String password, Role role, Double balance) {
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.balance = balance;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return authorities;

    }

    @Override
    public String getUsername() {
        return email;
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