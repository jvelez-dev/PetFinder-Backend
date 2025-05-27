package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Pet;
import com.example.demo.entities.User;
import com.example.demo.repositories.PetRepository;
import com.example.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    private final Path root = Paths.get("uploads");

    // Mascotas paginadas y ordenadas (más recientes primero)
    public Page<Pet> getAllPets(Pageable pageable) {
        return petRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    // Mascotas de un usuario paginadas y ordenadas (más recientes primero)
    public Page<Pet> getPetsByUserEmail(String userEmail, Pageable pageable) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return petRepository.findByOwnerOrderByCreatedAtDesc(owner, pageable);
    }
    public Page<Pet> searchPetsByName(String name, Pageable pageable) {
        return petRepository.findByNameContainingIgnoreCaseOrderByCreatedAtDesc(name, pageable);
    }
    public Pet updatePetFields(UUID id, String name, String description, Double latitude, Double longitude, Boolean isFound, String photoUrl, String userEmail) {
        System.out.println("---- [PetService] updatePetFields ----");
        System.out.println("isFound recibido en service: " + isFound);

        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        pet.setName(name);
        pet.setDescription(description);
        pet.setLatitude(latitude);
        pet.setLongitude(longitude);
        pet.setFound(isFound != null ? isFound : false);

        if (photoUrl != null) {
            pet.setPhotoUrl(photoUrl);
        }

        if (userEmail != null && !userEmail.isEmpty()) {
            User owner = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            pet.setOwner(owner);
        }

        Pet saved = petRepository.save(pet);

        System.out.println("isFound guardado en la BD: " + saved.isFound());

        return saved;
    }


//    public Pet updatePetFields(UUID id, String name, String description, Double latitude, Double longitude, Boolean isFound, String photoUrl, String userEmail) {
//        Pet pet = petRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
//
//        pet.setName(name);
//        pet.setDescription(description);
//        pet.setLatitude(latitude);
//        pet.setLongitude(longitude);
//        pet.setFound(isFound);
//
//        if (photoUrl != null) {
//            pet.setPhotoUrl(photoUrl);
//        }
//
//        // Opcional: validar que el usuario sea el dueño
//        if (userEmail != null && !userEmail.isEmpty()) {
//            User owner = userRepository.findByEmail(userEmail)
//                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//            pet.setOwner(owner);
//        }
//
//        return petRepository.save(pet);
//    }

    public Pet createOrUpdatePet(Pet pet, String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        pet.setOwner(owner);
        return petRepository.save(pet);
    }

    public void savePetImage(UUID petId, MultipartFile file) throws IOException {
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }
        Path filePath = root.resolve(petId.toString() + "_" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    public Optional<Pet> getPetById(UUID id) {
        return petRepository.findById(id);
    }

    public List<Pet> getPetsByFoundStatus(boolean isFound) {
        return petRepository.findByIsFound(isFound);
    }

    public Pet getPetByName(String name) {
        return petRepository.findByName(name);
    }

    public List<Pet> getPetsByLocation(double latitude, double longitude) {
        return petRepository.findByLatitudeAndLongitude(latitude, longitude);
    }

    public Pet updatePet(UUID id, Pet petDetails) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
        existingPet.setName(petDetails.getName());
        existingPet.setDescription(petDetails.getDescription());
        existingPet.setPhotoUrl(petDetails.getPhotoUrl());
        existingPet.setOwner(petDetails.getOwner());
        existingPet.setLatitude(petDetails.getLatitude());
        existingPet.setLongitude(petDetails.getLongitude());
        existingPet.setFound(petDetails.isFound());
        return petRepository.save(existingPet);
    }

    public void deletePet(UUID id) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
        existingPet.setDeletedAt(LocalDateTime.now());
        petRepository.save(existingPet);
    }
}
