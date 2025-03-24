-- Primary Keys:
-- carriers: The primary key is `cid`.
-- cancellation_codes: The primary key is `cancellation_code`.
-- n_numbers: The primary key is `n_number`.
-- aircraft_types: The primary key is `code`.

-- flights: This table has no primary key.

-- Foreign Key References:
-- flights.op_unique_carrier references carriers.cid.
-- n_numbers.mfr_mdl_code references aircraft_types.code.

-- Notes:
-- flights.n_number is not a foreign key to the n_numbers table.
-- flights.cancellation_code is not a foreign key to the cancellation_codes table.
-- Reasons for these exclusions will be explained further in the documentation.

CREATE TABLE carriers (
  cid TEXT PRIMARY KEY,                -- the "code" of the airline
  cname TEXT                  -- the long name of the airline
);

-- no primary_key
CREATE TABLE flights (
  year INT,
  month INT,                   -- 1-12
  day_of_month INT,            -- 1-31
  day_of_week INT,             -- 1-7, 1 = Mon
  op_unique_carrier TEXT,      -- the code of the airline, see carriers table
  tail_num TEXT,               -- the "license plate" of the aircraft
  op_carrier_fl_num INT,
  origin TEXT,                 -- airport code where flight started
  origin_city_name TEXT,
  origin_state_abr TEXT,
  dest TEXT,                   -- airport code of destination
  dest_city_name TEXT,
  dest_state_abr TEXT,
  crs_dep_time TEXT,           -- scheduled departure time
  dep_time TEXT,               -- actual departure time
  dep_delay REAL,
  crs_arr_time TEXT,           -- scheduled arrival time
  arr_time TEXT,               -- actual arrival time
  arr_delay REAL,
  cancelled REAL,              -- 1 if flight was cancelled, else 0
  cancellation_code TEXT,      -- reason for cancellation, or empty
                               -- see cancellation_codes table
  diverted REAL,               -- 1 if the flight was diverted, else 0
  actual_elapsed_time TEXT,    -- departure-to-arrival time in minutes
  distance REAL,                -- miles between origin and destination
  FOREIGN KEY(op_unique_carrier) REFERENCES carriers(cid)
);

CREATE TABLE cancellation_codes (
  cancellation_code TEXT PRIMARY KEY,     -- the code
  description TEXT            -- why the flight was cancelled
);

CREATE TABLE aircraft_types (
  code TEXT PRIMARY KEY,            -- the code, used by the n_numbers table
  mfr TEXT,             -- name of the manufacturer
  model TEXT,           -- name of the model
  no_eng INT,           -- number of engines on this type
  no_seats INT,         -- maximum number of seats on this type
  ac_weight TEXT,       -- weight class (1 through 4)
  speed INT             -- average cruising speed of this type in miles per hour
);

CREATE TABLE  n_numbers (
  n_number TEXT PRIMARY KEY,         -- the "license plate number" without the N
  serial_number TEXT,
  mfr_mdl_code TEXT,     -- manufacturer and model code, see aircraft_types
  eng_mfr_mdl TEXT,
  year_mfr TEXT,
  name TEXT,             -- name of entity that registered the aircraft
  street TEXT,           -- their address is stored in the rest of the columns
  street2 TEXT,
  city TEXT,
  state TEXT,
  zip_code TEXT,
  region TEXT,
  county TEXT,
  country TEXT,
  FOREIGN KEY(mfr_mdl_code) REFERENCES aircraft_types(code)
);
