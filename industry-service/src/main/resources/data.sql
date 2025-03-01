-- Insert default industries if they don't exist
INSERT INTO public.industries (industry_name)
VALUES 
    ('Technology'),
    ('Healthcare'),
    ('Finance'),
    ('Education'),
    ('Manufacturing'),
    ('Retail'),
    ('Hospitality'),
    ('Transportation'),
    ('Construction'),
    ('Entertainment'),
    ('Agriculture'),
    ('Energy'),
    ('Telecommunications'),
    ('Real Estate'),
    ('Media'),
    ('Consulting'),
    ('Legal Services'),
    ('Automotive'),
    ('Aerospace'),
    ('Pharmaceuticals')
ON CONFLICT (industry_name) DO NOTHING; 