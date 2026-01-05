package gr.aueb.cf.flux.dto;

import java.time.LocalDateTime;

public record CategoryReadOnlyDTO(
        String id,
        String userId,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
