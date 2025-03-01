package com.goodjob.skill.repository;

import com.goodjob.skill.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Skill entity.
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    /**
     * Find a skill by its abbreviation.
     *
     * @param abbreviation the skill abbreviation
     * @return an Optional containing the skill if found, or empty if not found
     */
    Optional<Skill> findByAbbreviation(String abbreviation);

    /**
     * Check if a skill exists by its abbreviation.
     *
     * @param abbreviation the skill abbreviation
     * @return true if the skill exists, false otherwise
     */
    boolean existsByAbbreviation(String abbreviation);

    /**
     * Check if a skill exists by its name.
     *
     * @param name the skill name
     * @return true if the skill exists, false otherwise
     */
    boolean existsByName(String name);
} 