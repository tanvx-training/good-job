package com.goodjob.candidate.query.service;

import com.goodjob.candidate.common.dto.CandidateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for candidate query operations.
 */
public interface CandidateQueryService {

    /**
     * Finds a candidate by ID.
     *
     * @param id the candidate ID
     * @return the candidate response
     */
    CandidateResponse findById(Integer id);

    /**
     * Finds a candidate by user ID.
     *
     * @param userId the user ID
     * @return the candidate response
     */
    CandidateResponse findByUserId(String userId);

    /**
     * Finds candidates by location.
     *
     * @param location the location
     * @param pageable the pagination information
     * @return a page of candidate responses
     */
    Page<CandidateResponse> findByLocation(String location, Pageable pageable);

    /**
     * Finds candidates who are open to work.
     *
     * @param pageable the pagination information
     * @return a page of candidate responses
     */
    Page<CandidateResponse> findOpenToWork(Pageable pageable);

    /**
     * Finds candidates who are open to relocate.
     *
     * @param pageable the pagination information
     * @return a page of candidate responses
     */
    Page<CandidateResponse> findOpenToRelocate(Pageable pageable);

    /**
     * Searches for candidates by keyword.
     *
     * @param keyword the keyword
     * @param pageable the pagination information
     * @return a page of candidate responses
     */
    Page<CandidateResponse> searchByKeyword(String keyword, Pageable pageable);

    /**
     * Finds candidates with a specific skill.
     *
     * @param skillName the skill name
     * @param pageable the pagination information
     * @return a page of candidate responses
     */
    Page<CandidateResponse> findBySkill(String skillName, Pageable pageable);

    /**
     * Finds candidates with experience at a specific company.
     *
     * @param companyName the company name
     * @param pageable the pagination information
     * @return a page of candidate responses
     */
    Page<CandidateResponse> findByCompanyExperience(String companyName, Pageable pageable);

    /**
     * Finds candidates with education at a specific institution.
     *
     * @param institution the institution name
     * @param pageable the pagination information
     * @return a page of candidate responses
     */
    Page<CandidateResponse> findByEducationInstitution(String institution, Pageable pageable);
} 