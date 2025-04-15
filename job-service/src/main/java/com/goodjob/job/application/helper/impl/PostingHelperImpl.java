package com.goodjob.job.application.helper.impl;

import com.goodjob.common.util.DateTimeUtils;
import com.goodjob.job.application.helper.JobHelper;
import com.goodjob.job.application.helper.PostingHelper;
import com.goodjob.job.domain.job.dto.JobBenefitView;
import com.goodjob.job.domain.job.dto.JobCompanyView;
import com.goodjob.job.domain.job.dto.JobIndustryView;
import com.goodjob.job.domain.job.dto.JobSkillView;
import com.goodjob.job.domain.job.entity.*;
import com.goodjob.job.infrastructure.common.enums.EducationLevel;
import com.goodjob.job.infrastructure.common.enums.ExperienceLevel;
import com.goodjob.job.infrastructure.common.enums.PayPeriod;
import com.goodjob.job.infrastructure.common.enums.WorkType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostingHelperImpl implements PostingHelper {

    private final JobHelper jobHelper;

    @Override
    public String generateJobDescription(Job job) {

        Objects.requireNonNull(job);
        StringBuilder description = new StringBuilder();

        // Tiêu đề công việc
        description.append("<h2>").append(job.getTitle()).append("</h2>");

        // Thông tin công ty
        JobCompanyView company = jobHelper.getCompany(job.getCompanyId());
        Objects.requireNonNull(company);
        description.append("<p><strong>Công ty:</strong> ").append(company.getName()).append("</p>");
        description.append("<p><strong>Địa chỉ:</strong> ").append(company.getAddress()).append("</p>");

        // Mô tả công việc
        description.append("<h3>Mô tả công việc</h3>");
        description.append("<p>").append(job.getDescription()).append("</p>");

        // Thông tin lương
        JobSalary salary = job.getJobSalary();
        Objects.requireNonNull(salary);
        description.append("<h3>Mức lương</h3><p>");
        description.append(salary.getMinSalary()).append(" - ").append(salary.getMaxSalary());
        description.append("Trung bình: ").append(salary.getMedSalary());
        description.append(" ").append(salary.getCurrency())
                .append(" / ").append(getPayPeriodName(salary.getPayPeriod()))
                .append("</p>");

        // Lợi ích
        Set<JobBenefitView> benefits = jobHelper.getBenefits(job.getJobBenefits().stream()
                .map(JobBenefit::getBenefitId)
                .toList());
        description.append("<h3>Lợi ích</h3><ul>");
        for (JobBenefitView benefit : benefits) {
            description.append("<li>").append(benefit.getBenefitName()).append("</li>");
        }
        description.append("</ul>");

        // Ngành nghề
        Set<JobIndustryView> industries = jobHelper.getIndustries(job.getJobIndustries().stream()
                .map(JobIndustry::getIndustryId)
                .toList());
        description.append("<h3>Ngành nghề</h3><ul>");
        for (JobIndustryView industry : industries) {
            description.append("<li>").append(industry.getIndustryName()).append("</li>");
        }
        description.append("</ul>");

        // Kỹ năng
        Set<JobSkillView> skills = jobHelper.getSkills(job.getJobSkills().stream()
                .map(JobSkill::getSkillId)
                .toList());
        description.append("<h3>Kỹ năng yêu cầu</h3><ul>");
        for (JobSkillView skill : skills) {
            description.append("<li>").append(skill.getName()).append("</li>");
        }
        description.append("</ul>");

        // Thông tin bổ sung
        description.append("<h3>Thông tin bổ sung</h3>");
        description.append("<p><strong>Loại công việc:</strong> ").append(getWorkTypeName(job.getWorkType())).append("</p>");
        description.append("<p><strong>Trình độ học vấn:</strong> ").append(getEducationLevelName(job.getEducationLevel())).append("</p>");
        description.append("<p><strong>Kinh nghiệm:</strong> ").append(getExperienceLevelName(job.getExperienceLevel())).append("</p>");
        description.append("<p><strong>Làm việc từ xa:</strong> ").append(job.getRemoteAllowed() ? "Có" : "Không").append("</p>");
        if (job.getLocation() != null) {
            description.append("<p><strong>Địa điểm:</strong> ").append(job.getLocation()).append("</p>");
        }
        if (job.getZipCode() != null) {
            description.append("<p><strong>Mã bưu điện:</strong> ").append(job.getZipCode()).append("</p>");
        }
        if (job.getSkillsDesc() != null) {
            description.append("<p><strong>Mô tả kỹ năng:</strong> ").append(job.getSkillsDesc()).append("</p>");
        }
        description.append("<p><strong>Hạn đăng tuyển:</strong> ").append(DateTimeUtils.fromTimestamp(job.getExpiry())).append("</p>");

        return description.toString();
    }

    private String getPayPeriodName(Integer payPeriod) {
        return PayPeriod.fromValue(payPeriod).getDescription();
    }

    private String getWorkTypeName(Integer workType) {
        return WorkType.fromValue(workType).getDescription();
    }

    private String getEducationLevelName(Integer educationLevel) {
        return EducationLevel.fromValue(educationLevel).getDescription();
    }

    private String getExperienceLevelName(Integer experienceLevel) {
        return ExperienceLevel.fromValue(experienceLevel).getDescription();
    }
}
