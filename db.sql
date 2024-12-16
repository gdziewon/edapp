CREATE TABLE patients (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          age INT NOT NULL,
                          condition_description TEXT,
                          triage_priority INT,
                          doctor_assigned_id BIGINT,
                          registration_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE doctors (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         specialization VARCHAR(255)
);
CREATE TABLE vital_signs (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             patient_id BIGINT NOT NULL,
                             blood_pressure VARCHAR(50),
                             heart_rate INT,
                             oxygen_saturation INT,
                             FOREIGN KEY (patient_id) REFERENCES patients(id),
			     recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE schedules (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           staff_name VARCHAR(255),
                           role VARCHAR(50),
                           shift_start TIMESTAMP,
                           shift_end TIMESTAMP
);
CREATE TABLE prescriptions (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               patient_id BIGINT NOT NULL,
                               description TEXT NOT NULL,
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);
