package com.smartvillage.controller;

import com.smartvillage.dto.LoginResponse;
import com.smartvillage.dto.UserResponse;
import com.smartvillage.entity.User;
import com.smartvillage.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // =========================================
    // GET ALL USERS
    // =========================================

    @GetMapping
    public List<UserResponse> getAllUsers() {

        return userService.getAllUsers()
                .stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }


    // =========================================
    // GET USER BY ID
    // =========================================

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Integer id) {

        return userService.getUserById(id)
                .map(this::convertToUserResponse)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }


    // =========================================
    // REGISTER USER
    // =========================================

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
            @RequestBody User user) {

        User savedUser =
                userService.registerUser(user);

        return ResponseEntity.ok(
                convertToUserResponse(savedUser)
        );
    }


    // =========================================
    // LOGIN USER
    // =========================================

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody Map<String, String> loginData) {

        String email =
                loginData.get("email");

        String password =
                loginData.get("password");


        Optional<User> user =
                userService.loginUser(
                        email,
                        password
                );


        if (user.isPresent()) {

            User loggedInUser =
                    user.get();


            LoginResponse response =
                    new LoginResponse(

                            loggedInUser.getUserId(),

                            loggedInUser.getName(),

                            loggedInUser.getEmail(),

                            loggedInUser.getPhone(),

                            loggedInUser.getRole()
                    );


            return ResponseEntity.ok(response);
        }


        return ResponseEntity
                .status(401)
                .body("Invalid email or password");
    }


    // =========================================
    // DELETE USER
    // =========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Integer id) {

        Optional<User> user =
                userService.getUserById(id);


        if (user.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        userService.deleteUser(id);


        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }


    // =========================================
    // CONVERT USER → SAFE RESPONSE
    // =========================================

    private UserResponse convertToUserResponse(
            User user) {

        return new UserResponse(

                user.getUserId(),

                user.getName(),

                user.getEmail(),

                user.getPhone(),

                user.getRole()
        );
    }
}