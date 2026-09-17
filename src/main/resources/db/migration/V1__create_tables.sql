CREATE TABLE segment (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255),
                         min_spending DOUBLE PRECISION
);

CREATE TABLE customer (
                          id BIGSERIAL PRIMARY KEY,
                          full_name VARCHAR(255),
                          monthly_spending DOUBLE PRECISION,
                          segment_id BIGINT REFERENCES segment(id)
);

CREATE TABLE campaign (
                          id BIGSERIAL PRIMARY KEY,
                          title VARCHAR(255),
                          description VARCHAR(255),
                          segment_id BIGINT REFERENCES segment(id)
);