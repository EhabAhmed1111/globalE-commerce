package com.ihab.e_commerce.service.user.auth;


import com.ihab.e_commerce.rest.request.AuthenticationRequest;
import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
import com.ihab.e_commerce.rest.request.RegisterRequest;
import com.ihab.e_commerce.data.enums.Role;
import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.data.repo.UserRepository;
import com.ihab.e_commerce.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public GlobalSuccessResponse register(RegisterRequest request) {
        /* todo handel conflict email*/
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                //TODO we should get it dynamically
                .role(request.getRole())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return GlobalSuccessResponse.builder()
                .data(jwtToken)
                .build();
    }
    public GlobalSuccessResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()->new UsernameNotFoundException("User Not Found")
        );
        var jwtToken = jwtService.generateToken(user);
        return GlobalSuccessResponse.builder()
                .data(jwtToken)
                .build();
    }
}
