package ar.com.bbva.crud.security.dto;

public record AuthResponse(
    String token,
    String username
) {
}
