package com.smartvillage.service;

import com.smartvillage.entity.User;
import com.smartvillage.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository =
                userRepository;

        this.passwordEncoder =
                passwordEncoder;
    }


    // =========================================
    // GET ALL USERS
    // =========================================

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }


    // =========================================
    // GET USER BY ID
    // =========================================

    public Optional<User> getUserById(
            Integer id) {

        return userRepository.findById(id);
    }


    // =========================================
    // REGISTER USER
    // =========================================

    public User registerUser(User user) {

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        return userRepository.save(user);
    }


    // =========================================
    // FIND USER BY EMAIL
    // =========================================

    public Optional<User> findByEmail(
            String email) {

        return userRepository.findByEmail(email);
    }


    // =========================================
    // LOGIN
    // =========================================

    public Optional<User> loginUser(
            String email,
            String password) {

        Optional<User> userOptional =
                userRepository.findByEmail(email);


        if (userOptional.isPresent()) {

            User user =
                    userOptional.get();


            if (
                passwordEncoder.matches(
                    password,
                    user.getPassword()
                )
            ) {

                return Optional.of(user);
            }
        }


        return Optional.empty();
    }


    // =========================================
    // DELETE USER
    // =========================================

    public void deleteUser(Integer id) {

        userRepository.deleteById(id);
    }
}