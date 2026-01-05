package gr.aueb.cf.flux.dto;

import gr.aueb.cf.flux.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionReadOnlyDTO(
        String id,

        String walletId,

        String categoryId,

        TransactionType type,

        BigDecimal amount,

        String description,

        LocalDateTime date,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
