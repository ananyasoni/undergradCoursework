-- Write a query without joins to find how many different
-- carriers each tail number flew. Output tail_num and
-- carrier_count (the number of different carriers that
-- tail number flew for). Only include rows where the number
-- of carriers is 2 or more.

-- the reason tail_num is empty is because if we look at the output from
-- our query for 1a we see that all the tail numbers in the flights data
-- set that flew flights for more than one carrier are empty as well
SELECT DISTINCT tail_num, count(distinct op_unique_carrier) num_carriers
FROM flights
GROUP BY tail_num
HAVING num_carriers >= 2;