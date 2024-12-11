/*
 * Name: Ananya Shreya Soni
 * Date: May 17th, 2024
 * Section: CSE 154 AC
 *
 * This server side JS file manages various endpoints for a stationary ecommerce site,
 * including user authentication, searching inventory, managing purchase history,
 * creating an account, and finalizing purchases.
 */
"use strict";

const express = require('express');
const multer = require("multer");

// sqlite3 and sqlite import (promisified version)
const sqlite3 = require('sqlite3');
const sqlite = require('sqlite');
const app = express();

// for application/x-www-form-urlencoded
app.use(express.urlencoded({
  extended: true
})); // built-in middleware

// for application/json
app.use(express.json()); // built-in middleware

// for multipart/form-data (required with FormData)
app.use(multer().none()); // requires the "multer" module

// Endpoint for user authentication
app.post('/authenticate', async (req, res) => {
  try {
    const username = req.body.username;
    const password = req.body.password;
    if (!username || !password) {
      res.status(400).type("text");

      res.send('need to send username and password');
    } else {
      // SELECT username FROM Accounts WHERE username = username
      const query = 'SELECT username FROM Accounts WHERE username = ?';
      const db = await getDBConnection();
      let result = await db.get(query, username);
      if (result === undefined) {
        res.status(400).type("text");

        res.send('user does not exist');
        return;
      }
      const query2 = 'SELECT password FROM Accounts WHERE username = ?';
      result = await db.get(query2, username);
      if (password !== result.password) {
        res.status(400).type("text");

        res.send('incorrect password try again');
      } else {
        const query3 = 'SELECT token FROM Accounts WHERE username = ?';
        result = await db.get(query3, username);
        res.type('json').send(result);
      }
    }
  } catch (err) {
    res.type('text').status(500);

    res.send('An error occurred on the server. Try again later.');
  }
});

// Endpoint for searching inventory
app.post('/search', async (req, res) => {
  try {
    const db = await getDBConnection();
    let priceFilter = req.body.price;
    let categoryFilter = req.body.category;
    let searchTerm = req.body.search;
    let query = 'SELECT * FROM Inventory WHERE "item-name" LIKE ? ';
    if (priceFilter === undefined && categoryFilter === undefined && searchTerm === undefined) {
      let result = await db.all('SELECT * FROM Inventory');
      res.type('json').send({
        inventory: result
      });
      return;
    }
    if (categoryFilter === "none") {
      query = query + 'AND "item-category" LIKE ?';
      categoryFilter = "%%";
    } else {
      query = query + 'AND "item-category" = ?';
    }
    if (priceFilter !== "none") {
      if (priceFilter === "DESC") {
        query += ' ORDER BY "item-price" DESC';
      } else {
        query += ' ORDER BY "item-price" ASC';
      }
    }
    let result = await db.all(query, ["%" + searchTerm + "%", categoryFilter]);
    res.type('json');

    res.send({
      inventory: result
    });
    return;
  } catch (err) {
    res.type('text').status(500);

    res.send('An error occurred on the server. Try again later.');
  }
});

// Endpoint for retrieving purchase history
app.post('/purchaseHistory', async (req, res) => {
  try {
    const db = await getDBConnection();
    let username = req.body.username;
    let query = 'SELECT p."purchase-id", p."item-id", ';
    query += 'i."item-name" FROM Purchases p  JOIN Inventory i ON ';
    query += 'p."item-id" = i."item-id"  WHERE p.username = ?'
    const userExists = await checkUserExists(username);
    if (!userExists) {
      res.status(400).type('text').send('invalid user: user does not exist');
      return;
    }
    let result = await db.all(query, username);
    res.type('json').send({
      history: result
    });
    return;
  } catch (err) {
    res.type('text').status(500).send('An error occurred on the server. Try again later.');
  }
});

// Endpoint for creating a user account
app.post('/addAccount', async (req, res) => {
  try {
    let username = req.body.username;
    let password = req.body.password;
    const db = await getDBConnection();
    if (!username || !password) {
      res.status(400).type('text').send('must send username and password');
      return;
    }
    const userExists = await checkUserExists(username);
    if (userExists) {
      res.status(400).type('text');

      res.send('invalid username: username already exists please choose another username');
      return;
    }
    let query = 'INSERT INTO Accounts (username, password, token) VALUES (?, ?, ';
    query += 'hex(randomblob(5)))';
    await db.run(query, username, password);
    const query2 = 'SELECT token FROM Accounts WHERE username = ?';
    const result = await db.get(query2, username);
    res.type('json').send(result);
    return;
  } catch (err) {
    res.type('text').status(500).send('An error occurred on the server. Try again later.');
  }
});

// Endpoint for checking user authentication token
app.post('/checkToken', async (req, res) => {
  try {
    const username = req.body.username;
    const token = req.body.token;
    if (!username && !token) {
      res.status(400).type("text").send('invalid request: must send username and token');
      return;
    }
    const db = await getDBConnection();
    let query = 'SELECT username, token From Accounts WHERE username = ?';
    let result = await db.get(query, username);
    if (result !== undefined && result.token === token) {
      res.send('success');
      return;
    } else {
      res.status(400).type("text").send('invalid request: must send valid username and token');
    }
  } catch (err) {
    res.type('text').status(500);
    res.send('An error occurred on the server. Try again later.');
  }
});

// Endpoint for finalizing a purchase
app.post('/finalizePurchase', async (req, res) => {
  try {
    const itemID = req.body["itemID"];
    const username = req.body.username;
    const db = await getDBConnection();
    let itemExists = await checkItemExists(itemID);
    if (itemExists === false) {
      res.status(500).type('text').send('item out of stock');
      return;
    }
    let query = 'INSERT INTO Purchases ("item-id", username) VALUES (?, ?)';
    let result = await db.run(query, itemID, username);
    let query2 = 'UPDATE Inventory SET "item-stock" = ';
    query2 += '((SELECT "item-stock" FROM Inventory WHERE "item-id"= ?) - 1) WHERE "item-id" = ?;'
    await db.run(query2, itemID, itemID);
    const orderID = result.lastID;
    res.send({
      orderID: orderID
    });
  } catch (err) {
    console.error(err)
  }
});

/**
 * Checks if an item exists in the inventory based on the item ID.
 * @param {number} itemID - The ID of the item to check.
 * @returns {Promise<boolean>} - Returns true if the item exists and has stock greater than 0, otherwise false.
 */
async function checkItemExists(itemID) {
  const db = await getDBConnection();
  let query = 'SELECT "item-stock" FROM Inventory WHERE "item-id"= ?'
  let result = await db.get(query, itemID);
  if (result['item-stock'] > 0) {
    return true;
  } else {
    return false;
  }
}

/**
 * Checks if a user exists in the Accounts table based on the username.
 * @param {string} userName - The username to check.
 * @returns {Promise<boolean>} - Returns true if the user exists in the Accounts table, otherwise false.
 */
async function checkUserExists(userName) {
  const db = await getDBConnection();
  let query = 'SELECT username FROM Accounts WHERE "username"= ?'
  let result = await db.get(query, userName);
  if (result === undefined) {
    return false;
  } else {
    return true;
  }
}

/**
 * Establishes a database connection to the database and returns the database object.
 * Any errors that occur should be caught in the function that calls this one.
 * @returns {sqlite3.Database} - The database object for the connection.
 */
async function getDBConnection() {
  const db = await sqlite.open({
    filename: "stationary.db",
    driver: sqlite3.Database
  });
  return db;
}

app.use(express.static('public'));
const PORT = process.env.PORT || 8000;
app.listen(PORT);