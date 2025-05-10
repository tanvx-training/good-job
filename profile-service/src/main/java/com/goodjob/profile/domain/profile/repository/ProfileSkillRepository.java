package com.goodjob.profile.domain.profile.repository;

import com.goodjob.profile.domain.profile.entity.Profile;
import com.goodjob.profile.domain.profile.entity.ProfileSkill;
import com.goodjob.profile.domain.profile.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for ProfileSkill entity.
 */
@Repository
public interface ProfileSkillRepository extends JpaRepository<ProfileSkill, Long> {
    
    List<ProfileSkill> findByProfile(Profile profile);
    
    List<ProfileSkill> findByProfileProfileId(Long profileId);
    
    Optional<ProfileSkill> findByProfileAndSkill(Profile profile, Skill skill);
    
    boolean existsByProfileAndSkill(Profile profile, Skill skill);
    
    void deleteByProfileProfileId(Long profileId);
} 