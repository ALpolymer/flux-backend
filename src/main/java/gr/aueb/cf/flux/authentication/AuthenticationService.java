package gr.aueb.cf.flux.authentication;

import gr.aueb.cf.flux.dto.AuthNestedResponseDTO;
import gr.aueb.cf.flux.dto.AuthRequestDTO;
import gr.aueb.cf.flux.dto.AuthResponseDTO;
import gr.aueb.cf.flux.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO authenticate( AuthRequestDTO dto){

        Authentication authentication =   authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );
        User user = (User) authentication.getPrincipal();

        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());

        AuthNestedResponseDTO userDto = new AuthNestedResponseDTO(
                user.getActualUsername(),
                user.getUsername()
        );

        return new AuthResponseDTO(token, userDto);
    }
}
