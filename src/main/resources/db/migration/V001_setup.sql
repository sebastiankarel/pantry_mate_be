CREATE TABLE IF NOT EXISTS pantry_mate."user"(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    password TEXT
);

CREATE TABLE IF NOT EXISTS pantry_mate.pantry(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES pantry_mate."user"(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pantry_mate.item(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    quantity INT,
    created_on TIMESTAMP WITH TIME,
    pantry_id INT NOT NULL,
    CONSTRAINT fk_pantry FOREIGN KEY (pantry_id) REFERENCES pantry.pantry (id) ON DELETE CASCADE
)