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

  const BASE_URL1 = "https://api.dictionaryapi.dev/api/v2/entries/en/";
  const BASE_URL2 = "https://api.zippopotam.us/us/";

  window.addEventListener("load", init);

  // list of images to flip between
  let imgSlideShow = ["dog-3.jpg", "cheeku-and-me.jpg", "cheeku-puppy.jpg", "dog-1.jpg",
                      "anime-style-dog-and-me.jpg", "dog-2.jpg", "dog-4.jpg", "dog-5.jpg",
                      "ocean-view.jpg"];

  /**
   * keeps track of number of times image is clicked
   * and used to decide the next image to be displayed
   */
  let imgClickCount = 0;
  let imgHoverCount = 0;

  /**
   * initial function run after page loads. Adds event listeners to the images and buttons
   * on my about me page.
   */
  function init() {
    document.getElementById("slideshow").addEventListener('click', changeSlideShowImgOnClick);
    document.getElementById("sad-image").addEventListener('mouseover', onHover);
    document.getElementById("sad-image").addEventListener('click', getDefinitionOfStorm);
    document.querySelector("button").addEventListener('mouseenter', onMouseEnter);
    document.querySelector("button").addEventListener('mouseleave', onMouseLeave);
    document.querySelector("button").addEventListener('click', getLocation);
    let buttons = document.querySelectorAll(".button");
    for (let i = 0; i < buttons.length; i++) {
      buttons[i].addEventListener('mouseenter', onMouseEnter);
      buttons[i].addEventListener('mouseleave', onMouseLeave);
    }
  }

  /**
   * Fetches information about my location
   * with postal code 98034 and handles displaying
   * information or error in case of an error.
   */
  async function getLocation() {
    let url = BASE_URL2 + "98034";
    try {
      let result = await fetch(url);
      result = await statusCheck(result);
      result = await result.json();
      processLocationData(result);
    } catch (err) {
      handleError(err);
    }
  }

  /**
   * Processes location response data and displays
   * my location on my about me page.
   * @param {Object} responseData response after processing
   * fetch request in getLocation
   */
  function processLocationData(responseData) {
    let header = document.createElement("h3");
    header.textContent = "My Location";
    document.querySelector("header").appendChild(header);
    header.classList.add("title");
    let country = document.createElement("h4");
    country.textContent = "Country I was born in: " + responseData.country;
    document.querySelector("header").appendChild(country);
    let postalCode = document.createElement("h4");
    postalCode.textContent = "Postal Code: " + responseData["post code"];
    document.querySelector("header").appendChild(postalCode);
    let location = document.createElement("h4");
    location.textContent = "Location: " + responseData.places[0]["place name"] + ", " +
                            responseData.places[0]["state"] + " => latitude and longitude: (" +
                            responseData.places[0]["latitude"] + ", " +
                            responseData.places[0]["longitude"] + ")";
    document.querySelector("header").appendChild(location);
    document.querySelector("button").removeEventListener('click', getLocation);
    document.querySelector("header").classList.add("card");
  }

  /**
   * Fetches information about the definition of the
   * word "storm" and handles displaying
   * information or error in case of an error.
   */
  async function getDefinitionOfStorm() {
    let url = BASE_URL1 + "storm";
    try {
      let result = await fetch(url);
      result = await statusCheck(result);
      result = await result.json();
      processDefinitionOfStorm(result);
    } catch (err) {
      handleError(err);
    }
  }

  /**
   * elegant error handling function
   */
  function handleError() {
    let errorHeader = document.createElement("h2");
    errorHeader.textContent = "Please Try Again :( Could not fetch";
    document.getElementById("sad").appendChild(errorHeader);
    errorHeader.classList.add("error");
  }

  /**
   * Processes definition of storm response data and displays
   * the definition of storm below the image of a storm on
   * my about me page.
   * @param {Object} responseData response after processing
   * fetch request in getDefinitionOfStorm
   */
  function processDefinitionOfStorm(responseData) {
    let wordTitle = document.createElement("h2");
    wordTitle.textContent = "Word: " + responseData[0].word;
    document.getElementById("sad").appendChild(wordTitle);
    for (let i = 0; i < responseData.length; i++) {
      let nextMeanings = responseData[i].meanings;
      processMeanings(nextMeanings);
    }
    document.getElementById("sad-image").removeEventListener('click', getDefinitionOfStorm);
    document.getElementById("sad").classList.add("card");
  }

  /**
   * Processes meanings of the word "storm" given
   * the meanings field value from the response data
   * after the fetch request in getDefinitionOfStorm is
   * process
   * @param {Array} meanings contains information about the word "storm"
   * such as its antonyms, synonyms, definitions, and part of speech
   */
  function processMeanings(meanings) {
    for (let i = 0; i < meanings.length; i++) {
      let definitions = meanings[i].definitions;
      let partOfSpeech = document.createElement("h4");
      partOfSpeech.textContent = "Part Of Speech " + (i + 1) + ": " + meanings[i].partOfSpeech;
      document.getElementById("sad").appendChild(partOfSpeech);
      let listOfAntonyms = meanings[i].antonyms;
      let listOfSynonyms = meanings[i].synonyms;
      if (listOfAntonyms.length > 0) {
        for (let k = 0; k < listOfAntonyms.length; k++) {
          let antonym = document.createElement("p");
          antonym.textContent = "Antonym " + (k + 1) + ": " + (String)(listOfAntonyms[k]);
          document.getElementById("sad").appendChild(antonym);
        }
      }
      if (listOfSynonyms.length > 0) {
        for (let k = 0; k < listOfSynonyms.length; k++) {
          let synonym = document.createElement("p");
          synonym.textContent = "Synonym " + (k + 1) + ": " + (String)(listOfSynonyms[k]);
          document.getElementById("sad").appendChild(synonym);
        }
      }
      for (let j = 0; j < definitions.length; j++) {
        let definition = document.createElement("p");
        definition.textContent = "Definition " + (j + 1) + ": " + definitions[j].definition;
        document.getElementById("sad").appendChild(definition);
      }
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
   * Flips to the next image every time the image is clicked. If the last
   * image in the list of images is reached then this function starts over
   * from the beginning of the list.
   */
  function changeSlideShowImgOnClick() {
    document.getElementById("slideshow").classList.add("image");
    document.getElementById("slideshow").src = imgSlideShow[imgClickCount];
    if (imgClickCount >= imgSlideShow.length - 1) {
      imgClickCount = 0;
    } else {
      imgClickCount++;
    }
  }

  /**
   * changes the color of the button while hovering over it
   * to green
   * @param {Event} evt - parameter description
   */
  function onMouseEnter(evt) {
    evt.currentTarget.classList.remove('button');
    evt.currentTarget.classList.add('button_hover');
  }

  /**
   * changes the color of the button back to purple after user
   * stops hovering it
   * @param {Event} evt - parameter description
   */
  function onMouseLeave(evt) {
    evt.currentTarget.classList.add('button');
    evt.currentTarget.classList.remove('button_hover');
  }

  /**
   * after hovering over the second image on my about me
   * page some text shows up below the caption of the image.
   * nothing happens after hovering over it once.
   */
  function onHover() {
    if (imgHoverCount <= 0) {
      let paragraph = document.createElement("p");
      paragraph.textContent = "touch some grass to feel better ;)";
      document.getElementById("sad").appendChild(paragraph);
      imgHoverCount++;
    }
  }
})();
