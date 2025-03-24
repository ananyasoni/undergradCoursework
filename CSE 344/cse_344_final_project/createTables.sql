-- Add all your SQL setup statements here. 

-- When we test your submission, you can assume that the following base
-- tables have been created and loaded with data.  However, before testing
-- your own code, you will need to create and populate them on your
-- Postgres instance
--
-- Do not alter the following tables' contents or schema in your code.
-- \copy CARRIERS from '/Users/ananyasoni/Desktop/project/flights-data/carriers.csv' CSV
-- \copy MONTHS from '/Users/ananyasoni/Desktop/project/flights-data/months.csv' CSV
-- \copy WEEKDAYS from '/Users/ananyasoni/Desktop/project/flights-data/weekdays.csv' CSV
-- \copy FLIGHTS from '/Users/ananyasoni/Desktop/project/flights-data/flights-small.csv' CSV
-- CREATE TABLE FLIGHTS(fid int primary key, 
--         month_id int REFERENCES MONTHS,        -- 1-12
--         day_of_month int,    -- 1-31 
--         day_of_week_id int REFERENCES WEEKDAYS,  -- 1-7, 1 = Monday, 2 = Tuesday, etc
--         carrier_id varchar(7) REFERENCES CARRIERS, 
--         flight_num int,
--         origin_city varchar(34), 
--         origin_state varchar(47), 
--         dest_city varchar(34), 
--         dest_state varchar(46), 
--         departure_delay int, -- in mins
--         taxi_out int,        -- in mins
--         arrival_delay int,   -- in mins
--         canceled int,        -- 1 means canceled
--         actual_time int,     -- in mins
--         distance int,        -- in miles
--         capacity int, 
--         price int            -- in $
--         );

-- CREATE TABLE CARRIERS(cid varchar(7) primary key,
--          name varchar(83));

-- CREATE TABLE MONTHS(mid int primary key,
--        month varchar(9));

-- CREATE TABLE WEEKDAYS(did int primary key,
--          day_of_week varchar(9));

CREATE TABLE Users_ananya99 (
    username TEXT PRIMARY KEY,
    password BYTEA NOT NULL,
    balance INT
);

CREATE TABLE Reservations_ananya99 (
    reservation_id INT PRIMARY KEY,
    username TEXT NOT NULL,
    is_paid BOOLEAN NOT NULL,
    is_direct BOOLEAN NOT NULL,
    f1_id INT NOT NULL,
    f2_id INT,
    total_amount INT NOT NULL,
    day_of_month INT NOT NULL,
    FOREIGN KEY (username) REFERENCES Users_ananya99(username),
    FOREIGN KEY (f1_id) REFERENCES FLIGHTS(fid),
    FOREIGN KEY (f2_id) REFERENCES FLIGHTS(fid)
);