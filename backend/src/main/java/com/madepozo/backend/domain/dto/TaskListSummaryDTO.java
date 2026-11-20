package com.madepozo.backend.domain.dto;

import java.util.UUID;

public record TaskListSummaryDTO(
        UUID id,
        String title,
        String description,
        Integer count) {

}
