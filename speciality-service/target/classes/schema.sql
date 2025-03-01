-- Create sequence for specialities if it doesn't exist
CREATE SEQUENCE IF NOT EXISTS specialities_speciality_id_seq;

-- Create specialities table if it doesn't exist
CREATE TABLE IF NOT EXISTS public.specialities (
    speciality_id integer NOT NULL DEFAULT nextval('specialities_speciality_id_seq') PRIMARY KEY,
    speciality_name character varying(100) NOT NULL UNIQUE,
    description character varying(500)
);

-- Create indexes
CREATE UNIQUE INDEX IF NOT EXISTS public_specialities_pkey ON public.specialities (speciality_id);
CREATE UNIQUE INDEX IF NOT EXISTS public_specialities_name_key ON public.specialities (speciality_name); 