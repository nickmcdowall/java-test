# Java Exercise

Note that `Java 11` was used to complete this exercise.

## Assumptions
  1. Using `double`s are sufficient for this exercise  - would normally use `BigDecimal` for real world monetary related implementations due to risk of double rounding errors.
  2. No need to remove items from basket
  3. No need for a running total
  4. No need for an itemised list of items along with total cost on checkout
  5. No interface required for adding additional promotions via command line
  
## Build/Test

Use Gradle wrapper to test and build the application:

```
./gradlew clean build
```

### Run

To interact with the application you can:
* Run the `main` method in `CommandPromptInterface.java` via your IDE.
* Or use the Gradle wrapper to launch the prompt:

```
./gradlew run
```

#### Inputs

Available options are `exit`, `stock`, `add <count> <item>`, `checkout`, `exit`

Examples:
* `stock` # List available stock items
* `add 1 Bread`
* `add 2 Milk`
* `add 1 Apple add 1 Soup` # Add commands can be chained
* `checkout` # Defaults to today
* `checkout today +1` # Price if bought on a future date
* `checkout today -5` # Price if bought on a past date
* `exit`