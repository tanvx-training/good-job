-- Create sequence for benefits if it doesn't exist
CREATE SEQUENCE IF NOT EXISTS benefits_benefit_id_seq;

-- Create benefits table if it doesn't exist
CREATE TABLE IF NOT EXISTS public.benefits (
    benefit_id integer NOT NULL DEFAULT nextval('benefits_benefit_id_seq') PRIMARY KEY,
    type character varying UNIQUE
);

-- Create indexes
CREATE UNIQUE INDEX IF NOT EXISTS public_benefits_pkey ON public.benefits (benefit_id);
CREATE UNIQUE INDEX IF NOT EXISTS public_benefits_type_key ON public.benefits (type); 