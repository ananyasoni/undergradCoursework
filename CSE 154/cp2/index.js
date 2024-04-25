/*
 * Name: Ananya Shreya Soni
 * Date: April 19th, 2024
 * Section: CSE 154 AC
 *
 * This JS file respresents the behavior for my about me page
 * in my website and adds some interactivity. For example,
 * there is now a slidshow of images the user can click on
 * to change to the next image and a button hover animation.
 */
"use strict";
(function() {

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
    let buttons = document.querySelectorAll(".button");
    for (let i = 0; i < buttons.length; i++) {
      buttons[i].addEventListener('mouseenter', onMouseEnter);
      buttons[i].addEventListener('mouseleave', onMouseLeave);
    }
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
