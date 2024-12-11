/*
 * Name: Ananya Shreya Soni
 * Date: May 17th, 2024
 * Section: CSE 154 AC
 *
 * This JS file manges a user's to do list through
 * various endpoints. In can add to do items
 * to a to do list, sort items, and clear todos.
 */

"use strict";

const express = require('express');
const fs = require('fs').promises;
const app = express();
const multer = require("multer");

// for application/x-www-form-urlencoded
app.use(express.urlencoded({extended: true})); // built-in middleware

// for application/json
app.use(express.json()); // built-in middleware

// for multipart/form-data (required with FormData)
app.use(multer().none()); // requires the "multer" module

app.post("/add_task", async (req, res) => {
  try {
    if (!(req.body['todo-input'] && req.body['minutes'])) {
      res.status(400).type('text');

      res.send('need to provide task and approximate time required to complete task');
    } else {
      const todoInput = req.body['todo-input'];
      const minutes = req.body['minutes'];
      await addTodo(todoInput, minutes);
      res.status(200);
      let newToDoList = await fs.readFile('todos.json', 'utf-8');
      JSON.stringify(newToDoList);

      res.send(newToDoList);
    }
  } catch (error) {
    if (error.code === 'ENOENT') {
      res.status(500).type('text');

      res.send('file not found');
    } else {
      res.status(500).type('text');

      res.send('error');
    }
  }
});

app.get("/get_all_tasks", async (req, res) => {
  try {
    let existingToDos = await fs.readFile('todos.json', 'utf-8');
    JSON.stringify(existingToDos);

    res.send(existingToDos);
  } catch (error) {
    if (error.code === 'ENOENT') {
      res.status(500).type('text');

      res.send('file not found');
    } else {
      res.status(500).type('text');

      res.send('error');
    }
  }
});

app.post("/clear_tasks", async (req, res) => {
  try {
    await clearTodos();
    res.status(200).type('text');

    res.send('successfully cleared todos');
  } catch (error) {
    if (error.code === 'ENOENT') {
      res.status(500).type('text');

      res.send('file not found');
    } else {
      res.status(500).type('text');

      res.send('error');
    }
  }
});

app.post("/sort_tasks", async (req, res) => {
  try {
    await sortTodos();
    let sortedToDos = await fs.readFile('todos.json', 'utf-8');
    JSON.stringify(sortedToDos);

    res.send(sortedToDos);
  } catch (error) {
    if (error.code === 'ENOENT') {
      res.status(500).type('text');

      res.send('file not found');
    } else {
      res.status(500).type('text');

      res.send('error');
    }
  }
});

/**
 * Adds a todo item to the file todos.json
 * @param {JSON} todoInput - The request todo input the user
 * sends
 * @param {JSON} minutes - The request approximate minutes
 * to complete task input the user sends
 */
async function addTodo(todoInput, minutes) {
  let existingToDos = await fs.readFile('todos.json', 'utf-8');
  existingToDos = JSON.parse(existingToDos);
  existingToDos.todos.push({'todo-input': todoInput, 'minutes': minutes, 'completed': false});
  let updatedToDos = existingToDos;
  await fs.writeFile('todos.json', JSON.stringify(updatedToDos));
}

/**
 * Clears todo items in the file todos.json
 */
async function clearTodos() {
  let existingToDos = await fs.readFile('todos.json', 'utf-8');
  existingToDos = JSON.parse(existingToDos);
  existingToDos.todos = [];
  let updatedToDos = existingToDos;
  await fs.writeFile('todos.json', JSON.stringify(updatedToDos));
}

/**
 * Sorts todo items in the file todos.json
 * by time in ascending order
 */
async function sortTodos() {
  let existingToDos = await fs.readFile('todos.json', 'utf-8');
  existingToDos = JSON.parse(existingToDos);

  /**
   * sort array using JS sort function with a sort key parameter
   * that tells JS how to sort the array (sorts tasks in ascending
   * order based on approximate time required to complete)
   */
  existingToDos.todos.sort((todo1, todo2) => {
    return todo1.minutes - todo2.minutes;
  });
  let updatedToDos = existingToDos;
  await fs.writeFile('todos.json', JSON.stringify(updatedToDos));
}

app.use(express.static('public'));
const PORT = process.env.PORT || 8000;
app.listen(PORT);