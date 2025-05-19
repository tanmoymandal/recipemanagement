package com.example.recipemanagement.repository;

import com.example.recipemanagement.model.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {
}
