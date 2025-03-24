-- For each airline, calculate the percentage of its non-canceled flights that arrived on time
-- or early (i.e., arrival delay <= 0). Sort the output in descending order of this percentage,
-- and break ties in ascending alphabetical order by airline name. Report percentages as percentages,
-- not decimals (e.g., report 75.2534... rather than 0.7525...). Do not round the percentages. ]
-- Name the output columns carrier_name and on_time_pct. In case a carrier has no flights, include a
-- row for that carrier with percentage 0.0.

-- Hint: Use CASE expressions (link to SQLite documentation) to implement conditionals in SQL
-- (kind of like if statements or the ternary operator in other languages).

-- Hint: You can put a CASE expression as the argument to an aggregate function.

-- (Output relation cardinality: 1743 rows because there are 1743 carriers in the carriers table.
-- First row of output has carrier_name "Endeavor Air Inc." and on_time_pct 80.46...)

SELECT
    c.cname AS carrier_name,
    (CASE
        WHEN count(f.arr_delay) = 0
        THEN 0.0
        ELSE (count(CASE WHEN f.cancelled = 0 AND f.arr_delay <= 0 THEN 1 END) * 100.0 /
              count(CASE WHEN f.cancelled = 0 THEN 1 END))
     END) AS on_time_pct
FROM carriers c
LEFT OUTER JOIN flights f ON c.cid = f.op_unique_carrier
GROUP BY c.cname
ORDER BY on_time_pct DESC, carrier_name ASC;
