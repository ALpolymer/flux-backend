package gr.aueb.cf.flux.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record WalletInsertDTO(
        @NotBlank(message = "Wallet name is required")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String name,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotNull(message = "Balance is required")
        @Digits(integer = 10, fraction = 2, message = "Balance must have up to 10 integer digits and 2 decimal places")
        BigDecimal balance
) {
}
