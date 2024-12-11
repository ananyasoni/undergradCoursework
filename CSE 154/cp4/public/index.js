/*
 * Name: Ananya Shreya Soni
 * Date: April 19th, 2024
 * Section: CSE 154 AC
 *
 * This JS file adds behavior to my about me page
 * in my website and adds some interactivity. For example,
 * there is now a slidshow of images the user can click on
 * to change to the next image and a button hover animation.
 * If the my location button is clicked my location is displayed
 * and if the storm image is clicked the definition of storm is
 * displayed.
 */
"use strict";
(function() {
  window.addEventListener("load", init);

  /**
   * initial function run after page loads. Adds event listeners to buttons
   */
  function init() {
    id("clear").addEventListener("click", clearTodos);
    id("order").addEventListener("click", sortTodos);
    id("form-input").addEventListener("submit", addTodo);
  }

  /**
   * Clears To Do List
   * @param {EventListenerObject} event event object
   */
  async function clearTodos(event) {
    try {
      event.preventDefault();
      id("todos").innerHTML = "";
      let url = "/clear_tasks";
      let params = new FormData();
      let result = await fetch(url, {
        method: "POST",
        body: params
      });
      await statusCheck(result);
      result = await result.text();
    } catch (err) {
      handleError(err);
    }
  }

  /**
   * Sorts To-Do List in ascending order by time
   * @param {EventListenerObject} event event object
   */
  async function sortTodos(event) {
    try {
      event.preventDefault();
      id("todos").innerHTML = "";
      let url = "/sort_tasks";
      let params = new FormData();
      let result = await fetch(url, {
        method: "POST",
        body: params
      });
      await statusCheck(result);
      result = await result.json();
      renderToDoList(result);
    } catch (err) {
      handleError(err);
    }
  }

  /**
   * Renders all current To Do List Tasks on the screen
   * @param {Object} toDoObj Contains To Do List
   */
  function renderToDoList(toDoObj) {
    for (let i = 0; i < toDoObj.todos.length; i++) {
      let nextToDoContainer = gen("div");
      let nextToDo = gen("input");
      nextToDo.type = "checkbox";
      nextToDo.id = (i + 1);
      let label = gen("label");
      label.setAttribute("for", nextToDo.id);
      label.textContent = "Task " + (i + 1) + ": " + toDoObj.todos[i]['todo-input'] +
                          ", approximate amount of time: " + toDoObj.todos[i]['minutes'] + " mins";
      nextToDoContainer.appendChild(nextToDo);
      nextToDoContainer.appendChild(label);
      nextToDoContainer.id = (i);
      id("todos").appendChild(nextToDoContainer);
    }
  }

  /**
   * Adds user's next inputted task and corresponding
   * @param {EventListenerObject} event event object
   * time to the To Do List
   */
  async function addTodo(event) {
    try {
      event.preventDefault();
      id("todos").innerHTML = "";
      let url = "/add_task";
      let params = new FormData(id("form-input"));
      let result = await fetch(url, {
        method: "POST",
        body: params
      });
      await statusCheck(result);
      result = await result.json();
      renderToDoList(result);
    } catch (err) {
      handleError(err);
    }
  }

  /**
   * elegant error handling function
   * @param {Error} err error object describing error
   */
  function handleError(err) {
    let errorHeader = document.createElement("h2");
    errorHeader.textContent = "Please Try Again :( Could not fetch: " + err;
    qs("header").appendChild(errorHeader);
  }

  /**
   * Checks the status of a fetch response and throws an error if it's not ok.
   * @param {Response} response - The response object to check.
   * @throws {Error} Throws an error with the response text if the response is not ok.
   * @returns {Response} The response object if it is ok.
   */
  async function statusCheck(response) {
    if (!response.ok) {
      throw new Error(await response.text());
    }
    return response;
  }

  /**
   * Finds the element with the specified ID attribute.
   * @param {string} id - element ID
   * @returns {HTMLElement | null} DOM object associated with id.
   */
  function id(id) {
    return document.getElementById(id);
  }

  /**
   * returns all elements that would be matched by the given CSS selector string
   * @param {any} selector specifies which HTML elements to return
   * @returns {NodeListOf<HTMLElement>} list of DOM object associated with selector
   */
  function qs(selector) {
    return document.querySelector(selector);
  }

  /**
   * creates and returns a new empty DOM node representing an element of that type
   * @param {any} tagName specified HTMLElement type
   * @returns {HTMLElement} DOM object
   */
  function gen(tagName) {
    return document.createElement(tagName);
  }

})();
