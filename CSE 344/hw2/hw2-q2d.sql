-- Find all aircraft (identified by tail_num) that departed the same airport
-- (identified by the airport code) twice on the same day to different destination
-- airports where the two flights totaled at least 5000 miles.
-- Output columns tail_num and origin. Do not include duplicate rows.

SELECT DISTINCT f1.tail_num, f1.origin FROM flights AS f1
JOIN flights AS f2
ON f1.tail_num = f2.tail_num AND f1.origin = f2.origin
WHERE   f2.year = f1.year
        AND f2.month = f1.month
        AND f2.day_of_month = f1.day_of_month
        AND f1.dest != f2.dest
        AND f1.distance + f2.distance >= 5000;