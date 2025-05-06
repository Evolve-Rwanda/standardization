package com.example.springauth.repositories;

import com.example.springauth.models.jpa.AppSetupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppSetupUserRepository extends JpaRepository<AppSetupUser, Long>{
    Optional<AppSetupUser> findByUsername(String username);
}
