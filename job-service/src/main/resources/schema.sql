-- Create sequence for jobs
CREATE SEQUENCE IF NOT EXISTS jobs_job_id_seq;

-- Create jobs table
CREATE TABLE IF NOT EXISTS jobs (
    job_id INTEGER PRIMARY KEY DEFAULT nextval('jobs_job_id_seq'),
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    requirements TEXT,
    employer_id VARCHAR(255) NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    job_type VARCHAR(50) NOT NULL, -- FULL_TIME, PART_TIME, CONTRACT, etc.
    experience_level VARCHAR(50), -- ENTRY, MID, SENIOR, etc.
    education_level VARCHAR(50), -- BACHELOR, MASTER, PHD, etc.
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', -- ACTIVE, CLOSED, DRAFT
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    views INTEGER DEFAULT 0,
    applications INTEGER DEFAULT 0,
    search_vector JSONB -- For fast searching
);

-- Create job_salaries table
CREATE TABLE IF NOT EXISTS job_salaries (
    salary_id SERIAL PRIMARY KEY,
    job_id INTEGER NOT NULL REFERENCES jobs(job_id) ON DELETE CASCADE,
    min_salary DECIMAL(12, 2),
    max_salary DECIMAL(12, 2),
    currency VARCHAR(3) DEFAULT 'USD',
    salary_period VARCHAR(20) DEFAULT 'YEARLY' -- YEARLY, MONTHLY, HOURLY, etc.
);

-- Create job_skills table
CREATE TABLE IF NOT EXISTS job_skills (
    job_skill_id SERIAL PRIMARY KEY,
    job_id INTEGER NOT NULL REFERENCES jobs(job_id) ON DELETE CASCADE,
    skill_id INTEGER NOT NULL,
    skill_level VARCHAR(20), -- BEGINNER, INTERMEDIATE, ADVANCED, etc.
    is_required BOOLEAN DEFAULT TRUE
);

-- Create job_industries table
CREATE TABLE IF NOT EXISTS job_industries (
    job_industry_id SERIAL PRIMARY KEY,
    job_id INTEGER NOT NULL REFERENCES jobs(job_id) ON DELETE CASCADE,
    industry_id INTEGER NOT NULL
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_jobs_employer_id ON jobs(employer_id);
CREATE INDEX IF NOT EXISTS idx_jobs_status ON jobs(status);
CREATE INDEX IF NOT EXISTS idx_jobs_location ON jobs(location);
CREATE INDEX IF NOT EXISTS idx_jobs_job_type ON jobs(job_type);
CREATE INDEX IF NOT EXISTS idx_jobs_experience_level ON jobs(experience_level);
CREATE INDEX IF NOT EXISTS idx_job_skills_skill_id ON job_skills(skill_id);
CREATE INDEX IF NOT EXISTS idx_job_industries_industry_id ON job_industries(industry_id);
CREATE INDEX IF NOT EXISTS idx_jobs_search_vector ON jobs USING GIN (search_vector); 