-- List the distinct pairs of carrier name and origin city name where it is
-- possible to take a flight of at least 3000 miles from that city on that carrier.
-- Name the output columns cname and origin_city_name in that order.

SELECT DISTINCT cname, origin_city_name FROM flights
JOIN carriers
ON carriers.cid = flights.op_unique_carrier
WHERE flights.distance >= 3000;

