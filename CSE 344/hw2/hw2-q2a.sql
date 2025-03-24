-- List the distinct carrier names of all carriers that offer a flight
-- from the Seattle airport (code SEA) to any airport in Chicago.
-- (Warning: there is more than one airport in Chicago. Your query should
-- work even if they build yet another airport in Chicago.) Name the output column cname.
SELECT DISTINCT cname FROM carriers, flights WHERE carriers.cid = flights.op_unique_carrier AND flights.origin = 'SEA' AND flights.dest_city_name = 'Chicago, IL';