-- Find all registered aircraft (in the N-numbers table) owned by
-- 'UNIVERSITY OF WASHINGTON' along with the type of aircraft
-- (from the aircraft types table).
-- Output columns n_number, name, mfr, and model.
SELECT n_number, name, mfr, model
FROM n_numbers, aircraft_types
WHERE   n_numbers.mfr_mdl_code = aircraft_types.code
        AND name = 'UNIVERSITY OF WASHINGTON';