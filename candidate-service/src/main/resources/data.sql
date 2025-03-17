-- Insert default benefits if they don't exist
INSERT INTO public.benefits (type)
VALUES 
    ('Health Insurance'),
    ('Dental Insurance'),
    ('Vision Insurance'),
    ('401(k) Retirement Plan'),
    ('Paid Time Off'),
    ('Remote Work'),
    ('Professional Development'),
    ('Gym Membership'),
    ('Flexible Work Hours'),
    ('Parental Leave'),
    ('Life Insurance'),
    ('Performance Bonus'),
    ('Stock Options'),
    ('Commuter Benefits'),
    ('Employee Discounts')
ON CONFLICT (type) DO NOTHING; 