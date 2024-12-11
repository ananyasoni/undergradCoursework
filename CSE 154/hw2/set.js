/*
 * Name: Ananya Shreya Soni
 * Date: April 25th, 2024
 * Section: CSE 154 AC
 *
 * This JS file represents a web-based version of the Set card game, providing
 * features to generate new games for a user and to keep track of how many correct
 * Sets a player has found. It also contains a basic timer for the user to keep track
 * of how quickly it takes them to solve a given game.
 */
"use strict";
(function() {

  let timerId;
  let remainingSeconds;
  const EASY_DECK_SIZE = 9;
  const STANDARD_DECK_SIZE = 12;
  const NUM_SEC_IN_MIN = 60;
  const styles = ['solid', 'outline', 'striped'];
  const colors = ['green', 'purple', 'red'];
  const shapes = ['diamond', 'oval', 'squiggle'];
  const counts = [1, 2, 3];
  window.addEventListener("load", init);

  /**
   * initial function run after page loads. Adds event listeners to buttons
   */
  function init() {
    id("start-btn").addEventListener('click', toggleViews);
    id("start-btn").addEventListener('click', startGame);
    id("back-btn").addEventListener('click', toggleViews);
    id("back-btn").addEventListener('click', endGame);
  }

  /**
   * Checks to see if the three selected cards make up a valid set. This is done by comparing each
   * of the type of attribute against the other two cards. If each four attributes for each card are
   * either all the same or all different, then the cards make a set. If not, they do not make a set
   * @param {DOMList} selected - list of all selected cards to check if a set.
   * @return {boolean} true if valid set false otherwise.
   */
  function isASet(selected) {
    let attributes = [];
    for (let i = 0; i < selected.length; i++) {
      attributes.push(selected[i].id.split("-"));
    }
    for (let i = 0; i < attributes[0].length; i++) {
      let diff = attributes[0][i] !== attributes[1][i] &&
                attributes[1][i] !== attributes[2][i] &&
                attributes[0][i] !== attributes[2][i];
      let same = attributes[0][i] === attributes[1][i] &&
                    attributes[1][i] === attributes[2][i];
      if (!(same || diff)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Used to switch between the menu view and game view of the game
   */
  function toggleViews() {
    id("menu-view").classList.toggle("hidden");
    id("game-view").classList.toggle("hidden");
  }

  /**
   * sets up the game board with randomized unique cards
   */
  function setUpBoard() {
    let isEasy = qsa("p input")[0].checked;

    // clear all cards previously on board
    id("board").innerHTML = "";

    // generate 9 random cards if easy mode
    if (isEasy) {
      for (let i = 0; i < EASY_DECK_SIZE; i++) {
        let nextCard = generateUniqueCard(isEasy);
        id("board").appendChild(nextCard);
      }
    } else {

      // generate 12 random cards if standard mode mode
      for (let i = 0; i < STANDARD_DECK_SIZE; i++) {
        let nextCard = generateUniqueCard(isEasy);
        id("board").appendChild(nextCard);
      }
    }
  }

  /**
   * starts a new Set game and resets the board
   */
  function startGame() {

    // renable the refresh button
    id("refresh-btn").disabled = false;

    // reset the score
    id("set-count").textContent = 0;
    id("board").innerHTML = "";
    let isEasy = qsa("p input")[0].checked;
    setUpBoard(isEasy);
    id("refresh-btn").addEventListener('click', setUpBoard);

    // start the game timer
    startTimer();
  }

  /**
   * starts the timer for a new game
   */
  function startTimer() {
    timerId = setInterval(advanceTimer, 1000);
    let timeOptions = qsa("#menu-view select option");

    // 1 minute selected
    if (timeOptions[0].selected === true) {
      remainingSeconds = NUM_SEC_IN_MIN;

    // 3 minutes selected
    } else if (timeOptions[1].selected === true) {
      remainingSeconds = NUM_SEC_IN_MIN * 3;

    // 5 minutes selected
    } else {
      remainingSeconds = NUM_SEC_IN_MIN * 5;
    }
    displayTime();
  }

  /**
   * dispays the time in (#time) based on the remaining seconds in the game
   */
  function displayTime() {
    let minutes = Math.floor(remainingSeconds / NUM_SEC_IN_MIN);
    let secondsTensPlace = Math.floor((remainingSeconds % NUM_SEC_IN_MIN) / 10);
    let secondsOnesPlace = Math.floor((remainingSeconds % NUM_SEC_IN_MIN) % 10);
    id("time").textContent = "0" + minutes + ":" + secondsTensPlace + secondsOnesPlace;
  }

  /**
   * Updates the game timer (module-global and #time shown on page) by 1 second.
   */
  function advanceTimer() {
    remainingSeconds -= 1;
    displayTime();

    /**
     * NOTE: ALWAYS make sure to check whether the amount of time remaining
     * has reached 0 right after decrementing the time. Otherwise another
     * second passes due to the 1000 MS interval before the current game stops
     */
    if (remainingSeconds === 0) {
      clearInterval(timerId);
      displayTime();
      id("refresh-btn").disabled = true;
      let allCards = qsa("#board > div");
      for (let i = 0; i < allCards.length; i++) {
        allCards[i].removeEventListener('click', cardSelected);
        allCards[i].classList.remove("selected");
      }
    }
  }

  /**
   * disables refresh button, resets the score
   * and clears the timer
   */
  function endGame() {
    id("refresh-btn").disabled = true;
    id("set-count").textContent = 0;
    clearInterval(timerId);
  }

  /**
   * Returns a randomly-generated array of attributes in the form
   * [STYLE, SHAPE, COLOR, COUNT]
   * @param {boolean} isEasy game difficulty
   * @returns {[string, string, string, number]} description
   */
  function generateRandomAttributes(isEasy) {
    let randIndexes = [(Math.floor(Math.random() * shapes.length)),
                       (Math.floor(Math.random() * colors.length)),
                       (Math.floor(Math.random() * counts.length))];
    if (isEasy) {
      // only generate random attributes for shape, color, and count, style fixed to solid
      return [styles[0], shapes[randIndexes[0]], colors[randIndexes[1]], counts[randIndexes[2]]];
    }

    // generate random attributes for all 4 attribute types
    randIndexes.splice(0, 0, (Math.floor(Math.random() * styles.length)));
    return [styles[randIndexes[0]], shapes[randIndexes[1]],
            colors[randIndexes[2]], counts[randIndexes[3]]];
  }

  /**
   * Return a div element with COUNT number of img elements appended as children
   * representing a unique card not already in the current game board
   * @param {boolean} isEasy difficulty level
   * @returns {HTMLElement} DOM Node
   */
  function generateUniqueCard(isEasy) {
    let randomAttributes = generateRandomAttributes(isEasy);
    let imgID = randomAttributes[0] + "-" + randomAttributes[1] + "-" +
                randomAttributes[2] + "-" + (String)(randomAttributes[3]);

    /**
     * keep generating random attributes until you find a set of unique attributes
     * that is not already on the board
     */
    while (contains(qsa("#board > div"), imgID)) {
      randomAttributes = generateRandomAttributes(isEasy);
      imgID = randomAttributes[0] + "-" + randomAttributes[1] + "-" +
              randomAttributes[2] + "-" + (String)(randomAttributes[3]);
    }

    // generate unique card to be returned
    let uniqueCard = gen("div");
    let cardImgSrc = "img/" + randomAttributes[0] + "-" + randomAttributes[1] +
                     "-" + randomAttributes[2] + ".png";

    // append COUNT number of images to card div
    for (let i = 0; i < randomAttributes[3]; i++) {
      let cardImg = gen("img");
      cardImg.src = cardImgSrc;
      cardImg.alt = imgID;
      uniqueCard.appendChild(cardImg);
    }
    uniqueCard.id = imgID;

    /**
     * add card class to unique card as well as an event listener that
     * calls the function cardSelected
     */
    uniqueCard.classList.add("card");
    uniqueCard.addEventListener('click', cardSelected);
    return uniqueCard;
  }

  /**
   * Checks whether the given list of HTMLElements contains an element with the
   * specified id
   * @param {NodeListOf<HTMLElement>} domList list of HTMLElements
   * @param {string} elementID id of element
   * @returns {boolean} true or false
   */
  function contains(domList, elementID) {
    for (let i = 0; i < domList.length; i++) {
      if (domList[i].id === elementID) {
        return true;
      }
    }
    return false;
  }

  /**
   * 	Used when a card is selected, checks how many cards are currently
   *  selected. If 3 cards are selected, uses isASet to handle
   *  "correct" and "incorrect" cases
   */
  function cardSelected() {
    let isEasy = qsa("p input")[0].checked;
    this.classList.toggle("selected");
    let selectedCards = qsa(".selected");

    // 3 cards selected
    if (selectedCards.length === 3) {

      // valid set case
      if (isASet(selectedCards)) {
        let score = (Number)(id("set-count").textContent) + 1;
        id("set-count").textContent = score;

        /**
         * generate new random unique cards and replace the selected
         * cards with them in the exact same location in the DOM
         */
        for (let i = 0; i < selectedCards.length; i++) {
          let newCard = generateUniqueCard(isEasy);
          id("board").replaceChild(newCard, selectedCards[i]);
          newCard.classList.remove("selected");
          let message = gen("p");
          message.textContent = "Set!";
          newCard.classList.add("hide-imgs");
          newCard.appendChild(message);
          setTimeout(removeFeedback, 1000, newCard, message);
        }

      // non valid set case
      } else {
        for (let i = 0; i < selectedCards.length; i++) {
          selectedCards[i].classList.remove("selected");
          let message = gen("p");
          message.textContent = "Not a Set";
          selectedCards[i].classList.add("hide-imgs");
          selectedCards[i].appendChild(message);
          setTimeout(removeFeedback, 1000, selectedCards[i], message);
        }
      }
    }
  }

  /**
   * Makes the card visible again and removes the feedback message
   * @param {HTMLElement} card element ID
   * @param {HTMLElement} message element ID
   */
  function removeFeedback(card, message) {
    card.classList.remove("hide-imgs");
    message.remove();
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
   * creates and returns a new empty DOM node representing an element of that type
   * @param {any} tagName specified HTMLElement type
   * @returns {HTMLElement} DOM object
   */
  function gen(tagName) {
    return document.createElement(tagName);
  }

})();