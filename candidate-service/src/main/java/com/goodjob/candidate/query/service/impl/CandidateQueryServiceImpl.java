package com.goodjob.candidate.query.service.impl;

import com.goodjob.candidate.dto.CandidateResponse;
import com.goodjob.candidate.entity.Candidate;
import com.goodjob.candidate.exception.CandidateNotFoundException;
import com.goodjob.candidate.query.service.CandidateQueryService;
import com.goodjob.candidate.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the CandidateQueryService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CandidateQueryServiceImpl implements CandidateQueryService {

    private final CandidateRepository candidateRepository;

    /**
     * Finds a candidate by ID.
     *
     * @param id the candidate ID
     * @return the candidate response
     */
    @Override
    public CandidateResponse findById(String id) {
        log.info("Finding candidate by ID: {}", id);
        
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException(id));
        
        // Only return visible profiles
        if (!candidate.isProfileVisible()) {
            log.warn("Candidate profile is not visible: {}", id);
            throw new CandidateNotFoundException(id);
        }
        
        return CandidateResponse.fromEntity(candidate);
    }

    /**
     * Finds a candidate by user ID.
     *
     * @param userId the user ID
     * @return the candidate response
     */
    @Override
    public CandidateResponse findByUserId(String userId) {
        log.info("Finding candidate by user ID: {}", userId);
        
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException("User ID: " + userId));
        
        return CandidateResponse.fromEntity(candidate);
    }

    /**
     * Finds candidates by location.
     *
     * @param location the location
     * @param pageable the pagination information
     * @return the page of candidate responses
     */
    @Override
    public Page<CandidateResponse> findByLocation(String location, Pageable pageable) {
        log.info("Finding candidates by location: {}", location);
        
        Page<Candidate> candidates = candidateRepository.findByLocationContainingIgnoreCaseAndProfileVisibleTrue(
                location, pageable);
        
        return candidates.map(CandidateResponse::fromEntity);
    }

    /**
     * Finds candidates who are open to work.
     *
     * @param pageable the pagination information
     * @return the page of candidate responses
     */
    @Override
    public Page<CandidateResponse> findOpenToWork(Pageable pageable) {
        log.info("Finding candidates who are open to work");
        
        Page<Candidate> candidates = candidateRepository.findByOpenToWorkTrueAndProfileVisibleTrue(pageable);
        
        return candidates.map(CandidateResponse::fromEntity);
    }

    /**
     * Finds candidates who are open to relocate.
     *
     * @param pageable the pagination information
     * @return the page of candidate responses
     */
    @Override
    public Page<CandidateResponse> findOpenToRelocate(Pageable pageable) {
        log.info("Finding candidates who are open to relocate");
        
        Page<Candidate> candidates = candidateRepository.findByOpenToRelocateTrueAndProfileVisibleTrue(pageable);
        
        return candidates.map(CandidateResponse::fromEntity);
    }

    /**
     * Searches for candidates by keyword.
     *
     * @param keyword the keyword
     * @param pageable the pagination information
     * @return the page of candidate responses
     */
    @Override
    public Page<CandidateResponse> searchByKeyword(String keyword, Pageable pageable) {
        log.info("Searching candidates by keyword: {}", keyword);
        
        Page<Candidate> candidates = candidateRepository.findByKeyword(keyword, pageable);
        
        return candidates.map(CandidateResponse::fromEntity);
    }

    /**
     * Finds candidates with a specific skill.
     *
     * @param skill the skill
     * @param pageable the pagination information
     * @return the page of candidate responses
     */
    @Override
    public Page<CandidateResponse> findBySkill(String skill, Pageable pageable) {
        log.info("Finding candidates with skill: {}", skill);
        
        Page<Candidate> candidates = candidateRepository.findBySkillName(skill, pageable);
        
        return candidates.map(CandidateResponse::fromEntity);
    }

    /**
     * Finds candidates with experience at a specific company.
     *
     * @param company the company
     * @param pageable the pagination information
     * @return the page of candidate responses
     */
    @Override
    public Page<CandidateResponse> findByCompanyExperience(String company, Pageable pageable) {
        log.info("Finding candidates with experience at company: {}", company);
        
        Page<Candidate> candidates = candidateRepository.findByCompanyExperience(company, pageable);
        
        return candidates.map(CandidateResponse::fromEntity);
    }

    /**
     * Finds candidates with education at a specific institution.
     *
     * @param institution the institution
     * @param pageable the pagination information
     * @return the page of candidate responses
     */
    @Override
    public Page<CandidateResponse> findByEducationInstitution(String institution, Pageable pageable) {
        log.info("Finding candidates with education at institution: {}", institution);
        
        Page<Candidate> candidates = candidateRepository.findByEducationInstitution(institution, pageable);
        
        return candidates.map(CandidateResponse::fromEntity);
    }
} 