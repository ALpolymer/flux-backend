package gr.aueb.cf.flux.dto;

import gr.aueb.cf.flux.core.enums.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionInsertDTO(
        @NotNull(message = "Wallet ID is required")
        String walletId,
        @NotNull(message = "Category ID is required")
        String categoryId,

        @NotNull(message = "Transaction type is required")
        TransactionType type,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        @Digits(integer = 10, fraction = 2, message = "Amount must have up to 10 integer digits and 2 decimal places")
        BigDecimal amount,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotNull(message = "Date is required")
        LocalDateTime date
) {
}
