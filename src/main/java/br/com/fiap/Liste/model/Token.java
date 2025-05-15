package br.com.fiap.Liste.model;

public record Token(
    String token,
    Long expiration,
    String type,
    String role
) {}
