# Malabe Tuk-Tuk & Three-Wheeler Spares Depot

This is my CM1601 Programming Fundamentals coursework.

It is a JavaFX app for a spare parts shop. You can clean dirty data, manage inventory, search parts, pick random dealers, use a cart with discounts, and keep an audit log.

### Requirements

- JDK 26 (OpenJDK 26.0.1)
- JavaFX 21.0.6
- Maven
- JUnit 5 for tests

## How to Run

1. Install JDK 26 and set it in IntelliJ.
2. Open the **JavaFxCW** folder (the one with `pom.xml`).
3. Wait for Maven to load.
4. Choose the **Launcher** run config.
5. Main class should be `com.example.javafxcw.Launcher`.
6. Working directory should be the project folder.
7. If JavaFX does not start, add this in VM options:

```cmd
--module-path "$PROJECT_DIR$/lib" --add-modules javafx.controls,javafx.fxml
```

8. Click Run.

You can also run this in the terminal from the project folder:

```cmd
mvnw.cmd clean javafx:run
```

## How to Run Tests

My tests are in `src/test/java/com/example/javafxcw/`.

In IntelliJ, right click that package and run the tests.

Or use:

```cmd
mvnw.cmd test
```

### Git Repository

https://github.com/esandiliyanapathirana/JavaFxCW-Coursework

## Data Files

- `inventory_legacy.txt` - dirty inventory data
- `dealers_legacy.txt` - dirty dealer data
- `inventory_cleaned.txt` - cleaned inventory used by the app
- `dealers_cleaned.txt` - cleaned dealers
- `audit_log.txt` - created when the app saves actions (add, update, delete, checkout)
- `images/` - pictures for parts

### Cleaned inventory format

`code|name|brand|price|quantity|category|date|image|threshold`

### Cleaned dealer format

`id|name|phone|location`

## Key Assumptions

### Low stock threshold

The old inventory file has no threshold, so when I clean the data I set the default threshold to **10**.

A part is low stock when quantity is less than the threshold.

In Manage Parts I can change the threshold for each part.

### Dirty data and delimiters

Legacy lines can use `,` or `;` or `|`, so my cleaner splits on all of them.

After cleaning I always save with `|` so the file is neat.

### Dates with commas

Some dates look like `Oct 15, 2023`. Because of the comma, the split can break that date.

If the date is bad, I save it as **Undated**. That way the cleaner does not crash.

### Missing values

- missing brand -> `Unknown`
- missing image -> `No Image`
- missing dealer phone -> `No Contact`
- bad or empty date -> `Undated`
- bad price / quantity -> set to 0 so one bad line does not stop everything

### Sorting

I used my own **bubble sort**.

- inventory: sort by category, then by code
- dealers: pick 4 different dealers, then sort by location

I did not use `Collections.sort` for these.

### Cart discounts

- if quantity is 3 or more, that line gets **5%** off
- if the cart has both Engine and Electrical parts, the total gets another **10%** off

### Checkout and audit

When I checkout, stock goes down in `inventory_cleaned.txt`, and the action is added to `audit_log.txt`.

The audit file only appends. It does not overwrite old lines.

### Other notes

Category checks are case insensitive, so Engine and engine are treated the same.

The app should be run from the project root, so it can find the text files and the images folder.
