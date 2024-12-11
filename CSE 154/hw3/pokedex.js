/*
 * Name: Ananya Shreya Soni
 * Date: May 8th, 2024
 * Section: CSE 154 AC
 *
 * This JS file represents a Pokemon game. It allows users to
 * collect pokemon by fighting against a random pokemon. If a user
 * wins they get another pokemon that is added to their collection of pokemon
 * and if not then they do not gain another pokemon and must repeat
 * the process if they would like to add more pokemon to their collection.
 */
"use strict";
(function() {

  const POKEDEX_URL = "https://courses.cs.washington.edu/courses/cse154/webservices/pokedex/pokedex.php?";
  const GAME_URL = "https://courses.cs.washington.edu/courses/cse154/webservices/pokedex/game.php";
  const SPRITES_URL = "https://courses.cs.washington.edu/courses/cse154/webservices/pokedex/sprites/";
  const BASE_URL = "https://courses.cs.washington.edu/courses/cse154/webservices/pokedex/";

  let currentPokemon;
  let currentPokemonOriginalHP;
  let gameID;
  let playerID;

  window.addEventListener("load", init);

  /**
   * This is the initial function run after the page loads.
   * This function populates the pokedex in the main view of the
   * game.
   */
  function init() {
    qs("#p1 #start-btn").addEventListener("click", startGame);
    populatePokedex();
  }

  /**
   * Starts the game and changes the view from the main view
   * to the game view with your chosen pokemon and player 2s
   * random pokemon card.
   */
  async function startGame() {
    try {
      let params = new FormData();
      params.append("startgame", "true");
      params.append("mypokemon", currentPokemon);
      let result = await fetch(GAME_URL, {
        method: "POST",
        body: params
      });
      await statusCheck(result);
      result = await result.json();
      playerID = result.pid;
      gameID = result.guid;
      qs("header h1").textContent = "Pokemon Battle";
      populateCard(result.p2, "#p2");
      id("p2").classList.remove("hidden");
      id("pokedex-view").classList.add("hidden");
      qs("#p1 .hp-info").classList.remove("hidden");
      id("results-container").classList.remove("hidden");
      id("p1-turn-results").classList.remove("hidden");
      id("flee-btn").classList.remove("hidden");
      id("start-btn").classList.add("hidden");
      id("flee-btn").addEventListener("click", playGame);
      turnOffPokemonMoveButtons(false);
    } catch (err) {
      console.error();
    }
  }

  /**
   * Plays the loading animation and moves the game to the
   * next game state after the player plays their next
   * move.
   * @param {EventListenerObject} evt the event object
   */
  async function playGame(evt) {
    id("loading").classList.remove("hidden");
    await fetchNextGameState(evt);
    id("loading").classList.add("hidden");
  }

  /**
   * This function fetches the next game state based on the
   * user's next move.
   * @param {EventListenerObject} evt the event object
   */
  async function fetchNextGameState(evt) {
    let move;
    if (evt.currentTarget.id === "flee-btn") {
      move = "flee";
    } else {
      move = evt.currentTarget.id;
      move = move.replaceAll(" ", "");
      move = move.toLowerCase();
    }
    try {
      let params = new FormData();
      params.append("guid", gameID);
      params.append("pid", playerID);
      params.append("movename", move);
      let result = await fetch(GAME_URL, {
        method: "POST",
        body: params
      });
      await statusCheck(result);
      result = await result.json();
      updateGameState(result);
    } catch (err) {
      console.error();
    }
  }

  /**
   * This function updates the game state in the game view
   * based on the user's most recent played move.
   * @param {Object} result contains information about the game
   * state after the user's most recent move which allows
   * this function to update the game state
   */
  function updateGameState(result) {
    currentPokemonOriginalHP = result.p1.hp;
    updateGameStateHealthDisplay(result);
    id("p1-turn-results").textContent = "Player 1 played " + result.results["p1-move"] +
                                        " and " + result.results["p1-result"] + "!";
    if (result.results["p2-move"] === null || result.results["p2-result"] === null) {
      id("p2-turn-results").classList.add("hidden");
    } else {
      id("p2-turn-results").classList.remove("hidden");
      id("p2-turn-results").textContent = "Player 2 played " + result.results["p2-move"] +
                                          " and " + result.results["p2-result"] + "!";
    }
    if (result.p1['current-hp'] <= 0 || result.p2['current-hp'] <= 0) {
      id("flee-btn").classList.add("hidden");
      turnOffPokemonMoveButtons(true);
      id("endgame").classList.remove("hidden");
      id("endgame").addEventListener("click", endGame);
      if (result.p1['current-hp'] <= 0) {
        qs("header h1").textContent = "You Lost!";
      } else {
        p1WinCase(result);
      }
    }
  }

  /**
   * This function updates player 1 (the user) and player 2s
   * health bar and hp stats based on the user's most recent played move
   * and the updated game state.
   * @param {Object} result contains information about the game
   * state after the user's most recent move which allows
   * this function to update the health of player 1 and player 2
   */
  function updateGameStateHealthDisplay(result) {
    const p1HealthPercentage = result.p1['current-hp'] / currentPokemonOriginalHP;
    const p2HealthPercentage = result.p2['current-hp'] / result.p2.hp;
    qs("#p1 .health-bar").style.width = (p1HealthPercentage * 100) + "%";
    qs("#p2 .health-bar").style.width = (p2HealthPercentage * 100) + "%";
    qs("#p1 .hp").textContent = result.p1['current-hp'] + "HP";
    qs("#p2 .hp").textContent = result.p2['current-hp'] + "HP";
    if (p1HealthPercentage <= 0.20) {
      qs("#p1 .health-bar").classList.add("low-health");
    } else {
      qs("#p1 .health-bar").classList.remove("low-health");
    }
    if (p2HealthPercentage <= 0.20) {
      qs("#p2 .health-bar").classList.add("low-health");
    } else {
      qs("#p2 .health-bar").classList.remove("low-health");
    }
  }

  /**
   * This function disables the user's ability to make a move
   * in the game state if turnOff is true and enables the user's
   * ability to choose and make a move if turnOff is false.
   * @param {boolean} turnOff lets this function know whether
   * to disable or enable the move buttons
   */
  function turnOffPokemonMoveButtons(turnOff) {
    let pokemonMoveButtons = qsa(" #p1 .moves button");
    for (let i = 0; i < pokemonMoveButtons.length; i++) {
      pokemonMoveButtons[i].disabled = turnOff;
    }
  }

  /**
   * This function handles the case where the user
   * (player 1) wins. It updates the game state
   * and adds player 2s pokemon to the user's collection
   * of pokemon.
   * @param {Object} result description
   */
  function p1WinCase(result) {
    qs("header h1").textContent = "You Won!";
    const foundPokemon = qsa(".found");
    let havePokemon = false;
    for (let i = 0; i < foundPokemon.length; i++) {
      if (foundPokemon.id === result.p2.shortname) {
        havePokemon = true;
      }
    }
    if (!havePokemon) {
      const allPokemon = qsa(".sprite");
      for (let i = 0; i < allPokemon.length; i++) {
        if (allPokemon[i].id === result.p2.shortname) {
          allPokemon[i].classList.add("found");
          allPokemon[i].addEventListener("click", onSpriteClick);
        }
      }
    }
  }

  /**
   * This function handles ending the game. It switches the
   * view from the game view to the main view displaying
   * the user's collection of pokemon.
   */
  function endGame() {
    id("flee-btn").classList.add("hidden");
    id("endgame").classList.add("hidden");
    id("results-container").classList.add("hidden");
    qs("#p1 .hp-info").classList.add("hidden");
    id("p2").classList.add("hidden");
    id("start-btn").classList.remove("hidden");
    qs("header h1").textContent = " Your Pokedex";
    qs("#p1 .hp").textContent = currentPokemonOriginalHP + "HP";
    qs("#p1 .health-bar").style.width = "100%";
    qs("#p1 .health-bar").classList.remove("low-health");
    qs("#p2 .health-bar").style.width = "100%";
    qs("#p2 .health-bar").classList.remove("low-health");
    id("pokedex-view").classList.remove("hidden");

    id("results-container").classList.add("hidden");
    id("p1-turn-results").classList.add("hidden");
    id("p2-turn-results").classList.add("hidden");
  }

  /**
   * This function populates all 151 available pokemon
   * in the pokedex in the main view of the game.
   * Only the 3 starter pokemon are
   * added to the user's collection of pokemon
   * at the start of the game.
   */
  async function populatePokedex() {
    let url = POKEDEX_URL + "pokedex=all";
    try {
      let result = await fetch(url);
      await statusCheck(result);
      result = await result.text();
      result = result.split("\n");
      for (let i = 0; i < result.length; i++) {
        let nextPokemonShortName = result[i].split(":")[1];
        let nextPokemonSprite = gen("img");
        nextPokemonSprite.src = SPRITES_URL + nextPokemonShortName + ".png";
        nextPokemonSprite.alt = nextPokemonShortName;
        nextPokemonSprite.id = nextPokemonShortName;
        nextPokemonSprite.classList.add("sprite");

        // 3 starter pokemon: Bulbasaur, Charmander, and Squirtle
        if (nextPokemonShortName === "bulbasaur" || nextPokemonShortName === "charmander" ||
            nextPokemonShortName === "squirtle") {
          nextPokemonSprite.classList.add("found");
          nextPokemonSprite.addEventListener("click", onSpriteClick);
        }
        id("pokedex-view").appendChild(nextPokemonSprite);
      }
    } catch (err) {
      console.error();
    }
  }

  /**
   * This function allows a user to click on a pokemon
   * icon in the pokedex and see a detailed view of that
   * specific pokemon displayed .
   * @param {EventListenerObject} evt the event object
   */
  async function onSpriteClick(evt) {
    let pokemon = evt.currentTarget;
    currentPokemon = pokemon.id;
    let url = POKEDEX_URL + "pokemon=" + currentPokemon;
    try {
      let result = await fetch(url);
      await statusCheck(result);
      result = await result.json();
      currentPokemon = pokemon.id;
      populateCard(result, "#p1");
      qs("#p1 #start-btn").classList.remove("hidden");
    } catch (err) {
      console.error();
    }
  }

  /**
   * This function populates a pokemon card with
   * information about the pokemon such as its type,
   * moves, HP, etc.
   * @param {Object} result contains infomation about the pokemon
   * which is displayed by this function
   * @param {string} player specifies whether the card is player
   * 1s or player 2s ("#p1" or "#p2")
   */
  function populateCard(result, player) {
    qs(player + " .name").textContent = result.name;
    qs(player + " .pokepic").src = BASE_URL + result.images.photo;
    qs(player + " .type").src = BASE_URL + result.images["typeIcon"];
    qs(player + " .weakness").src = BASE_URL + result.images.weaknessIcon;
    qs(player + " .hp").textContent = result.hp + "HP";
    qs(player + " .info").textContent = result.info.description;
    let pokemonMoves = result.moves;
    let pokemonMoveButtons = qsa(player + " .moves button");
    let pokemonMoveButtonsMoveName = qsa(player + " .moves .move");
    let pokemonMoveButtonsMoveType = qsa(player + " .moves img");
    let pokemonMoveButtonsDP = qsa(player + " .moves .dp");
    let i = 0;
    while (i < pokemonMoves.length) {
      pokemonMoveButtons[i].classList.remove("hidden");
      pokemonMoveButtonsMoveType[i].src = BASE_URL + "icons/" + pokemonMoves[i].type + ".jpg";
      pokemonMoveButtonsMoveName[i].textContent = pokemonMoves[i].name;
      pokemonMoveButtonsDP[i].textContent = (pokemonMoves[i].dp > 0) ? pokemonMoves[i].dp +
                                            " DP" : pokemonMoves[i].dp;
      if (player === "#p1") {
        pokemonMoveButtons[i].addEventListener("click", playGame);
        pokemonMoveButtons[i].id = pokemonMoves[i].name;
      }
      i++;
    }
    while (i < pokemonMoveButtons.length) {
      pokemonMoveButtons[i].classList.add("hidden");
      i++;
    }
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
  function qsa(selector) {
    return document.querySelectorAll(selector);
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
