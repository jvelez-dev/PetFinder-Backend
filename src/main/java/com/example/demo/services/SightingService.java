//package com.example.demo.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.entities.Pet;
//import com.example.demo.entities.PetSighting;
//import com.example.demo.entities.User;
//import com.example.demo.repositories.PetRepository;
//import com.example.demo.repositories.SightingRepository;
//import com.example.demo.repositories.UserRepository;
//
//import java.util.List;
//
//@Service
//public class SightingService {
//
//	@Autowired
//    private PetRepository petRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private final SightingRepository sightingRepository;
//
//    public SightingService(SightingRepository sightingRepository) {
//        this.sightingRepository = sightingRepository;
//    }
//
//    public PetSighting createOrUpdatePetSighting(PetSighting petSighting, String userEmail, Long petId) {
//        User user = userRepository.findByEmail(userEmail)
//            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//        petSighting.setReportedBy(user);
//        Pet pet = petRepository.findById(petId)
//                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
//        petSighting.setPet(pet);
//        return sightingRepository.save(petSighting);
//    }
//
//    public PetSighting saveSighting(PetSighting sighting) {
//        return sightingRepository.save(sighting);
//    }
//
//    public List<PetSighting> getAllSightings() {
//        return sightingRepository.findAll();
//    }
//
//    public List<PetSighting> getSightingsByPetId(Long petId) {
//        return sightingRepository.findByPetId(petId);
//    }
//
//}
package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Pet;
import com.example.demo.entities.PetSighting;
import com.example.demo.entities.User;
import com.example.demo.repositories.PetRepository;
import com.example.demo.repositories.SightingRepository;
import com.example.demo.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SightingService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    private final SightingRepository sightingRepository;

    public SightingService(SightingRepository sightingRepository) {
        this.sightingRepository = sightingRepository;
    }

    public PetSighting createOrUpdatePetSighting(PetSighting petSighting, String userEmail, UUID petId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        petSighting.setReportedBy(user);

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        petSighting.setPet(pet);

        return sightingRepository.save(petSighting);
    }

    public PetSighting saveSighting(PetSighting sighting) {
        return sightingRepository.save(sighting);
    }

    public List<PetSighting> getAllSightings() {
        return sightingRepository.findAll();
    }

    public List<PetSighting> getSightingsByPetId(UUID petId) {
        return sightingRepository.findByPetId(petId);
    }
    public void deleteSighting(UUID id) {
        PetSighting sighting = sightingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avistamiento no encontrado"));
        sighting.setDeletedAt(LocalDateTime.now());
        sightingRepository.save(sighting);
    }

}
