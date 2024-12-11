CREATE TABLE "Accounts" (
	"username"	TEXT NOT NULL UNIQUE,
	"password"	TEXT NOT NULL,
	"token"	TEXT UNIQUE,
	PRIMARY KEY("username")
);

CREATE TABLE "Carts" (
	"user-id"	INTEGER
);

CREATE TABLE "Inventory" (
	"item-id"	INTEGER UNIQUE,
	"item-name"	TEXT NOT NULL,
	"item-price"	NUMERIC NOT NULL,
	"img-link"	TEXT NOT NULL,
	"item-avg-rating"	NUMERIC NOT NULL DEFAULT 0,
	"item-num-ratings"	INTEGER NOT NULL DEFAULT 0,
	"item-description"	TEXT NOT NULL,
	"item-category"	TEXT,
	"item-stock"	INTEGER,
	PRIMARY KEY("item-id" AUTOINCREMENT)
);

CREATE TABLE "Purchases" (
	"purchase-id"	INTEGER,
	"username"	TEXT,
	"item-id"	INTEGER,
	FOREIGN KEY("item-id") REFERENCES "Inventory"("item-id"),
	FOREIGN KEY("username") REFERENCES "Accounts"("username"),
	PRIMARY KEY("purchase-id" AUTOINCREMENT)
);

CREATE TABLE "sqlite_sequence" (
	"name",
	"seq"
);