package com.example.demo.controllers;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.example.demo.entities.Pet;
import com.example.demo.entities.User;
import com.example.demo.services.PetService;
import com.example.demo.services.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadPetImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        try {
            petService.savePetImage(id, file);
            return ResponseEntity.ok("Imagen subida exitosamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPet(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("isFound") Boolean isFound,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("userEmail") String userEmail) {

        try {
            String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
            Path filePath = Paths.get("uploads/" + fileName);
            Files.write(filePath, photo.getBytes());

            String photoUrl = "http://localhost:8080/uploads/" + fileName;

            Pet newPet = new Pet();
            newPet.setName(name);
            newPet.setDescription(description);
            newPet.setLatitude(latitude);
            newPet.setLongitude(longitude);
            newPet.setFound(isFound);
            newPet.setPhotoUrl(photoUrl);

            Pet savedPet = petService.createOrUpdatePet(newPet, userEmail);

            return ResponseEntity.ok(savedPet);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen");
        }
    }

    // Mascotas paginadas y ordenadas por fecha de creación descendente
//    @GetMapping
//    public Page<Pet> getAllPets(
//            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
//    ) {
//        return petService.getAllPets(pageable);
//    }
    @GetMapping
    public Page<Pet> getAllPets(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Pet> pets = petService.getAllPets(pageable);
        pets.forEach(pet -> System.out.println("[DEBUG] Controller: " + pet.getName() + " | photoUrl: " + pet.getPhotoUrl()));
        return pets;
    }

    @GetMapping("/search")
    public Page<Pet> searchPetsByName(
            @RequestParam String name,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return petService.searchPetsByName(name, pageable);
    }


    // Mascotas de un usuario paginadas y ordenadas por fecha de creación descendente
    @GetMapping("/by-user")
    public Page<Pet> getPetsByUserEmail(
            @RequestParam String email,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return petService.getPetsByUserEmail(email, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable UUID id) {
        Optional<Pet> pet = petService.getPetById(id);
        return pet.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/found/{status}")
    public ResponseEntity<List<Pet>> getPetsByFoundStatus(@PathVariable boolean status) {
        List<Pet> pets = petService.getPetsByFoundStatus(status);
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Pet> getPetByName(@PathVariable String name) {
        Pet pet = petService.getPetByName(name);
        return pet != null ? new ResponseEntity<>(pet, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/location")
    public ResponseEntity<List<Pet>> getPetsByLocation(
            @RequestParam double latitude, @RequestParam double longitude) {
        List<Pet> pets = petService.getPetsByLocation(latitude, longitude);
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }
//
//    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> updatePet(
//            @PathVariable UUID id,
//            @RequestParam("name") String name,
//            @RequestParam("description") String description,
//            @RequestParam("latitude") Double latitude,
//            @RequestParam("longitude") Double longitude,
//            @RequestParam("isFound") Boolean isFound,
//            @RequestParam(value = "photo", required = false) MultipartFile photo,
//            @RequestParam("userEmail") String userEmail
//    ) {
//        try {
//            String photoUrl = null;
//            if (photo != null && !photo.isEmpty()) {
//                String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
//                Path filePath = Paths.get("uploads/" + fileName);
//                Files.write(filePath, photo.getBytes());
//                photoUrl = "http://localhost:8080/uploads/" + fileName;
//            }
//
//            Pet updatedPet = petService.updatePetFields(
//                    id, name, description, latitude, longitude, isFound, photoUrl, userEmail
//            );
//
//            return ResponseEntity.ok(updatedPet);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la mascota");
//        }
//    }
@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> updatePet(
        @PathVariable UUID id,
        @RequestParam("name") String name,
        @RequestParam("description") String description,
        @RequestParam("latitude") Double latitude,
        @RequestParam("longitude") Double longitude,
        @RequestParam("isFound") Boolean isFound,
        @RequestParam(value = "photo", required = false) MultipartFile photo,
        @RequestParam("userEmail") String userEmail
) {
    // PRINTS para depuración
    System.out.println("---- [PetController] updatePet ----");
    System.out.println("id: " + id);
    System.out.println("name: " + name);
    System.out.println("description: " + description);
    System.out.println("latitude: " + latitude);
    System.out.println("longitude: " + longitude);
    System.out.println("isFound: " + isFound);
    System.out.println("photo: " + (photo != null ? photo.getOriginalFilename() : "null"));
    System.out.println("userEmail: " + userEmail);

    try {
        String photoUrl = null;
        if (photo != null && !photo.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
            Path filePath = Paths.get("uploads/" + fileName);
            Files.write(filePath, photo.getBytes());
            photoUrl = "http://localhost:8080/uploads/" + fileName;
        }

        Pet updatedPet = petService.updatePetFields(
                id, name, description, latitude, longitude, isFound, photoUrl, userEmail
        );

        return ResponseEntity.ok(updatedPet);
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la mascota");
    }
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable UUID id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}
