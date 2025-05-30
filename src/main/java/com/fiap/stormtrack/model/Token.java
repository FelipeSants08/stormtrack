package com.fiap.stormtrack.model;

public record Token(
        String token,
        Long expiration,
        String type,
        String role
) {
}
