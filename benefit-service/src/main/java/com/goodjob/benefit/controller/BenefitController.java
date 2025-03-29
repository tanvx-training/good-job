package com.goodjob.benefit.controller;

import com.goodjob.benefit.command.dto.CreateBenefitCommand;
import com.goodjob.benefit.command.dto.UpdateBenefitCommand;
import com.goodjob.benefit.command.service.BenefitCommandService;
import com.goodjob.benefit.query.dto.BenefitQuery;
import com.goodjob.benefit.query.dto.BenefitView;
import com.goodjob.benefit.query.service.BenefitQueryService;
import com.goodjob.common.dto.response.ApiResponse;
import com.goodjob.common.dto.response.PageResponseDTO;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Controller for benefit-related endpoints.
 */
@RestController
@RequestMapping("/api/v1/benefits")
@RequiredArgsConstructor
public class BenefitController {

  private final BenefitCommandService benefitCommandService;
  private final BenefitQueryService benefitQueryService;

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_BENEFIT')")
  public ResponseEntity<ApiResponse<PageResponseDTO<BenefitView>>> getAllBenefits(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "20") Integer size,
      @RequestParam(value = "sort", defaultValue = "benefitId,asc") String sort) {

    PageResponseDTO<BenefitView> benefits = benefitQueryService.getAllBenefits(
        BenefitQuery.builder()
            .page(page)
            .size(size)
            .sort(sort)
            .build()
    );
    return ResponseEntity.ok(ApiResponse.success(benefits));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('READ_BENEFIT')")
  public ResponseEntity<ApiResponse<BenefitView>> getBenefitById(@PathVariable Integer id) {
    BenefitView benefit = benefitQueryService.getBenefitById(id);
    return ResponseEntity.ok(ApiResponse.success(benefit));
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('CREATE_BENEFIT')")
  public ResponseEntity<Void> createBenefit(@Valid @RequestBody CreateBenefitCommand command) {

    Integer benefitId = benefitCommandService.createBenefit(command);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(benefitId)
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('UPDATE_BENEFIT')")
  public ResponseEntity<Void> updateBenefit(
      @PathVariable Integer id,
      @Valid @RequestBody UpdateBenefitCommand command) {
    benefitCommandService.updateBenefit(id, command);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/batch")
  @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ_BENEFIT')")
  public ResponseEntity<ApiResponse<List<BenefitView>>> getBatchBenefits(@RequestParam("ids") String ids) {
    List<Integer> idList = Arrays.stream(ids.split(","))
        .map(Integer::parseInt)
        .toList();
    return ResponseEntity.ok(ApiResponse.success(benefitQueryService.getAllByIdList(idList)));
  }
} 