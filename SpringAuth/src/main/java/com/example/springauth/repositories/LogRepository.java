package com.example.springauth.repositories;

import com.example.springauth.models.jpa.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogRepository extends JpaRepository<Log, String> {
}
