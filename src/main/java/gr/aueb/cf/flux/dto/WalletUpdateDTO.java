package gr.aueb.cf.flux.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record WalletUpdateDTO(
        @NotBlank
        @Size(min = 3, max = 30, message = "Wallet name must be between 3 and 30 characters")
        String name,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotNull
        @Digits(integer = 10, fraction = 2, message = "Balance must have up to 10 integer digits and 2 decimal places")
        BigDecimal balance
) {
}
