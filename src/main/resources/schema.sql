SET MODE MYSQL;

CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)　 NOT NULL,
    furigana VARCHAR(100)　 NOT NULL,
    nickname VARCHAR(100),
    email_address VARCHAR(100)　 NOT NULL,
    region VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    remark VARCHAR(200),
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS students_courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    course_name VARCHAR(100),
    start_date DATE,
    expected_end_date DATE
);
