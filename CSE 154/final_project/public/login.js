/*
 * Name: Ananya Shreya Soni
 * Date: April 19th, 2024
 * Section: CSE 154 AC
 *
 * This JS file handles the user authentication and account creation
 * processes for the stationary e-commerce site. It allows a user to
 * to switch between login and sign-up forms and login or sign-up.
 */
"use strict";

(function() {
  window.addEventListener("load", init);

  /**
   * Initial function run after page loads. Adds event listeners to the buttons.
   */
  function init() {
    const login = document.getElementById('login-form');
    login.addEventListener('submit', authenticate);
    id("sign-up-btn").addEventListener('click', changeToSignUp);
    id("sign-in-btn").addEventListener('click', changeToSignIn);
    id('sign-up-form').addEventListener('submit', addAccount);
    let buttons = document.querySelectorAll(".button");
    for (let i = 0; i < buttons.length; i++) {
      buttons[i].addEventListener('mouseenter', onMouseEnter);
      buttons[i].addEventListener('mouseleave', onMouseLeave);
    }
  }

  /**
   * Switches the view to the sign-up form.
   */
  function changeToSignUp() {
    id('error').classList.add("hidden");
    id('login-form').classList.add("hidden");
    id('h1').classList.add('hidden');
    id('sign-up-btn').classList.add('hidden');
    id('sign-up-form').classList.remove("hidden");
    id('h2').classList.remove('hidden');
    id('sign-in-btn').classList.remove('hidden');
  }

  /**
   * Switches the view to the sign-in form.
   */
  function changeToSignIn() {
    id('error').classList.add("hidden");
    id('login-form').classList.remove("hidden");
    id('h1').classList.remove('hidden');
    id('sign-up-btn').classList.remove('hidden');
    id('sign-up-form').classList.add("hidden");
    id('h2').classList.add('hidden');
    id('sign-in-btn').classList.add('hidden');
  }

  /**
   * Authenticates the user
   * @param {Event} evt - The event object from the form submission.
   */
  async function authenticate(evt) {
    try {
      evt.preventDefault();
      id('error').classList.add("hidden");
      const userNameInput = document.getElementById("username").value;
      const passwordInput = document.getElementById("password").value;
      let params = new FormData();
      params.append("username", userNameInput);
      params.append("password", passwordInput);
      let result = await fetch('authenticate', {
        method: "POST",
        body: params
      });
      result = await statusCheck(result);
      result = await result.json();
      if (result.token !== undefined) {
        window.sessionStorage.setItem("username", userNameInput);
        window.sessionStorage.setItem("token", result.token);
        location.assign("index.html");
      }
    } catch (err) {
      id('error').classList.remove("hidden");
      console.error(err);
    }
  }

  /**
   * Adds a new account given the username and password provided by the user.
   * @param {Event} evt - The event object from the form submission.
   */
  async function addAccount(evt) {
    try {
      evt.preventDefault();
      id('error').classList.add("hidden");
      const userNameInput = document.getElementById("username-signup").value;
      const passwordInput = document.getElementById("password-signup").value;
      let params = new FormData();
      params.append("username", userNameInput);
      params.append("password", passwordInput);
      let result = await fetch('/addAccount', {
        method: "POST",
        body: params
      });
      result = await statusCheck(result);
      result = await result.json();
      changeToSignIn();
    } catch (err) {
      id('error').classList.remove("hidden");
      console.error(err);
    }
  }

  /**
   * Changes the color of the button to a darker green while hovering over it.
   * @param {Event} evt - The event object from the mouseenter event.
   */
  function onMouseEnter(evt) {
    evt.currentTarget.classList.remove('button');
    evt.currentTarget.classList.add('button_hover');
  }

  /**
   * Changes the color of the button back to a lighter shade of green after the user stops hovering over it.
   * @param {Event} evt - The event object from the mouseleave event.
   */
  function onMouseLeave(evt) {
    evt.currentTarget.classList.add('button');
    evt.currentTarget.classList.remove('button_hover');
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
   * @param {string} id - The ID of the element to find.
   * @returns {HTMLElement | null} The DOM object associated with the ID, or null if not found.
   */
  function id(id) {
    return document.getElementById(id);
  }
})();
