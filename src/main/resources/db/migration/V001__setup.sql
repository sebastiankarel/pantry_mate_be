CREATE SCHEMA IF NOT EXISTS pantry_mate;

CREATE TABLE IF NOT EXISTS pantry_mate."user"(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(1024),
    password TEXT,
    created_on TIMESTAMP WITH TIME ZONE
);

CREATE INDEX IF NOT EXISTS user_index ON pantry_mate."user"(username);

CREATE TABLE IF NOT EXISTS pantry_mate.pantry(
    id SERIAL PRIMARY KEY,
    name VARCHAR(1024),
    user_id INT NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
        REFERENCES pantry_mate."user"(id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS pantry_index ON pantry_mate.pantry(name);

CREATE TABLE IF NOT EXISTS pantry_mate.pantry_box(
    id SERIAL PRIMARY KEY,
    name VARCHAR(1024),
    quantity INT NOT NULL,
    pantry_id INT NOT NULL,
    created_on TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_pantry
        FOREIGN KEY(pantry_id)
        REFERENCES pantry_mate.pantry(id)
        ON DELETE CASCADE
);
