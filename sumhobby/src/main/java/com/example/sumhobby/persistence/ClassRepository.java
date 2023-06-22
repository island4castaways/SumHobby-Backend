package com.example.sumhobby.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sumhobby.model.ClassEntity;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, String>{

}
