package com.example.demo.services;

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

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private final Path root = Paths.get("uploads");
    
    
    public Pet createOrUpdatePet(Pet pet, String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        pet.setOwner(owner);
        return petRepository.save(pet);
    }
    
    public void savePetImage(Long petId, MultipartFile file) throws IOException {
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }
        Path filePath = root.resolve(petId + "_" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    // Crear o actualizar una mascota
    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    // Obtener todas las mascotas
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // Obtener una mascota por su ID
    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    // Obtener mascotas encontradas
    public List<Pet> getPetsByFoundStatus(boolean isFound) {
        return petRepository.findByIsFound(isFound);
    }

    // Buscar mascotas por su nombre
    public Pet getPetByName(String name) {
        return petRepository.findByName(name);
    }

    // Buscar mascotas por ubicación (usando latitud y longitud)
    public List<Pet> getPetsByLocation(double latitude, double longitude) {
        return petRepository.findByLatitudeAndLongitude(latitude, longitude);
    }
    
    public Pet updatePet(Long id, Pet petDetails) {
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
    
    public void deletePet(Long id) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
        petRepository.delete(existingPet);
    }
    
    public List<Pet> getPetsByUserEmail(String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return petRepository.findByOwner(owner);
    }
}
