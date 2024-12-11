/*
 * Name: Ananya Shreya Soni
 * Date: May 17th, 2024
 * Section: CSE 154 AC
 *
 * This client side JS file manages user authentication, item searches,
 * token verification, and purchase finalization for
 * a stationary eccomerce site.
 */

"use strict";
(function() {

  window.addEventListener("load", init);

  let currentItemID;

  /**
   * Initial function run after the page loads. Adds event listeners to the buttons.
   */
  async function init() {
    currentItemID = undefined;
    id('home-btn').addEventListener('click', switchToHomeView);
    id('cancel-btn').addEventListener('click', switchToHomeView);
    id('history-btn').addEventListener('click', retrievePurchaseHistory)
    document.getElementById("filter-btn").addEventListener('click', function() {
      const sidePanel = document.getElementById("side-panel");
      sidePanel.classList.toggle("active");
      sidePanel.classList.toggle("hidden");
    });
    id('purchase-btn').addEventListener('click', finalizePurchase);
    id("filter-form").addEventListener('submit', getFilteredItems);
    let buttons = document.querySelectorAll(".button");
    for (let i = 0; i < buttons.length; i++) {
      buttons[i].addEventListener('mouseenter', onMouseEnter);
      buttons[i].addEventListener('mouseleave', onMouseLeave);
    }
    await getAllItems();
  }

  /**
   * Displays user's purchase history
   */
  async function retrievePurchaseHistory() {
    try {
      id("product-details").innerHTML = '';
      if (await checkValidToken(window.sessionStorage.getItem('username'), window.sessionStorage.getItem('token'))) {
        let params = new FormData();
        const username = window.sessionStorage.getItem("username");
        params.append("username", username);
        let result = await fetch('/purchaseHistory', {
          method: "POST",
          body: params
        });
        result = await statusCheck(result);
        result = await result.json();
        id('purchase-history').classList.remove('hidden');
        id('order-confirmation').classList.add('hidden');
        id('main-view').classList.add('hidden');
        id('product-details').innerHTML = '';
        id('product-details').classList.remove('productDetails');
        id('product-details').classList.add('hidden');
        id('unsuccesful-purchase').classList.add('hidden');
        id('purchase-status').classList.add('hidden');
        id('purchase-btn').classList.add('hidden');
        id('purchase').classList.add('hidden');
        const tableBody = document.querySelector('#purchase-table tbody');
        tableBody.innerHTML = '';
        const data = result.history;
        for (let i = 0; i < data.length; i++) {
          const row = document.createElement('tr');
          const purchaseIdCell = document.createElement('td');
          purchaseIdCell.textContent = data[i]['purchase-id'];
          row.appendChild(purchaseIdCell);
          const itemIdCell = document.createElement('td');
          itemIdCell.textContent = data[i]['item-id'];
          row.appendChild(itemIdCell);
          const itemNameCell = document.createElement('td');
          itemNameCell.textContent = data[i]['item-name'];
          row.appendChild(itemNameCell);
          tableBody.appendChild(row);
        }
      }
    } catch (err) {
      console.error(err);
    }
  }
  /**
   * Switches the view to the purchase view.
   * @param {string} itemIDToPurchase - The ID of the item to purchase.
   */
  function switchToPurchaseView(itemIDToPurchase) {
    id("product-details").innerHTML = '';
    id('purchase-history').classList.add('hidden');
    id('order-confirmation').classList.add('hidden')
    id('unsuccesful-purchase').classList.add('hidden');
    id('purchase-btn').classList.remove('hidden');
    id('order-confirmation').classList.add('hidden');
    id('main-view').classList.add('hidden');
    id('product-details').innerHTML = '';
    id('product-details').classList.remove('productDetails');
    id('product-details').classList.add('hidden');
    id('purchase').classList.remove('hidden');

    //set current item id to the selected item id
    currentItemID = itemIDToPurchase;
  }

  /**
   * Finalizes the purchase of the current item.
   */
  async function finalizePurchase() {
    try {
      id("product-details").innerHTML = '';
      if (currentItemID !== undefined) {
        id("purchase-status").classList.add("hidden");
        id("unsuccesful-purchase").classList.add("hidden");
        let params = new FormData();
        const username = window.sessionStorage.getItem("username");
        params.append("itemID", currentItemID);
        params.append("username", username);
        let result = await fetch('/finalizePurchase', {
          method: "POST",
          body: params
        });
        result = await statusCheck(result);
        result = await result.json();
        id("purchase-status").classList.remove("hidden");
        id("purchase-status").textContent = "Please Save the Order ID for your records: " + result.orderID;
      }
    } catch (err) {
      id("unsuccesful-purchase").classList.remove("hidden");
      console.error(err);
    }
  }

  /**
   * Changes the color of the button while hovering over it to darker green.
   * @param {Event} evt - The event object.
   */
  function onMouseEnter(evt) {
    evt.currentTarget.classList.remove('button');
    evt.currentTarget.classList.add('button_hover');
  }

  /**
   * Changes the color of the button back to a lighter shade of green after the user stops hovering over it.
   * @param {Event} evt - The event object.
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
   * @param {string} id - The element ID.
   * @returns {HTMLElement | null} The DOM object associated with the ID.
   */
  function id(id) {
    return document.getElementById(id);
  }

  /**
   * Creates and returns a new empty DOM node representing an element of the specified type.
   * @param {string} tagName - The specified HTMLElement type.
   * @returns {HTMLElement} The DOM object.
   */
  function gen(tagName) {
    return document.createElement(tagName);
  }

  /**
   * Switches the view to the home view.
   */
  async function switchToHomeView() {
    try {
      id('purchase-history').classList.add('hidden');
      id('order-confirmation').classList.add('hidden');
      id('main-view').classList.remove('hidden');
      id('product-details').innerHTML = '';
      id('product-details').classList.remove('productDetails');
      id('product-details').classList.add('hidden');
      id('unsuccesful-purchase').classList.add('hidden');
      id('purchase-status').classList.add('hidden');
      id('purchase-btn').classList.add('hidden');
      id('purchase').classList.add('hidden');
    } catch (err) {
      console.error(err);
    }
    await getAllItems();
  }

  /**
   * Gets all items
   * @param {Event} evt - The event object.
   */
  async function getAllItems() {
    try {
      let params = new FormData();
      let result = await fetch('/search', {
        method: "POST",
        body: params
      });
      result = await statusCheck(result);
      result = await result.json();
      await displayItems(result);
    } catch (err) {
      console.error(err)
    }
  }

  /**
   * Handles the filtering of items based on user input.
   * @param {Event} evt - The event object.
   */
  async function getFilteredItems(evt) {
    try {
      id('main-view').classList.remove('hidden');
      id('product-details').innerHTML = '';
      id('product-details').classList.remove('productDetails');
      id('product-details').classList.add('hidden');
      id("side-panel").classList.toggle("hidden");
      id("side-panel").classList.toggle("active");
      evt.preventDefault();
      let params = new FormData();
      params.append("price", id("price-filter").value);
      params.append("category", id("category").value);
      params.append("search", id("search").value.trim());
      let result = await fetch('/search', {
        method: "POST",
        body: params
      });
      result = await statusCheck(result);
      result = await result.json();
      await displayItems(result);
    } catch (err) {
      console.error(err);
    }
  }

  /**
   * Displays the filtered items on the page.
   * @param {Object} data - The data containing the filtered items.
   */
  async function displayItems(data) {
    try {
      id("pen").innerHTML = '';
      id("pencil").innerHTML = '';
      id("notebook").innerHTML = '';
      id("storage").innerHTML = '';
      id("office-items").innerHTML = '';
      const items = data.inventory;
      for (let i = 0; i < items.length; i++) {
        const name = items[i]["item-name"];
        const price = items[i]["item-price"];
        let image = items[i]["img-link"];
        const category = items[i]["item-category"];
        let productCard = gen("div");
        let productImg = gen("img");
        productImg.src = image;
        let productName = gen("h2");
        productName.id = items[i]["item-id"];
        productName.textContent = name;
        let productPrice = gen("h3");
        productPrice.textContent = "$" + price;
        productCard.appendChild(productName);
        productCard.appendChild(productImg);
        productCard.appendChild(productPrice);
        productCard.id = items[i]["item-id"];
        if (category === "pen" || category === "pencil" || category === "office-items" || category === "storage" || category === "notebook") {
          id(category).appendChild(productCard);
        }
        productCard.classList.add("productCard");
        productName.addEventListener('click', () => {
          getProductDetails(name, price, image, category,
            items[i]["item-description"],
            items[i]["item-stock"], items[i]["item-avg-rating"],
            items[i]["item-num-ratings"]);
        });
        let productBuyButton = gen("button");
        productBuyButton.textContent = "Purchase";
        productCard.appendChild(productBuyButton);
        if (await checkValidToken(window.sessionStorage.getItem('username'), window.sessionStorage.getItem('token'))) {
          productBuyButton.disabled = false;
        } else {
          productBuyButton.disabled = true;
        }
        let itemIDToPurchase = items[i]["item-id"];
        productBuyButton.id = String(itemIDToPurchase) + " purchase btn";
        productBuyButton.addEventListener('click', () => {
          buyProduct(name, price, image, category, items[i]["item-description"], items[i]["item-stock"], items[i]["item-avg-rating"], items[i]["item-num-ratings"], itemIDToPurchase);
        });
      }
    } catch (err) {
      console.error(err);
    }
  }

  /**
   * Handles the purchase of a product.
   * @param {string} name - The name of the product.
   * @param {number} price - The price of the product.
   * @param {string} image - The image URL of the product.
   * @param {string} category - The category of the product.
   * @param {string} description - The description of the product.
   * @param {number} stock - The stock quantity of the product.
   * @param {number} avgRating - The average rating of the product.
   * @param {number} numRatings - The number of ratings for the product.
   * @param {string} itemIDToPurchase - The ID of the item to purchase.
   */
  function buyProduct(name, price, image, category, description, stock, avgRating, numRatings, itemIDToPurchase) {
    id('main-view').classList.add('hidden');
    id('product-details').innerHTML = '';
    id('product-details').classList.remove('productDetails');
    id('product-details').classList.add('hidden');
    id('order-confirmation').classList.remove('hidden');
    getProductDetails(name, price, image, category, description, stock, avgRating, numRatings);
    id('confirm-btn').addEventListener('click', () => {
      switchToPurchaseView(itemIDToPurchase);
    });
  }

  /**
   * Checks if the provided token is valid.
   * @param {string} username - The username.
   * @param {string} token - The token.
   * @returns {boolean} True if the token is valid, false otherwise.
   */
  async function checkValidToken(username, token) {
    try {
      let params = new FormData();
      params.append("username", username);
      params.append("token", token);
      let result = await fetch('/checkToken', {
        method: "POST",
        body: params
      });
      if (!result.ok) {
        return false;
      } else {
        return true;
      }
    } catch (err) {
      console.error(err);
      return false;
    }
  }

  /**
   * Displays the product details.
   * @param {string} name - The name of the product.
   * @param {number} price - The price of the product.
   * @param {string} image - The image URL of the product.
   * @param {string} category - The category of the product.
   * @param {string} description - The description of the product.
   * @param {number} stock - The stock quantity of the product.
   * @param {number} avgRating - The average rating of the product.
   * @param {number} numRatings - The number of ratings for the product.
   */
  function getProductDetails(name, price, image, category, description, stock, avgRating, numRatings) {
    id("side-panel").classList.add("hidden");
    id('product-details').innerHTML = '';
    id('main-view').classList.add('hidden');
    id('product-details').classList.remove('hidden');
    let productCard = gen("div");
    let productImg = gen("img");
    let productName = gen("h2");
    let productCategory = gen("h3");
    let productDescription = gen("p");
    let productPrice = gen("h3");
    let productStock = gen("h3");
    productStock.textContent = "Amount Currently In Stock: " + stock;
    let productRating = gen("h4");
    productRating.textContent = "Rating: " + avgRating;
    productPrice.textContent = "$" + price;
    productImg.src = image;
    productName.textContent = name;
    productCategory.textContent = "Category: " + category;
    productDescription.textContent = "Description: " + description;
    productCard.appendChild(productName);
    productCard.appendChild(productImg);
    productCard.appendChild(productPrice);
    productCard.appendChild(productCategory);
    productCard.appendChild(productRating);
    productCard.appendChild(productStock);
    productCard.appendChild(productDescription);
    id('product-details').appendChild(productCard);
    id('product-details').classList.add('productDetails');
  }
})();