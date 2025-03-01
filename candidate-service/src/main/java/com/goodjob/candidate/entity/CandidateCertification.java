package com.goodjob.candidate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing a certification associated with a candidate.
 * Contains information about the certification name, issuing organization, and validity.
 */
@Entity
@Table(name = "candidate_certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String issuingOrganization;

    @Column(length = 100)
    private String credentialId;

    @Column(length = 255)
    private String credentialUrl;

    private LocalDate issueDate;

    private LocalDate expirationDate;

    @Column(nullable = false)
    private boolean doesNotExpire;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Checks if the certification is currently valid.
     *
     * @return true if the certification is valid, false otherwise
     */
    @Transient
    public boolean isValid() {
        return doesNotExpire || (expirationDate != null && expirationDate.isAfter(LocalDate.now()));
    }

    /**
     * Returns a formatted string representation of the certification validity period.
     *
     * @return the formatted validity period string (e.g., "Issued Jan 2022 - No Expiration" or "Issued Mar 2021 - Expires Dec 2024")
     */
    @Transient
    public String getFormattedValidity() {
        if (issueDate == null) {
            return "";
        }
        
        String issued = "Issued " + issueDate.getMonth().toString().substring(0, 3) + " " + issueDate.getYear();
        String expiration = doesNotExpire ? "No Expiration" : 
                           (expirationDate != null ? "Expires " + expirationDate.getMonth().toString().substring(0, 3) + " " + expirationDate.getYear() : "");
        
        return issued + (expiration.isEmpty() ? "" : " - " + expiration);
    }
} 