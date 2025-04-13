package com.goodjob.company.domain.company.entity;

import com.goodjob.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing a company.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "companies")
public class Company extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer companyId;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "company_size")
    private Integer companySize;
    
    @Column(name = "state", length = 50)
    private String state;
    
    @Column(name = "country", length = 10)
    private String country;
    
    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "zip_code", length = 20)
    private String zipCode;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    
    @Column(name = "url")
    private String url;

    @OneToOne(mappedBy = "company")
    private CompanyMetric companyMetric;

    @OneToMany(mappedBy = "company")
    private Set<CompanySpeciality> companySpecialities;

    @OneToMany(mappedBy = "company")
    private Set<CompanyIndustry> companyIndustries;
} 