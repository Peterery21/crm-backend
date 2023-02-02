package com.kodzotech.utilisateur.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest implements Serializable {
    private String email;
    private String name;
    private String username;
    private String password;
    private boolean enabled;
}