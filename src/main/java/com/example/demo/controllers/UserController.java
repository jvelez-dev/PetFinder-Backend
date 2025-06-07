//package com.example.demo.controllers;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.web.bind.annotation.*;
//
//import com.example.demo.entities.User;
//import com.example.demo.services.UserService;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/users")
//
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping
//    public ResponseEntity<User> registerUser(@RequestBody User user) {
//        User savedUser = userService.saveUser(user);
//        return ResponseEntity.ok(savedUser);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
//        User updatedUser = userService.updateUser(id, user);
//        return ResponseEntity.ok(updatedUser);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//         userService.deleteUser(id);
//         return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/user")
//    public ResponseEntity<?> getUser(Authentication authentication) {
//        if (authentication == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated");
//        }
//        return ResponseEntity.ok(authentication.getPrincipal());
//    }
//
//    @GetMapping("/userEmail")
//    public ResponseEntity<?> getUserEmail(Authentication authentication) {
//        if (authentication == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated");
//        }
//        OAuth2User user = (OAuth2User) authentication.getPrincipal();
//        String email = user.getAttribute("email");
//
//        return ResponseEntity.ok(Map.of("email", email));
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
//        SecurityContextHolder.clearContext();
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//
//        Cookie cookie = new Cookie("JSESSIONID", null);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//
//        return ResponseEntity.ok().build();
//    }
//}
package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
         userService.deleteUser(id);
         return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated");
        }
        return ResponseEntity.ok(authentication.getPrincipal());
    }

    @GetMapping("/userEmail")
    public ResponseEntity<?> getUserEmail(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated");
        }
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        String email = user.getAttribute("email");

        return ResponseEntity.ok(Map.of("email", email));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
//////package com.example.demo.controllers;
//////
//////import org.springframework.http.HttpStatus;
//////import org.springframework.http.ResponseEntity;
//////import org.springframework.security.core.Authentication;
//////import org.springframework.security.oauth2.core.user.OAuth2User;
//////import org.springframework.security.core.context.SecurityContextHolder;
//////import org.springframework.web.bind.annotation.*;
//////
//////import com.example.demo.entities.User;
//////import com.example.demo.services.UserService;
//////
//////import jakarta.servlet.http.Cookie;
//////import jakarta.servlet.http.HttpServletRequest;
//////import jakarta.servlet.http.HttpServletResponse;
//////import jakarta.servlet.http.HttpSession;
//////
//////import java.util.List;
//////import java.util.Map;
//////import java.util.UUID;
//////
//////@RestController
//////@RequestMapping("/api/users")
//////public class UserController {
//////
//////    private final UserService userService;
//////
//////    public UserController(UserService userService) {
//////        this.userService = userService;
//////    }
//////
//////    @PostMapping
//////    public ResponseEntity<User> registerUser(@RequestBody User user) {
//////        User savedUser = userService.saveUser(user);
//////        return ResponseEntity.ok(savedUser);
//////    }
//////
//////    @GetMapping
//////    public ResponseEntity<List<User>> getAllUsers() {
//////        return ResponseEntity.ok(userService.getAllUsers());
//////    }
//////
//////    @PutMapping("/{id}")
//////    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
//////        User updatedUser = userService.updateUser(id, user);
//////        return ResponseEntity.ok(updatedUser);
//////    }
//////
//////    @DeleteMapping("/{id}")
//////    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
//////        userService.deleteUser(id);
//////        return ResponseEntity.noContent().build();
//////    }
//////
//////    @GetMapping("/user")
//////    public ResponseEntity<?> getUser(Authentication authentication) {
//////        if (authentication == null) {
//////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated");
//////        }
//////        return ResponseEntity.ok(authentication.getPrincipal());
//////    }
//////
//////    @GetMapping("/userEmail")
//////    public ResponseEntity<?> getUserEmail(Authentication authentication) {
//////        if (authentication == null) {
//////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated");
//////        }
//////        OAuth2User user = (OAuth2User) authentication.getPrincipal();
//////        String email = user.getAttribute("email");
//////
//////        return ResponseEntity.ok(Map.of("email", email));
//////    }
//////
//////    @PostMapping("/logout")
//////    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
//////        SecurityContextHolder.clearContext();
//////        HttpSession session = request.getSession(false);
//////        if (session != null) {
//////            session.invalidate();
//////        }
//////
//////        Cookie cookie = new Cookie("JSESSIONID", null);
//////        cookie.setHttpOnly(true);
//////        cookie.setSecure(false); // Ajusta según entorno (true en producción con HTTPS)
//////        cookie.setPath("/");
//////        cookie.setMaxAge(0);
//////        response.addCookie(cookie);
//////
//////        return ResponseEntity.ok().build();
//////    }
//////}
