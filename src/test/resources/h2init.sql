
SET REFERENTIAL_INTEGRITY FALSE;

DROP TABLE IF EXISTS subTask;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS member_project;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS member;

SET REFERENTIAL_INTEGRITY TRUE;


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




INSERT INTO member (name, email, password)
VALUES
    ('Alice Johnson', 'alice@example.com', 'password123'),
    ('Bob Smith', 'bob@example.com', 'password456');

INSERT INTO project (title, description)
VALUES
    ('Website Redesign', 'Revamping company website'),
    ('Mobile App', 'Building Android/iOS app'),
    ('Data Migration', 'Migrating legacy database'),
    ('API Integration', 'Integrating third-party APIs'),
    ('Automation Tool', 'Internal automation utility');



INSERT INTO member_project (member_id, project_id)
VALUES
    (1,1),
    (1,2),
    (1,3),

    (2,3),
    (2,4),
    (2,5);


INSERT INTO task (project_id, title, description, estimated_time, status, member_id)
VALUES
    (1, 'Design Mockups', 'Create UI/UX mockups', 20, 'BACKLOG', 1),
    (1, 'Frontend Development', 'Implement new layout in React', 40, 'BACKLOG', 1);

INSERT INTO task (project_id, title, description, estimated_time, status, member_id)
VALUES
    (2, 'Setup Project Structure', 'Initialize mobile app project', 10, 'BACKLOG', 1),
    (2, 'Implement Login', 'Build authentication screen', 15, 'BACKLOG', 1);

INSERT INTO task (project_id, title, description, estimated_time, status, member_id)
VALUES
    (3, 'Export Legacy Data', 'Extract data from legacy system', 12, 'BACKLOG', 1),
    (3, 'Transform Data', 'Clean and map dataset', 18, 'BACKLOG', 1);

INSERT INTO task (project_id, title, description, estimated_time, status, member_id)
VALUES
    (4, 'OAuth Setup', 'Configure OAuth client', 8, 'BACKLOG', 2),
    (4, 'API Request Handlers', 'Implement API calls', 25, 'BACKLOG', 2);

INSERT INTO task (project_id, title, description, estimated_time, status, member_id)
VALUES
    (5, 'Script Setup', 'Prepare automation script structure', 6, 'BACKLOG', 2),
    (5, 'Job Scheduling', 'Configure cron-like scheduler', 14, 'BACKLOG', 2);


INSERT INTO subtask (task_id, title, description, estimated_time, status, member_id)
VALUES
    (1, 'Wireframes', 'Low-fidelity screens', 5, 'BACKLOG', 1),
    (1, 'High-Fidelity Designs', 'Polished final UI', 10, 'BACKLOG', 1);

INSERT INTO subtask (task_id, title, description, estimated_time, status, member_id)
VALUES
    (2, 'Navbar Component', 'Build responsive navbar', 4, 'BACKLOG', 1),
    (2, 'Homepage Section', 'Build hero + features', 6, 'BACKLOG', 1);

INSERT INTO subtask (task_id, title, description, estimated_time, status, member_id)
VALUES
    (4, 'UI Layout', 'Login screen layout', 3, 'BACKLOG', 2),
    (4, 'Auth API', 'Integrate backend login', 5, 'BACKLOG', 2);

INSERT INTO subtask (task_id, title, description, estimated_time, status, member_id)
VALUES
    (6, 'Remove Duplicates', 'Handle duplicate records', 4, 'BACKLOG', 2),
    (6, 'Normalize Fields', 'Ensure consistent data format', 6, 'BACKLOG', 2);