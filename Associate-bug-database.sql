CREATE TABLE bug (
    Bug_ID SERIAL PRIMARY KEY,
    Product VARCHAR(100),
    Environment VARCHAR(100),
    Type VARCHAR(100),
    Description TEXT,
    Status VARCHAR(50)
);
select * from "bug";