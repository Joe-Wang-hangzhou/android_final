INSERT INTO departments (id, name, description) VALUES
(1, '内科', '常见内科疾病诊疗'),
(2, '外科', '外科疾病诊断和治疗'),
(3, '儿科', '儿童常见疾病诊疗'),
(4, '眼科', '视力检查和眼部疾病诊疗')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO doctors (id, name, title, specialty, introduction, department_id) VALUES
(1, '张医生', '主任医师', '高血压、糖尿病', '从事内科临床工作多年，经验丰富。', 1),
(2, '李医生', '副主任医师', '胃肠疾病', '擅长消化系统常见疾病诊疗。', 1),
(3, '王医生', '主治医师', '普外科', '擅长外科常见病诊疗。', 2),
(4, '赵医生', '主任医师', '儿童呼吸道疾病', '专注儿童常见病和多发病。', 3),
(5, '陈医生', '副主任医师', '近视、干眼症', '擅长眼科检查和眼部疾病治疗。', 4)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO schedules (id, doctor_id, work_date, time_period, total_number, left_number) VALUES
(1, 1, '2026-06-03', '上午', 20, 20),
(2, 1, '2026-06-03', '下午', 20, 18),
(3, 2, '2026-06-04', '上午', 15, 12),
(4, 3, '2026-06-04', '下午', 16, 16),
(5, 4, '2026-06-05', '上午', 18, 15),
(6, 5, '2026-06-05', '下午', 18, 18)
ON DUPLICATE KEY UPDATE left_number = VALUES(left_number);

INSERT INTO users (id, name, phone, password) VALUES
(1, '测试用户', '13800000000', '123456')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO notices (id, title, content, publish_time) VALUES
(1, '医院预约挂号系统上线', '用户可以通过手机客户端查看科室、医生排班并在线预约挂号。', '2026-06-01 09:00:00'),
(2, '端午节门诊安排', '节假日期间部分科室正常开放，请以医生排班为准。', '2026-06-02 10:30:00')
ON DUPLICATE KEY UPDATE title = VALUES(title);
