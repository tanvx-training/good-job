# Company Service

### Database Optimization

```sql
-- 1. Index trên company_industries
CREATE INDEX IF NOT EXISTS idx_company_industries_company_id
  ON public.company_industries(company_id);

-- (Nếu thường xuyên filter hoặc join thêm theo industry_id thì có thể thêm composite index:)
-- CREATE INDEX IF NOT EXISTS idx_company_industries_company_id_industry_id
--   ON public.company_industries(company_id, industry_id);


-- 2. Index trên company_metrics
CREATE INDEX IF NOT EXISTS idx_company_metrics_company_id
  ON public.company_metrics(company_id);

-- (Ví dụ composite index nếu hay lọc theo metric_name hoặc date)
-- CREATE INDEX IF NOT EXISTS idx_company_metrics_company_id_metric_name
--   ON public.company_metrics(company_id, metric_name);


-- 3. Index trên company_specialities
CREATE INDEX IF NOT EXISTS idx_company_specialities_company_id
  ON public.company_specialities(company_id);

-- (Composite index ví dụ:)
-- CREATE INDEX IF NOT EXISTS idx_company_specialities_company_id_speciality_id
--   ON public.company_specialities(company_id, speciality_id);

```