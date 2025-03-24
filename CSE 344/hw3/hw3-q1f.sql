-- For each origin city, determine the destination city with the longest flight, based on
-- flight duration (i.e., actual_elapsed_time). Use what you learned in the previous problem
-- to make sure you are handling actual_elapsed_time correctly. If there is more than one
-- destination city that achieves the maximum flight time for a given origin city, pick one
-- to include arbitrarily.

-- Output columns as origin_city, longest_dest_city, and longest_time.

-- Order the output from highest longest_time to lowest. Break ties with origin_city
-- alphabetically A-to-Z.

-- Hint: Use SQLite's special handling of bare columns in aggregate queries with max/min.

-- (Output relation cardinality: 334 rows. First row of output has longest_time 690.0.)

SELECT f.origin_city_name AS origin_city, f.dest_city_name AS longest_dest_city,
       MAX(CAST(f.actual_elapsed_time AS INT)) AS longest_time
FROM flights f
GROUP BY f.origin_city_name
ORDER BY CAST(longest_time AS INT) DESC, origin_city ASC;