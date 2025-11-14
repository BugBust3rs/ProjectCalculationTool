DROP
DATABASE IF EXISTS ProjectCalculationTool;
CREATE
DATABASE ProjectCalculationTool
DEFAULT CHARACTER    SET utf8mb4;
USE
ProjectCalculationTool;

CREATE TABLE member
(
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(200) NOT NULL,
    email     VARCHAR(200) NOT NULL,
    password  VARCHAR(200) NOT NULL
);

CREATE TABLE project
(
    project_id     INT AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(200),
    description    VARCHAR(200),
    estimated_time INT
);

CREATE TABLE member_project
(
    member_id  INT,
    project_id INT,
    PRIMARY KEY (member_id, project_id),
    FOREIGN KEY (member_id)
        REFERENCES member (member_id) ON DELETE CASCADE,
    FOREIGN KEY (project_id)
        REFERENCES project (project_id) ON DELETE CASCADE
);

CREATE TABLE task
(
    task_id        INT AUTO_INCREMENT PRIMARY KEY,
    project_id     INT,
    title          VARCHAR(200),
    description    VARCHAR(200),
    estimated_time INT,
    FOREIGN KEY (project_id)
        REFERENCES project (project_id) ON DELETE CASCADE
);

CREATE TABLE subTask
(
    subtask_ID     INT AUTO_INCREMENT PRIMARY KEY,
    task_ID        INT,
    title          VARCHAR(200),
    description    VARCHAR(200),
    estimated_time INT,
    FOREIGN KEY (task_id)
        REFERENCES task (task_id) ON DELETE CASCADE
);
