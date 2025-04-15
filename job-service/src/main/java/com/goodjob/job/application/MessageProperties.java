package com.goodjob.job.application;

public class MessageProperties {

    public static class JobField {
        public static final String JOB_ID = "jobId";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String WORK_TYPE = "workType";
        public static final String EDUCATION_LEVEL = "educationLevel";
        public static final String EXPERIENCE_LEVEL = "experienceLevel";
        public static final String LOCATION = "location";
        public static final String ZIP_CODE = "zipCode";
        public static final String SKILL_DESC = "skillDesc";
        public static final String EXPIRY = "expiry";
        public static final String JOB_STATUS = "jobStatus";
        public static final String COMPANY_ID = "companyId";
        public static final String JOB_BENEFITS = "jobBenefits";
        public static final String JOB_INDUSTRIES = "jobIndustries";
        public static final String JOB_SKILLS = "jobSkills";
    }

    public static class JobSalaryField {
        public static final String JOB_SALARY = "jobSalary";
        public static final String MIN_SALARY = "minSalary";
        public static final String MED_SALARY = "medSalary";
        public static final String MAX_SALARY = "maxSalary";
        public static final String PAY_PERIOD = "payPeriod";
        public static final String CURRENCY = "currency";
        public static final String COMPENSATION_TYPE = "compensationType";
    }

    public static class CompanyField {
        public static final String COMPANY_ID = "companyId";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String COMPANY_SIZE = "companySize";
        public static final String ADDRESS = "address";
        public static final String URL = "url";
    }
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
        public static final String EXPIRY_REQUIRED = "posting.expiry.required";
        public static final String EXPIRY_INVALID = "posting.expiry.invalid";
        public static final String JOB_STATUS_INVALID = "posting.jobStatus.invalid";

        // JobSalary fields
        public static final String JOB_SALARY_INVALID = "posting.jobSalary.invalid";
        public static final String MIN_SALARY_REQUIRED = "posting.minSalary.required";
        public static final String MIN_SALARY_INVALID = "posting.minSalary.invalid";
        public static final String MED_SALARY_INVALID = "posting.medSalary.invalid";
        public static final String MAX_SALARY_REQUIRED = "posting.maxSalary.required";
        public static final String MAX_SALARY_INVALID = "posting.maxSalary.invalid";
        public static final String MAX_SALARY_LESS_THAN_MIN = "posting.maxSalary.lessThanMin";
        public static final String PAY_PERIOD_INVALID = "posting.payPeriod.invalid";
        public static final String CURRENCY_INVALID = "posting.currency.invalid";
        public static final String COMPENSATION_TYPE_INVALID = "posting.compensationType.invalid";

        // JobBenefit, JobIndustry, JobSkill
        public static final String JOB_BENEFITS_INVALID = "posting.jobBenefits.invalid";
        public static final String JOB_BENEFITS_REQUIRED = "posting.benefitId.required";
        public static final String JOB_INDUSTRIES_INVALID = "posting.jobIndustries.invalid";
        public static final String JOB_INDUSTRIES_REQUIRED = "posting.industryId.required";
        public static final String JOB_SKILLS_INVALID = "posting.skills.invalid";
        public static final String JOB_SKILLS_REQUIRED = "posting.skillId.required";

        // Additional conditions
        public static final String TOO_MANY_BENEFITS = "posting.tooManyBenefits";
        public static final String TOO_MANY_INDUSTRIES = "posting.tooManyIndustries";
        public static final String TOO_MANY_SKILLS = "posting.tooManySkills";

        // Company conditions
        public static final String COMPANY_NAME_REQUIRED = "posting.companyName.required";
        public static final String COMPANY_DESCRIPTION_REQUIRED = "posting.companyDescription.required";
        public static final String COMPANY_SIZE_REQUIRED = "posting.companySize.required";
        public static final String COMPANY_ADDRESS_REQUIRED = "posting.companyAddress.required";
        public static final String COMPANY_URL_REQUIRED = "posting.companyUrl.required";
    }
}
