-- The "competition factor" on a route is the number of distinct carriers who fly that route.
-- For each route with competition factor 7, find the names of the 7 distinct carriers that fly
-- that route. Report your results in three columns: the origin and dest airport codes of the routes,
-- and a third column called competitors that is a string consisting of all the names of the
-- competitors in alphabetical (A-to-Z) order separated by semicolons and spaces.
-- (See the example output below.) Sort the output by origin airport code (A-to-Z) and break
-- ties with destination airport code (A-to-Z). Hint: To compute the competitors column, use
-- the string_agg function discussed in lecture and refer to its documentation. Pay special
-- attention to getting the carriers within the string in the right order using the weird ORDER BY
-- syntax inside of the string_agg function. (string_agg was added to SQLite in 2023. Older versions
-- of SQLite support another identical function group_conat. Use group_concat if you are on an older
-- version. I prefer the name string_agg because it also works in Postgres, but both are fine for
-- this HW. Also, older versions that only support group_concat do not support the ORDER BY syntax
-- inside of aggregate functions. If you are using an older version, feel free to ignore the requirement
-- that airlines are listed in alphabetical order.)

-- You are allowed/expected to hard code the number 7 in your query.

-- Expected output cardinality: 8 rows. The first row is from ATL to PHL with this competitors string:

-- American Airlines Inc.; Delta Air Lines Inc.; Frontier Airlines Inc.; PSA Airlines Inc.;
-- Republic Airline; Southwest Airlines Co.; Spirit Air Lines

SELECT f.origin, f.dest, GROUP_CONCAT(c.cname, '; ') AS competitors
FROM (
    SELECT DISTINCT origin, dest, op_unique_carrier
    FROM flights
) f
JOIN carriers c ON f.op_unique_carrier = c.cid
GROUP BY f.origin, f.dest
HAVING COUNT(f.op_unique_carrier) = 7
ORDER BY f.origin ASC, f.dest ASC;



