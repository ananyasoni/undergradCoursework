-- Find all aircraft that were manufactured in 2024 and took a
-- flight from the city in which they are registered. Output
-- columns n_number, name, and city from the N-numbers table.
-- Do not include duplicates.
SELECT DISTINCT n_number, name, city
FROM flights, n_numbers
WHERE   'N'||n_numbers.n_number = flights.tail_num
        AND n_numbers.year_mfr = 2024
        AND flights.dest_city_name
        LIKE '%'||n_numbers.city||', '||n_numbers.state||'%';