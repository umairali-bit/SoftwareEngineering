package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.LoginDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.SignUpDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.UserDto;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.exceptions.ResourceNotFoundException;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Override
    @NonNull
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new BadCredentialsException("User not found with username: " + username));
    }

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with userId: " + userId));
    }

    public UserEntity  getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
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


    public UserEntity save(UserEntity newUser) {
        return userRepository.save(newUser);
    }
}
