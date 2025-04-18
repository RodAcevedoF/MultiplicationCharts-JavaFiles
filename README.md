# Multiplication Chart Generator

This is a simple Java console application developed for academic purposes. It allows the user to generate and read multiplication tables (from 1 to 10) and save them as `.txt` files.

## Features

- Generate a multiplication chart (1 to 10) for a given base number.
- Save the chart as a `.txt` file.
- Read a full chart from a file.
- Read a specific line from a chart.

## Project Structure

The project is organized using basic packages:

- `controller`: Manages operations and communication between model and services.
- `model`: Contains the logic for generating multiplication tables.
- `service`: Manages file reading/writing.
- `ui`: Handles user interaction (console interface).
- `exceptions`: Custom exceptions used in the app.
- `Main`: Entry point of the application.

## How to Run

1. Clone or download the project.
2. Compile the Java source files using your favorite IDE or via terminal.
3. Run the `Main` class to start the application.

```bash
javac -d out src/**/*.java
java -cp out org.example.Main
```

## Example

Generating a multiplication chart for number 5:

```
5 * 1 = 5
5 * 2 = 10
5 * 3 = 15
...
5 * 10 = 50
```

This will be saved to a file like `chart_5.txt` in the default directory.

## Requirements

- Java 11 or higher

---

*Developed as part of a tech school assignment.*
