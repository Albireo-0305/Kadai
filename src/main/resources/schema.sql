CREATE TABLE students (
    student_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    furigana VARCHAR(100),
    nickname VARCHAR(100),
    email_address VARCHAR(100),
    region VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    remark VARCHAR(200),
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE students_courses (
    course_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_name VARCHAR(100),
    start_date DATE,
    expected_end_date DATE
);
