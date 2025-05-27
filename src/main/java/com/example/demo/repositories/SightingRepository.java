package com.example.demo.repositories;

import com.example.demo.entities.PetSighting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SightingRepository extends JpaRepository<PetSighting, UUID> {
    List<PetSighting> findByPetId(UUID petId);

    // Para soft delete
    List<PetSighting> findByDeletedAtIsNull();
}
