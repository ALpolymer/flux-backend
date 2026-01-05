package gr.aueb.cf.flux.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletReadOnlyDTO(
        String id,
        String userId,
        String name,
        String description,
        BigDecimal balance,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
