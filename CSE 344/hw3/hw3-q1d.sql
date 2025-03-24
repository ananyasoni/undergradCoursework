-- For each possible cancellation reason (description in cancellation_codes),
-- find how many flights were canceled for that reason. Include cancellation
-- reasons that are never used, displayed with 0 flights. Output columns cancellation_reason
-- and num_flights.

-- (Output relation cardinality: 4 rows. Min value of num_flights is 0.)

SELECT c.description AS cancellation_reason, count(f.tail_num) AS num_flights
FROM cancellation_codes c
LEFT OUTER JOIN flights f
ON f.cancellation_code = c.cancellation_code
GROUP BY cancellation_reason;