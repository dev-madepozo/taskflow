package com.madepozo.backend.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details) {
}
