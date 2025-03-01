-- Create sequence for industries if it doesn't exist
CREATE SEQUENCE IF NOT EXISTS industries_industry_id_seq;

-- Create industries table if it doesn't exist
CREATE TABLE IF NOT EXISTS public.industries (
    industry_id integer NOT NULL DEFAULT nextval('industries_industry_id_seq') PRIMARY KEY,
    industry_name character varying NOT NULL UNIQUE
);

-- Create indexes
CREATE UNIQUE INDEX IF NOT EXISTS public_industries_pkey ON public.industries (industry_id);
CREATE UNIQUE INDEX IF NOT EXISTS public_industries_industry_name_key ON public.industries (industry_name); 