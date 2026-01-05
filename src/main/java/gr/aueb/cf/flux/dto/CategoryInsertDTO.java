package gr.aueb.cf.flux.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryInsertDTO(
        @NotBlank(message = "Category name is required")
        @Size(min = 3, max = 30, message = "Category name must be between 3 and 30 characters")
        String name,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description
        ) {
}
