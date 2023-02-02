package com.kodzotech.utilisateur.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse implements Serializable {
    private Long id;
    private String username;
    private String name;
    private String password;
    private String email;
    private Instant created;
    private boolean enabled;
}
