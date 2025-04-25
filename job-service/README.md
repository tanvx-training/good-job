# Job Service

### Database Optimization

```sql
-- Index cho các bảng phụ
CREATE INDEX idx_job_benefits_job_id ON job_benefits(job_id);

CREATE INDEX idx_job_industries_job_id ON job_industries(job_id);

CREATE INDEX idx_job_salaries_job_id ON job_salaries(job_id);

CREATE INDEX idx_job_skills_job_id ON job_skills(job_id);
```