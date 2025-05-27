//package com.example.demo.repositories;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.example.demo.entities.User;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//	    Optional<User> findByEmail(String email);
//}
package com.example.demo.repositories;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);
	List<User> findByDeletedAtIsNull();
}
