package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.LoginDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.SignUpDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.UserDto;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public UserDto singUp(@Valid SignUpDTO inputSignUp) {
        Optional<UserEntity> user = userRepository.findByEmail(inputSignUp.getEmail());
        if(user.isPresent()) {
            throw new UsernameNotFoundException("Email already in use" + inputSignUp.getEmail());
        }

        UserEntity creatingUser = modelMapper.map(inputSignUp, UserEntity.class);
        creatingUser.setPassword(passwordEncoder.encode(inputSignUp.getPassword()));

        UserEntity savingUser = userRepository.save(creatingUser);
        return modelMapper.map(savingUser, UserDto.class);



    }

    public String login(LoginDTO inputLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(inputLogin.getEmail(), inputLogin.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        assert user != null;
        return jwtService.generateJwtToken(user);





    }
}
