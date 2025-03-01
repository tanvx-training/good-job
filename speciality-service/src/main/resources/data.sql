-- Insert default specialities if they don't exist
INSERT INTO public.specialities (speciality_name, description)
VALUES 
    ('AI & Machine Learning', 'Artificial Intelligence and Machine Learning technologies and applications'),
    ('DevOps', 'Development and Operations practices, tools, and methodologies'),
    ('Cybersecurity', 'Information security, network security, and application security'),
    ('Cloud Computing', 'Cloud platforms, services, and architecture'),
    ('Web Development', 'Frontend and backend web development technologies'),
    ('Mobile Development', 'iOS, Android, and cross-platform mobile development'),
    ('Data Science', 'Data analysis, visualization, and statistical modeling'),
    ('Blockchain', 'Blockchain technology, cryptocurrencies, and smart contracts'),
    ('IoT', 'Internet of Things devices, platforms, and applications'),
    ('Game Development', 'Game design, development, and optimization'),
    ('AR/VR', 'Augmented Reality and Virtual Reality technologies'),
    ('Embedded Systems', 'Hardware and software for embedded systems'),
    ('Quantum Computing', 'Quantum algorithms, programming, and applications'),
    ('Robotics', 'Robot design, programming, and automation'),
    ('Big Data', 'Big data processing, storage, and analytics')
ON CONFLICT (speciality_name) DO NOTHING; 