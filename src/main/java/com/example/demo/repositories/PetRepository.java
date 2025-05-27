package com.example.demo.repositories;

import com.example.demo.entities.Pet;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, UUID> {
    // Mascotas paginadas y ordenadas por fecha de creación descendente
    Page<Pet> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Pet> findByNameContainingIgnoreCaseOrderByCreatedAtDesc(String name, Pageable pageable);
    // Mascotas de un usuario paginadas y ordenadas por fecha de creación descendente
    Page<Pet> findByOwnerOrderByCreatedAtDesc(User owner, Pageable pageable);

    Pet findByName(String name);
    List<Pet> findByIsFound(boolean isFound);
    List<Pet> findByLatitudeAndLongitude(double latitude, double longitude);
    List<Pet> findByOwner(User owner);
}
