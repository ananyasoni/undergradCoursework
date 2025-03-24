-- The "competition factor" on a route is the number of distinct carriers who fly
-- that route. For each distinct competition factor, find how many distinct routes
-- have that competition factor. Report columns competition_factor and num_routes.
-- Sort the output by competition factor from highest to lowest.

-- Expected output cardinality: 7 rows, one each for competition factors 7, 6, ..., 1.
-- The first row is (7, 8). In other words, there are 8 routes with competition factor
-- 7.

WITH route_competition AS (
    SELECT origin, dest, COUNT(DISTINCT op_unique_carrier) AS competition_factor
    FROM flights
    GROUP BY origin, dest
)
SELECT competition_factor, COUNT(origin) AS num_routes
FROM route_competition
GROUP BY competition_factor
ORDER BY competition_factor DESC;
