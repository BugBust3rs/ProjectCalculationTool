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
    email     VARCHAR(200) NOT NULL UNIQUE,
    password  VARCHAR(200) NOT NULL
);

CREATE TABLE project
(
    project_id     INT AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(200),
    description    VARCHAR(200)

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
    status         VARCHAR(200),
    member_id      INT,
    FOREIGN KEY (project_id)
        REFERENCES project (project_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id)
        REFERENCES member (member_id) ON DELETE CASCADE
);

CREATE TABLE subtask
(
    subtask_id     INT AUTO_INCREMENT PRIMARY KEY,
    task_id        INT,
    title          VARCHAR(200),
    description    VARCHAR(200),
    estimated_time INT,
    status         VARCHAR(200),
    member_id      INT,
    FOREIGN KEY (task_id)
        REFERENCES task (task_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id)
        REFERENCES member (member_id) ON DELETE CASCADE
);


