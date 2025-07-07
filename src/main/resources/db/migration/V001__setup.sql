CREATE SCHEMA IF NOT EXISTS pantry_mate;

CREATE TABLE IF NOT EXISTS pantry_mate."user"(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(1024),
    password TEXT,
    created_on TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS pantry_mate.pantry_box(
    id SERIAL PRIMARY KEY,
    name VARCHAR(1024),
    quantity INT NOT NULL,
    user_id INT NOT NULL,
    created_on TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
        REFERENCES pantry_mate."user"(id)
        ON DELETE CASCADE
);
