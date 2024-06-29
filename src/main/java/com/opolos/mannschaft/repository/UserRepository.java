package com.opolos.mannschaft.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.opolos.mannschaft.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);



    // Page<User> findByTitleContaining(String title, Pageable pageable);
    
    // List<User> findByTitleContaining(String title, Sort sort);
    
}
