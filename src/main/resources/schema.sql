CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    furigana VARCHAR(50) NOT NULL,
    nick_name VARCHAR(50) NOT NULL,
    email_address VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    age INT CHECK (age BETWEEN 10 AND 99),
    gender VARCHAR(10),
    remark TEXT,
    isDeleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS students_courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course VARCHAR(100),
    enrollment_start_date TIMESTAMP,
    enrollment_end_date TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);
