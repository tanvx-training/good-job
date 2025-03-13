package com.goodjob.skill.repository;

import com.goodjob.skill.entity.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Skill entity.
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Page<Skill> findAllByDeleteFlg(boolean deleteFlg, Pageable pageable);

    /**
     * Find a skill by its abbreviation.
     *
     * @param abbreviation the skill abbreviation
     * @return an Optional containing the skill if found, or empty if not found
     */
    Optional<Skill> findByAbbreviationAndDeleteFlg(String abbreviation, boolean deleteFlg);

    /**
     * Check if a skill exists by its abbreviation.
     *
     * @param abbreviation the skill abbreviation
     * @return true if the skill exists, false otherwise
     */
    boolean existsByAbbreviationAndDeleteFlg(String abbreviation, boolean deleteFlg);

    /**
     * Check if a skill exists by its name.
     *
     * @param name the skill name
     * @return true if the skill exists, false otherwise
     */
    boolean existsByNameAndDeleteFlg(String name, boolean deleteFlg);
} 