SELECT  carriers.cname,
        f1.op_carrier_fl_num AS f1_flight_num,
        f1.actual_elapsed_time AS f1_time ,
        f1.dest_city_name AS intermediate_city,
        f2.op_carrier_fl_num AS f2_flight_num,
        f2.actual_elapsed_time AS f2_time,
        f1.actual_elapsed_time + f2.actual_elapsed_time AS [total time]
FROM flights AS f1, carriers
JOIN flights AS f2
ON  f2.op_unique_carrier = f1.op_unique_carrier
    AND f1.dep_time < f2.dep_time
    AND f2.year = f1.year
    AND f2.month = f1.month
    AND f2.day_of_month = f1.day_of_month
WHERE f1.year = 2024
    AND f1.month = 9
    AND f1.day_of_month = 15
    AND f1.origin  = 'SEA'
    AND f2.dest_city_name = 'Chicago, IL'
    AND f1.dest = f2.origin
    AND carriers.cid = f1.op_unique_carrier
    AND [total time] < 480;