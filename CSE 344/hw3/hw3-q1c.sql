-- Write a query to count how many non-canceled flights each tail number made.
-- Output columns tail_num, carrier, mfr, and model, and count. (carrier is the
-- name of the carrier from the carriers table. mfr and model are from the
-- aircraft types table. count refers to the number of rows this tail number
-- has in the flights table.) Sort the output by count from highest to lowest,
-- and return only the 20 rows with the highest counts.

-- (Output relation cardinality: 20 rows. Max value of count is 294.)

-- Then, write a comment in your hw3-q1c.sql file explaining why you chose
-- the GROUP BY clause that you chose. (There is more than one reasonable choice.)

-- I grouped by tail_num since for each *tail_num we want to count how many
-- non-canceled flights were made with that tail_num. Since tail_num is our
-- parameter to the count aggregate function we want to group by tail_num

SELECT DISTINCT f.tail_num, c.cname AS carrier, a.mfr, a.model, count(f.tail_num) AS count
FROM flights f
JOIN carriers c
ON f.op_unique_carrier = c.cid
JOIN n_numbers n
ON 'N'||n.n_number = f.tail_num
JOIN aircraft_types a
ON n.mfr_mdl_code = a.code
WHERE f.cancelled = 0
GROUP BY f.tail_num
ORDER BY count(f.tail_num)  DESC
LIMIT 20;

