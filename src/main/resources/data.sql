
INSERT INTO hospitals (id, name, latitude, longitude, free_beds) VALUES
  (1, 'Hôpital Saint Vincent de Paul', 50.620312, 3.077438, 2),
  (2, 'Centre Hospitalier Universitaire de Lille', 50.610937, 3.034687, 0),
  (3, 'Hôpital privé La Louvière', 50.646438,3.083563, 5);

INSERT INTO specialties (id, name) VALUES
  (1, 'Cardiologie'),
  (2, 'Immunologie'),
  (3, 'Neuropathologie diagnostique');
  

INSERT INTO hospitals_specialties (hospital_id, specialties_id) VALUES
  (1, 1),
  (1, 2),
  (2, 1),
  (3, 2),
  (3, 3);
  