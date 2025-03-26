package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Pet;
import com.example.demo.entities.User;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Pet findByName(String name);
    
    List<Pet> findByIsFound(boolean isFound);

    
    List<Pet> findByLatitudeAndLongitude(double latitude, double longitude);
    
    List<Pet> findByOwner(User owner);

}
