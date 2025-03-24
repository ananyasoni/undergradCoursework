-- Find all carriers that operated a route where no other carrier operated
-- the same route. (This is equivalent to finding all carriers who operate
-- a route with competition factor 1.)

-- Your solution to this question may not use grouping or aggregation.
-- Therefore, it may not be easy to compute the competition factor directly
-- for this question. Instead, use NOT EXISTS (or any other strategy you want
-- that is not based on grouping or aggregation) to translate the
-- "no other carrier..." part of the question. Output one column
-- carrier_name with the name of all such carriers. Do not include duplicates.

-- Expected output cardinality: 15 rows

WITH routes_carriers AS (
  SELECT DISTINCT origin, dest, op_unique_carrier
  FROM flights
)
SELECT DISTINCT cname
FROM routes_carriers AS f1
JOIN carriers AS c
ON f1.op_unique_carrier = c.cid
WHERE NOT EXISTS (
    SELECT 1
    FROM routes_carriers AS f2
    WHERE f1.origin = f2.origin
      AND f1.dest = f2.dest
      AND f1.op_unique_carrier != f2.op_unique_carrier
);

