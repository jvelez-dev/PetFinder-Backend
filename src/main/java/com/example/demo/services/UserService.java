//package com.example.demo.services;
//
//import org.springframework.stereotype.Service;
//
//import com.example.demo.entities.User;
//import com.example.demo.repositories.UserRepository;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public User saveUser(User user) {
//    	user.setRole("USER");
//        return userRepository.save(user);
//    }
//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User getUserById(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
//    }
//
//    public User updateUser(Long id, User userDetails) {
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
//
//        existingUser.setEmail(userDetails.getEmail());
//        existingUser.setUsername(userDetails.getUsername());
//        return userRepository.save(existingUser);
//    }
//
//    public void deleteUser(Long id) {
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
//        userRepository.delete(existingUser);
//    }
//}
package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        user.setRole("USER");
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findByDeletedAtIsNull(); // Solo usuarios activos
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public User updateUser(UUID id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        existingUser.setEmail(userDetails.getEmail());
        existingUser.setUsername(userDetails.getUsername());
        return userRepository.save(existingUser);
    }

    public void deleteUser(UUID id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        existingUser.setDeletedAt(LocalDateTime.now());
        userRepository.save(existingUser);
    }

    // Método adicional para buscar por email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }
}
