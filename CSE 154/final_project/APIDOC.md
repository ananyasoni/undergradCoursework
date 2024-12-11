# *Stationary Pop Up Shop* API Documentation
*Manages Inventory, Authentication, Purchase History, and User Accounts for a Stationary Ecommerce Platform*

## *Authenticate User*
**Request Format:** */authenticate*

**Request Type:** *POST*

**Returned Data Format**: JSON

**Description:** *Checks whether the user exists and the password is correct. If the user is successfully authenticated, this endpoint returns a token used to verify the user before any transactions*

**Example Request:** */authenticate*
```json
{
    "username": "user123",
    "password": "password123"
}
```

**Example Response:**
```json
{
    "token": "abcd1234efgh5678"
}
```

**Error Handling:**
*400 Bad Request: If username or password is missing.*
*400 Bad Request: If user does not exist or password is incorrect.*
*500 Internal Server Error: If a server-side error occurs.*

## *Search Inventory*
**Request Format:** */search*

**Request Type:** *POST*

**Returned Data Format**: JSON

**Description:** *Retrieves inventory items based on optional filters of price, category, and search term.*

**Example Request:** */search*
```json
{
    "price": "ASC",
    "category": "pens",
    "search": undefined
}

```

**Example Response:**
```json
{
    "inventory": [
        {"item-id": "3", "item-name": "Pilot G2 Gel Pen Black", "inventory": "100", "category": "pens", "price": "1.00", "avg-rating": "4.8", "num-rating": "5", "img": "link3"},
        {"item-id": "6", "item-name": "Pilot G2 Gel Pen Blue", "inventory": "50", "category": "pens", "price": "1.20", "avg-rating": "4.6", "num-rating": "8", "img": "link6"}
    ]
}

```

**Error Handling:**
*500 Internal Server Error: If a server-side error occurs.*

## *Retrieve Purchase History*
**Request Format:** */purchaseHistory*

**Request Type:** *POST*

**Returned Data Format**: JSON

**Description:** *Retrieves purchase history for a user based on username*

**Example Request:** */purchaseHistory*
```json
{
    "username": "user123"
}
```

**Example Response:**
```json
{
    "history": [
        {"purchase-id": "1", "item-id": "3", "item-name": "Pilot G2 Gel Pen Black"},
        {"purchase-id": "2", "item-id": "6", "item-name": "Pilot G2 Gel Pen Blue"}
    ]
}
```

**Error Handling:**
*400 Bad Request: If username is missing or invalid*
*500 Internal Server Error: If a server-side error occurs*

## *Create User Account*
**Request Format:** */addAccount*

**Request Type:** *POST*

**Returned Data Format**: JSON

**Description:** *Creates a new user account with a username and password and returns token associated with user*

**Example Request:** */addAccount*
```JSON
{
    "username": "user123",
    "password": "password123"
}

```
**Example Response:**
```JSON
{
    "token": "4efgh5678"
}
```

**Error Handling:**
*400 Bad Request: If username or password is missing or username already exists.*
*500 Internal Server Error: If a server-side error occurs*

## *Check User Authentication Token*
**Request Format:** */checkToken*

**Request Type:** *POST*

**Returned Data Format**: text

**Description:** *Creates a new user account with a username and password and returns token associated with user*

**Example Request:** */addAccount*
```JSON
{
    "username": "user123",
    "token": "45gh5678"
}


```
**Example Response:**
```Plain Text
success
```

**Error Handling:**
*400 Bad Request: If username or token is missing or invalid.*
*500 Internal Server Error: If a server-side error occurs.*

## *Finalize Purchase*
**Request Format:** */finalizePurchase*

**Request Type:** *POST*

**Returned Data Format**: JSON

**Description:** *Finalizes a purchase for a user by reducing item stock and logging the purchase.*

**Example Request:** */addAccount*
```JSON
{
    "itemID": "3",
    "username": "user123"
}


```
**Example Response:**
```JSON
{
    "orderID": 1
}
```

**Error Handling:**
*500 Internal Server Error: If a server-side error occurs*