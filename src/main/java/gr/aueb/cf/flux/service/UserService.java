package gr.aueb.cf.flux.service;

import gr.aueb.cf.flux.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.flux.dto.UserInsertDTO;
import gr.aueb.cf.flux.dto.UserReadOnlyDTO;
import gr.aueb.cf.flux.mapper.UserMapper;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO) throws AppObjectAlreadyExists {
        if(userRepository.findByUsername(userInsertDTO.username()).isPresent()){
            throw new AppObjectAlreadyExists("USER", "user with username" +  userInsertDTO.username() + "already exists");
        }

        if(userRepository.findByEmail(userInsertDTO.email()).isPresent()){
            throw new AppObjectAlreadyExists("EMAIL", "user with email" + userInsertDTO.email() + "already exists");
        }

        User user =  userMapper.mapToUserEntity(userInsertDTO);

        User savedUser = userRepository.save(user);

        log.info("User with email={} saved", userInsertDTO.email());

        return userMapper.mapToUserReadOnlyDto(savedUser);
    }
}
