
CREATE TABLE hospitals (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  latitude FLOAT NOT NULL,
  longitude FLOAT NOT NULL,
  free_beds INT NOT NULL,
);
 
 CREATE TABLE specialties (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
);
 
 CREATE TABLE hospitals_specialties (
  hospital_id INT NOT NULL,
  specialties_id INT NOT NULL,
  PRIMARY KEY(`hospital_id`, `specialties_id`)
);
