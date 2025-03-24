-- Write a query without aggregation or grouping to find all tail numbers
-- in the flights data set that flew flights for more than one carrier.
-- Output the tail_num and the carrier code carrier. Do not include duplicate rows.

SELECT DISTINCT f1.tail_num, f1.op_unique_carrier
FROM flights f1, flights f2
WHERE f1.op_unique_carrier != f2.op_unique_carrier AND f1.tail_num = f2.tail_num;