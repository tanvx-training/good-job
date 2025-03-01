-- Insert sample jobs if they don't exist
INSERT INTO jobs (job_id, title, description, requirements, employer_id, company_name, location, job_type, experience_level, education_level, status, expires_at, search_vector)
SELECT 1, 'Senior Java Developer', 
       'We are looking for an experienced Java developer to join our team. You will be responsible for developing high-performance applications and services.',
       'Strong knowledge of Java, Spring Boot, and microservices architecture. Experience with cloud platforms like AWS or Azure.',
       'employer-123', 'Tech Solutions Inc.', 'New York, NY', 'FULL_TIME', 'SENIOR', 'BACHELOR', 'ACTIVE', 
       CURRENT_TIMESTAMP + INTERVAL '30 days',
       jsonb_build_object(
           'title', 'Senior Java Developer',
           'company', 'Tech Solutions Inc.',
           'location', 'New York, NY',
           'skills', jsonb_build_array('Java', 'Spring Boot', 'Microservices', 'AWS', 'Azure'),
           'job_type', 'FULL_TIME',
           'experience_level', 'SENIOR'
       )
WHERE NOT EXISTS (SELECT 1 FROM jobs WHERE job_id = 1);

INSERT INTO jobs (job_id, title, description, requirements, employer_id, company_name, location, job_type, experience_level, education_level, status, expires_at, search_vector)
SELECT 2, 'Frontend Developer', 
       'Join our creative team to build beautiful and responsive user interfaces. You will work closely with designers and backend developers.',
       'Proficiency in JavaScript, React, and CSS. Experience with responsive design and cross-browser compatibility.',
       'employer-456', 'Digital Creations', 'San Francisco, CA', 'FULL_TIME', 'MID', 'BACHELOR', 'ACTIVE', 
       CURRENT_TIMESTAMP + INTERVAL '45 days',
       jsonb_build_object(
           'title', 'Frontend Developer',
           'company', 'Digital Creations',
           'location', 'San Francisco, CA',
           'skills', jsonb_build_array('JavaScript', 'React', 'CSS', 'HTML', 'UI/UX'),
           'job_type', 'FULL_TIME',
           'experience_level', 'MID'
       )
WHERE NOT EXISTS (SELECT 1 FROM jobs WHERE job_id = 2);

INSERT INTO jobs (job_id, title, description, requirements, employer_id, company_name, location, job_type, experience_level, education_level, status, expires_at, search_vector)
SELECT 3, 'DevOps Engineer', 
       'We need a skilled DevOps engineer to help us automate our deployment processes and manage our cloud infrastructure.',
       'Experience with CI/CD pipelines, Docker, Kubernetes, and cloud platforms. Knowledge of infrastructure as code tools like Terraform.',
       'employer-789', 'Cloud Systems', 'Remote', 'FULL_TIME', 'MID', 'BACHELOR', 'ACTIVE', 
       CURRENT_TIMESTAMP + INTERVAL '60 days',
       jsonb_build_object(
           'title', 'DevOps Engineer',
           'company', 'Cloud Systems',
           'location', 'Remote',
           'skills', jsonb_build_array('Docker', 'Kubernetes', 'CI/CD', 'Terraform', 'AWS'),
           'job_type', 'FULL_TIME',
           'experience_level', 'MID'
       )
WHERE NOT EXISTS (SELECT 1 FROM jobs WHERE job_id = 3);

-- Insert sample job salaries
INSERT INTO job_salaries (job_id, min_salary, max_salary, currency, salary_period)
SELECT 1, 100000, 150000, 'USD', 'YEARLY'
WHERE NOT EXISTS (SELECT 1 FROM job_salaries WHERE job_id = 1);

INSERT INTO job_salaries (job_id, min_salary, max_salary, currency, salary_period)
SELECT 2, 80000, 120000, 'USD', 'YEARLY'
WHERE NOT EXISTS (SELECT 1 FROM job_salaries WHERE job_id = 2);

INSERT INTO job_salaries (job_id, min_salary, max_salary, currency, salary_period)
SELECT 3, 90000, 130000, 'USD', 'YEARLY'
WHERE NOT EXISTS (SELECT 1 FROM job_salaries WHERE job_id = 3);

-- Insert sample job skills
-- Java Developer skills
INSERT INTO job_skills (job_id, skill_id, skill_level, is_required)
SELECT 1, 1, 'ADVANCED', TRUE
WHERE NOT EXISTS (SELECT 1 FROM job_skills WHERE job_id = 1 AND skill_id = 1);

INSERT INTO job_skills (job_id, skill_id, skill_level, is_required)
SELECT 1, 2, 'ADVANCED', TRUE
WHERE NOT EXISTS (SELECT 1 FROM job_skills WHERE job_id = 1 AND skill_id = 2);

INSERT INTO job_skills (job_id, skill_id, skill_level, is_required)
SELECT 1, 3, 'INTERMEDIATE', TRUE
WHERE NOT EXISTS (SELECT 1 FROM job_skills WHERE job_id = 1 AND skill_id = 3);

-- Frontend Developer skills
INSERT INTO job_skills (job_id, skill_id, skill_level, is_required)
SELECT 2, 4, 'ADVANCED', TRUE
WHERE NOT EXISTS (SELECT 1 FROM job_skills WHERE job_id = 2 AND skill_id = 4);

INSERT INTO job_skills (job_id, skill_id, skill_level, is_required)
SELECT 2, 5, 'ADVANCED', TRUE
WHERE NOT EXISTS (SELECT 1 FROM job_skills WHERE job_id = 2 AND skill_id = 5);

INSERT INTO job_skills (job_id, skill_id, skill_level, is_required)
SELECT 2, 6, 'INTERMEDIATE', FALSE
WHERE NOT EXISTS (SELECT 1 FROM job_skills WHERE job_id = 2 AND skill_id = 6);

-- DevOps Engineer skills
INSERT INTO job_skills (job_id, skill_id, skill_level, is_required)
SELECT 3, 7, 'ADVANCED', TRUE
WHERE NOT EXISTS (SELECT 1 FROM job_skills WHERE job_id = 3 AND skill_id = 7);

INSERT INTO job_skills (job_id, skill_id, skill_level, is_required)
SELECT 3, 8, 'ADVANCED', TRUE
WHERE NOT EXISTS (SELECT 1 FROM job_skills WHERE job_id = 3 AND skill_id = 8);

INSERT INTO job_skills (job_id, skill_id, skill_level, is_required)
SELECT 3, 9, 'INTERMEDIATE', TRUE
WHERE NOT EXISTS (SELECT 1 FROM job_skills WHERE job_id = 3 AND skill_id = 9);

-- Insert sample job industries
INSERT INTO job_industries (job_id, industry_id)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM job_industries WHERE job_id = 1 AND industry_id = 1);

INSERT INTO job_industries (job_id, industry_id)
SELECT 2, 1
WHERE NOT EXISTS (SELECT 1 FROM job_industries WHERE job_id = 2 AND industry_id = 1);

INSERT INTO job_industries (job_id, industry_id)
SELECT 3, 1
WHERE NOT EXISTS (SELECT 1 FROM job_industries WHERE job_id = 3 AND industry_id = 1);

INSERT INTO job_industries (job_id, industry_id)
SELECT 3, 2
WHERE NOT EXISTS (SELECT 1 FROM job_industries WHERE job_id = 3 AND industry_id = 2); 