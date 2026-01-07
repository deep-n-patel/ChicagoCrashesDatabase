# COMP 3380 A02 – Final Project Submission

## Group Members
- **Aamir Sangey** – 7960129  
- **Deep Patel** – 7957389  
- **Hoang Huy Truong** – 7960938  

---

## Project Overview

This project is a command-line interface (CLI) application written in Java that interacts with a Chicago car crashes database. The application allows users to create, populate, delete, and query crash-related data stored in an SQL database hosted on Uranium.

The system demonstrates practical use of:
- Java for CLI-based user interaction  
- SQL for data storage and querying  
- A remote database server (Uranium) for persistent data management  

Users can run predefined queries to retrieve meaningful information about car crashes in Chicago, with some queries accepting user-provided parameters.

---

## Compilation and Execution

To compile and run the program, execute the following command from the project directory:

```bash
make run
```

## Database Access (Uranium)

The credentials required to log into the Uranium database server are stored in the auth.cfg file.

- UserID: pateld43

- Password: 7957389

## Database Setup and Management

Once the program starts:

- Type C in the terminal to enter the help/interface mode.

- Entering anything other than C will cause the program to exit.

Inside the interface:

- Type pop to create and populate the Chicago car crashes database on Uranium using the chicago_crashes.sql file.

- Type del to delete the database from Uranium.

- Type q at any time to quit the program.

## Running Queries

- The CLI provides several query commands to retrieve crash-related data from the database.

- Queries that require user input are displayed using angle brackets (<>) as placeholders.

Example

```bash
msa <int N>
```

- To run this query, simply enter:

```bash
msa 5
```

Do not include the angle brackets in the actual command.
