INSERT INTO client (cname) VALUES
('Johnny Depp'),
('Arnold Schwarzenegger'),
('Jim Carrey'),
('Emma Watson'),
('Robert Downey Jr.'),
('Daniel Radcliffe'),
('Chris Evans'),
('Leonardo DiCaprio'),
('Tom Cruise'),
('Brad Pitt');

INSERT INTO planet (id, pname) VALUES
('MER039', 'Mercury'),
('VEN072', 'Venus'),
('EAR100', 'Earth'),
('MAR152', 'Mars'),
('JUP520', 'Jupiter'),
('SAT954', 'Saturn'),
('URN1922', 'Uranus'),
('NPT3006', 'Neptune'),
('PLT5906', 'Pluto'),
('GND7780', 'Ganymede');

INSERT INTO ticket (created_at, client_id, from_planet_id, to_planet_id) VALUES
('2023-10-02 18:15:00', 5, 'MER039', 'JUP520'),
('2022-07-15 11:32:00', 9, 'VEN072', 'URN1922'),
('2023-05-08 09:45:00', 7, 'EAR100', 'MAR152'),
('2025-08-25 22:05:00', 10, 'URN1922', 'NPT3006'),
('2020-01-22 12:20:00', 1, 'PLT5906', 'SAT954'),
('2015-11-05 16:56:00', 8, 'EAR100', 'GND7780'),
('2018-04-01 07:05:00', 3, 'URN1922', 'MER039'),
('2028-11-14 17:35:00', 6, 'JUP520', 'SAT954'),
('2030-06-23 14:20:00', 2, 'EAR100', 'EAR100'),
('2024-09-11 21:40:00', 4, 'MAR152', 'GND7780');

