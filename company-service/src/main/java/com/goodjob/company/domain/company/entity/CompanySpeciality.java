package com.goodjob.company.domain.company.entity;

import com.goodjob.common.domain.entity.BaseEntity;
import com.goodjob.company.domain.company.entity.id.CompanySpecialityId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "company_specialities")
public class CompanySpeciality extends BaseEntity {

  @EmbeddedId
  private CompanySpecialityId companySpecialityId;

  @ManyToOne
  @MapsId("companyId")
  @JoinColumn(name = "company_id")
  private Company company;

  public Integer getSpecialityId() {
    return companySpecialityId.getSpecialityId();
  }
}
