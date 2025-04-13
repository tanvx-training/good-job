package com.goodjob.company.domain.company.entity;

import com.goodjob.common.entity.BaseEntity;
import com.goodjob.company.domain.company.entity.id.CompanyIndustryId;
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
@Table(name = "company_industries")
public class CompanyIndustry extends BaseEntity {

  @EmbeddedId
  private CompanyIndustryId companyIndustryId;

  @ManyToOne
  @MapsId("companyId")
  @JoinColumn(name = "company_id")
  private Company company;

  public Integer getIndustryId() {
    return companyIndustryId.getIndustryId();
  }
}
