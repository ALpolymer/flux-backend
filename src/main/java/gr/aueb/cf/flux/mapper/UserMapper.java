package gr.aueb.cf.flux.mapper;

import gr.aueb.cf.flux.core.enums.Role;
import gr.aueb.cf.flux.dto.UserInsertDTO;
import gr.aueb.cf.flux.dto.UserReadOnlyDTO;
import gr.aueb.cf.flux.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User mapToUserEntity(UserInsertDTO dto){
        User user = new User();

        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.SIMPLE_USER);

        return user;
    }


    public UserReadOnlyDTO mapToUserReadOnlyDto(User user){
        return new UserReadOnlyDTO(
                user.getUuid(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
