package com.fiap.stormtrack.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Credentials(
        @Email
        @NotBlank
        String email,

        @NotNull
        String password
) {}
