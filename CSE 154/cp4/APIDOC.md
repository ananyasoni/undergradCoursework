# *To Do List* API Documentation
*Manges User's Current To Do List*

## *Add Task*
**Request Format:** */add_task*

**Request Type:** *POST*

**Returned Data Format**: JSON

**Description:** *Allows a user to add a task to their to do list and returns all items currently in the To Do List after adding the iterm*

**Example Request:** *Post Paramters of todo-item: "code", minutes: 60*

**Example Response:**
```json
{
  "todos": [
    {"todo-input":"code","minutes":"60","completed":false}
  ]
}

```

**Error Handling:**
*Possible 500 errors (all plain text):If something goes wrong on the server, returns error with `error` or `file not found`*
*Possible 400 errors (all plain text):If something user doesn't provide all parameters todo-item and minutes*

## *Sort Tasks*
**Request Format:** */sort_tasks*

**Request Type:** *POST*

**Returned Data Format**: JSON

**Description:** *sorts tasks in asceding order by time*

**Example Request:** *POST Paramters none (request to /sort_tasks)*

**Example Response:**

```json
{
  "todos": [
    {
      "todo-input": "Write Email",
      "minutes": "20",
      "completed": false
    },
    {
      "todo-input": "Make Pasta",
      "minutes": "30",
      "completed": false
    }
  ]
}
```

**Error Handling:**
*Possible 500 errors (all plain text):If something goes wrong on the server, returns error with `error` or `file not found`*


## *Clear Tasks*
**Request Format:** */clear_tasks*

**Request Type:** *POST*

**Returned Data Format**: Plain Text

**Description:** *Clears To-Do List*

**Example Request:** *Post Parameters none (request to /clear_tasks)*

**Example Response:**
```plaintext
successfully cleared tasks
```

**Error Handling:**
*Possible 500 errors (all plain text):If something goes wrong on the server, returns error with `error` or `file not found`*