package gr.aueb.cf.flux.dto;

import java.time.LocalDateTime;

public record UserReadOnlyDTO(
        String id,
        String username,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
