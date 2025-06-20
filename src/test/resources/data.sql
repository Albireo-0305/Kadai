SET MODE MYSQL;

INSERT INTO students (student_id, name, furigana, nickname, email_address, region, age, gender, remark, is_deleted)
VALUES (1, '武藤 遊戯', 'むとう ゆうぎ', '遊戯王', 'yu-gi-oh@example.com', '童実野町', 17, '男性', '', 0);

INSERT INTO students (student_id, name, furigana, nickname, email_address, region, age, gender, remark, is_deleted)
VALUES (2, '遊城 十代', 'ゆうき じゅうだい', 'ガッチャ', 'yu-gi-ohgx@example.com', 'アカデミア レッド寮', 16, '男性', '', 0);

INSERT INTO students (student_id, name, furigana, nickname, email_address, region, age, gender, remark, is_deleted)
VALUES (3, '不動 遊星', 'ふどう ゆうせい', 'ダニエル', 'yu-gi-oh5ds@example.com', 'ネオドミノシティ', 18, '男性', '', 0);

INSERT INTO students (student_id, name, furigana, nickname, email_address, region, age, gender, remark, is_deleted)
VALUES (4, '九十九遊馬', 'つくも ゆうま', '先生', 'yu-gi-ohzexal@example.com', 'ハートランド', 13, '男性', '', 0);

INSERT INTO students (student_id, name, furigana, nickname, email_address, region, age, gender, remark, is_deleted)
VALUES (5, '榊遊矢', 'さかき ゆうや', 'トマト', 'yugioARCV@gmail.com', '舞網戸市', 14, '男性', '',0);

INSERT INTO students (student_id, name, furigana, nickname, email_address, region, age, gender, remark, is_deleted)
VALUES (6, '藤木遊作', 'ふじき ゆうさく', 'Playmaker', 'yu-gi-ohVR@example.com', 'Den City', 16, '男性', '',0);

INSERT INTO students (student_id, name, furigana, nickname, email_address, region, age, gender, remark, is_deleted)
VALUES (7, '王道 遊我', 'おうどう ゆうが', 'ゴーハの革命児', 'yuga@goharules.com', 'ゴーハ市', 11, '男性', '',0);


INSERT INTO students_courses (course_id, student_id, course_name, start_date, expected_end_date)
VALUES (1, 3, '不動性ソリティア理論', '2008-04-02', '2011-03-30');

INSERT INTO students_courses (course_id, student_id, course_name, start_date, expected_end_date)
VALUES (2, 2, '超融合理論', '2004-10-06', '2008-03-26');

INSERT INTO students_courses (course_id, student_id, course_name, start_date, expected_end_date)
VALUES (3, 1, 'LDSスタンダード', '2000-04-18', '2004-09-29');

INSERT INTO students_courses (course_id, student_id, course_name, start_date, expected_end_date)
VALUES (4, 4, '特殊カード変質理論', '2011-04-11', '2014-03-23');

INSERT INTO students_courses (course_id, student_id, course_name, start_date, expected_end_date)
VALUES (5, 5, 'エンタメ課', '2014-04-06', '2017-04-23');

INSERT INTO students_courses (course_id, student_id, course_name, start_date, expected_end_date)
VALUES (6, 6, 'リンク総合理論', '2017-05-10', '2019-09-25');

INSERT INTO students_courses (course_id, student_id, course_name, start_date, expected_end_date)
VALUES (7, 7, 'ラッシュ学部', '2025-04-01', '2026-03-31');
