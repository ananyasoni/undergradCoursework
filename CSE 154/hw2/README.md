# Homework 2 - Set! - Project Specification
_Special thanks to Melissa Hovik for the developing the original version of this assignment, to Lauren Bricker for editing, structure, and guidance on specification development, and to Tal Wolman and Jack Venberg for restructuring the assignment to include scaffolding for students._
***
<p>
  <img src="https://courses.cs.washington.edu/courses/cse154/24sp/homework/hw2/screenshots/spec-intro-view.png" width="60%" alt="Set! Game board">
</p>

This assignment extends what we've learned in Module 1 (HTML/CSS) and puts into practice what you're learning in Module 2 (JavaScript, DOM, and Animations) to build an interactive webpage! A fully-functional UI (user interface) takes more time to plan and implement than just an HTML/CSS webpage (such as the one you created in HW1). Since this assignment focuses on Module 2 material, we've provided all of the HTML and CSS to get you started.

## Assignment Overview
In this assignment you will use JS to add functionality to a basic webpage. Specifically, you will implement a web-based version of the Set card game, providing features to generate new games for a user and to keep track of how many correct Sets a player has found. You will also implement a basic timer for the user to keep track of how quickly it takes them to solve a given game. You can find a video demonstrating expected behavior for different cases on the [homework
page](https://courses.cs.washington.edu/courses/cse154/24sp/homework/hw2/hw2.mp4) of the course website.


### Rules of Set
This assignment is inspired by the classic SET! Card game. A game consists of a board of cards. Each card has one of three options for 4 different "attributes":

| Attribute | **Options**  | | |
|-------    |---------|---------|----------|
| STYLE     | solid   | outline | striped  |
| COLOR     | green   | purple  | red      |
| SHAPE     | diamond | oval    | squiggle |
| COUNT     | 1       | 2       | 3        |

The goal of the game is to find as many "Sets" of 3 cards such that for each attribute, all cards share the attribute or no cards share the attribute. For example, the following three cards build a Set because none share style, color, or shape attributes but they all share the count attribute.

<p>
  <img src="https://courses.cs.washington.edu/courses/cse154/24sp/homework/hw2/screenshots/set-example.png" width="50%" alt="Example of something that is a set">
</p>


However, the following three cards do not form a Set since the color attribute does not follow the "all or none" requirement (purple is shared by the first and third card, but not the second).

<p>
  <img src="https://courses.cs.washington.edu/courses/cse154/24sp/homework/hw2/screenshots/not-set-example.png" width="50%" alt="Example of something that is not a Set">
</p>

You will be dynamically generating the board with JavaScript and use event listeners to support user interaction. You will also learn how to implement an increasing/decreasing game timer on this page, as well as how to show/hide feedback to users with a 1 second delay.

### Starter Files and Final Deliverables
You will have the required `img`, `set.html`, and `set.css` files in your repository. For the final submission, you must not change `set.html`, `set.css`, or any of the images, but you will be submitting your new `set.js` file to meet this specification's requirements.

| File/folders   | Repository files to stay unchanged |
|----------------|------------------------------|
| `set.html`     | Provided HTML linking to your `set.css` and `set.js` files |
| `img`          | A folder with 27 classic set cards, each named with the convention: *STYLE-SHAPE-COLOR.png* (replacing STYLE, SHAPE, COLOR with a value for that attribute as listed in "Rules of Set" section) |
| `set.css`      | Stylesheet for set.html |
| `set.js`       | JS file for managing game UI and behavior |

Your final solution will be graded only on `set.js` - any changes you
make to `set.html`, `set.css` or the card images folder will not be eligible for full credit.

---

## Final Submission Requirements
The submission for this assignment requires that you fully implement the user interaction and game management for the Set! game. To be eligible for full credit on this assignment your submission:
* Must meet the expected behavior as outlined below and as demoed in the provided video
* Implement the 6 required functions described in the spec

### Notes
* **IMPORTANT:** The defined functions described below must be implemented as described in the spec. You are expected to refactor sections of code into _smaller_, helper functions that are called by the defined functions or that make calls to the defined functions. This makes your code significantly easier to read and reduces redundancy across your JS file.
* These functions are **not** exhaustive meaning that you are _still_ expected to write the code to tie these functions together and connect them to the user interface. This will definitely require writing additional functions beyond what is outlined in the spec. Think of these defined functions as the building blocks of the game. It is your task to implement the fundamentals and connect the pieces.

**Note about difficulty and generated cards**: In both Easy (9 card) and Standard (12 card) difficulties, there are 4 attributes (style, color, shape, and count) and 3 possible options for any attribute (e.g. red, green, or purple options for the color attribute). For Standard difficulty, each card should have a randomly-selected value for each of the 4 attributes. For Easy difficulty, the "style" attribute should be fixed to "solid". To avoid redundancy in your program and allow for flexibility if users later want to change the images and attributes, **we require using 4 arrays as module-global constants, one for each attribute type (style, color, shape, count) - since count is an integer, you may alternatively use a different datatype to represent it as a constant.** Remember that a module-global is any that is in the module pattern but defined outside of the local scope of functions within the module pattern (in general, you should minimize the number of module-global _variables_).

# Behavior Details Overview
Below are the details describing how to play the game Set. This description, as well as the description of the required functions should provide you with all the information necessary to implement the game.

### Preparing to Play a Game
* The Main Menu view should be displayed
* The Game View should be hidden
* A user should be able to select the timing option and difficulty level as desired. The default difficulty level is Easy and the default timing option is 3 minutes (this step does not require any code on your part).
* The start button should be clickable, and when clicked should have the desired behavior described in [Starting a Game](###Starting-a-Game)

### Starting a Game
* A timer should be started depending on the currently-selected option in the dropdown. The game timer should start with the number of seconds determined by the selected option and decrement each second (never going below 0 seconds).
* The Main Menu view should be hidden.
* The Game View should be displayed (after the first two steps so that the initial game information is shown correctly when the view changes).
* A new randomly-generated board of cards is generated, where each of the 4 attributes of a card are randomly-chosen (recall each attribute has 3 options) in Standard difficulty. The only difference in the randomness of cards in Easy difficulty is that the "style" attribute should always be fixed to "solid".
* The generated board should never have duplicate cards (cards that share all 4 attributes). During this step, you should not create more than 9 or 12 card DOM elements (for easy or standard mode, respectively).

### Game Play
Upon initialization a user should be able to select and deselect the cards on the board. When three cards have been selected there will be a message displayed to the user indicating whether the three cards make a Set or not:
  * If the cards make a Set, the message "SET!" will be briefly displayed and the cards will be replaced with three, new, unique cards.
  * If the cards do not make a Set, the message "Not a Set" will be briefly displayed and the same cards will be re-displayed on the board.

### Refreshing the Board
When the "Refresh Board" button is clicked:
* All cards on the board should be replaced with a new collection of cards, following the same rules for generating a board outlined in "Starting a Game".

### Ending the Game
A game ends when the user clicks the "Back to Main" button or when they run out of time. In both cases, the game timer should be stopped and not started again until a new game is started by the user (when the "Start" button is clicked again).

If the game ends due to running out of time:
* The current view should remain on the Game View until the "Back to Main" button is clicked.
* Any cards currently selected should appear unselected.
* To ensure a user cannot continue playing until a new game is started, nothing should happen when a user clicks on a card. (It should no longer call the `cardSelected()` function)
* The "Refresh Board" button should also be disabled until it is re-enabled after clicking #back-btn.
* The timer calling `advanceTimer()` should be stopped and cleared.

When the "Back to Main" button is clicked:
* The current view should be switched from the Game View to the Menu View having the same selected options for difficulty as the previous game.
* The current set count should be 0.
* The `Refresh Board` button should be reenabled.

## Required Module Global Variables
| Module Global Name | Description |
|--------------------|-------------|
|`timerId`           | represents the timer for the current game  |
|`remainingSeconds`  | represents the time in seconds left in the current game  |

**Note**: These are the **only** module-global *variables* that you should declare in this assignment.

## Provided Function:

### `isASet(selected)`
* This function should be copied into and used within `set.js`

```javascript
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
```

Accepted parameters:
* `selected` {DOMList}: A DOM list of properly generated card `div` elements that are selected.
  * These cards should be generated from `generateUniqueCard(isEasy)` described below

Expected returns:
* Returns `true` if all the given cards are a Set, otherwise returns `false`

#### Optional, but strongly recommended functions
You are welcome to use the helper/shorthand functions discussed in lecture like `id`, `qs`, `qsa`, and `gen`. These functions make your code more concise as well as easier to write and read. However, make sure you do not include helper functions in your `set.js` file that are not used as that would be unnecessary and ensure that, just like any other function in your `set.js`, these functions have appropriate JSDoc header comments.

## Required Functions
There are 6 required functions to implement for this assignment. We have provided a table for a summary, but more details are provided below the table for each function.

As a reminder, you are expected to break down your program into smaller functions as necessary, but these will be used to test your code and will need to work with the different interactive elements and events using event listeners appropriately.

| Function Name/Arguments             | Brief Description/Return Value |
| ----------------------------------- | ------------------------------ |
| `toggleViews()`                     | Used to switch between the menu view and game view of the game |
| `generateRandomAttributes(isEasy)`  | Returns a randomly-generated array of string attributes in the form `[STYLE, SHAPE, COLOR, COUNT]` |
| `generateUniqueCard(isEasy)`        | Return a `div` element with `COUNT` number of `img` elements appended as children |
| `startTimer()`                      |Starts the timer for a new game. No return value.|
| `advanceTimer()`                    |Updates the game timer (module-global and #time shown on page) by 1 second. No return value.|
| `cardSelected()`                     |Used when a card is selected, checking how many cards are currently selected. If 3 cards are selected, uses `isASet` (provided) to handle "correct" and "incorrect" cases. No return value.|


### `toggleViews()`
* Called when the `#start-btn` or `#back-btn` is clicked.
* When this function is called, the `.hidden` class should be toggled for `#menu-view` and `#game-view` (note that there should always be exactly one view element having this class, starting with `#game-view`).

### `generateRandomAttributes(isEasy)`
Accepted parameters:
* `isEasy` {boolean} : if `true` style of card will always be solid, otherwise the style attribute should be randomly selected.

Each card has four different attributes - style, shape, color and count.
* Use `Math.random()` to randomly generate each of these four different attributes. Store each attribute in an array in the exact order shown here:`[STYLE, SHAPE, COLOR, COUNT]` and return the array.
* Implementation suggestion: focus on generating these attribute combinations one at a time to reduce redundancy.

Expected returns:
* Returns a randomly generated array of attributes in the form `[STYLE, SHAPE, COLOR, COUNT]`

### `generateUniqueCard(isEasy)`
Accepted parameters:
* `isEasy` {boolean} : if `true`, the style of card will always be solid, otherwise each of the three possible styles is equally likely.

Expected returns:
* Return a `div` element with `COUNT` number of `img` elements appended as children
    * `img` elements should have a `src` attribute following the appropriate image naming convention (i.e. `STYLE-SHAPE-COLOR`) to reference the correct image inside of the `/img` directory.
    * `img` elements should also have an `alt` that stored the attributes of the form: `STYLE-SHAPE-COLOR-COUNT`
* For keeping track of unique cards, you are required to give your card `div`'s an ID with the 4 attributes in the form: `STYLE-SHAPE-COLOR-COUNT`.
    * **IMPORTANT**: You cannot make any assumptions about the current state of the game and cards previously put on the board, you should only use the information currently inside of `#board` and nothing you have kept track of previously.
    * `generateUniqueCard()` should **only** be called to generate cards that are **not** currently inside of `#board`, meaning they do not have the same ID. Given a correct implementation of this assignment, if `generateUniqueCard()` has been called this means the card returned is guaranteed to be unique.
    * You should only call the `generateRandomAttributes()` function the minimum amount of times required to get a unique ID.
* The card `div` should have the class `card` added to it in order to style the cards.
* The card should have an click event listener attached to it that calls the function `cardSelected()` defined below in the spec.

Note: When populating the `#board` with calls to `generateUniqueCard`, the function should be called exactly the number of times as cards needed on the board.

### `startTimer()`
* Called when a game starts.
* Grabs the timing option from the `#menu-view` and sets the timer to display that time and updates the state of the game to keep track of the current time.
* Starts the periodic calling of `advanceTimer()` every `1` second (see specification for function below)

### `advanceTimer()`
* With *each call* of this function, the time should be decremented by `1` second.
* This should both update the time kept track in the game and update the `#time` on the board
  * **Note about the game timer format**: When updating during the Game View, the timer format in `#time` must stay in proper MM:SS format (for example, if there are 8 seconds left, the timer should display 00:08).
* If there is no time left in the game (time reaches 0), the board should be disabled as described in the [Ending the Game](#ending-the-game) above:
> * The current view should remain on the Game View until the "Back to Main" button is clicked.
> * Any cards currently selected should appear unselected.
> * To ensure a user cannot continue playing until a new game is started, nothing should happen when a
user clicks on a card. (It should no longer call the `cardSelected()` function)
> * The "Refresh Board" button should also be disabled until it is re-enabled after clicking #back-btn.
> * The timer calling `advanceTimer()` should be stopped and cleared.

### `cardSelected()`
* Once the game has been initialized, a user can click on cards to find a Set. Clicking on a card should toggle its `.selected` state (all cards are initially unselected). When three cards are `.selected` on the board, there are two possible cases you should handle depending on what `isASet` returns.
  * **REMINDER**: the function for checking whether 3 cards are a set (`isASet`) is provided. Please do not reimplement or modify this function. Modifications to the provided code are ineligible for full credit.

**Both Cases**:
* For both cases, the three selected cards should lose the `.selected` appearance **immediately** just prior to the appropriate 1-second message displaying (refer to provided video demo linked on the course website).
* Below is an example of the message displayed when an incorrect Set is selected:

  <img src="https://courses.cs.washington.edu/courses/cse154/24sp/homework/hw2/screenshots/game-view-not-set.png" width="60%" alt="Not a Set display example">


**Case 1: The three selected cards create a Set**
* You should immediately replace the selected cards with a new **unique** card that is not currently on the `#board`. For this, you should use the `generateUniqueCard(isEasy)` function that you have already implemented.
  * When replacing cards in a found Set, the order/position of other cards on the board should remain unchanged - refer to the demo video for an example.
* Number of sets found should be incremented (the set counter should be incremented by one in `#set-count`)
* A `<p>` element containing the text message "SET!" should appear in each card and the `img` elements within the card should be hidden by adding the class `hide-imgs` to the `.card` itself.
  * This `<p>` element is added to the **newly _replaced_** card that was immediately generated after a valid Set was selected (see the code excerpt below for an example).
  * Remember that you may **not** modify the provided HTML file but the structure of your card should look like this for 1 second before revealing the randomly generated card that replaced the previous one (we recommend setting a breakpoint in the Chrome Inspector Sources tab and check the card's HTML when you expect a card to display the message).
  ```html
  <div id="id-of-card" class="card hide-imgs">
      <img src="img-src" alt="id-of-card">
      <!-- possibly more images -->
      <p>SET!</p>
  </div>
  ```

**Case 2: The three selected cards do not create a Set**
* A `<p>` element containing the text message "Not a Set" should appear in each card and the `img` elements within the card should be hidden by adding the class `hide-imgs` to the `.card` itself
  * Remember that you may **not** modify the provided HTML file but the structure of your card should look like this for 1 second before returning to its original structure (_without the `<p>` element_).
  ```html
  <div id="id-of-card" class="card hide-imgs">
      <img src="img-src" alt="id-of-card">
      <!-- possibly more images -->
      <p>Not a Set</p>
  </div>
  ```

---

## JS File Contents
In order to be eligible for full credit, **the naming and casing of all required functions must be correct**. Below is the skeleton for the function with the correct naming and casing. To help ensure it is correct in your code, you can copy it into you `.js` file. Note that the function documentation is not included but required. Additionally, in order to be eligible for full credit you should **remove all unnecessary comments** before submitting. Failure to do any of the above may be reflected in your assignment grade.
```js
// Required module globals
let timerId;
let remainingSeconds;

// Required functions to implement are below
function toggleViews()  {
  // code for function goes here
}

function generateRandomAttributes(isEasy) {
  // code for function goes here
}

function generateUniqueCard(isEasy) {
  // code for function goes here
}

function startTimer() {
  // code for function goes here
}

function advanceTimer() {
  // code for function goes here
}

function cardSelected() {
  // code for function goes here
}
```

## Development Strategies
If you’re unsure where to start, the following is a roadmap of recommended steps to make the assignment more approachable:

1. Understand the overall behavior of the game to give important context in implementing the game
   * Watch the videos, read the [Behavior Details Overview](#behavior-details-overview).
   * Understand the `isASet` function and when you might use it.
2. Start by implementing the defined functions in the order they're introduced in the spec
   * This should give you the majority of the behavior of the game.
   * Take each function one step at a time and remember to use your other functions to avoid reimplementing shared behavior.
   * When you are implementing the functions, make sure you only add the functionality outlined in the spec.
   * Remember to break down the defined functions into smaller functions functions to make the problem easier to tackle, read, and understand.
3. Implement the overall connection of the DOM and the defined functions and any other behavior to complete the game.
   * You may find it helpful to create a table similar to the one suggested in the CP2 spec while developing
4. Running into bugs? Use the Chrome console/sources tab as demonstrated in lecture/section, use `console.log` to see what the result of a statement is, add a breakpoint in the sources tab to access information at each step of the function, etc.
5. Rewatch the video to ensure you have not missed any details.
6. Be proud of what you've done (this is a challenging assignment) and play the game!

## Internal Requirements
For full credit, your page must not only match the External Requirements listed above, you must also demonstrate that you understand what it means to write code following a set of programming standards. Your CSS and JS should also maintain good code quality by following the [CSE154 Code Quality Guide](https://courses.cs.washington.edu/courses/cse154/codequalityguide/). Make sure to review the section specific to JavaScript! We also expect you to implement relevant feedback from previous assignments. Some guidelines that are particularly important to remember for this assignment:

#### JS:
* Your `.js` file must be [in the module pattern](https://courses.cs.washington.edu/courses/cse154/codequalityguide/javascript/#module-pattern) and run in strict mode by putting `"use strict";` above the module pattern.
* Use `camelCase` naming conventions for variables and functions
* There should be never be any DOM elements on the page sharing the same ID.
* All images in cards should be given an appropriate alt attribute.
* You should not have any unnecessary interval/time-out running on your page at any time (make sure you understand the difference between an interval and delay)
* Do not use any global variables, and minimize the use of module-global variables. Do not ever store DOM element objects, such as those returned by the `document.getElementById` function, as global variables.
  * *Note*: Our solution only uses the two required module-globals described above (the game timer and number of seconds in a current game). Otherwise, these are the **only** module-global variables you should declare in this assignment.
* If a particular literal value is used frequently, declare it as a module-global "constant" `IN_UPPER_CASE` and use the constant in your code. Our solution has an array for each attribute and a path to the images folder. Constants make your code significantly more readable and modular, you are encouraged to declare them to eliminate "magic" values in your code.
* Format your JS to be as readable as possible, similarly to the examples from class: Properly use whitespace and indent your JS code as shown in class. You can find information about JS conventions [here](https://courses.cs.washington.edu/courses/cse154/codequalityguide/attributejavascript/#whitespace-before-blocks).
* You may not use any CSS/JS frameworks for this assignment.
* Your page must pass the CSE 154 ESLint (which runs every time you push to Gitlab)

### Tests
**Note:** You are provided a subset of the tests we will use while grading your homework for this assignment. You are expected to use these tests while completing this homework. These tests are **not** exhaustive and do not check for everything in the spec. You will need to be thorough to make sure you have completed all the necessary requirements. These tests will rerun every time you push to GitLab as well as when you submit to Gradescope. After submitting to Gradescope you will need to wait in order to see the autograder results. The Gradescope tests take precedent over Gitlab ones. Waiting for test results after pushing to Gitlab will not excuse any late work. If your submission does not pass at least one test case, your homework will not be graded.

### Documentation
Place a comment header **in the js file** with your name, section, a 2-3 sentence description of the assignment, and the file's contents (examples have been given on previous assignments). Do not add a header comment to the `.html` or `.css` files.
* You will be expected to properly document your functions in `set.js` using JSDoc with `@param` and `@return` where necessary. Refer to the [Code Quality Guide](https://courses.cs.washington.edu/courses/cse154/codequalityguide/attributejavascript/#comments-function-header) for some examples.

## Grading
This homework assignment will be out of 25 points and the grading distribution will be broken down as follows (subject to small changes):
* External Correctness: 45-55%
* Internal Correctness: 35-45%
* Documentation: 5-10%

## Academic Integrity
All work submitted for your CSE 154 homework assignments must be your own and should not be shared with other students. This includes but is not limited to:
  * You may not use code directly from any external sources (no copying and pasting from external sites), other than templates that are explicitly given to students for use in class.
  * We expect that the homework you submit is your own work and that you do not receive any inappropriate help from other people or provide inappropriate help to others.
  * You must not place your solution to a publicly-accessible web site, neither during nor after the school quarter is over.

Doing any of the above is considered a violation of our course academic integrity policy. As a reminder this page states:

  * The Paul G Allen School has an entire page on [Academic Misconduct](https://www.cs.washington.edu/academics/misconduct) within the context of Computer Science
  * The University of Washington has an entire page on how [Academic Misconduct](https://www.washington.edu/cssc/for-students/academic-misconduct/) is handled on their [Community Standards and Student Conduct](https://www.washington.edu/cssc/) Page

Please acquaint yourself with both of those pages, and in particular how academic misconduct will be reported to the University.