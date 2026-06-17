CREATE DATABASE IF NOT EXISTS hospital_registration
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE hospital_registration;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  phone VARCHAR(255),
  password VARCHAR(255),
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS departments (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(255),
  hospital_name VARCHAR(255),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET @department_hospital_column_count = (
  SELECT COUNT(*)
  FROM information_schema.columns
  WHERE table_schema = DATABASE()
    AND table_name = 'departments'
    AND column_name = 'hospital_name'
);

SET @department_hospital_sql = IF(
  @department_hospital_column_count = 0,
  'ALTER TABLE departments ADD COLUMN hospital_name VARCHAR(255) NULL',
  'SELECT ''departments.hospital_name already exists'''
);

PREPARE department_hospital_stmt FROM @department_hospital_sql;
EXECUTE department_hospital_stmt;
DEALLOCATE PREPARE department_hospital_stmt;

CREATE TABLE IF NOT EXISTS doctors (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  title VARCHAR(255),
  specialty VARCHAR(255),
  introduction VARCHAR(255),
  department_id BIGINT,
  PRIMARY KEY (id),
  KEY idx_doctors_department_id (department_id),
  CONSTRAINT fk_doctors_department
    FOREIGN KEY (department_id) REFERENCES departments (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS schedules (
  id BIGINT NOT NULL AUTO_INCREMENT,
  doctor_id BIGINT,
  work_date DATE,
  time_period VARCHAR(255),
  total_number INT,
  left_number INT,
  PRIMARY KEY (id),
  KEY idx_schedules_doctor_id (doctor_id),
  CONSTRAINT fk_schedules_doctor
    FOREIGN KEY (doctor_id) REFERENCES doctors (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS appointments (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT,
  doctor_id BIGINT,
  schedule_id BIGINT,
  patient_name VARCHAR(255),
  patient_phone VARCHAR(255),
  status VARCHAR(255),
  create_time DATETIME,
  PRIMARY KEY (id),
  KEY idx_appointments_user_id (user_id),
  KEY idx_appointments_doctor_id (doctor_id),
  KEY idx_appointments_schedule_id (schedule_id),
  CONSTRAINT fk_appointments_user
    FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_appointments_doctor
    FOREIGN KEY (doctor_id) REFERENCES doctors (id),
  CONSTRAINT fk_appointments_schedule
    FOREIGN KEY (schedule_id) REFERENCES schedules (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS notices (
  id BIGINT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255),
  content VARCHAR(255),
  publish_time DATETIME,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
