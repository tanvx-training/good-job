package com.goodjob.company.domain.company.entity.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Data
@Embeddable
public class CompanySpecialityId implements Serializable {

  private Integer companyId;
  private Integer specialityId;
}
