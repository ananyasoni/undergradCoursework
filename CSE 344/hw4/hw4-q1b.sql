-- A "route" is an ordered pair of origin airport code and destination airport code.
-- (This is different from a "carrier route" which includes the carrier.)
-- Find the 20 distinct most flown routes as well as every route that was
-- flown exactly once. Show these results combined in one table, with columns
-- origin, origin_city_name, dest, dest_city_name, and num_flights. Sort the
-- output by num_flights from lowest to highest, and break ties with origin,
-- and then dest. Hint: to combine two relations with the same schema, check out
-- the UNION keyword. Note carefully the limitations of UNION with regards to its
-- component queries and LIMIT and ORDER BY. You may need to work around these
-- limitations using additional subqueries or using the WITH keyword.

-- Expected output relation cardinality: 70 rows. First row is a route from ATL to
-- ISP. Last row is a route from LAX to SFO

WITH flown_most AS (
    SELECT origin, origin_city_name, dest, dest_city_name, count(op_unique_carrier) AS num_flights
    FROM flights f
    GROUP BY origin, dest
    ORDER BY num_flights DESC
    LIMIT 20
),
flown_once AS (
    SELECT origin, origin_city_name, dest, dest_city_name, count(op_unique_carrier) AS num_flights
    FROM flights f
    GROUP BY origin, dest
    HAVING count(op_unique_carrier) = 1
    ORDER BY num_flights DESC
)
SELECT * FROM flown_most
UNION ALL
SELECT * FROM flown_once
ORDER BY num_flights ASC, origin, dest