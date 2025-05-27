//package com.example.demo.controllers;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.example.demo.entities.Pet;
//import com.example.demo.entities.PetSighting;
//import com.example.demo.entities.User;
//import com.example.demo.services.PetService;
//import com.example.demo.services.SightingService;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/sightings")
//public class SightingController {
//
//    private final SightingService sightingService;
//
//    public SightingController(SightingService sightingService) {
//        this.sightingService = sightingService;
//    }
//
//    @PostMapping("/create_sight")
//    public ResponseEntity<?> createSight(
//            @RequestParam("message") String message,
//            @RequestParam("latitude") Double latitude,
//            @RequestParam("longitude") Double longitude,
//            @RequestParam("petId") Long petId,
//            @RequestParam("userEmail") String userEmail) {
//
//        try {
//            PetSighting newPetSighting = new PetSighting();
//            newPetSighting.setMessage(message);
//            newPetSighting.setLatitude(latitude);
//            newPetSighting.setLongitude(longitude);
//
//            PetSighting savedPetSighting = sightingService.createOrUpdatePetSighting(newPetSighting, userEmail, petId);
//
//            return ResponseEntity.ok(savedPetSighting);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el avistamiento"+ e.getMessage());
//        }
//    }
//
//
//    @PostMapping
//    public ResponseEntity<PetSighting> reportSighting(@RequestBody PetSighting sighting) {
//        PetSighting savedSighting = sightingService.saveSighting(sighting);
//        return ResponseEntity.ok(savedSighting);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<PetSighting>> getAllSightings() {
//        return ResponseEntity.ok(sightingService.getAllSightings());
//    }
//
//    @GetMapping("/sightings")
//    public List<PetSighting> getSightings(@RequestParam Long petId) {
//    	 return sightingService.getSightingsByPetId(petId);
//    }
//
//    @GetMapping("/pet/{petId}")
//    public List<PetSighting> getSightingsByPet(@PathVariable Long petId) {
//        return sightingService.getSightingsByPetId(petId);
//    }
//
//}
package com.example.demo.controllers;
import com.example.demo.dto.PetSightingDTO; // Importa el DTO
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.PetSighting;
import com.example.demo.services.SightingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sightings")
public class SightingController {

    private final SightingService sightingService;

    public SightingController(SightingService sightingService) {
        this.sightingService = sightingService;
    }



    @PostMapping("/create_sight")
    public ResponseEntity<?> createSight(@RequestBody PetSightingDTO sightingDTO) {
        try {
            PetSighting newPetSighting = new PetSighting();
            newPetSighting.setMessage(sightingDTO.getMessage());
            newPetSighting.setLatitude(sightingDTO.getLatitude());
            newPetSighting.setLongitude(sightingDTO.getLongitude());

            PetSighting savedPetSighting = sightingService.createOrUpdatePetSighting(
                    newPetSighting,
                    sightingDTO.getUserEmail(),
                    sightingDTO.getPetId()
            );

            return ResponseEntity.ok(savedPetSighting);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el avistamiento: " + e.getMessage());
        }
    }

//    @PostMapping("/create_sight")
//    public ResponseEntity<?> createSight(
//            @RequestParam("message") String message,
//            @RequestParam("latitude") Double latitude,
//            @RequestParam("longitude") Double longitude,
//            @RequestParam("petId") UUID petId,
//            @RequestParam("userEmail") String userEmail) {
//
//        try {
//            PetSighting newPetSighting = new PetSighting();
//            newPetSighting.setMessage(message);
//            newPetSighting.setLatitude(latitude);
//            newPetSighting.setLongitude(longitude);
//
//            PetSighting savedPetSighting = sightingService.createOrUpdatePetSighting(newPetSighting, userEmail, petId);
//
//            return ResponseEntity.ok(savedPetSighting);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error al guardar el avistamiento: " + e.getMessage());
//        }
//    }

    @PostMapping
    public ResponseEntity<PetSighting> reportSighting(@RequestBody PetSighting sighting) {
        PetSighting savedSighting = sightingService.saveSighting(sighting);
        return ResponseEntity.ok(savedSighting);
    }

    @GetMapping
    public ResponseEntity<List<PetSighting>> getAllSightings() {
        return ResponseEntity.ok(sightingService.getAllSightings());
    }

    @GetMapping("/sightings")
    public List<PetSighting> getSightings(@RequestParam UUID petId) {
        return sightingService.getSightingsByPetId(petId);
    }

    @GetMapping("/pet/{petId}")
    public List<PetSighting> getSightingsByPet(@PathVariable UUID petId) {
        return sightingService.getSightingsByPetId(petId);
    }
}
