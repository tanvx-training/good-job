package com.goodjob.speciality.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a speciality.
 */
@Entity
@Table(name = "specialities", schema = "public", 
       indexes = {
           @Index(name = "public_specialities_pkey", columnList = "speciality_id", unique = true),
           @Index(name = "public_specialities_name_key", columnList = "speciality_name", unique = true)
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "specialities_speciality_id_seq")
    @SequenceGenerator(name = "specialities_speciality_id_seq", sequenceName = "specialities_speciality_id_seq", allocationSize = 1)
    @Column(name = "speciality_id")
    private Integer id;

    @Column(name = "speciality_name", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;
} 