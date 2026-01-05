package gr.aueb.cf.flux.dto;
import jakarta.validation.constraints.*;
import gr.aueb.cf.flux.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionUpdateDTO(
        String  walletId,

        String categoryId,

        TransactionType type,

        @Positive(message = "Amount must be positive")
        @Digits(integer = 10, fraction = 2, message = "Amount must have up to 10 integer digits and 2 decimal places")
        BigDecimal amount,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        LocalDateTime date
) {
}
