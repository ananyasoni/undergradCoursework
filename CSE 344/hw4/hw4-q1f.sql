-- We consider a flight to be "on time" if it has an arrival delay of at most 15 minutes.

-- The daily on-time percentage for a carrier is the percentage of that carrier's flights
-- that arrived on time for that day. Find all carriers that flew at least one flight and
-- have a daily on-time percentage of at least 75% on every day that they flew a flight.
-- ie: there does not exist a daily on time percentage less than 75% on every day the
-- carrier flew a flight

-- Be careful not to assume the data only consists of one month of flights. Use
-- (year, month, day_of_month) to identify a day, not just day_of_month.

-- Output one column carrier_name with the names of the carriers in any order but without duplicates.

-- Hint: "Every day" is a for-all statement.
-- Expected output cardinality: 4 rows.

WITH on_time_pcts AS (
    SELECT DISTINCT cname, f.arr_delay, f.year, f.month, f.day_of_month, f.op_unique_carrier,
        (count(CASE WHEN f.cancelled = 0 AND CAST(f.arr_delay AS INT) <= 15 THEN 1 END)) * 100.0 /
        (count(CASE WHEN f.cancelled = 0 THEN 1 END)) AS on_time_pct
    FROM flights f
    JOIN carriers c
    ON f.op_unique_carrier = c.cid
    WHERE f.cancelled = 0
    GROUP BY f.year, f.month, f.day_of_month, f.op_unique_carrier
)
SELECT DISTINCT f1.cname
FROM on_time_pcts f1
WHERE NOT EXISTS (
    SELECT 1
	FROM on_time_pcts
    WHERE on_time_pcts.cname = f1.cname
    AND CAST(on_time_pcts.on_time_pct AS REAL) < 75.0
)