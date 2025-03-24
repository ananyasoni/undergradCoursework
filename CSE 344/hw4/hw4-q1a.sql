-- Find the 10 longest distinct distances flown. Then, for each such distance,
-- find all offered "carrier routes", i.e., distinct (carrier, origin, destination)
-- triples, and report how many times that carrier route was flown. Identify routes by
-- airport codes, not city names. Return columns carrier_name, origin, dest, distance,
-- and num_flights. Sort the output in order of distance (highest distance first),
-- breaking ties by origin (A-to-Z), then destination (A-to-Z), and then carrier
-- name (A-to-Z). Note that a flight from, say SEA to ORD by Alaska Airlines, is
-- considered different from the route ORD to Seattle by Alaska Airlines. Most
-- carriers offer routes in both directions, which should appear as two separate rows
-- in your output. Expected output relation cardinality: 22 rows. (You might expect it
-- to be 20 rows, 2 directions for each of the top 10 distances, but some distances have
-- multiple pairs of cities associated with them.)
-- SELECT DISTINCT distance FROM flights ORDER BY distance DESC LIMIT 10;

WITH longestDist AS (
    SELECT DISTINCT distance
    FROM flights
    ORDER BY distance DESC
    LIMIT 10
)
SELECT DISTINCT c.cname AS carrier_name, origin, dest,
                f.distance, count(op_unique_carrier) AS num_flights
FROM flights f, longestDist
JOIN carriers c
ON f.op_unique_carrier = c.cid
WHERE f.distance = longestDist.distance
GROUP BY op_unique_carrier, origin, dest
ORDER BY f.distance DESC, origin ASC, dest ASC, carrier_name ASC

