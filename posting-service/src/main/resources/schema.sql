-- Create sequence for job postings
CREATE SEQUENCE IF NOT EXISTS job_postings_id_seq;

-- Create job_postings table
CREATE TABLE IF NOT EXISTS job_postings (
    posting_id INTEGER PRIMARY KEY DEFAULT nextval('job_postings_id_seq'),
    job_id INTEGER NOT NULL,
    employer_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    job_type VARCHAR(50) NOT NULL,
    experience_level VARCHAR(50),
    education_level VARCHAR(50),
    min_salary DECIMAL(12, 2),
    max_salary DECIMAL(12, 2),
    currency VARCHAR(3) DEFAULT 'USD',
    salary_period VARCHAR(20) DEFAULT 'YEARLY',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    views INTEGER DEFAULT 0,
    applications INTEGER DEFAULT 0
);

-- Create sequence for job applications
CREATE SEQUENCE IF NOT EXISTS job_applications_id_seq;

-- Create job_applications table
CREATE TABLE IF NOT EXISTS job_applications (
    application_id INTEGER PRIMARY KEY DEFAULT nextval('job_applications_id_seq'),
    posting_id INTEGER NOT NULL REFERENCES job_postings(posting_id) ON DELETE CASCADE,
    applicant_id VARCHAR(255) NOT NULL,
    resume_url VARCHAR(255),
    cover_letter TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    employer_viewed BOOLEAN DEFAULT FALSE,
    applicant_viewed BOOLEAN DEFAULT FALSE
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_job_postings_job_id ON job_postings(job_id);
CREATE INDEX IF NOT EXISTS idx_job_postings_employer_id ON job_postings(employer_id);
CREATE INDEX IF NOT EXISTS idx_job_postings_status ON job_postings(status);
CREATE INDEX IF NOT EXISTS idx_job_postings_location ON job_postings(location);
CREATE INDEX IF NOT EXISTS idx_job_postings_job_type ON job_postings(job_type);
CREATE INDEX IF NOT EXISTS idx_job_postings_experience_level ON job_postings(experience_level);

CREATE INDEX IF NOT EXISTS idx_job_applications_posting_id ON job_applications(posting_id);
CREATE INDEX IF NOT EXISTS idx_job_applications_applicant_id ON job_applications(applicant_id);
CREATE INDEX IF NOT EXISTS idx_job_applications_status ON job_applications(status); 