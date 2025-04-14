package com.goodjob.job.application;

public class MessageProperties {

    public static class Posting {
        // Job fields
        public static final String JOB_ID_INVALID = "posting.jobId.invalid";
        public static final String COMPANY_ID_REQUIRED = "posting.companyId.required";
        public static final String COMPANY_ID_INVALID = "posting.companyId.invalid";
        public static final String TITLE_REQUIRED = "posting.title.required";
        public static final String TITLE_TOO_LONG = "posting.title.tooLong";
        public static final String DESCRIPTION_REQUIRED = "posting.description.required";
        public static final String WORK_TYPE_INVALID = "posting.workType.invalid";
        public static final String EDUCATION_LEVEL_INVALID = "posting.educationLevel.invalid";
        public static final String EXPERIENCE_LEVEL_INVALID = "posting.experienceLevel.invalid";
        public static final String REMOTE_ALLOWED_REQUIRED = "posting.remoteAllowed.required";
        public static final String LOCATION_REQUIRED = "posting.location.required";
        public static final String ZIP_CODE_INVALID = "posting.zipCode.invalid";
        public static final String SKILL_DESC_REQUIRED = "posting.skillDesc.required";
        public static final String EXPIRY_INVALID = "posting.expiry.invalid";
        public static final String CLOSED_TIME_INVALID = "posting.closedTime.invalid";
        public static final String JOB_STATUS_INVALID = "posting.jobStatus.invalid";

        // JobSalary fields
        public static final String MIN_SALARY_INVALID = "posting.minSalary.invalid";
        public static final String MED_SALARY_INVALID = "posting.medSalary.invalid";
        public static final String MAX_SALARY_INVALID = "posting.maxSalary.invalid";
        public static final String MAX_SALARY_LESS_THAN_MIN = "posting.maxSalary.lessThanMin";
        public static final String PAY_PERIOD_REQUIRED = "posting.payPeriod.required";
        public static final String PAY_PERIOD_INVALID = "posting.payPeriod.invalid";
        public static final String CURRENCY_REQUIRED = "posting.currency.required";
        public static final String CURRENCY_INVALID = "posting.currency.invalid";
        public static final String COMPENSATION_TYPE_INVALID = "posting.compensationType.invalid";

        // JobBenefit, JobIndustry, JobSkill
        public static final String BENEFIT_ID_INVALID = "posting.benefitId.invalid";
        public static final String INDUSTRY_ID_INVALID = "posting.industryId.invalid";
        public static final String SKILL_ID_INVALID = "posting.skillId.invalid";

        // Additional conditions
        public static final String TOO_MANY_BENEFITS = "posting.tooManyBenefits";
        public static final String TOO_MANY_INDUSTRIES = "posting.tooManyIndustries";
        public static final String TOO_MANY_SKILLS = "posting.tooManySkills";
    }
}
