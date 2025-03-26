package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Pet;
import com.example.demo.entities.PetSighting;

@Repository
public interface SightingRepository extends JpaRepository<PetSighting, Long> {
	List<PetSighting> findByPetId(Long petId);
	
    List<PetSighting> findByPet(Pet pet);

}
