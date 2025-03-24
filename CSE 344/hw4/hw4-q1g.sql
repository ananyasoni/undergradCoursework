-- Starting at some origin airport, a "one-hop destination" is an airport reachable
-- in a single flight from the origin airport. The "connection factor" of an origin
-- airport is how many distinct one-hop destinations one can reach from that origin.

-- Find airports where all of their one-hop destinations have a strictly higher
-- connection factor.
-- ie: there doesn't exist a one-hop destination with a <= connection factor
-- (not a strictly higher connection factor)

-- Output a single column airport with the airport codes of all such airports in
-- any order but without duplicates.

-- Expected output cardinality: 252 rows

-- find the connection factor of all origin airports
-- SELECT DISTINCT f.origin, count(DISTINCT dest)
-- FROM flights f
-- GROUP BY f.origin;

WITH connection_factors AS (
    SELECT f.origin, COUNT(DISTINCT f.dest) AS connection_factor
    FROM flights f
    GROUP BY f.origin
),
all_flights_connection_factors AS (
    SELECT DISTINCT flights.origin, flights.dest, connection_factor
    FROM flights
    JOIN connection_factors
    ON flights.origin = connection_factors.origin
)
SELECT DISTINCT f1.origin
FROM all_flights_connection_factors f1
WHERE NOT EXISTS (
    SELECT 1
    FROM all_flights_connection_factors f2
    WHERE f2.dest = f1.origin
    AND f2.connection_factor <= f1.connection_factor
)