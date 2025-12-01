
INSERT INTO member (name, email, password)
VALUES
    ('Alice Johnson', 'alice@example.com', 'password123'),
    ('Bob Smith', 'bob@example.com', 'password456');

INSERT INTO project (title, description, estimated_time)
VALUES
    ('Website Redesign', 'Revamping company website', 120),
    ('Mobile App', 'Building Android/iOS app', 200),
    ('Data Migration', 'Migrating legacy database', 90),
    ('API Integration', 'Integrating third-party APIs', 150),
    ('Automation Tool', 'Internal automation utility', 80);



INSERT INTO member_project (member_id, project_id)
VALUES
    (1,1),
    (1,2),
    (1,3),

    (2,3),
    (2,4),
    (2,5);


INSERT INTO task (project_id, title, description, estimated_time, member_id)
VALUES
    (1, 'Design Mockups', 'Create UI/UX mockups', 20, 1),
    (1, 'Frontend Development', 'Implement new layout in React', 40, 1);

INSERT INTO task (project_id, title, description, estimated_time, member_id)
VALUES
    (2, 'Setup Project Structure', 'Initialize mobile app project', 10, 1),
    (2, 'Implement Login', 'Build authentication screen', 15, 1);

INSERT INTO task (project_id, title, description, estimated_time, member_id)
VALUES
    (3, 'Export Legacy Data', 'Extract data from legacy system', 12, 1),
    (3, 'Transform Data', 'Clean and map dataset', 18, 1);

INSERT INTO task (project_id, title, description, estimated_time, member_id)
VALUES
    (4, 'OAuth Setup', 'Configure OAuth client', 8, 2),
    (4, 'API Request Handlers', 'Implement API calls', 25, 2);

INSERT INTO task (project_id, title, description, estimated_time, member_id)
VALUES
    (5, 'Script Setup', 'Prepare automation script structure', 6, 2),
    (5, 'Job Scheduling', 'Configure cron-like scheduler', 14, 2);


INSERT INTO subtask (task_id, title, description, estimated_time, member_id)
VALUES
    (1, 'Wireframes', 'Low-fidelity screens', 5, 1),
    (1, 'High-Fidelity Designs', 'Polished final UI', 10, 1);

INSERT INTO subtask (task_id, title, description, estimated_time, member_id)
VALUES
    (2, 'Navbar Component', 'Build responsive navbar', 4, 1),
    (2, 'Homepage Section', 'Build hero + features', 6, 1);

INSERT INTO subtask (task_id, title, description, estimated_time, member_id)
VALUES
    (4, 'UI Layout', 'Login screen layout', 3, 2),
    (4, 'Auth API', 'Integrate backend login', 5, 2);

INSERT INTO subtask (task_id, title, description, estimated_time, member_id)
VALUES
    (6, 'Remove Duplicates', 'Handle duplicate records', 4, 2),
    (6, 'Normalize Fields', 'Ensure consistent data format', 6, 2);