
SET REFERENTIAL_INTEGRITY FALSE;

DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS member_project;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS subTask;

SET REFERENTIAL_INTEGRITY TRUE;

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
    FOREIGN KEY (member_id)
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

------------------------------------
-- INSERT MEMBERS
------------------------------------
INSERT INTO member (name, email, password)
VALUES
    ('Alice Johnson', 'alice@example.com', 'password123'),
    ('Bob Smith', 'bob@example.com', 'password456');

------------------------------------
-- INSERT PROJECTS
------------------------------------
INSERT INTO project (title, description, estimated_time)
VALUES
    ('Website Redesign', 'Revamping company website', 120),
    ('Mobile App', 'Building Android/iOS app', 200),
    ('Data Migration', 'Migrating legacy database', 90),
    ('API Integration', 'Integrating third-party APIs', 150),
    ('Automation Tool', 'Internal automation utility', 80);

------------------------------------
-- MEMBER ↔ PROJECT RELATIONS
------------------------------------
-- Alice owns projects 1, 2, 3
INSERT INTO member_project (member_id, project_id)
VALUES
    (1,1),
    (1,2),
    (1,3),

-- Bob owns projects 3, 4, 5
    (2,3),
    (2,4),
    (2,5);

------------------------------------
-- INSERT TASKS
------------------------------------
-- For readability, tasks are inserted in groups by project

-- Project 1: Website Redesign
INSERT INTO task (project_id, title, description, estimated_time)
VALUES
    (1, 'Design Mockups', 'Create UI/UX mockups', 20),
    (1, 'Frontend Development', 'Implement new layout in React', 40);

-- Project 2: Mobile App
INSERT INTO task (project_id, title, description, estimated_time)
VALUES
    (2, 'Setup Project Structure', 'Initialize mobile app project', 10),
    (2, 'Implement Login', 'Build authentication screen', 15);

-- Project 3: Data Migration
INSERT INTO task (project_id, title, description, estimated_time)
VALUES
    (3, 'Export Legacy Data', 'Extract data from legacy system', 12),
    (3, 'Transform Data', 'Clean and map dataset', 18);

-- Project 4: API Integration
INSERT INTO task (project_id, title, description, estimated_time)
VALUES
    (4, 'OAuth Setup', 'Configure OAuth client', 8),
    (4, 'API Request Handlers', 'Implement API calls', 25);

-- Project 5: Automation Tool
INSERT INTO task (project_id, title, description, estimated_time)
VALUES
    (5, 'Script Setup', 'Prepare automation script structure', 6),
    (5, 'Job Scheduling', 'Configure cron-like scheduler', 14);

------------------------------------
-- INSERT SUBTASKS
------------------------------------

-- Subtasks for Website Redesign → Design Mockups (task_id = 1)
INSERT INTO subTask (task_id, title, description, estimated_time)
VALUES
    (1, 'Wireframes', 'Low-fidelity screens', 5),
    (1, 'High-Fidelity Designs', 'Polished final UI', 10);

-- Subtasks for Website Redesign → Frontend Development (task_id = 2)
INSERT INTO subTask (task_id, title, description, estimated_time)
VALUES
    (2, 'Navbar Component', 'Build responsive navbar', 4),
    (2, 'Homepage Section', 'Build hero + features', 6);

-- Subtasks for Mobile App → Login (task_id = 4)
INSERT INTO subTask (task_id, title, description, estimated_time)
VALUES
    (4, 'UI Layout', 'Login screen layout', 3),
    (4, 'Auth API', 'Integrate backend login', 5);

-- Subtasks for Data Migration → Transform Data (task_id = 6)
INSERT INTO subTask (task_id, title, description, estimated_time)
VALUES
    (6, 'Remove Duplicates', 'Handle duplicate records', 4),
    (6, 'Normalize Fields', 'Ensure consistent data format', 6);