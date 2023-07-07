package com.umc_spring.Heart_Hub.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.umc_spring.Heart_Hub.constant.entity.BaseEntity;
import com.umc_spring.Heart_Hub.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 45)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 1)
    private String gender;

    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String userImgUrl;

    @Column(nullable = false, length = 45)
    private String nickname;

    @Column(nullable = false, length = 1)
    private String marketingStatus;

    //private LocalDateTime birth;

    @Column(nullable = false, length = 1)
    private String status;

    private Long mate;

    @ElementCollection(fetch = FetchType.EAGER) // 테이블 생성, 부모 Entity에 의해 관리.
    @Builder.Default
    private List<String> roles = new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)// x -> new SimpleGrantedAuthority(x)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
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
