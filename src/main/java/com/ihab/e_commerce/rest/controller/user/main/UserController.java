package com.ihab.e_commerce.rest.controller.user.main;

import com.ihab.e_commerce.data.model.User;
import com.ihab.e_commerce.rest.response.GlobalSuccessResponse;
import com.ihab.e_commerce.service.user.main.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<GlobalSuccessResponse> getCurrentUser() {
        User user = userService.loadCurrentUser();
        return ResponseEntity.ok(new GlobalSuccessResponse("User found", user));
    }

    // This for admin
    @DeleteMapping("/{userId}")
    public ResponseEntity<GlobalSuccessResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new GlobalSuccessResponse("User deleted", null));
    }

    @PutMapping("/{currentUserId}")
    public ResponseEntity<GlobalSuccessResponse> updateUser(@PathVariable Long currentUserId, @RequestBody User updatReqUser) {
        User user = userService.updateUser(updatReqUser, currentUserId);
        return ResponseEntity.ok(new GlobalSuccessResponse("User updated successfully", user));
    }

    // todo create more user functionality

}
