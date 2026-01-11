package gr.aueb.cf.flux.dto;

import jakarta.validation.constraints.NotNull;

public record AuthRequestDTO(
        @NotNull String email,
        @NotNull String password
) { }
