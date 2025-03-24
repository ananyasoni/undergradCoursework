-- The column actual_elapsed_time represents the flight duration in minutes.
-- Write a comment at the top of your file hw3-q1e.sql that explains (1) what
-- is surprising about the order of the rows in the output of this query and
-- (2) why SQLite orders it that way. (Note the ORDER BY clause.
-- Look carefully at the actual output.)

-- SELECT DISTINCT actual_elapsed_time
-- FROM flights
-- ORDER BY actual_elapsed_time

-- Then rewrite the ORDER BY clause of the query to put the rows in the
-- "right" order that the original query author probably intended.
-- Do not change any other clauses. Put your query in the same file, hw3-q1e.sql.

-- Hint: You may find the SQLite data types documentation page useful again.

-- 1) The first few tuples are in the correct order but there are places where
--    the next tuple suddenly decreases relative to the previous tuple.
--    for example from 159.00 to 16.00 and 179.00 to 18.00
-- 2) SQLite orders the rows this way because it is sorting actual_elapsed_time as text
--    rather than as a number. As a result, it follows a lexicographic order based on a
--    collating sequence, rather than numerical order.

-- (Output relation cardinality: 614 rows. Second row of corrected output has
-- actual_elapsed_time 16.00).

SELECT DISTINCT actual_elapsed_time
FROM flights
ORDER BY CAST(actual_elapsed_time AS INT);