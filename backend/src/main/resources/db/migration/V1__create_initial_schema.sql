
CREATE TABLE task_lists (
    id UUID NOT NULL,
    title VARCHAR(150) NOT NULL,
    description VARCHAR(1000),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,

    CONSTRAINT pk_task_lists PRIMARY KEY (id)
);

CREATE TABLE tasks (
    id UUID NOT NULL,
    title VARCHAR(150) NOT NULL,
    description VARCHAR(1000),
    due_date TIMESTAMP WITHOUT TIME ZONE,
    status VARCHAR(10) NOT NULL,
    priority VARCHAR(10) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    task_list_id UUID NOT NULL,

    CONSTRAINT pk_tasks PRIMARY KEY (id),

    CONSTRAINT fk_tasks_task_lists
        FOREIGN KEY (task_list_id)
        REFERENCES task_lists (id),

    CONSTRAINT chk_tasks_status
        CHECK (status IN ('OPEN', 'CLOSED')),

    CONSTRAINT chk_tasks_priority
        CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH'))
);

CREATE INDEX idx_tasks_task_list_id
    ON tasks (task_list_id);
