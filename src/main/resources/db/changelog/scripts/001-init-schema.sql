CREATE TYPE project_domain AS ENUM ('ya ne ebu kakie estь');

CREATE TABLE employee (
    id UUID PRIMARY KEY,
    fio VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    start_date DATE NOT NULL
);

CREATE TABLE project (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(500) NOT NULL,
    domain project_domain NOT NULL
);

CREATE TABLE employee_project (
    id UUID PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    employee_id UUID REFERENCES employee(id) ON DELETE CASCADE ON UPDATE CASCADE,
    project_id UUID REFERENCES project(id) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE employee
    ADD CONSTRAINT email_format_check
        CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');