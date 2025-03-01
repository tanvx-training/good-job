-- Create sequence for skills if it doesn't exist
CREATE SEQUENCE IF NOT EXISTS skills_skill_id_seq;

-- Create skills table if it doesn't exist
CREATE TABLE IF NOT EXISTS public.skills (
    skill_id integer NOT NULL DEFAULT nextval('skills_skill_id_seq') PRIMARY KEY,
    skill_abr character varying NOT NULL UNIQUE,
    skill_name character varying NOT NULL
);

-- Create indexes
CREATE UNIQUE INDEX IF NOT EXISTS public_skills_pkey ON public.skills (skill_id);
CREATE UNIQUE INDEX IF NOT EXISTS public_skills_skill_abr_key ON public.skills (skill_abr); 