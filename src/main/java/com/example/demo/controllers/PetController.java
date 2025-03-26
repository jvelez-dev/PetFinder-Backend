package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Pet;
import com.example.demo.entities.User;
import com.example.demo.services.PetService;
import com.example.demo.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;
    
    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadPetImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
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

    
    @GetMapping("/by-user")
    public List<Pet> getPetsByUserEmail(@RequestParam String email) {
        System.out.println("Email recibido en backend: " + email);
        List<Pet> pets = petService.getPetsByUserEmail(email);
        System.out.println("Mascotas encontradas: " + pets);
        return pets;
    }


    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
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
    
    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody Pet pet) {
        Pet updatedPet = petService.updatePet(id, pet);
        return ResponseEntity.ok(updatedPet);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
         petService.deletePet(id);
         return ResponseEntity.noContent().build();
    }
}
