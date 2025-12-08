package com.ihab.e_commerce.service.user.main;

import com.ihab.e_commerce.data.mapper.ProductMapper;
import com.ihab.e_commerce.data.model.Product;
import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.data.repo.UserRepository;
import com.ihab.e_commerce.exception.GlobalNotFoundException;
import com.ihab.e_commerce.rest.response.ProductResponse;
import com.ihab.e_commerce.rest.response.WishlistResponse;
import com.ihab.e_commerce.service.jwt.JwtService;
import com.ihab.e_commerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // TODO(create UserDto)
    // todo return the product with user

    public UserDetails getUserByEmail(String userName){
        return userDetailsService.loadUserByUsername(userName);
    }

    public User loadCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        return userRepository.findByEmail(userName).orElseThrow(
                ()-> new GlobalNotFoundException("there is no user with email: " + userName)
        );
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new GlobalNotFoundException("There is no user with id: " + id)
        );
    }

    public void deleteUser(Long id){
        userRepository.delete(getUserById(id));
    }

    // todo need to change from user to UpdateUserRequest
    public User updateUser(User updateUserRequest, Long existedUserId){
        User user = getUserById(existedUserId);
        return updateUser(user, updateUserRequest);
    }

    public User updateCurrentUser(User updateUserRequest){
        User user = loadCurrentUser();
        return updateUser(updateUserRequest, user.getId());
    }

    private User updateUser(User existedUser, User updateRequest){
        existedUser.setFirstName(updateRequest.getFirstName());
        existedUser.setLastName(updateRequest.getLastName());
        return existedUser;
    }

    // for security
    public User changePassword(Long userId, String oldPassword, String newPassword){

        User user = getUserById(userId);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        return userRepository.save(user);
    }

    public User changeCurrentPassword(String oldPassword, String newPassword){
        User user = loadCurrentUser();
        return changePassword(user.getId(), oldPassword, newPassword);
    }



    // todo(for admin we can get allUser or update userRole)
}
