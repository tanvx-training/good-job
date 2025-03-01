-- Insert sample job postings if they don't exist
INSERT INTO job_postings (posting_id, job_id, employer_id, title, description, company_name, location, job_type, experience_level, education_level, min_salary, max_salary, currency, salary_period, status, expires_at)
SELECT 1, 1, 'employer-123', 'Senior Java Developer', 
       'We are looking for an experienced Java developer to join our team. You will be responsible for developing high-performance applications and services.',
       'Tech Solutions Inc.', 'New York, NY', 'FULL_TIME', 'SENIOR', 'BACHELOR', 100000, 150000, 'USD', 'YEARLY', 'ACTIVE', 
       CURRENT_TIMESTAMP + INTERVAL '30 days'
WHERE NOT EXISTS (SELECT 1 FROM job_postings WHERE posting_id = 1);

INSERT INTO job_postings (posting_id, job_id, employer_id, title, description, company_name, location, job_type, experience_level, education_level, min_salary, max_salary, currency, salary_period, status, expires_at)
SELECT 2, 2, 'employer-456', 'Frontend Developer', 
       'Join our creative team to build beautiful and responsive user interfaces. You will work closely with designers and backend developers.',
       'Digital Creations', 'San Francisco, CA', 'FULL_TIME', 'MID', 'BACHELOR', 80000, 120000, 'USD', 'YEARLY', 'ACTIVE', 
       CURRENT_TIMESTAMP + INTERVAL '45 days'
WHERE NOT EXISTS (SELECT 1 FROM job_postings WHERE posting_id = 2);

INSERT INTO job_postings (posting_id, job_id, employer_id, title, description, company_name, location, job_type, experience_level, education_level, min_salary, max_salary, currency, salary_period, status, expires_at)
SELECT 3, 3, 'employer-789', 'DevOps Engineer', 
       'We need a skilled DevOps engineer to help us automate our deployment processes and manage our cloud infrastructure.',
       'Cloud Systems', 'Remote', 'FULL_TIME', 'MID', 'BACHELOR', 90000, 130000, 'USD', 'YEARLY', 'ACTIVE', 
       CURRENT_TIMESTAMP + INTERVAL '60 days'
WHERE NOT EXISTS (SELECT 1 FROM job_postings WHERE posting_id = 3);

-- Insert sample job applications
INSERT INTO job_applications (application_id, posting_id, applicant_id, resume_url, cover_letter, status, created_at)
SELECT 1, 1, 'applicant-123', 'https://storage.goodjob.com/resumes/applicant-123-resume.pdf', 
       'I am excited to apply for the Senior Java Developer position at Tech Solutions Inc. With over 8 years of experience in Java development and a strong background in microservices architecture, I believe I would be a great fit for your team.',
       'PENDING', CURRENT_TIMESTAMP - INTERVAL '5 days'
WHERE NOT EXISTS (SELECT 1 FROM job_applications WHERE application_id = 1);

INSERT INTO job_applications (application_id, posting_id, applicant_id, resume_url, cover_letter, status, created_at)
SELECT 2, 2, 'applicant-456', 'https://storage.goodjob.com/resumes/applicant-456-resume.pdf', 
       'I am writing to express my interest in the Frontend Developer position at Digital Creations. As a passionate frontend developer with 5 years of experience in React and modern CSS frameworks, I am confident in my ability to contribute to your team.',
       'UNDER_REVIEW', CURRENT_TIMESTAMP - INTERVAL '3 days'
WHERE NOT EXISTS (SELECT 1 FROM job_applications WHERE application_id = 2);

INSERT INTO job_applications (application_id, posting_id, applicant_id, resume_url, cover_letter, status, created_at)
SELECT 3, 3, 'applicant-789', 'https://storage.goodjob.com/resumes/applicant-789-resume.pdf', 
       'I am applying for the DevOps Engineer position at Cloud Systems. With my extensive experience in CI/CD pipelines, Docker, and Kubernetes, I am well-equipped to help your team streamline deployment processes and manage cloud infrastructure effectively.',
       'ACCEPTED', CURRENT_TIMESTAMP - INTERVAL '7 days'
WHERE NOT EXISTS (SELECT 1 FROM job_applications WHERE application_id = 3); 