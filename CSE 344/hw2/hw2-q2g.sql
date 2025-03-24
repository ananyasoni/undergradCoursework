-- Find all flights from 'Tampa, FL' on a 737 that were canceled due to weather.
-- For the purposes of this problem, consider "a 737" to be any aircraft that has
-- '737' as a substring of its aircraft type's model column. As in the previous
-- problem, you may assume that tail_num in the flights table always starts
-- with an N, even though that is not true.
-- Output columns year, month, day_of_month, cname, and flight_num.

-- SELECT year, month, day_of_month, cname, op_carrier_fl_num AS flight_num, n_number, model, cancellation_codes.cancellation_code
-- FROM flights, carriers, cancellation_codes, n_numbers, aircraft_types
-- WHERE   flights.op_unique_carrier = carriers.cid
--         AND flights.cancellation_code = cancellation_codes.cancellation_code
--         AND cancellation_codes.description = 'Weather'
--         AND flights.origin_city_name = 'Tampa, FL'
--         AND 'N'||n_numbers.n_number = flights.tail_num
--         AND n_numbers.mfr_mdl_code = aircraft_types.code
--         AND aircraft_types.model LIKE '%737%';


SELECT year, month, day_of_month, cname, op_carrier_fl_num AS flight_num
FROM flights, carriers, cancellation_codes, n_numbers, aircraft_types
WHERE   flights.op_unique_carrier = carriers.cid
        AND flights.cancellation_code = cancellation_codes.cancellation_code
        AND 'N'||n_numbers.n_number = flights.tail_num
        AND n_numbers.mfr_mdl_code = aircraft_types.code
        AND cancellation_codes.description = 'Weather'
        AND flights.origin_city_name = 'Tampa, FL'
        AND aircraft_types.model LIKE '%737%';

