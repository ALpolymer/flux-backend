package gr.aueb.cf.flux.api;

import gr.aueb.cf.flux.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.flux.dto.UserInsertDTO;
import gr.aueb.cf.flux.dto.UserReadOnlyDTO;
import gr.aueb.cf.flux.service.UserService;
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
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserReadOnlyDTO> register(@RequestBody UserInsertDTO dto) throws AppObjectAlreadyExists {
        UserReadOnlyDTO savedUser = userService.saveUser(dto);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
