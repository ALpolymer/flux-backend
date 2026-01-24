package gr.aueb.cf.flux.api;

import gr.aueb.cf.flux.authentication.AuthenticationService;
import gr.aueb.cf.flux.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.flux.dto.AuthRequestDTO;
import gr.aueb.cf.flux.dto.AuthResponseDTO;
import gr.aueb.cf.flux.dto.UserInsertDTO;
import gr.aueb.cf.flux.dto.UserReadOnlyDTO;
import gr.aueb.cf.flux.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User registration and login endpoints")

public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<UserReadOnlyDTO> register(@Valid @RequestBody UserInsertDTO dto) throws AppObjectAlreadyExists {
        UserReadOnlyDTO savedUser = userService.saveUser(dto);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Authenticate user and get JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO requestDTO){
        AuthResponseDTO responseDTO = authenticationService.authenticate(requestDTO);
        return  new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
