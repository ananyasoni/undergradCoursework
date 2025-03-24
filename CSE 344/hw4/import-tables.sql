.mode csv
PRAGMA foreign_keys=ON;
.import --skip 1 data/carriers.csv carriers
.import --skip 1 data/flights-2024-09.csv flights
.import --skip 1 data/aircraft_types.csv aircraft_types
.import --skip 1 data/n_numbers.csv n_numbers
.import --skip 1 data/cancellation_codes.csv cancellation_codes