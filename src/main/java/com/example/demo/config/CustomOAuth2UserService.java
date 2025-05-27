package com.example.demo.config;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String email = oauth2User.getAttribute("email");

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            // Puedes actualizar info si quieres
        } else {
            user = new User();
            user.setEmail(email);
            user.setUsername(email); // o extraer otro atributo del OAuth2User
            user.setRole("USER");
            userRepository.save(user);
        }

        return oauth2User;
    }
}
