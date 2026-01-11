package gr.aueb.cf.flux.dto;

public record AuthResponseDTO(
        String token,
        AuthNestedResponseDTO user
) {
}
