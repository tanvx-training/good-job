package com.goodjob.industry.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing an industry category.
 */
@Entity
@Table(name = "industries", schema = "public", 
       indexes = {
           @Index(name = "public_industries_pkey", columnList = "industry_id", unique = true),
           @Index(name = "public_industries_industry_name_key", columnList = "industry_name", unique = true)
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Industry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "industries_industry_id_seq")
    @SequenceGenerator(name = "industries_industry_id_seq", sequenceName = "industries_industry_id_seq", allocationSize = 1)
    @Column(name = "industry_id")
    private Integer id;

    @Column(name = "industry_name", unique = true, nullable = false)
    private String name;
} 