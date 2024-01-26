package com.example.alchohol.user.model.dto;

import com.example.alchohol.user.model.entity.UserEntity;
import com.example.alchohol.user.model.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {
    private Long id;
    private String userEmail;
    private String password;
    private String nickname;
    private String statement;
    private String userImage;
    private Timestamp registerAt;
    private Timestamp updatedAt;
    private UserRole userRole;

    public static User fromEntity(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getUserEmail(),
                userEntity.getPassword(),
                userEntity.getNickname(),
                userEntity.getStatement(),
                userEntity.getUserImage(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt(),
                userEntity.getUserRole()
        );
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.toString()));
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return false;
    }
}
